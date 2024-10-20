package uni.ldts.map;

import uni.ldts.elements.entity.player.Player;
import uni.ldts.engine.Renderer;
import uni.ldts.map.Map;

public class Camera {

    // top left corner
    private double x;
    private double y;
    private final Map map; // the map the camera is in

    /**
     * Even though a level is very extensive, only a portion of it
     * is drawn at a time. A camera is used for this functionality
     */
    public Camera(Map map) {
        this.map = map;
        this.reset();
    }

    public double getX() { return this.x; }
    public double getY() { return this.y; }

    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }

    public void reset() {
        this.x = 0;
        this.y = 0;
    }

    public void update() {
        if (map.getPlayer().getX() < this.getX() + (int) (Renderer.WIDTH * 0.4)) {
            this.setX(map.getPlayer().getX() - (int) (Renderer.WIDTH * 0.4));
        }
        else if (map.getPlayer().getX() > this.getX() + (int) (Renderer.WIDTH * 0.6)) {
            this.setX(map.getPlayer().getX() - (int) (Renderer.WIDTH * 0.6));
        }

        if (map.getPlayer().getY() + map.getPlayer().getBoundingBox().getSizeY() < this.getY() + Renderer.HEIGHT * 0.2) {
            this.setY(map.getPlayer().getY() + map.getPlayer().getBoundingBox().getSizeY() - Renderer.HEIGHT * 0.2);
        }
        else if (map.getPlayer().getY() + map.getPlayer().getBoundingBox().getSizeY() > this.getY() + Renderer.HEIGHT * 0.8) {
            this.setY(map.getPlayer().getY() + map.getPlayer().getBoundingBox().getSizeY() - Renderer.HEIGHT * 0.8);
        }

        // Camera bound checking
        if (this.getX() < 0) this.setX(0); // prevent going out of the map
        if (this.getX() > map.getMapWidth() - Renderer.WIDTH) this.setX(map.getMapWidth() - Renderer.WIDTH);

        if (this.getY() < 0) this.setY(0); // prevent going out of the map
        if (this.getY() > map.getMapHeight() - Renderer.HEIGHT) this.setY(map.getMapHeight() - Renderer.HEIGHT);
    }
}
