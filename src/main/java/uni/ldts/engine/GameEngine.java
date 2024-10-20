package uni.ldts.engine;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFrame;
import uni.ldts.listeners.KeyListener;
import uni.ldts.listeners.MasterListener;

import java.io.IOException;
import java.lang.reflect.Method;

public class GameEngine implements Runnable {
    public final static String TITLE = "Super Mario";
    public final static int TPS = 120;
    public final static int FPS = 90;

    private static int fpsCounter = 0;
    private static long timeSinceLastFPS = System.currentTimeMillis();

    private final MasterListener listener;
    private final GameManager manager;
    private final Renderer renderer;
    private boolean isRunning;

    /**
     * An engine is a foundational component of a game, which usually
     * oversees all other components and acts as a communication middleman
     */
    public GameEngine() {
        try {
            this.manager = new GameManager();
            this.renderer = new Renderer(manager);
            this.listener = new MasterListener(manager);
            this.init();
        } catch (IOException e) {
            // system doesn't support javax.Swing (very unlikely)
            throw new UnsupportedOperationException("Error initializing engine, aborting");
        }
        this.thread = new Thread(this, TITLE+" helper");
    }

    /**
     * Initialize the game engine by loading core components.
     * After initialization, call the start method to start the game
     * @throws IOException system not supported
     */
    protected void init() throws IOException {

        DefaultTerminalFactory factory = new DefaultTerminalFactory();
        factory.setInitialTerminalSize(new TerminalSize(1,1));
        factory.setTerminalEmulatorTitle(TITLE);

        SwingTerminalFrame terminal = factory.createSwingTerminal();
        this.makeWindowVisible(terminal);

        Screen screen = new TerminalScreen(terminal);
        screen.startScreen();

        /*
        from here on out, we'll use lantern's SwingTerminal
        underlying architecture, Swing, for better performance
         */
        terminal.setContentPane(renderer);
        terminal.pack();
        terminal.setLocationRelativeTo(null);

        // master coordinates input listener events
        terminal.addKeyListener(this.listener);
    }

    // game loop runs async
    protected Thread thread;

    public void start() {
        if (isRunning) return;
        this.isRunning = true;
        this.manager.getMap().init();
        this.thread.start();
    }

    public void stop() {
        if (!isRunning) return;
        this.isRunning = false;
        this.thread.interrupt();
    }

    @Override
    public void run() {
        double delta1 = 1000.0D/TPS;
        double delta2 = 1000.0D/FPS;

        long lastTick = System.currentTimeMillis();
        long lastFrame = lastTick;

        while (isRunning && !thread.isInterrupted()) {
            long now = System.currentTimeMillis();
            if (now-lastTick >= delta1) {
                this.manager.tick();
                lastTick = System.currentTimeMillis();
            }
            if (now-lastFrame >= delta2) {
                lastFrame = System.currentTimeMillis();
                if (now - timeSinceLastFPS >= 1000) {
                    System.out.println("FPS: " + fpsCounter);
                    timeSinceLastFPS = now;
                    fpsCounter = 0;
                }
                else fpsCounter++;

                // redraw the screen
                this.renderer.repaint();
            }
        }
    }

    /*
    used to avoid a bug in lanterna where, if you ask for a specific type of terminal,
     like a SwingTerminal, it forgets to call this method, causing the screen not to start
    */
    private void makeWindowVisible(Terminal terminal) {
        try {
            Class<?> cls = Class.forName("java.awt.Window");
            Method method = cls.getDeclaredMethod("setVisible", boolean.class);
            method.invoke(terminal, true);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to make terminal emulator window visible.", ex);
        }
    }

    public GameManager getManager() { return this.manager; }
    public void registerListener(KeyListener l) { this.listener.newListener(l); }
}
