package uni.ldts.map;

import uni.ldts.Sprite;
import uni.ldts.Texture;
import uni.ldts.elements.entity.*;
import uni.ldts.elements.entity.enemy.Enemy;
import uni.ldts.elements.entity.enemy.EnemyFactory;
import uni.ldts.elements.entity.enemy.Goomba;
import uni.ldts.elements.entity.item.Item;
import uni.ldts.elements.entity.item.ItemFactory;
import uni.ldts.elements.entity.player.Player;
import uni.ldts.elements.tile.ItemTile;
import uni.ldts.elements.tile.Tile;
import uni.ldts.elements.tile.TileFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapFactory {
    private final Sprite spritesheet;
    private final TileFactory tileFactory = new TileFactory();
    private final ItemFactory itemFactory = new ItemFactory();
    private final EnemyFactory enemyFactory = new EnemyFactory();
    private final HashMap<Integer, Class<? extends Tile>> idTileClass = new HashMap<>(); // tile class of each id
    private final HashMap<Integer, Class<? extends Item>> idItemClass = new HashMap<>(); // item class of each id
    private final HashMap<Integer, Class<? extends Enemy>> idEnemyClass = new HashMap<>(); // enemy class of each id
    private final List<List<Integer>> tiles = new ArrayList<>();

    public MapFactory(Sprite background) {
        this.spritesheet = background;
    }

    public void load(String path) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line;
            int y = 0;

            // tiled exports maps as csv data
            while ((line = br.readLine()) != null) {
                tiles.add(new ArrayList<>());
                String[] values = line.split(",");
                int x = 0;

                for (String value : values) {
                    tiles.get(y).add(x, Integer.parseInt(value));
                    x++;
                }

                y++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Maps a single 16*16 tile of the tileset to a tile class.
     * @param row
     * @param column
     * @param tileClass
     */
    public void addTileClass(int row, int column, Class<? extends Tile> tileClass) {
        this.idTileClass.put(row * spritesheet.getCols() + column, tileClass);
    }

    /**
     * Maps a whole row of the tileset to a tile class.
     * @param row t
     * @param tileClass
     */
    public void addTileClassRow(int row, Class<? extends Tile> tileClass) {
        for (int col = 0; col < spritesheet.getCols(); col++) {
            this.idTileClass.put(row * spritesheet.getCols() + col, tileClass);
        }
    }

    /**
     * Maps a tile class to a 16*16 tile of the tileset to be put inside ItemBlock.
     * @param row
     * @param column
     * @param itemClass
     */
    public void addItem(int row, int column, Class<? extends Item> itemClass) {
        this.idItemClass.put(row * spritesheet.getCols() + column, itemClass);
    }

    /**
     * Maps a enemy class to a 16*16 tile of the tileset.
     * @param row
     * @param column
     * @param enemyClass
     */
    public void addEnemy(int row, int column, Class<? extends Enemy> enemyClass) {
        this.idEnemyClass.put(row * spritesheet.getCols() + column, enemyClass);
    }

    /**
     * Constructs the map.
     * @param p Player
     * @param background Background texture
     * @return The created map
     * @throws IOException
     */
    public Map getMap(Player p, Texture background) throws IOException {
        ArrayList<Tile> mapTiles = new ArrayList<>();
        ArrayList<Entity> entities = new ArrayList<>();
        Sprite used = new Sprite("src/main/resources/tileSprites/usedBlock.png", 16, 16);


        // go through every id
        for (int y = 0; y < this.tiles.size(); y++) {
            for (int x = 0; x < this.tiles.get(0).size(); x++) {
                int id = this.tiles.get(y).get(x);
                if (id == -1) continue; // empty space

                // enemies have priority because tileFactory will produce an UnbreakableTile by default
                if (idEnemyClass.containsKey(id)) {
                    enemyFactory.setEnemyClass(idEnemyClass.get(Goomba.class));
                    entities.add(enemyFactory.createEnemy(x * 16, y * 16));
                }
                else {
                    Class<? extends Tile> tileClass = idTileClass.get(id);
                    tileFactory.setClass(tileClass);
                    
                    if (tileClass == ItemTile.class) {
                        itemFactory.setItemClass(idItemClass.getOrDefault(id, null));
                        Item item = itemFactory.createItem(x * 16, y * 16);
                        
                        if (item != null) {
                            item.frozen(true);
                            item.setVisible(false);
                            entities.add(item);
                        }

                        // itemTiles with a null item will give a coin
                        tileFactory.setItem(item);
                    }
                    
                    tileFactory.setSprite(this.spritesheet.getSubSprite(id));
                    tileFactory.setUsed(used);
                    mapTiles.add(tileFactory.createTile(x, y));
                }
            }
        }

        return new Map(this.tiles.get(0).size() * 16, this.tiles.size() * 16, p, background, entities, mapTiles);
    }
}
