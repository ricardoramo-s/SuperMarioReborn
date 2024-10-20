package uni.ldts.listeners;

import java.awt.event.KeyEvent;

/**
 * note: this key listener is nearly identical to java.awt KeyListener
 * but does not provide the keyType() method, since we do not need it
 */
public interface KeyListener {
    void onKeyPress(KeyEvent e);
    void onKeyRelease(KeyEvent e);
}
