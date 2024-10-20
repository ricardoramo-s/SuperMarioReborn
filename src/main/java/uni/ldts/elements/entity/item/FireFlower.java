package uni.ldts.elements.entity.item;

import uni.ldts.Sprite;
import uni.ldts.elements.entity.Entity;
import uni.ldts.physics.AABB;
import uni.ldts.elements.entity.player.Player;
import uni.ldts.elements.entity.player.PlayerPowerUpState;
import uni.ldts.physics.ContactDirection;

import java.io.IOException;

public class FireFlower extends Item {
    private static final Sprite fireFlowerSprite = new Sprite("src/main/resources/itemSprites/fireFlower.png", 16, 16);

    public FireFlower(int x, int y) {
        super(fireFlowerSprite, new AABB(x, y, 16, 16));
    }

    @Override
    public void onContact(Entity entity, ContactDirection direction) {
        if (entity.getClass() != Player.class) return;

        Player p = (Player) entity;
        if (direction != ContactDirection.DOWN) {
            p.updatePowerUp(PlayerPowerUpState.FIRE);
            kill();
        }
    }

    @Override
    public void tick() {

    }
}
