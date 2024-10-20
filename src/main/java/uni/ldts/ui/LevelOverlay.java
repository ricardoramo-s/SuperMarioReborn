package uni.ldts.ui;

import org.w3c.dom.Text;
import uni.ldts.Texture;
import uni.ldts.elements.Drawable;
import uni.ldts.engine.Renderer;
import uni.ldts.map.Map;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class LevelOverlay implements Drawable {
    private final ArrayList<Texture> numbers = new ArrayList<>();
    private final Texture timeAndCoins, livesIcon;
    private final Map map;
    public LevelOverlay (Map map) {
        this.map = map;
        this.timeAndCoins = new Texture("src/main/resources/hudSprites/time_and_coins_hud.png");
        this.livesIcon = new Texture("src/main/resources/hudSprites/lives_hud.png");
        // load all the numbers to draw
        for (int i = 0; i < 10; i++) {
            numbers.add(new Texture("src/main/resources/hudSprites/numbers/" + i +  ".png"));
        }
    }

    @Override
    public void draw(int x, int y, Graphics2D g) {
        int coinsUiX = Renderer.WIDTH - 28;
        int coinsUiY = 9;

        draw2Number(coinsUiX, coinsUiY, map.getPlayer().getCoins(), g);
        draw3Number(coinsUiX - 65, coinsUiY + 7, map.getTimer(), g);

        timeAndCoins.draw(coinsUiX - 66, coinsUiY, g);

        int livesX = 10;
        int livesY = 8;
        draw2Number(livesX + 24, livesY + 7, map.getPlayer().getLives(), g);
        livesIcon.draw(livesX, livesY, g);
    }

    private void draw2Number(int x, int y, int number, Graphics2D g) {
        number %= 100;
        numbers.get(number / 10).draw(x, y, g);
        numbers.get(number % 10).draw(x + 8, y, g);
    }

    private void draw3Number(int x, int y, int number, Graphics2D g) {
        number %= 1000;
        numbers.get(number / 100).draw(x, y, g);
        numbers.get((number / 10) % 10).draw(x + 8, y, g);
        numbers.get(number % 10).draw(x + 16, y, g);
    }
}
