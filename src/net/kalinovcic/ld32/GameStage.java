package net.kalinovcic.ld32;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

public class GameStage implements Stage
{
    private Sprite player;
    private Enemy selected;
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
                {
                    if (e == selected) selected = null;
                    enemies.set(i - 'a', null);
                }
            }
        }
        
        spawnCountdown -= timeDelta;
        while (spawnCountdown < 0)
        {
            String word = Dictionary.words.get(LD32.random.nextInt(Dictionary.words.size()));
            boolean valid = word.length() > 0;
            for (int i = 0; i < word.length(); i++)
                if (word.charAt(i) < 'a' || word.charAt(i) > 'z')
                {
                    valid = false;
                    break;
                }
            if (valid)
                if (enemies.get(word.charAt(0) - 'a') == null)
                    enemies.set(word.charAt(0) - 'a', new Enemy(word, LD32.texturePL, 16 + LD32.random.nextFloat() * (LD32.WW - 32), 32, 300 / word.length()));
            spawnCountdown += spawnTime;
        }
        
        while (Keyboard.next())
        {
            char c = Keyboard.getEventCharacter();
            if (c < 'a' || c > 'z') continue;
            if (selected == null)
                selected = enemies.get(c - 'a');
            if (selected != null)
            {
                if (selected.word.charAt(0) == c)
                {
                    selected.word = selected.word.substring(1);
                    if (selected.word.length() == 0)
                    {
                        enemies.set(selected.origc - 'a', null);
                        selected = null;
                    }
                    // TODO: plink!
                }
                else
                {
                    // TODO: beep beep!
                }
            }
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
