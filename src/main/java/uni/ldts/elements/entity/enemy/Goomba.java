package uni.ldts.elements.entity.enemy;

import uni.ldts.Sprite;
import uni.ldts.elements.entity.Entity;
import uni.ldts.physics.AABB;
import uni.ldts.elements.entity.player.Player;
import uni.ldts.physics.ContactDirection;

import java.io.IOException;

public class Goomba extends Enemy{
    private final static Sprite walkingSprite = new Sprite("src/main/resources/enemySprites/goomba/walking.png", 16, 16);
    private final static Sprite squashed = new Sprite("src/main/resources/enemySprites/goomba/squashed.png", 16, 16);
    private final double goombaVelocity = 5.0D;
    private int animationFPS = 6;
    private long timeSinceLastSpriteFrame = 0;
    private boolean isSquashed = false;
    private long timeSinceSquashed;

    public Goomba(int x, int y) {
        super(walkingSprite, new AABB(x, y, 16, 16));
        setVelX(-goombaVelocity);
    }

    public void squash() {
        frozen(true);
        isSquashed = true;
        sprite = squashed;
        timeSinceSquashed = System.currentTimeMillis();
    }

    @Override
    public void tick() {
        if (!isSquashed && isLeftCollision()) setVelX(goombaVelocity);
        else if (!isSquashed && isRightCollision()) setVelX(-goombaVelocity);

        long now = System.currentTimeMillis();
        if (isSquashed && now - timeSinceSquashed > 500) {
            kill();
            return;
        }
        if (now - timeSinceLastSpriteFrame >  1000 / animationFPS) {
            sprite.next();
            timeSinceLastSpriteFrame = now;
        }
    }

    @Override
    public void onContact(Entity entity, ContactDirection direction) {
        if (entity.getClass() != Player.class) return;

        Player p = (Player) entity;
        if (direction == ContactDirection.LEFT || direction == ContactDirection.RIGHT) {
            p.damage();
        }
        else if (direction == ContactDirection.DOWN && p.getLastY() + p.getBoundingBox().getSizeY() < getY()) {
            p.jump(true);
            squash();
        }
    }
}
