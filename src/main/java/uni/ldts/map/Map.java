package uni.ldts.map;

import uni.ldts.Texture;
import uni.ldts.elements.entity.Entity;
import uni.ldts.elements.entity.player.Player;
import uni.ldts.elements.tile.Tile;
import uni.ldts.ui.LevelOverlay;

import java.awt.*;
import java.util.List;

public class Map {
    private final Texture background;
    private final TileManager tileManager; // manages tiles
    private final EntityManager entityManager; // manages entities

    private final int mapWidth, mapHeight;
    private int timer = 400; // game timer
    private boolean isTimerStopped;
    private long stoppedTimeStamp;
    private long lastTimerTick;

    private final Player player;
    private final Camera camera;

    private final LevelOverlay levelOverlay;

    /**
     * A map can be used to represent any game state, but it's primarily focused on levels
     * (you can import a map from a file through the MapFactory class, check docs)
     */
    public Map(int width, int height, Player ch, Texture bg, List<Entity> e, List<Tile> t) {
        this.player = ch;
        this.camera = new Camera(this);
        this.levelOverlay = new LevelOverlay(this);
        this.background = bg;
        this.tileManager = new TileManager(width, height, t);
        this.entityManager = new EntityManager(e);
        this.mapWidth = width;
        this.mapHeight = height;

        this.player.setManager(entityManager);
        freezeEntities();
    }

    public void init() {
        initTimer();
        unfreezeEntities();
    }

    public void disable() {
        pauseTimer();
        freezeEntities();
    }

    private void freezeEntities() {
        entityManager.freezeEntities();
        player.frozen(true);
    }

    private void unfreezeEntities() {
        entityManager.unfreezeEntities();
        player.frozen(false);
    }

    private void initTimer() {
        lastTimerTick = System.currentTimeMillis();
        isTimerStopped = false;
    }

    public void pauseTimer() {
        if (isTimerStopped) return;

        isTimerStopped = true;
        stoppedTimeStamp = System.currentTimeMillis();
    }

    public void resumeTimer() {
        if (!isTimerStopped) return;

        isTimerStopped = false;
        lastTimerTick += System.currentTimeMillis() - stoppedTimeStamp;
    }

    public void updateTimer() {
        if (isTimerStopped) return;

        long now = System.currentTimeMillis();
        if (now - lastTimerTick >= 1000) {
            timer--;
            lastTimerTick = now;
        }
    }

    public int getTimer() { return timer; }
    public Player getPlayer() { return this.player; }

    public EntityManager getEntityManager() { return this.entityManager; }
    public TileManager getTileManager() { return this.tileManager; }

    public int getMapWidth() {
        return mapWidth;
    }
    public int getMapHeight() {
        return mapHeight;
    }

    public void draw(Graphics2D g) {
        // background color
        if (this.background != null) this.background.draw(0, 0, g);

        // background (passable) elements
        this.tileManager.drawBackground((int) -camera.getX(), (int) -camera.getY(), g);

        // entities
        for (Entity e : this.entityManager.getEntities())
            if (e.isVisible())
                e.draw((int) -camera.getX(), (int) -camera.getY(), g);

        // foreground (not passable) elements
        this.tileManager.drawForeground((int) -camera.getX(), (int) -camera.getY(), g);

        // player
        if (this.player != null && this.player.getSprite() != null)
            this.player.draw((int) -camera.getX(), (int) -camera.getY(), g);

        // overlay
        this.levelOverlay.draw(0, 0, g);
    }

    public void tick() {
        if (player != null) {
            player.tick();
            camera.update();
            updateTimer();
        }

        entityManager.tick();
        tileManager.tick();
    }
}
