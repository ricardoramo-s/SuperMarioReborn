package uni.ldts.elements.tile;

import uni.ldts.Sprite;
import uni.ldts.elements.entity.Entity;
import uni.ldts.elements.entity.player.Player;
import uni.ldts.elements.entity.player.PlayerPowerUpState;

public class BreakableTile extends InteractableTile {

    public BreakableTile(int row, int column, Sprite sprite, boolean passable, boolean jumpThrough) {
        super(row, column, sprite, null, passable, jumpThrough);
    }

    @Override
    public void action() {

    }

    @Override
    public void trigger(Entity entity) {
        if (entity.getClass() != Player.class) return;

        Player p = (Player) entity;
        if (p.getPowerUpState() == PlayerPowerUpState.SMALL) jump();
        else destroy();
    }
}
