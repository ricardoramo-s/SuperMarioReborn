package uni.ldts.physics;

import uni.ldts.elements.entity.Entity;
import uni.ldts.elements.entity.player.Player;
import uni.ldts.elements.tile.CoinTile;
import uni.ldts.elements.tile.Tile;
import uni.ldts.elements.tile.WaterTile;
import uni.ldts.engine.GameManager;
import uni.ldts.map.Map;

import java.lang.Math;

public class PhysicsEngine {

    private final GameManager manager;
    private long lastUpdate = 0;

    /*
    the delta factor is multiplied by dt to
    achieve the exact game speed we desire
     */
    private static final double DELTA_FACTOR = 0.0078D;
    private static final double GRAVITY = 9.8D;
    private static final double TERMINAL_VELOCITY = 35.0D;
    private static final double OFFSET = 0.025; // represents the distance between a body and their collision sensor

    public PhysicsEngine(GameManager manager) { this.manager = manager; }

    /**
     * Update the physics of the game
     * (update every single entity)
     */
    public void update() {
        if (System.currentTimeMillis() - lastUpdate > 1000) {
            lastUpdate = System.currentTimeMillis();
            return;
        }

        double now = System.currentTimeMillis();
        double dt = now - lastUpdate;
        dt *= DELTA_FACTOR;
        // calculate dt ^

        Map map = this.manager.getMap();
        for (Entity e : map.getEntityManager().getEntities()) if (!e.isFrozen()) this.updateEntity(e,dt);
        if (map.getPlayer() != null && !map.getPlayer().isFrozen()) this.updateEntity(map.getPlayer(), dt);
        this.lastUpdate = System.currentTimeMillis();
    }

    /**
     * Update the physics of an entity
     * @param e entity
     * @param dt change in time
     */
    private void updateEntity(Entity e, double dt) {
        updateCollisions(e);

        // update vel. based on acceleration
        e.setVelX(e.getVelX() + e.getAccX()*dt);
        e.setVelY(e.getVelY() + e.getAccY()*dt);

        // "apply" gravity to y-component
        if (!e.isDownCollision()) {
            e.setVelY(Math.min(e.getVelY() + GRAVITY*dt, TERMINAL_VELOCITY));
        }

        // update pos. based on velocity
        e.setX(e.getX() + e.getVelX()*dt);
        e.setY(e.getY() + e.getVelY()*dt);

        if (e.getClass() == Player.class) {
            // check bounds
            if (e.getX() < 0) {
                e.setX(0);
                e.setVelX(0);
            }
            if (e.getY() < 0) {
                e.setY(0);
                e.setVelY(0);
            }

            AABB bBox = e.getBoundingBox();
            if (bBox.getMaxX() > manager.getMap().getMapWidth()) {
                e.setX(manager.getMap().getMapWidth() - bBox.getSizeX());
                e.setVelX(0);
            }
            if (bBox.getMaxY() > manager.getMap().getMapHeight()) {
                e.setY(manager.getMap().getMapHeight() - bBox.getSizeY());
                e.setVelY(0);
                ((Player) e).killPlayer();
            }
        }
    }

    private void updateCollisions(Entity e) {
        e.setUpCollision(hasUpTileCollision(e));
        e.setDownCollision(hasDownTileCollision(e));
        e.setRightCollision(hasRightTileCollision(e));
        e.setLeftCollision(hasLeftTileCollision(e));

        hasUpEntityCollision(e);
        hasDownEntityCollision(e);
        hasLeftEntityCollision(e);
        hasRightEntityCollision(e);
    }

    /**
     * Checks for a tile collision below the entity
     * @param entity
     * @return
     */
    private boolean hasDownTileCollision(Entity entity) {
        int minX = (int) (entity.getBoundingBox().getMinX() + 2) / 16;
        int maxX = (int) (entity.getBoundingBox().getMaxX() - 2) / 16;
        int y = (int) (entity.getBoundingBox().getMaxY() + OFFSET) / 16;

        for (int x = minX; x <= maxX; x++) {
            Tile tile = manager.getMap().getTileManager().getTile(x, y);
            if (tile == null) continue;

            if (tile.getClass() == CoinTile.class || tile.getClass() == WaterTile.class) tile.trigger(entity);
            else if (!tile.isPassable()) {
                // jump through tiles logic
                if (tile.isJumpThrough() && entity.getLastY() + entity.getBoundingBox().getSizeY() > tile.getY()) {
                    return false;
                }

                // stop movement
                entity.setY(tile.getBoundingBox().getMinY() - entity.getBoundingBox().getSizeY());
                entity.setVelY(Math.min(entity.getVelY(), 0));
                return true;
            }
        }

        return false;
    }

    /**
     * Checks for a tile collision above the entity, triggering it
     * @param entity
     * @return
     */
    private boolean hasUpTileCollision(Entity entity) {
        int minX = (int) (entity.getBoundingBox().getMinX() + 2) / 16;
        int maxX = (int) (entity.getBoundingBox().getMaxX() - 2) / 16;
        int y = (int) (entity.getBoundingBox().getMinY() - OFFSET) / 16;

        for (int x = minX; x <= maxX; x++) {
            Tile tile = manager.getMap().getTileManager().getTile(x, y);
            if (tile == null) continue;

            if (tile.getClass() == CoinTile.class) tile.trigger(entity);
            else if (!tile.isPassable() && !tile.isJumpThrough()) {
                tile.trigger(entity);

                entity.setY(tile.getBoundingBox().getMaxY());
                entity.setVelY(Math.max(entity.getVelY(), 1)); // 1 to prevent clipping
                return true;
            }
        }

        return false;
    }

    /**
     * Checks for a tile collision to the left of the entity
     * @param entity
     * @return
     */
    private boolean hasLeftTileCollision(Entity entity) {
        int minY = (int) (entity.getBoundingBox().getMinY() + 2) / 16;
        int maxY = (int) (entity.getBoundingBox().getMaxY() - 2) / 16;
        int x = (int) (entity.getBoundingBox().getMinX() - OFFSET) / 16;

        for (int y = minY; y <= maxY; y++) {
            Tile tile = manager.getMap().getTileManager().getTile(x, y);
            if (tile == null) continue;

            if (tile.getClass() == CoinTile.class) tile.trigger(entity);
            else if (!tile.isPassable() && !tile.isJumpThrough()) {
                entity.setVelX(Math.max(entity.getVelX(), 0)); // stops momentum
                entity.setX(tile.getBoundingBox().getMaxX());
                return true;
            }
        }

        return false;
    }

    /**
     * Checks for a tile collision to the right of the entity
     * @param entity
     * @return
     */
    private boolean hasRightTileCollision(Entity entity) {
        int minY = (int) (entity.getBoundingBox().getMinY() + 0.5) / 16;
        int maxY = (int) (entity.getBoundingBox().getMaxY() - 0.5) / 16;
        int x = (int) (entity.getBoundingBox().getMaxX() + OFFSET) / 16;

        for (int y = minY; y <= maxY; y++) {
            Tile tile = manager.getMap().getTileManager().getTile(x, y);
            if (tile == null) continue;

            if (tile.getClass() == CoinTile.class) tile.trigger(entity);
            else if (!tile.isPassable() && !tile.isJumpThrough()) {
                entity.setX(tile.getBoundingBox().getMinX() - entity.getBoundingBox().getSizeX());
                entity.setVelX(Math.min(entity.getVelX(), 0));  // stops momentum
                return true;
            }
        }

        return false;
    }

    /**
     * Checks for an entity up.
     * @param entity
     * @return
     */
    private boolean hasUpEntityCollision(Entity entity) {
        double minX = entity.getBoundingBox().getMinX() + 2;
        double maxX = entity.getBoundingBox().getMaxX() - 2;
        double y = entity.getBoundingBox().getMinY() - OFFSET;

        boolean isMinInside, isMaxInside, bothOutside, yInside;

        for (Entity e : manager.getMap().getEntityManager().getEntities()) {
            if (e.isFrozen()) {
                continue;
            }

            yInside = y < e.getBoundingBox().getMaxY() && y > e.getBoundingBox().getMinY(); // matches y value of entity
            isMinInside = minX > e.getBoundingBox().getMinX() && minX < e.getBoundingBox().getMaxX(); // min intersects
            isMaxInside = maxX > e.getBoundingBox().getMinX() && maxX < e.getBoundingBox().getMaxX(); // max intersects
            bothOutside = (minX < e.getBoundingBox().getMinX() && maxX > e.getBoundingBox().getMaxX()) || (maxX < e.getBoundingBox().getMinX() && minX > e.getBoundingBox().getMaxX()); // both outside x-bound

            if (yInside && (isMinInside || isMaxInside || bothOutside)) {
                e.onContact(entity, ContactDirection.UP);
                return true;
            }
        }

        return false;
    }

    /**
     * Checks for an entity down.
     * @param entity
     * @return
     */
    private boolean hasDownEntityCollision(Entity entity) {
        double minX = entity.getBoundingBox().getMinX() + 2;
        double maxX = entity.getBoundingBox().getMaxX() - 2;
        double y = entity.getBoundingBox().getMaxY() + OFFSET;

        for (Entity o : manager.getMap().getEntityManager().getEntities()) {
            if (o.isFrozen()) {
                continue;
            }
            if ((y < o.getBoundingBox().getMaxY() && y > o.getBoundingBox().getMinY())
                    && ((minX > o.getBoundingBox().getMinX() && minX < o.getBoundingBox().getMaxX()) // min inside
                    || (maxX > o.getBoundingBox().getMinX() && maxX < o.getBoundingBox().getMaxX())  // max inside
                    || (minX < o.getBoundingBox().getMinX() && maxX > o.getBoundingBox().getMaxX()))) { // both outside
                o.onContact(entity, ContactDirection.DOWN);
                return true;
            }
        }

        return false;
    }

    /**
     * Checks for an entity left.
     * @param entity
     * @return
     */
    private boolean hasLeftEntityCollision(Entity entity) {
        double minY = entity.getBoundingBox().getMinY() + 0.2;
        double maxY = entity.getBoundingBox().getMaxY() - 0.2;
        double x = entity.getBoundingBox().getMinX() - OFFSET * 2;

        for (Entity o : manager.getMap().getEntityManager().getEntities()) {
            if (o.isFrozen()) continue;

            if ((x > o.getBoundingBox().getMinX() && x < o.getBoundingBox().getMaxX())
                    && ((minY > o.getBoundingBox().getMinY() && minY < o.getBoundingBox().getMaxY()) // min inside bound
                    || (maxY > o.getBoundingBox().getMinY() && maxY < o.getBoundingBox().getMaxY()) // max inside bound
                    || (minY < o.getBoundingBox().getMinY() && maxY > o.getBoundingBox().getMaxY()))) { // both outside
                o.onContact(entity, ContactDirection.LEFT);
                return true;
            }
        }

        return false;
    }

    /**
     * Checks for an entity right.
     * @param entity
     * @return
     */
    private boolean hasRightEntityCollision(Entity entity) {
        double minY = entity.getBoundingBox().getMinY() + 0.2;
        double maxY = entity.getBoundingBox().getMaxY() - 0.2;
        double x = entity.getBoundingBox().getMaxX() + OFFSET * 2;

        for (Entity o : manager.getMap().getEntityManager().getEntities()) {
            if (o.isFrozen()) continue;

            if ((x > o.getBoundingBox().getMinX() && x < o.getBoundingBox().getMaxX())
                    && ((minY > o.getBoundingBox().getMinY() && minY < o.getBoundingBox().getMaxY()) // min inside bound
                    || (maxY > o.getBoundingBox().getMinY() && maxY < o.getBoundingBox().getMaxY()) // max inside bound
                    || (minY < o.getBoundingBox().getMinY() && maxY > o.getBoundingBox().getMaxY()))) { // both outside
                o.onContact(entity, ContactDirection.RIGHT);
                return true;
            }
        }

        return false;
    }
}
