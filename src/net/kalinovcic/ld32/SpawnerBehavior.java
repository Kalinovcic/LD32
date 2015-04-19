package net.kalinovcic.ld32;

import static org.lwjgl.opengl.GL11.*;

public class SpawnerBehavior implements Behavior
{
    public static Behavior instance = new SpawnerBehavior();
    
    private SpawnerBehavior() {}

    @Override
    public int getTexture()
    {
        return LD32.textureSpawner;
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
    public void labelColor()
    {
        glColor3f(1.0f, 1.0f, 0.0f);
    }
    
    @Override
    public void init(Enemy enemy)
    {
        enemy.cooldown = 3;
    }
    
    @Override
    public void update(Enemy enemy, double timeDelta)
    {
        enemy.cooldown -= timeDelta;
        while (enemy.cooldown < 0)
        {
            enemy.cooldown += 2;
            char c = (char) (LD32.random.nextInt('z' - 'a' + 1) + 'a');
            if (enemy.alive && enemy.game.enemies.get(c - 'a') == null)
            {
                Enemy e = new Enemy(enemy.game, c + "", enemy.x, enemy.y, Math.max((float) enemy.game.maxSpeed * 0.2f, 50), BasicBehavior.instance);
                enemy.game.toAdd.add(e);
                enemy.game.enemies.set(c - 'a', e);
            }
        }
    }
}
