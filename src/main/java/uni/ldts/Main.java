package uni.ldts;

import uni.ldts.physics.AABB;
import uni.ldts.elements.entity.enemy.Goomba;
import uni.ldts.elements.entity.item.FireFlower;
import uni.ldts.elements.entity.item.Mushroom;
import uni.ldts.elements.entity.player.Player;
import uni.ldts.elements.entity.player.PlayerAnimationState;
import uni.ldts.elements.entity.player.PlayerPowerUpState;
import uni.ldts.elements.tile.*;
import uni.ldts.engine.GameEngine;
import uni.ldts.engine.InputManager;
import uni.ldts.map.Map;
import uni.ldts.map.MapFactory;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        GameEngine engine = new GameEngine();

        AABB bBox = new AABB(0, 0, 16, 15);
        Player mario = new Player(bBox);

        Sprite map1TilesetSprite = new Sprite("src/main/resources/maps/map_1/tileset.png", 10, 12, 16, 16, 120);
        Texture map1Background = new Texture("src/main/resources/maps/map_1/background.png");

        Sprite smallWalking = new Sprite("src/main/resources/playerSprites/small_mario/walking.png", 16, 16);
        Sprite smallRunning = new Sprite("src/main/resources/playerSprites/small_mario/running.png", 16, 16);
        Sprite smallFlying = new Sprite("src/main/resources/playerSprites/small_mario/flying.png", 16, 16);
        Sprite smallHolding = new Sprite("src/main/resources/playerSprites/small_mario/holding.png", 16, 16);
        Sprite smallJumping = new Sprite("src/main/resources/playerSprites/small_mario/jumping.png", 16, 16);
        Sprite smallKicking = new Sprite("src/main/resources/playerSprites/small_mario/kicking.png", 16, 16);
        Sprite smallPivoting = new Sprite("src/main/resources/playerSprites/small_mario/pivoting.png", 16, 16);
        Sprite smallIdle = new Sprite("src/main/resources/playerSprites/small_mario/idle.png", 16, 16);
        Sprite smallDying = new Sprite("src/main/resources/playerSprites/small_mario/dying.png", 16, 16);

        Sprite bigWalking = new Sprite("src/main/resources/playerSprites/big_mario/walking.png", 16, 27);
        Sprite bigRunning = new Sprite("src/main/resources/playerSprites/big_mario/running.png", 24, 27);
        Sprite bigFlying = new Sprite("src/main/resources/playerSprites/big_mario/flying.png", 24, 27);
        Sprite bigHolding = new Sprite("src/main/resources/playerSprites/big_mario/holding.png", 16, 27);
        Sprite bigJumping = new Sprite("src/main/resources/playerSprites/big_mario/jumping.png", 16, 27);
        Sprite bigKicking = new Sprite("src/main/resources/playerSprites/big_mario/kicking.png", 16, 27);
        Sprite bigPivoting = new Sprite("src/main/resources/playerSprites/big_mario/pivoting.png", 16, 27);
        Sprite bigIdle = new Sprite("src/main/resources/playerSprites/big_mario/idle.png", 16, 27);

        Sprite fireWalking = new Sprite("src/main/resources/playerSprites/fire_mario/walking.png", 16, 27);
        Sprite fireRunning = new Sprite("src/main/resources/playerSprites/fire_mario/running.png", 24, 27);
        Sprite fireFlying = new Sprite("src/main/resources/playerSprites/fire_mario/flying.png", 24, 27);
        Sprite fireJumping = new Sprite("src/main/resources/playerSprites/fire_mario/jumping.png", 16, 27);
        Sprite firePivoting = new Sprite("src/main/resources/playerSprites/fire_mario/pivoting.png", 16, 27);
        Sprite fireIdle = new Sprite("src/main/resources/playerSprites/fire_mario/idle.png", 16, 27);
        Sprite fireActing = new Sprite("src/main/resources/playerSprites/fire_mario/acting.png", 16, 27);

        mario.addSprite(PlayerPowerUpState.SMALL, PlayerAnimationState.WALKING, smallWalking);
        mario.addSprite(PlayerPowerUpState.SMALL, PlayerAnimationState.RUNNING, smallRunning);
        mario.addSprite(PlayerPowerUpState.SMALL, PlayerAnimationState.FLYING, smallFlying);
        mario.addSprite(PlayerPowerUpState.SMALL, PlayerAnimationState.HOLDING, smallHolding);
        mario.addSprite(PlayerPowerUpState.SMALL, PlayerAnimationState.JUMPING, smallJumping);
        mario.addSprite(PlayerPowerUpState.SMALL, PlayerAnimationState.KICKING, smallKicking);
        mario.addSprite(PlayerPowerUpState.SMALL, PlayerAnimationState.PIVOTING, smallPivoting);
        mario.addSprite(PlayerPowerUpState.SMALL, PlayerAnimationState.IDLE, smallIdle);
        mario.addSprite(PlayerPowerUpState.SMALL, PlayerAnimationState.DYING, smallDying);

        mario.addSprite(PlayerPowerUpState.BIG, PlayerAnimationState.WALKING, bigWalking);
        mario.addSprite(PlayerPowerUpState.BIG, PlayerAnimationState.RUNNING, bigRunning);
        mario.addSprite(PlayerPowerUpState.BIG, PlayerAnimationState.FLYING, bigFlying);
        mario.addSprite(PlayerPowerUpState.BIG, PlayerAnimationState.HOLDING, bigHolding);
        mario.addSprite(PlayerPowerUpState.BIG, PlayerAnimationState.JUMPING, bigJumping);
        mario.addSprite(PlayerPowerUpState.BIG, PlayerAnimationState.KICKING, bigKicking);
        mario.addSprite(PlayerPowerUpState.BIG, PlayerAnimationState.PIVOTING, bigPivoting);
        mario.addSprite(PlayerPowerUpState.BIG, PlayerAnimationState.IDLE, bigIdle);

        mario.addSprite(PlayerPowerUpState.FIRE, PlayerAnimationState.WALKING, fireWalking);
        mario.addSprite(PlayerPowerUpState.FIRE, PlayerAnimationState.RUNNING, fireRunning);
        mario.addSprite(PlayerPowerUpState.FIRE, PlayerAnimationState.FLYING, fireFlying);
        mario.addSprite(PlayerPowerUpState.FIRE, PlayerAnimationState.JUMPING, fireJumping);
        mario.addSprite(PlayerPowerUpState.FIRE, PlayerAnimationState.PIVOTING, firePivoting);
        mario.addSprite(PlayerPowerUpState.FIRE, PlayerAnimationState.IDLE, fireIdle);
        mario.addSprite(PlayerPowerUpState.FIRE, PlayerAnimationState.ACTING, fireActing);

        MapFactory factory = new MapFactory(map1TilesetSprite);

        factory.addTileClassRow(5, PassableTile.class);
        factory.addTileClassRow(6, PassableTile.class);
        factory.addTileClassRow(7, PassableTile.class);
        factory.addTileClass(8, 3, PassableTile.class);
        factory.addTileClass(8, 4, PassableTile.class);
        factory.addTileClass(8, 5, PassableTile.class);
        factory.addTileClass(9, 3, PassableTile.class);
        factory.addTileClass(9, 4, PassableTile.class);
        factory.addTileClass(9, 5, PassableTile.class);
        factory.addTileClass(2, 2, CoinTile.class);

        factory.addTileClass(2, 0, BreakableTile.class);
        factory.addTileClass(2, 1, ItemTile.class);
        factory.addTileClassRow(3, ItemTile.class);
        factory.addItem(3, 0, null);
        factory.addItem(3, 1, FireFlower.class);
        factory.addItem(3, 2, Mushroom.class);
        factory.addItem(3, 3, null);

        factory.addTileClassRow(4, JumpThroughTile.class);
        factory.addTileClass(8, 0, JumpThroughTile.class);
        factory.addTileClass(8, 1, JumpThroughTile.class);
        factory.addTileClass(8, 2, JumpThroughTile.class);
        factory.addTileClass(9, 0, JumpThroughTile.class);
        factory.addTileClass(9, 2, WaterTile.class);

        factory.addEnemy(2, 4, Goomba.class);

        factory.load("src/main/resources/maps/map_1/map1.csv");
        Map map = factory.getMap(mario, map1Background);
        engine.getManager().setMap(map);

        mario.setY(map.getMapHeight() - 48);

        // inject listener
        InputManager inputManager = new InputManager(map.getPlayer());
        engine.registerListener(inputManager);
        engine.getManager().setInputManager(inputManager);
        engine.start();
    }
}