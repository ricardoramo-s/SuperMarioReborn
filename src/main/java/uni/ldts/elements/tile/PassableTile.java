package uni.ldts.elements.tile;

import uni.ldts.Sprite;
import uni.ldts.elements.entity.Entity;
import uni.ldts.elements.entity.player.Player;

public class PassableTile extends Tile {
    public PassableTile(int row, int column, Sprite sprite) {
        super(row, column, sprite, true, true);
    }

    @Override
    public void tick() {

    }

    @Override
    public void trigger(Entity entity) {

    }
}
