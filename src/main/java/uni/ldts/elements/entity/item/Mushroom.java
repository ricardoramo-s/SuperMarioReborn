package uni.ldts.elements.entity.item;

import uni.ldts.Sprite;
import uni.ldts.elements.entity.Entity;
import uni.ldts.physics.AABB;
import uni.ldts.elements.entity.player.Player;
import uni.ldts.elements.entity.player.PlayerPowerUpState;
import uni.ldts.physics.ContactDirection;

import java.io.IOException;

public class Mushroom extends Item {
    private static final Sprite mushroomSprite = new Sprite("src/main/resources/itemSprites/mushroom.png", 16, 16);
    private final double mushroomVelocity = 8.0D;

    public Mushroom(int x, int y) {
        super(mushroomSprite, new AABB(x, y, 16, 16));
        setVelX(mushroomVelocity);
    }

    @Override
    public void onContact(Entity entity, ContactDirection direction) {
        if (entity.getClass() != Player.class) return;

        Player p = (Player) entity;
        p.updatePowerUp(PlayerPowerUpState.BIG);
        kill();
    }

    @Override
    public void tick() {
        if (isLeftCollision()) setVelX(mushroomVelocity);
        else if (isRightCollision()) setVelX(-mushroomVelocity);
    }
}
