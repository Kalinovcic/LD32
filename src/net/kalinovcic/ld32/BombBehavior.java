package net.kalinovcic.ld32;

import static org.lwjgl.opengl.GL11.*;

public class BombBehavior implements Behavior
{
    public static Behavior instance = new BombBehavior();
    
    private BombBehavior() {}

    @Override
    public int getTexture()
    {
        return LD32.textureBomb;
    }
    
    @Override
    public float getSize()
    {
        return 32;
    }
    
    @Override
    public float getSpeedMul()
    {
        return 0.3f;
    }
    
    @Override
    public void labelColor()
    {
        glColor3f(1.0f, 0.0f, 0.0f);
    }
    
    @Override
    public void init(Enemy enemy)
    {
        enemy.cooldown = 20;
    }
    
    @Override
    public void update(Enemy enemy, double timeDelta)
    {
        enemy.w = (float) (32 + (1.0 - (enemy.cooldown / 12.0f)) * 64);
        enemy.h = (float) (32 + (1.0 - (enemy.cooldown / 12.0f)) * 64);
        
        enemy.cooldown -= timeDelta;
        if (enemy.cooldown < 0 && enemy.alive)
        {
            enemy.removeMe = true;
            
            for (int i = 0; i < 10; i++)
            {
                char c1 = (char) (LD32.random.nextInt('z' - 'a' + 1) + 'a');
                char c2 = (char) (LD32.random.nextInt('z' - 'a' + 1) + 'a');
                char c3 = (char) (LD32.random.nextInt('z' - 'a' + 1) + 'a');
                if (enemy.game.enemies.get(c1 - 'a') == null)
                {
                    float x = BasicBehavior.instance.getSize() / 2 + (i / 9.0f) * (LD32.WW - BasicBehavior.instance.getSize());
                    Enemy e = new Enemy(enemy.game, c1 + "" + c2 + c3, x, -BasicBehavior.instance.getSize(), Math.max((float) enemy.game.maxSpeed * 0.2f, 50), BasicBehavior.instance);
                    enemy.game.toAdd.add(e);
                    enemy.game.enemies.set(c1 - 'a', e);
                }
            }
        }
    }
}
