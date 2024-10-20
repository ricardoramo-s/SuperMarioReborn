package uni.ldts.engine;

import uni.ldts.*;
import uni.ldts.map.Map;
import uni.ldts.physics.PhysicsEngine;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/** A game manager will handle all game logic */
public class GameManager {
    private Map map;
    private GameState state;
    private PhysicsEngine physics;
    private InputManager inputManager;

    public GameManager() {
        this.physics = new PhysicsEngine(this);
        this.state = GameState.LEVEL;
    }

    /**
     * A tick is a game unit. Every tick, physics are
     * updated, collisions are checks and entities are ticked
     */
    public void tick() {
        if (inputManager != null) inputManager.handleInput();
        this.physics.update();
        this.map.tick();

        if (map.getPlayer().isDead()) state = GameState.GAME_OVER;
    }

    public Map getMap() { return this.map; }
    public GameState getState() { return this.state; }

    public void setMap(Map map) {
        this.map = map;
    }
    public void setState(GameState state) { this.state = state; }
    public void setInputManager(InputManager inputManager) {
        this.inputManager = inputManager;
    }
    public void drawMap(Graphics2D g) {
        if (this.map != null) this.map.draw(g);
    }
    public void drawUI(Graphics2D g) throws IOException {

    }


}
