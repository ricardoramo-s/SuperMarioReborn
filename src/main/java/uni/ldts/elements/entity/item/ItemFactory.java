package uni.ldts.elements.entity.item;

public class ItemFactory {
    private Class<? extends Item> itemClass;

    public ItemFactory() { }

    public void setItemClass(Class<? extends Item> itemClass) { this.itemClass = itemClass; }

    public Item createItem(int x, int y) {
        if (itemClass == FireFlower.class) {
            return new FireFlower(x, y);
        }
        else if (itemClass == Mushroom.class){
            return new Mushroom(x, y);
        }
        else return null;
    }
}
