package uni.ldts.map;

import uni.ldts.elements.entity.Entity;
import uni.ldts.ui.LevelOverlay;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EntityManager {
    private final Set<Entity> entitySet;
    private final Set<Entity> frozenEntities;
    private final ArrayList<Entity> entitiesToKill;

    public EntityManager(List<Entity> e) {
        this.entitiesToKill = new ArrayList<>();
        this.entitySet = new HashSet<>();
        this.frozenEntities = new HashSet<>();

        for (Entity entity : e) {
            if (entity.isFrozen()) frozenEntities.add(entity); // saves the frozen state of each entity
            addEntity(entity);
        }
    }

    public void killEntity(Entity entity) {
        entitiesToKill.add(entity);
    }

    /**
     * To prevent crashes due to concurrent access to the map,
     * entities are queued to be killed after ticking.
     */
    private void killEntities() {
        for (Entity entity : entitiesToKill) {
            entitySet.remove(entity);
        }

        entitiesToKill.clear();
    }

    public void addEntity(Entity entity) {
        if (entity == null) return;

        entity.setManager(this);
        entitySet.add(entity);
    }

    public Set<Entity> getEntities() { return this.entitySet; }

    public void freezeEntities() {
        for (Entity entity : entitySet) {
            if (entity.isFrozen()) frozenEntities.add(entity); // preserves if the entity was already frozen before
            else entity.frozen(true);
        }
    }

    public void unfreezeEntities() {
        for (Entity entity : entitySet) {
            if (!frozenEntities.contains(entity)) entity.frozen(false);
        }
        frozenEntities.clear();
    }

    public void tick() {
        for (Entity entity : entitySet) {
            entity.tick();
        }

        killEntities();
    }
}
