package uni.ldts.map;

import uni.ldts.elements.tile.Tile;
import uni.ldts.engine.Renderer;

import java.awt.*;
import java.util.List;

public class TileManager {
    Tile[][] tiles; // stores all the tiles of the map in a grid

    public TileManager(int width, int height, List<Tile> t) {
        tiles = new Tile[width][height];

        for (Tile tile : t) {
            setTile(tile.getRow(), tile.getColumn(), tile);
        }
    }
    public Tile getTile(int row, int column) {
        if (row > tiles.length || row < 0 || column > tiles[0].length || column < 0) return null;

        return tiles[row][column];
    }
    public void setTile(int row, int column, Tile tile) {
        if (row > tiles.length || row < 0 || column > tiles[0].length || column < 0) return;

        if (tile != null) tile.setManager(this);
        tiles[row][column] = tile;
    }

    /**
     * Sets a tile to null on the grid, removing it from the map
     * @param tile Tile to be destroyed
     */
    public void destroyTile(Tile tile) {
        setTile(tile.getRow(), tile.getColumn(), null);
    }

    /**
     * Draws the foreground (non-passable) tiles
     * @param xOffset
     * @param yOffset
     * @param g
     */
    public void drawForeground(int xOffset, int yOffset, Graphics2D g) {
        for (int x = -xOffset / 16; x < Math.min(tiles.length, -xOffset + Renderer.WIDTH) / 16 + 1; x++) {
            for (int y = -yOffset / 16; y < Math.min(tiles[0].length, -yOffset + Renderer.WIDTH) / 16 + 1; y++) {
                if (tiles[x][y] != null && !tiles[x][y].isPassable()) tiles[x][y].draw(xOffset, yOffset, g);
            }
        }
    }

    /**
     * Draws the background (passable) tiles
     * @param xOffset
     * @param yOffset
     * @param g
     */
    public void drawBackground(int xOffset, int yOffset, Graphics2D g) {
        for (int x = -xOffset / 16; x < Math.min(tiles.length, -xOffset + Renderer.WIDTH) / 16 + 1; x++) {
            for (int y = -yOffset / 16; y < Math.min(tiles[0].length, -yOffset + Renderer.WIDTH) / 16 + 1; y++) {
                if (tiles[x][y] != null && tiles[x][y].isPassable()) tiles[x][y].draw(xOffset, yOffset, g);
            }
        }
    }

    /**
     * Ticks every tile in the grid
     */
    public void tick() {
        for (Tile[] tile : tiles) {
            for (int y = 0; y < tiles[0].length; y++) {
                if (tile[y] != null) tile[y].tick();
            }
        }
    }
}
