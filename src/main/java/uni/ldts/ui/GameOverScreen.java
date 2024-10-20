package uni.ldts.ui;

import org.w3c.dom.Text;
import uni.ldts.Texture;
import uni.ldts.elements.Drawable;

import java.awt.*;

public class GameOverScreen implements Drawable {
    private Texture gameOverScreenTexture;

    public GameOverScreen() {
        gameOverScreenTexture = new Texture("src/main/resources/uiScreens/gameOverScreen.png");
    }

    @Override
    public void draw(int x, int y, Graphics2D g) {
        gameOverScreenTexture.draw(x, y, g);
    }
}
