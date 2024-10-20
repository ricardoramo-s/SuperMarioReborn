package uni.ldts.elements.tile;

import uni.ldts.Sprite;

public abstract class InteractableTile extends Tile {
    private final Sprite used;
    private boolean isJumping = false;
    private boolean isActing = false;
    private double originalX, originalY;
    private int jumpAnimationFrames = 15; // Adjust this as needed
    private int currentJumpFrame = 0;
    private boolean alreadyInteracted = false;

    /**
     * Base building block of a Map, managed by TileManager
     *
     * @param row         x coordinate
     * @param column      y coordinate
     * @param passable
     * @param jumpThrough
     */
    public InteractableTile(int row, int column, Sprite sprite, Sprite usedSprite, boolean passable, boolean jumpThrough) {
        super(row, column, sprite, passable, jumpThrough);

        this.used = usedSprite;
        this.originalX = row * 16;
        this.originalY = column * 16;
    }

    /**
     * Action is triggered after the jumping animation.
     */
    abstract public void action();
    protected void jump() {
        isJumping = true;
    }
    protected void setInteractedFlag(boolean b) { alreadyInteracted = b; }
    protected boolean wasInteracted() { return alreadyInteracted; }
    protected void startAction() {
        this.isActing = true;
    }
    protected void endAction() {
        this.isActing = false;
    }

    @Override
    public void tick() {
        if (isJumping) {
            if (currentJumpFrame < jumpAnimationFrames / 2) {
                // Jumping up
                getBoundingBox().setMinY(getBoundingBox().getMinY() - 0.7D);
            } else {
                // Falling down
                getBoundingBox().setMinY(getBoundingBox().getMinY() + 0.7D);
            }

            currentJumpFrame++;

            if (currentJumpFrame >= jumpAnimationFrames) {
                isJumping = false;
                getBoundingBox().setMinX(originalX);
                getBoundingBox().setMinY(originalY);
                currentJumpFrame = 0;
                if (used != null) this.setSprite(used);

                startAction();
                action();
            }
        }
        if (isActing) {
            action();
        }
    }
}
