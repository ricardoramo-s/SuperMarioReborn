package stubs;

import uni.ldts.physics.AABB;
import uni.ldts.elements.entity.Entity;
import uni.ldts.Sprite;

public class StubEntity extends Entity {

    public StubEntity(Sprite sprite, AABB boundingBox) {
        super(sprite, boundingBox);
    }

    @Override
    public void tick() {

    }
}