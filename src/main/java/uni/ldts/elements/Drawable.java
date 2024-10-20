package uni.ldts.elements;

import java.awt.*;

/**
 * A drawable is an object capable of being drawn into a screen -
 * this is either an image, known as Texture, or a set of shapes, text, etc
 */
public interface Drawable {
    void draw(int x, int y, Graphics2D g);
}
