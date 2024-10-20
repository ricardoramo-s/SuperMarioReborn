package uni.ldts.elements.tile;

import uni.ldts.Sprite;
import uni.ldts.elements.entity.item.Item;

public class TileFactory {
    private Sprite sprite;
    private Sprite used;
    private Item item;
    private Class<? extends Tile> tileClass;
    public TileFactory() { }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
    public void setUsed(Sprite sprite) {
        this.used = sprite;
    }


    public void setItem(Item item) {
        this.item = item;
    }

    public void setClass(Class<? extends Tile> tileClass) {
        this.tileClass = tileClass;
    }

    public Tile createTile(int row, int column) {
        if (tileClass == BreakableTile.class) {
            return new BreakableTile(row, column, sprite, false, false);
        }
        else if (tileClass == ItemTile.class) {
            return new ItemTile(row, column, item, sprite, used, false, false);
        }
        else if (tileClass == CoinTile.class) {
            return new CoinTile(row, column, sprite);
        }
        else if (tileClass == PassableTile.class) {
            return new PassableTile(row, column, sprite);
        }
        else if (tileClass == WaterTile.class) {
            return new WaterTile(row, column, sprite);
        }
        else if (tileClass == JumpThroughTile.class) {
            return new JumpThroughTile(row, column, sprite);
        }
        else { // unbreakable default
            return new UnbreakableTile(row, column, sprite, false, false);
        }
    }
}
