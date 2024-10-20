package uni.ldts.elements.entity.enemy;

import uni.ldts.Sprite;
import uni.ldts.physics.AABB;
import uni.ldts.elements.entity.Entity;
import uni.ldts.map.EntityManager;

abstract public class Enemy extends Entity {
    public Enemy(Sprite sprite, AABB boundingBox) {
        super(sprite, boundingBox);
        frozen(false);
    }
}
