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
        return 48;
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
        long rvv11 = (long) enemy.cooldown;
        enemy.cooldown -= timeDelta;
        long rvv21 = (long) enemy.cooldown;
        if (enemy.cooldown >= 0 && enemy.cooldown <= 3 && rvv11 != rvv21)
        {
            AudioPlayer.setGain(0.7f);
            AudioPlayer.setPitch(0.9f);
            AudioPlayer.playWaveSound("warn");
        }

        if (enemy.cooldown >= 0) enemy.texture = LD32.textureBomb;
        if (enemy.cooldown < 0) enemy.texture = LD32.textureBombIn;

        enemy.sppr = enemy.sppg = enemy.sppb = 1;
        if (enemy.cooldown <= 3)
            if ((int) (enemy.cooldown * 2) % 2 == 1)
            {
                enemy.sppr = 0;
                enemy.sppg = enemy.sppb = 0;
            }
        
        enemy.ang = (float) enemy.cooldown * 90;
        
        if (enemy.cooldown < 0 && enemy.cooldown + timeDelta >= 0)
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
