package uni.ldts.engine;

import uni.ldts.ui.GameOverScreen;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Renderer extends JPanel {

    public static final int WIDTH = 362, HEIGHT = 225;
    public static int SCALE_X = 4, SCALE_Y = 4;

    private final GameManager manager;
    private final GameOverScreen gameOverScreen;

    // initialize renderer
    public Renderer(GameManager manager) {
        this.setPreferredSize(new Dimension(WIDTH*SCALE_X, HEIGHT*SCALE_Y));
        this.manager = manager;
        this.gameOverScreen = new GameOverScreen();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.scale(SCALE_X, SCALE_Y);

        if (manager.getState() == GameState.START_SCREEN) {

        }
        else if (manager.getState() == GameState.LEVEL) {
            manager.drawMap(g2);
            try {
                manager.drawUI(g2);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else if (manager.getState() == GameState.GAME_OVER) {
            gameOverScreen.draw(0, 0, g2);
        }
    }
}