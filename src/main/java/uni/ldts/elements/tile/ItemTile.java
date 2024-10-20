package uni.ldts.elements.tile;

import uni.ldts.Sprite;
import uni.ldts.elements.entity.Entity;
import uni.ldts.elements.entity.item.Item;
import uni.ldts.elements.entity.player.Player;

public class ItemTile extends InteractableTile {
    private Item item;
    private int spawnAnimationFrames = 64; // Adjust this as needed
    private int currentSpawnFrame = 0;

    /**
     * A block that holds an Item
     *
     * @param row         x coordinate
     * @param column      y coordinate
     * @param passable
     * @param jumpThrough
     */
    public ItemTile(int row, int column, Item item, Sprite sprite, Sprite used, boolean passable, boolean jumpThrough) {
        super(row, column, sprite, used, passable, jumpThrough);

        this.item = item;
        if (item != null) { // null is coin
            item.frozen(true);
            item.setX(this.getX());
            item.setY(this.getY());
        }
    }

    @Override
    public void action() {
        if (item == null) {
            endAction();
            return;
        }
        else item.setVisible(true);

        if (currentSpawnFrame < spawnAnimationFrames) {
            // Jumping up
            item.getBoundingBox().setMinY(item.getBoundingBox().getMinY() - .25D);
            currentSpawnFrame++;
        }
        else {
            item.frozen(false);
            endAction();
        }
    }

    @Override
    public void trigger(Entity entity) {
        if (entity.getClass() != Player.class) return;

        Player p = (Player) entity;
        if (wasInteracted()) return;
        if (item == null) p.addCoin();

        setInteractedFlag(true);
        jump();
    }
}
