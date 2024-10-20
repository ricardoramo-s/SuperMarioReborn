package uni.ldts.listeners;

import uni.ldts.engine.GameManager;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class MasterListener implements java.awt.event.KeyListener {

    protected final GameManager manager;
    private final ArrayList<KeyListener> listeners;

    public MasterListener(GameManager manager) {
        this.manager = manager;
        this.listeners = new ArrayList<>();
        // todo: add all child listeners here
    }

    // in case external sources want to inject more listeners
    public void newListener(KeyListener l) { this.listeners.add(l); }

    /**
     * When a key is pressed, the MasterListener will notify only the children whose
     * specified state (through the @EventHandler) matches the current game state
     */
    private boolean canNotify(KeyListener l) {
        EventHandler event = l.getClass().getAnnotation(EventHandler.class);
        if (event == null) return false; // <- forgot to add @EventHandler
        return event.master() || event.state() == manager.getState();
    }

    @Override
    public void keyTyped(KeyEvent e) { /* null - we don't need it */ }

    @Override
    public void keyPressed(KeyEvent e) {
        for (KeyListener l : this.listeners) {
            if (this.canNotify(l)) l.onKeyPress(e);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        for (KeyListener l : this.listeners) {
            if (this.canNotify(l)) l.onKeyRelease(e);
        }
    }
}
