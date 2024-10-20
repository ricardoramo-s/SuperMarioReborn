package uni.ldts.elements;

import uni.ldts.physics.AABB;

import java.awt.*;

/**
 * Provides a solid baseline for elements in a 2D
 * game such as entities, objects, characters, etc
 */
public abstract class Element {

    protected boolean visible;
    private double lastX, lastY;
    protected AABB boundingBox;

    public Element(AABB boundingBox) {
        this.boundingBox = boundingBox;
        this.lastX = boundingBox.getMinX();
        this.lastY = boundingBox.getMinY();
        this.visible = true;
    }

    public AABB getBoundingBox() { return this.boundingBox; }
    public void setBoundingBox(AABB boundingBox) { this.boundingBox = boundingBox; }
    public double getX() { return this.boundingBox.getMinX(); }
    public double getY() { return this.boundingBox.getMinY(); }

    public double getLastX() {
        return lastX;
    }
    public double getLastY() {
        return lastY;
    }

    public void setX(double x) {
        this.lastX = boundingBox.getMinX();
        this.boundingBox.setMinX(x);
    }
    public void setY(double y) {
        this.lastY = boundingBox.getMinY();
        this.boundingBox.setMinY(y);
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    // to be implemented by subclasses
    public abstract void draw(Graphics2D g);
    public abstract void draw(int transformX, int transformY, Graphics2D g);
    public abstract void tick();


}
