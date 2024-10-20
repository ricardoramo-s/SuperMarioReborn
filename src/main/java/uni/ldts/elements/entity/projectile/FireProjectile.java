package uni.ldts.elements.entity.projectile;

import org.w3c.dom.Text;
import uni.ldts.Sprite;
import uni.ldts.Texture;
import uni.ldts.elements.entity.Entity;
import uni.ldts.elements.entity.enemy.Goomba;
import uni.ldts.elements.entity.player.Player;
import uni.ldts.physics.AABB;
import uni.ldts.physics.ContactDirection;

public class FireProjectile extends Entity {
    private int animationFPS = 10;
    private long timeSinceLastSpriteFrame = 0;
    private final double projectileSpeed = 15.0D;
    public FireProjectile(double x, double y, boolean direction) {
        super(new Sprite("src/main/resources/playerSprites/fire_mario/fireProjectile.png", 8, 10), new AABB(x, y, 7, 10));
        setVelX((direction) ? projectileSpeed : -projectileSpeed);
        frozen(false);
    }

    @Override
    public void tick() {
        if (this.isOnGround()) setVelY(-25);
        if (this.isLeftCollision() || this.isRightCollision()) manager.killEntity(this);

        long now = System.currentTimeMillis();
        if (now - timeSinceLastSpriteFrame >  1000 / animationFPS) {
            sprite.next();
            timeSinceLastSpriteFrame = now;
        }
    }

    @Override
    public void onContact(Entity entity, ContactDirection direction) {
        if (entity.getClass() != Goomba.class) return;

        Goomba g = (Goomba) entity;
        g.kill();
        this.kill();
    }
}
