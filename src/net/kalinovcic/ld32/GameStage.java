package net.kalinovcic.ld32;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.lwjgl.input.Keyboard;

public class GameStage implements Stage
{
    private Sprite player;
    private Enemy selected;
    private List<Enemy> enemies = new ArrayList<Enemy>();
    
    private Set<Enemy> alive = new HashSet<Enemy>();
    private Set<Bullet> bullets = new HashSet<Bullet>();

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
        Set<Bullet> removeMeBullets = new HashSet<Bullet>();
        for (Bullet b : bullets)
        {
            b.update(timeDelta);
            if (b.removeMe)
                removeMeBullets.add(b);
        }
        bullets.removeAll(removeMeBullets);
        
        Set<Enemy> removeMeEnemies = new HashSet<Enemy>();
        for (Enemy e : alive)
        {
            e.update(timeDelta);
            if (e.alive && e.y > (LD32.WH + e.h / 2.0))
            {
                e.removeMe = true;
                enemies.set(e.origc - 'a', null);
            }
            if (e.removeMe)
            {
                if (e == selected) selected = null;
                removeMeEnemies.add(e);
            }
        }
        alive.removeAll(removeMeEnemies);
        
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
                {
                    Enemy e = new Enemy(word, LD32.texturePL, 16 + LD32.random.nextFloat() * (LD32.WW - 32), 32, Math.max(300 - 25 * word.length(), 50));
                    alive.add(e);
                    enemies.set(word.charAt(0) - 'a', e);
                }
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
                    bullets.add(new Bullet(c, player.x, player.y, selected, 1200.0f));
                    
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
        glColor3f(0.1f, 0.1f, 0.1f);
        glVertex2f(0.0f, 0.0f);
        glVertex2f(LD32.WW, 0.0f);
        glColor3f(0.1f, 0.1f, 0.3f);
        glVertex2f(LD32.WW, LD32.WH);
        glVertex2f(0.0f, LD32.WH);
        glEnd();
        
        /*
        if (selected != null)
        {
            double dx = selected.x - player.x;
            double dy = selected.y - player.y;
            double dist = Math.sqrt(dx * dx + dy * dy);
            double angle = Math.toDegrees(Math.atan2(dy, dx));
            
            glPushMatrix();
            glTranslatef(player.x, player.y, 0.0f);
            glRotatef((float) (-90 + angle), 0.0f, 0.0f, 1.0f);
            
            float beamyoff = (System.currentTimeMillis() % 250) / -250.0f * 16;
            
            glBindTexture(GL_TEXTURE_2D, LD32.textureBeam);
            glColor3f(1.0f, 1.0f, 1.0f);
            glBegin(GL_QUADS);
            glTexCoord2f(0.0f, beamyoff); glVertex2f(-8, 0);
            glTexCoord2f(1.0f, beamyoff); glVertex2f(8, 0);
            glTexCoord2f(1.0f, beamyoff + (float) dist / 16); glVertex2f(8, (float) dist);
            glTexCoord2f(0.0f, beamyoff + (float) dist / 16); glVertex2f(-8, (float) dist);
            glEnd();
            
            glPopMatrix();
        }
        */
        
        player.render();
        for (Bullet b : bullets)
            b.render();
        for (Enemy e : alive)
            e.render();
    }
}
