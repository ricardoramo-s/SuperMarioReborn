package uni.ldts.elements.entity;

import uni.ldts.physics.AABB;
import uni.ldts.Sprite;
import uni.ldts.elements.Element;
import uni.ldts.elements.entity.player.Player;
import uni.ldts.physics.ContactDirection;
import uni.ldts.map.EntityManager;

import java.awt.*;

public abstract class Entity extends Element {

    /**
     * The manager who manages this Tile
     */
    protected EntityManager manager;
    protected Sprite sprite;

    // manipulated by physics engine
    private double vx, vy;
    private double ax, ay;
    private boolean frozen;
    private boolean downCollision, upCollision, leftCollision, rightCollision;

    /**
     * Create an entity, such as a character or an enemy
     * @param sprite entity sprite
     * @param boundingBox entity bbox
     */
    public Entity(Sprite sprite, AABB boundingBox) {
        this(sprite, boundingBox, 0F, 9.8F);
        // default yAcc is earth's accel. ^
        frozen = true;
    }

    public Entity(Sprite sprite, AABB boundingBox, double ax, double ay) {
        super(boundingBox);
        this.sprite = sprite;
        this.ax = ax;
        this.ay = ay;
    }

    @Override
    public void draw(Graphics2D g) {
        // let the sprite handle it
        this.sprite.draw((int)getX(), (int)getY(), g);
    }

    @Override
    public void draw(int x, int y, Graphics2D g) {
        // let the sprite handle it
        this.sprite.draw((int) getX() + x, (int) getY() + y, g);
    }

    public double getVelX() { return this.vx; }
    public double getVelY() { return this.vy; }
    public double getAccX() { return this.ax; }
    public double getAccY() { return this.ay; }

    public void setVelX(double vx) { this.vx = vx; }
    public void setVelY(double vy) {
        this.vy = vy;
    }
    public void setAccX(double ax) { this.ax = ax; }
    public void setAccY(double ay) { this.ay = ay; }


    public Sprite getSprite() {
        return sprite;
    }

    public boolean isOnGround() {
        return isDownCollision();
    }
    public boolean isDownCollision() {
        return downCollision;
    }
    public void setDownCollision(boolean downCollision) {
        this.downCollision = downCollision;
    }

    public boolean isUpCollision() {
        return upCollision;
    }
    public void setUpCollision(boolean upCollision) {
        this.upCollision = upCollision;
    }

    public boolean isLeftCollision() {
        return leftCollision;
    }
    public void setLeftCollision(boolean leftCollision) {
        this.leftCollision = leftCollision;
    }

    public boolean isRightCollision() {
        return rightCollision;
    }
    public void setRightCollision(boolean rightCollision) {
        this.rightCollision = rightCollision;
    }

    public boolean isFrozen() { return frozen; }
    public void frozen(boolean b) { this.frozen = b; }

    public void setManager(EntityManager manager) { this.manager = manager; }
    public void kill() { this.manager.killEntity(this); }

    abstract public void onContact(Entity entity, ContactDirection direction);
}
