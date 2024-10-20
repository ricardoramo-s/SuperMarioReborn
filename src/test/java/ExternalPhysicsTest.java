import org.junit.jupiter.api.Test;

import java.io.IOException;

public class ExternalPhysicsTest {

    /*
    starts the game, but injects a custom map and
    custom listeners to check for bugs in game mechanics
     */
    @Test
    public static void main(String[] args) throws IOException {
        /*
        GameEngine engine = new StubGameEngine();

        World world = ((StubManager) engine.getManager()).getWorld();
        Sprite sprite = new Sprite("src/test/resources/character.png", 1, 1, 20, 20, 1);
        AABB bBox = new AABB(120, 0, 20, 20);
        StubEntityWithBody ch = new StubEntityWithBody(sprite, bBox, world);
        Texture platform = new Texture("src/test/resources/platform.png");

        StubGObjectWithBody o1 = new StubGObjectWithBody(platform, new AABB(140, 207, 20, 7), false, false, world);
        StubGObjectWithBody o2 = new StubGObjectWithBody(platform, new AABB(160, 207, 20, 7), false, false, world);
        StubGObjectWithBody o3 = new StubGObjectWithBody(platform, new AABB(180, 207, 20, 7), false, false, world);
        StubGObjectWithBody o4 = new StubGObjectWithBody(platform, new AABB(200, 207, 20, 7), false, false, world);
        StubGObjectWithBody o5 = new StubGObjectWithBody(platform, new AABB(220, 180, 20, 7), false, false, world);

        ArrayList<GObject> objects = new ArrayList<>();
        objects.add(o1);
        objects.add(o2);
        objects.add(o3);
        objects.add(o4);
        objects.add(o5);

        // build and inject map
        Map map = new Map(ch, null, objects, new ArrayList<>());
        engine.getManager().setMap(map);

        // inject listener
        engine.registerListener(new StubListener(engine.getManager()));

        engine.start();

         */
    }
}
