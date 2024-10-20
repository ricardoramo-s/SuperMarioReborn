package uni.ldts.elements.tile;

import uni.ldts.Sprite;
import uni.ldts.elements.entity.Entity;
import uni.ldts.physics.AABB;
import uni.ldts.elements.Element;
import uni.ldts.elements.entity.player.Player;
import uni.ldts.map.TileManager;

import java.awt.*;

public abstract class Tile extends Element {
    /**
     * The manager who manages this Tile
     */
    protected TileManager manager = null;

    private Sprite sprite;

    /*
    if an object is passable, then it's bounding box
    will be ignored, and players can pass through it
     */
    protected boolean passable;

    /*
    an object is considered jump-through if a player
    can jump right through it without hitting their head
     */
    protected boolean jumpThrough;


    /**
     * Base building block of a Map, managed by TileManager
     * @param row x coordinate
     * @param column y coordinate
     */
    public Tile(int row, int column, Sprite sprite, boolean passable, boolean jumpThrough) {
        super(new AABB(row * 16, column * 16, 16, 16));
        this.sprite = sprite;
        this.passable = passable;
        this.jumpThrough = jumpThrough;
    }

    protected void setSprite(Sprite sprite) { this.sprite = sprite; }

    @Override
    public void draw(Graphics2D g) {
        if (visible) {
            this.sprite.draw((int)getX(), (int)getY(), g);
        }
    }

    @Override
    public void draw(int x, int y, Graphics2D g) {
        // let the sprite handle it
        this.sprite.draw((int) getX() + x, (int)getY() + y, g);
    }

    public boolean isJumpThrough() { return this.jumpThrough; }
    public boolean isPassable() { return this.passable; }

    public void setManager(TileManager tileManager) { this.manager = tileManager; }
    public int getRow() { return (int) boundingBox.getMinX() / 16; }
    public int getColumn() { return (int) boundingBox.getMinY() / 16; }
    protected void destroy() {
        this.manager.destroyTile(this);
    }
    abstract public void trigger(Entity entity);
}
