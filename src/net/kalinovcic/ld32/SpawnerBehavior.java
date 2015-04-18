package net.kalinovcic.ld32;

public class SpawnerBehavior implements Behavior
{
    public static Behavior instance = new SpawnerBehavior();
    
    private SpawnerBehavior() {}

    @Override
    public int getTexture()
    {
        return LD32.texturePL;
    }
    
    @Override
    public float getSize()
    {
        return 64;
    }
    
    @Override
    public float getSpeedMul()
    {
        return 0.6f;
    }
    
    @Override
    public void update(Enemy enemy, double timeDelta)
    {
        enemy.cooldown -= timeDelta;
        while (enemy.cooldown < 0)
        {
            enemy.cooldown += 3;
            char c = (char) (LD32.random.nextInt('z' - 'a' + 1) + 'a');
            if (enemy.game.enemies.get(c - 'a') == null)
            {
                Enemy e = new Enemy(enemy.game, c + "", enemy.x, enemy.y, Math.max((float) enemy.game.maxSpeed * 0.2f, 50), BasicBehavior.instance);
                enemy.game.toAdd.add(e);
                enemy.game.enemies.set(c - 'a', e);
            }
        }
    }
}
