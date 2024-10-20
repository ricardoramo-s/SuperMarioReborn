package uni.ldts.elements.entity.enemy;

public class EnemyFactory {
    private Class<? extends Enemy> enemyClass;

    public EnemyFactory() { }

    public void setEnemyClass(Class<? extends Enemy> enemyClass) { this.enemyClass = enemyClass; }

    public Enemy createEnemy(int x, int y) {
        if (enemyClass == Goomba.class) {
            return new Goomba(x, y);
        }
        else return new Goomba(x, y);
    }
}
