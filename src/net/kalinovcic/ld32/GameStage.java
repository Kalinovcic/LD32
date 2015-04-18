package net.kalinovcic.ld32;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;
import java.util.List;

public class GameStage implements Stage
{
    private Sprite player;
    private List<Enemy> enemies = new ArrayList<Enemy>();

    private double spawnTime = 2;
    private double spawnCountdown = spawnTime;
    
    public GameStage()
    {
        player = new Sprite(LD32.texturePL, LD32.WW / 2, LD32.WH - 40, 64, 64, 0.0f);
        
        for (char i = 'a'; i <= 'z'; i++)
            enemies.add(null);
    }
    
    @Override
    public void update(double timeDelta)
    {
        for (char i = 'a'; i <= 'z'; i++)
        {
            Enemy e = enemies.get(i - 'a');
            if (e != null)
            {
                e.update(timeDelta);
                if (e.y > (LD32.WH + e.h / 2.0))
                    enemies.set(i - 'a', null);
            }
        }
        
        spawnCountdown -= timeDelta;
        while (spawnCountdown < 0)
        {
            String word = Dictionary.words.get(LD32.random.nextInt(Dictionary.words.size()));
            char fc = word.charAt(0);
            if (fc >= 'a' && fc <= 'z')
                if (enemies.get(fc - 'a') == null)
                    enemies.set(fc - 'a', new Enemy(word, LD32.texturePL, 16 + LD32.random.nextFloat() * (LD32.WW - 32), 32, 100));
            spawnCountdown += spawnTime;
        }
    }
    
    @Override
    public void render()
    {
        glBindTexture(GL_TEXTURE_2D, 0);
        
        glBegin(GL_QUADS);
        glColor3f(1.0f, 1.0f, 0.0f);
        glVertex2f(0.0f, 0.0f);
        glVertex2f(LD32.WW, 0.0f);
        glColor3f(0.0f, 1.0f, 1.0f);
        glVertex2f(LD32.WW, LD32.WH);
        glVertex2f(0.0f, LD32.WH);
        glEnd();
        
        player.render();
        for (char i = 'a'; i <= 'z'; i++)
        {
            Enemy e = enemies.get(i - 'a');
            if (e != null)
                e.render();
        }
    }
}
