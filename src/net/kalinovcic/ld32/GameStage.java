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
    public List<Enemy> enemies = new ArrayList<Enemy>();

    public Set<Enemy> alive = new HashSet<Enemy>();
    public Set<Enemy> toAdd = new HashSet<Enemy>();
    public Set<Bullet> bullets = new HashSet<Bullet>();

    public double spawnTime;
    public double maxSpeed;
    public long targetSpawns;
    
    private double spawnCountdown = spawnTime;
    private long spawnCount;

    private long sector = 0;

    private double calmTimer;
    private float currentR, currentG, currentB;
    private float targetR, targetG, targetB;
    
    public GameStage()
    {
        player = new Sprite(LD32.texturePL, LD32.WW / 2, LD32.WH - 40, 64, 64, 0.0f);
        
        for (char i = 'a'; i <= 'z'; i++)
            enemies.add(null);
    }
    
    @Override
    public void update(double timeDelta)
    {
        if (spawnCount >= targetSpawns && alive.isEmpty())
        {
            sector++;
            spawnCount = 0;

            targetR = LD32.random.nextFloat() * 0.1f + (LD32.random.nextBoolean() ? 0.3f : 0.0f) + 0.1f;
            targetG = LD32.random.nextFloat() * 0.1f + (LD32.random.nextBoolean() ? 0.3f : 0.0f) + 0.1f;
            targetB = LD32.random.nextFloat() * 0.1f + (LD32.random.nextBoolean() ? 0.3f : 0.0f) + 0.1f;
            
            calmTimer = 2.0;
        }

        currentR += (targetR - currentR) * (float) timeDelta;
        currentG += (targetG - currentG) * (float) timeDelta;
        currentB += (targetB - currentB) * (float) timeDelta;
        
        spawnTime = Math.max(5 - sector * 0.4, 1.0);
        maxSpeed = Math.min(100 + sector * 20, 600.0);
        targetSpawns = sector * 3;
        
        calmTimer -= timeDelta;
        if (calmTimer < 0) calmTimer = 0;
        else return;
        
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
                StageManager.stages.pop();
                break;
            }
            if (e.removeMe)
            {
                if (e == selected) selected = null;
                removeMeEnemies.add(e);
            }
        }
        alive.addAll(toAdd);
        toAdd.clear();
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
                if (enemies.get(word.charAt(0) - 'a') == null && spawnCount < targetSpawns)
                {
                    Behavior behavior = BasicBehavior.instance;
                    if (sector >= 2 && (LD32.random.nextInt(10) == 0))
                        behavior = SpawnerBehavior.instance;
                    else if (sector >= 3 && (LD32.random.nextInt(15) == 0))
                        behavior = BombBehavior.instance;
                    Enemy e = new Enemy(this, word, Math.max((float) maxSpeed - 30 * word.length(), 50), behavior);
                    spawnCount++;
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
                    bullets.add(new Bullet(c, player.x, player.y, selected, 800.0f));
                    
                    selected.word = selected.word.substring(1);
                    if (selected.word.length() == 0)
                    {
                        selected.alive = false;
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
        glColor3f(currentR, currentG, currentB);
        glVertex2f(LD32.WW, LD32.WH);
        glVertex2f(0.0f, LD32.WH);
        glEnd();
        
        player.render();
        for (Bullet b : bullets)
            b.render();
        for (Enemy e : alive)
            e.render();
        
        // GUI

        glColor3f(1.0f, 1.0f, 0.8f);
        
        if (calmTimer > 0)
            LD32.font.drawString(LD32.WW / 2, LD32.WH / 2, "Sector " + sector, 2.4f, -2.4f, TrueTypeFont.ALIGN_CENTER);
        else
            LD32.font.drawString(5, LD32.WH - 5, "Sector: " + sector + " - " + (int) ((spawnCount / (double) targetSpawns) * 100) + "%", 0.9f, -0.9f);
    }
}
