package stubs;

import uni.ldts.listeners.EventHandler;
import uni.ldts.listeners.KeyListener;
import uni.ldts.engine.GameManager;

import java.awt.event.KeyEvent;
import java.util.Map;

@EventHandler(master = true)
public class StubListener implements KeyListener {
    Map<Integer, Boolean> keysPressed;

    private final GameManager manager;
    public StubListener(GameManager m) { this.manager = m; }

    @Override
    public void onKeyPress(KeyEvent e) {
        keysPressed.put(e.getKeyCode(), true);
        /*
        Entity p = manager.getMap().getCharacter();
        if (e.getKeyChar() == ' ') {
            keysPressed.put(true, e);
            // p.setVelY(-35);
        }
        else if (e.getKeyCode() == 39) {
            // moved right
            if (p.getVelX()<0) {
                System.out.println("changing direction to LEFT");
            }
            else p.setVelX(15);
        }
        else if (e.getKeyCode() == 37) {
            // moved left
            if (p.getVelX()>0) {
                System.out.println("changing direction to RIGHT");
            }
            p.setVelX(-15);
        }

         */
    }

    @Override
    public void onKeyRelease(KeyEvent e) {
        keysPressed.put(e.getKeyCode(), false);
        /*
        Entity p = manager.getMap().getCharacter();
        if (e.getKeyCode() == 39 || e.getKeyCode() == 37) p.setVelX(0);
        // stopped moving left or right ^

         */
    }
}
