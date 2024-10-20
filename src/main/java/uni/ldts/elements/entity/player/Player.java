package uni.ldts.elements.entity.player;

import uni.ldts.elements.entity.projectile.FireProjectile;
import uni.ldts.physics.AABB;
import uni.ldts.Sprite;
import uni.ldts.elements.entity.Entity;
import uni.ldts.physics.ContactDirection;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.abs;

/**
 * Extension of Entity to be used as the main character.
 * Is responsible for managing the movement (acceleration and velocity) of the player.
 */
public class Player extends Entity {
    private final double runningTargetVelocity = 23.0D;
    private final double runningAcceleration = 4.0D;
    private final double runningThreshold = runningTargetVelocity - 4.0d;
    private final double walkingTargetVelocity = 8.5D;
    private final double walkingAcceleration = 3.0D;
    private double airVelocityCap = 23.0D;
    private final double friction = 15D;
    private final double airResistance = friction / 3;
    private int jumpFrames = 36;
    private boolean isStillJumping = false;
    private boolean isRunning = false;
    private boolean isActing;
    private int actingFrames = 20;
    private boolean isFacingRight = true;
    private boolean isDying, isDead;
    private int dyingFrames = 200;
    private int coins = 0;
    private int lives = 3;
    private final Map<PlayerPowerUpState, Map<PlayerAnimationState, Sprite>> spriteMap;
    private PlayerAnimationState animationState = PlayerAnimationState.IDLE;
    private PlayerPowerUpState powerUpState = PlayerPowerUpState.SMALL;
    private int animationFPS = 6;
    private long timeSinceLastSpriteFrame = 0;
    private long timeSinceDamage = 0;
    private long timeSinceAction = 0;

    public Player(AABB boundingBox) {
        super(null, boundingBox);
        spriteMap = new HashMap<>();
    }

    public void addSprite(PlayerPowerUpState powerUpState, PlayerAnimationState animationState, Sprite sprite) {
        if (!spriteMap.containsKey(powerUpState)) spriteMap.put(powerUpState, new HashMap<>());

        spriteMap.get(powerUpState).put(animationState, sprite);
    }

    public void idle() {
        if (isOnGround()) {
            animationState = PlayerAnimationState.IDLE;

            if (abs(getVelX()) < .8D) { // margin for error
                setAccX(0);
                setVelX(0);
            } else { // slow down until stopping
                if (getVelX() > 0) setAccX(-friction);
                else if (getVelX() < 0) setAccX(friction);
            }
        }
        else {
            if (abs(getVelX()) < .8D) { // margin for error
                setAccX(0);
                setVelX(0);
            } else { // slow down until stopping
                if (getVelX() > 0) setAccX(-airResistance);
                else if (getVelX() < 0) setAccX(airResistance);
            }
        }
    }

    public void moveRight() {
        isFacingRight = true;

        if (isRunning) {
            if (isOnGround()) animationState = (getVelX() > runningThreshold) ? PlayerAnimationState.RUNNING : PlayerAnimationState.WALKING;
            if (isOnGround() && getVelX() < 0) animationState = PlayerAnimationState.PIVOTING;

            double acceleration = (getVelX() > 0) ? runningAcceleration : runningAcceleration + friction / 4;
            setAccX(acceleration);
            capVelocity(true);
        }
        else {
            if (isOnGround()) animationState = PlayerAnimationState.WALKING;

            double acceleration = (getVelX() > 0) ? walkingAcceleration : walkingAcceleration + friction / 2;
            setAccX(acceleration);
            capVelocity(false);
        }
    }

    public void moveLeft() {
        isFacingRight = false;

        if (isRunning) {
            if (isOnGround()) animationState = (getVelX() < -runningThreshold) ? PlayerAnimationState.RUNNING : PlayerAnimationState.WALKING;
            if (isOnGround() && getVelX() > 0) animationState = PlayerAnimationState.PIVOTING;

            double acceleration = (getVelX() < 0) ? -runningAcceleration : -runningAcceleration - friction / 4;
            setAccX(acceleration);
            capVelocity(true);
        }
        else {
            if (isOnGround()) animationState = PlayerAnimationState.WALKING;

            double acceleration = (getVelX() < 0) ? -walkingAcceleration : -walkingAcceleration - friction / 2;
            setAccX(acceleration);
            capVelocity(false);
        }
    }

    public void action() {
        long now = System.currentTimeMillis();
        if (now - timeSinceAction < 500) return;

        timeSinceAction = now;
        if (powerUpState == PlayerPowerUpState.FIRE) {
            isActing = true;

            if (isFacingRight) {
                manager.addEntity(new FireProjectile(getBoundingBox().getMaxX(), getY() + 4, true));
            }
            else {
                manager.addEntity(new FireProjectile(getBoundingBox().getMinX(), getY() + 4, false));
            }
        }
    }

    public void damage() {
        long now = System.currentTimeMillis();
        if (now - timeSinceDamage > 1000) {
            timeSinceDamage = now;

            if (powerUpState == PlayerPowerUpState.SMALL) killPlayer();
            else if (powerUpState == PlayerPowerUpState.BIG) updatePowerUp(PlayerPowerUpState.SMALL);
            else if (powerUpState == PlayerPowerUpState.FIRE) updatePowerUp(PlayerPowerUpState.BIG);
        }
    }

    public void killPlayer() {
        powerUpState = PlayerPowerUpState.SMALL;
        animationState = PlayerAnimationState.DYING;
        isDying = true;
        this.frozen(true);
    }

    public void jump(boolean force) {
        if (force || (isOnGround() && !isStillJumping)) {
            setVelY(-15);
            airVelocityCap = abs(getVelX()) + 4;
            isStillJumping = true;
            jumpFrames = (force) ? 20 : 34; // after bonking an enemy
        }
        else if (isUpCollision()) {
            jumpFrames = 0;
        }
        else if (isStillJumping && jumpFrames > 0) {
            if (abs(getVelX()) > (runningThreshold)) animationState = PlayerAnimationState.FLYING;
            else animationState = PlayerAnimationState.JUMPING;

            if (getVelX() > airVelocityCap) setVelX(airVelocityCap);
            else if (getVelX() < -airVelocityCap) setVelX(-airVelocityCap);

            setVelY(-25);
            jumpFrames--;
        }
    }

    public void stopJumping() {
        jumpFrames = 0; // releasing the key prevents from gaining more height pressing space again
        isStillJumping = false; // prevents auto-jump when touching the floor again
    }

    public void setRunning(boolean b) { this.isRunning = b; }

    private void capVelocity(boolean running) {
        double targetVelocity = (running) ? runningTargetVelocity : walkingTargetVelocity;

        if (getVelX() > 0 && getVelX() > targetVelocity) setVelX(targetVelocity);
        else if (getVelX() < 0 && -getVelX() > targetVelocity) setVelX(-targetVelocity);
    }

    public PlayerPowerUpState getPowerUpState() {
        return powerUpState;
    }

    public boolean isDead() {
        return isDead;
    }

    public int getCoins() {
        return coins;
    }

    public void addCoin() { coins++; }

    public int getLives() {
        return lives;
    }

    public void updatePowerUp(PlayerPowerUpState powerUp) {
        if (powerUp == PlayerPowerUpState.SMALL) {
            if (powerUpState != PlayerPowerUpState.SMALL) setBoundingBox(new AABB(getX(), getY(), 16, 15));
            powerUpState = PlayerPowerUpState.SMALL;
        }
        else if (powerUp == PlayerPowerUpState.BIG && powerUpState != PlayerPowerUpState.FIRE) {
            if (powerUpState == PlayerPowerUpState.SMALL) setBoundingBox(new AABB(getX(), getY() - 11, 16, 27));
            powerUpState = PlayerPowerUpState.BIG;
        }
        else if (powerUp == PlayerPowerUpState.FIRE) {
            if (powerUpState == PlayerPowerUpState.SMALL) setBoundingBox(new AABB(getX(), getY() - 11, 16, 27));
            powerUpState = PlayerPowerUpState.FIRE;
        }
    }

    private void updateAnimation() {
        if (isDying) {
            animationState = PlayerAnimationState.DYING;
            dyingFrames--;

            if (dyingFrames == 0) isDead = true;
        }
        else if (isActing) {
            animationState = PlayerAnimationState.ACTING;
            actingFrames--;

            if (actingFrames == 20) spriteMap.get(PlayerPowerUpState.FIRE).get(PlayerAnimationState.ACTING).select(1);
            else if (actingFrames == 0) {
                spriteMap.get(PlayerPowerUpState.FIRE).get(PlayerAnimationState.ACTING).select(0);
                isActing = false;
                actingFrames = 40;
            }
        }

        Sprite newSprite = spriteMap.get(powerUpState).get(animationState);

        if (this.sprite != newSprite) {
            if (isRunning) {
                animationFPS = 10;
            }
            else {
                animationFPS = 5;
            }

            this.sprite = newSprite;
            this.sprite.select(0);
        }

        this.sprite.setReversed(isFacingRight);
        long now = System.currentTimeMillis();
        if (now - timeSinceLastSpriteFrame >  1000 / animationFPS) {
            sprite.next();
            timeSinceLastSpriteFrame = now;
        }
    }

    @Override
    public void tick() {
        updateAnimation();
    }

    @Override
    public void onContact(Entity entity, ContactDirection direction) {

    }
}
