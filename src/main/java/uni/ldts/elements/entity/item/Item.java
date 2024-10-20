package uni.ldts.elements.entity.item;

import uni.ldts.Sprite;
import uni.ldts.physics.AABB;
import uni.ldts.elements.entity.Entity;

public abstract class Item extends Entity {

    public Item(Sprite sprite, AABB boundingBox) {
        super(sprite, boundingBox);
        frozen(true);
    }
}
