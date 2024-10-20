package uni.ldts.elements.tile;

import uni.ldts.Sprite;
import uni.ldts.elements.entity.Entity;
import uni.ldts.elements.entity.player.Player;

public class WaterTile extends Tile {
    /**
     * Base building block of a Map, managed by TileManager
     *
     * @param row         x coordinate
     * @param column      y coordinate
     * @param sprite
     */
    public WaterTile(int row, int column, Sprite sprite) {
        super(row, column, sprite, false, false);
    }

    @Override
    public void tick() {

    }

    @Override
    public void trigger(Entity entity) {
        if (entity.getClass() != Player.class) return;

        Player p = (Player) entity;
        p.killPlayer();
    }
}
