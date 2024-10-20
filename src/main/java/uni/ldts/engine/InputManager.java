package uni.ldts.engine;

import uni.ldts.listeners.EventHandler;
import uni.ldts.listeners.KeyListener;
import uni.ldts.elements.entity.player.Player;

import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

/**
 * Is responsible for handling the movement of the character.
 * Must be added to MasterListener in the GameEngine.
 */
@EventHandler(master = true)
public class InputManager implements KeyListener {
    private final Set<Integer> keysPressed;
    private final Player player;
    public InputManager(Player p) {
        this.player = p;
        this.keysPressed = new HashSet<>();
    }

    @Override
    public void onKeyPress(KeyEvent e) {
        keysPressed.add(e.getKeyCode());
    }

    @Override
    public void onKeyRelease(KeyEvent e) {
        keysPressed.remove(e.getKeyCode());
    }

    public void handleInput() {
        // Handles sprinting and actions
        if (keysPressed.contains(KeyEvent.VK_SHIFT)) {
            player.setRunning(true);
            player.action();
        }
        else {
            player.setRunning(false);
        }

        // Handles jumping
        if (keysPressed.contains(KeyEvent.VK_SPACE)) {
            player.jump(false);
        }
        else {
            player.stopJumping();
        }

        // Handles when both directions are pressed at the same time
        if (keysPressed.contains(KeyEvent.VK_LEFT) && keysPressed.contains(KeyEvent.VK_RIGHT)) {
            player.idle();
        }
        else if (keysPressed.contains(KeyEvent.VK_LEFT)) {
            // Handles moving left
            player.moveLeft();
        }
        else if (keysPressed.contains(KeyEvent.VK_RIGHT)) {
            // Handles moving right
            player.moveRight();
        }
        else {
            // If any keys is pressed, idle
            player.idle();
        }
    }
}
