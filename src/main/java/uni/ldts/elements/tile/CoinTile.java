package uni.ldts.elements.tile;

import uni.ldts.Sprite;
import uni.ldts.elements.entity.Entity;
import uni.ldts.elements.entity.player.Player;

public class CoinTile extends Tile {
    /**
     * A coin that exists in the world.
     *
     * @param row         x coordinate
     * @param column      y coordinate
     * @param sprite coin sprite
     */
    public CoinTile(int row, int column, Sprite sprite) {
        super(row, column, sprite, true, true);
    }

    @Override
    public void tick() {

    }

    @Override
    public void trigger(Entity entity) {
        if (entity.getClass() != Player.class) return;

        Player p = (Player) entity;
        p.addCoin();
        destroy();
    }
}
