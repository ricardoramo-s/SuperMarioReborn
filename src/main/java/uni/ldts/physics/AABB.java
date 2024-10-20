package uni.ldts.physics;

/**
 * AABB stands for Axis-Aligned Bounding Box and it is
 * a rectangle that's used to aid in collision detection
 */
public class AABB {

    // using double instead of int to make
    // the physics engine more accurate
    private double minX;
    private double minY;

    private final double sizeX;
    private final double sizeY;

    public AABB(double minX, double minY, double sizeX, double sizeY) {
        this.minX = minX;
        this.minY = minY;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    public double getMinX() { return this.minX; }
    public double getMinY() { return this.minY; }

    public void setMinX(double x) { this.minX = x; }
    public void setMinY(double y) { this.minY = y; }

    public double getSizeX() { return this.sizeX; }
    public double getSizeY() { return this.sizeY; }

    public double getMaxX() { return this.minX + this.sizeX; }
    public double getMaxY() { return this.minY + this.sizeY; }

    public double getWidthMiddlePoint() { return this.minX + this.sizeX / 2.0; }
    public double getHeightMiddlePoint() { return this.minY + this.sizeY / 2.0; }


    // check if two AABBs overlap (collision)
    public boolean overlap(AABB other) {
        boolean overlapX = other.getMaxX() <= getMinX() || other.getMaxX() <= getMinX();
        boolean overlapY = other.getMinY() <= getMaxY() || other.getMaxY() <= getMinY();
        return overlapX && overlapY;
    }
}
