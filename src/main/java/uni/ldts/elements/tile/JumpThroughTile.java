package uni.ldts.elements.tile;

import uni.ldts.Sprite;
import uni.ldts.elements.entity.Entity;

public class JumpThroughTile extends Tile {
    /**
     * Base building block of a Map, managed by TileManager
     *
     * @param row         x coordinate
     * @param column      y coordinate
     * @param sprite
     */
    public JumpThroughTile(int row, int column, Sprite sprite) {
        super(row, column, sprite, false, true);
    }

    @Override
    public void tick() {

    }

    @Override
    public void trigger(Entity entity) {

    }
}
