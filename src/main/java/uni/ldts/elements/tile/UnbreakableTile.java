package uni.ldts.elements.tile;

import uni.ldts.Sprite;
import uni.ldts.elements.entity.Entity;
import uni.ldts.elements.entity.player.Player;

public class UnbreakableTile extends Tile {
    /**
     * A tile that is unbreakable.
     *
     * @param row         Row.
     * @param column      Column.
     * @param texture     Texture.
     * @param passable    True if passable.
     * @param jumpThrough True if jump through.
     */
    public UnbreakableTile(int row, int column, Sprite sprite, boolean passable, boolean jumpThrough) {
        super(row, column, sprite, passable, jumpThrough);
    }

    @Override
    public void trigger(Entity entity) {

    }

    @Override
    public void tick() {

    }
}
