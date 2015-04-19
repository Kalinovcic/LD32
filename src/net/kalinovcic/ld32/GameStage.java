package net.kalinovcic.ld32;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
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
    
    public int lives = 3;
    public int missiles = 0;
    
    private double spawnCountdown = spawnTime;
    private long spawnCount;

    private long sector = 0;

    private double calmTimer;
    private List<Enemy> missileVictims = new ArrayList<Enemy>();
    private double missileCooldown;
    
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
            
            calmTimer = 2.0;
            spawnCountdown = 0;
        }
        
        spawnTime = Math.max(5 - Math.log10(sector) * 3, 1);
        maxSpeed = Math.min(150 + Math.log10(sector) * 50, 300.0);
        targetSpawns = 5 + (long) (Math.log10(sector) * 12);
        
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
                lives--;
                
                if (lives == 0)
                {
                    StageManager.stages.pop();
                    break;
                }
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
                    if (LD32.random.nextInt(10) == 0)
                    {
                        if (LD32.random.nextInt(2) == 0)
                            behavior = PickupLifeBehavior.instance;
                        else
                            behavior = PickupMissileBehavior.instance;
                    }
                    else
                    {
                        if (sector >= 2 && (LD32.random.nextInt(Math.max(10 - (int) (sector / 5), 2)) == 0))
                            behavior = SpawnerBehavior.instance;
                        else if (sector >= 3 && (LD32.random.nextInt(Math.max(15 - (int) (sector / 5), 2)) == 0))
                            behavior = BombBehavior.instance;
                    }
                    Enemy e = new Enemy(this, word, Math.max((float) maxSpeed - 30 * word.length(), 50), behavior);
                    if (!e.isPickup) spawnCount++;
                    alive.add(e);
                    enemies.set(word.charAt(0) - 'a', e);
                }
            spawnCountdown += spawnTime;
        }
        
        while (Keyboard.next())
        {
            if (Keyboard.getEventKeyState() && (Keyboard.getEventKey() == Keyboard.KEY_F5))
            {
                for (char i = 'a'; i <= 'z'; i++)
                    enemies.set(i - 'a', null);
                alive.clear();
                bullets.clear();

                sector++;
                spawnCount = 0;
                calmTimer = 2.0;
            }
            if (missiles > 0 && missileVictims.size() == 0 && Keyboard.getEventKeyState() && (Keyboard.getEventKey() == Keyboard.KEY_LCONTROL || Keyboard.getEventKey() == Keyboard.KEY_RCONTROL))
            {
                for (char i = 'a'; i <= 'z'; i++)
                {
                    Enemy e = enemies.get(i - 'a');
                    if (e != null)
                        missileVictims.add(e);
                    enemies.set(i - 'a', null);
                }
                selected = null;
                
                selected = null;
                missileCooldown = 0;
                missiles--;
                continue;
            }
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
                }
                else
                {
                    AudioPlayer.setPitch(0.5f);
                    AudioPlayer.playWaveSound("shoot");
                }
            }
        }
        
        if (missileVictims.size() > 0)
        {
            missileCooldown -= timeDelta;
            while (missileCooldown < 0)
            {
                missileCooldown += 0.05;
                
                Iterator<Enemy> it = missileVictims.iterator();
                while (it.hasNext())
                {
                    Enemy e = it.next();
                    bullets.add(new Bullet(e.word.charAt(0), player.x, player.y, e, 800.0f));
                    
                    e.word = e.word.substring(1);
                    if (e.word.length() == 0)
                    {
                        e.alive = false;
                        it.remove();
                    }
                }
            }
        }
    }
    
    @Override
    public void render()
    {
        glColor3f(1, 1, 1);
        glBindTexture(GL_TEXTURE_2D, LD32.textureBG);
        
        float yoff = System.currentTimeMillis() % 60000 / 60000.0f;
        
        glBegin(GL_QUADS);
        glTexCoord2f(0.5f + yoff, 0.0f); glVertex2f(0.0f, 0.0f);
        glTexCoord2f(0.5f + yoff, 1.0f); glVertex2f(LD32.WW, 0.0f);
        glTexCoord2f(0.0f + yoff, 1.0f); glVertex2f(LD32.WW, LD32.WH);
        glTexCoord2f(0.0f + yoff, 0.0f); glVertex2f(0.0f, LD32.WH);
        glEnd();
        
        player.render();
        for (Bullet b : bullets)
            b.render();
        
        for (Enemy e : alive)
            if (e != selected)
                e.render();
        if (selected != null)
            selected.renderSpecial();
        
        // GUI

        glColor3f(1.0f, 1.0f, 0.8f);
        
        if (calmTimer > 0)
            LD32.font.drawString(LD32.WW / 2, LD32.WH / 2, "Sector " + sector, 2.4f, -2.4f, TrueTypeFont.ALIGN_CENTER);
        else
            LD32.font.drawString(5, LD32.WH - 5, "Sector: " + sector + " - " + (int) ((spawnCount / (double) targetSpawns) * 100) + "%", 0.9f, -0.9f);

        LD32.font.drawString(5, LD32.WH - 35, "maxs: " + maxSpeed, 0.9f, -0.9f);
        LD32.font.drawString(5, LD32.WH - 65, "time: " + spawnTime, 0.9f, -0.9f);
        LD32.font.drawString(5, LD32.WH - 95, "target: " + targetSpawns, 0.9f, -0.9f);
        
        float x = LD32.WW - 37;
        float y = LD32.WH - 37;
        
        glColor3f(1.0f, 1.0f, 1.0f);
        glBindTexture(GL_TEXTURE_2D, LD32.textureLife);
        
        glBegin(GL_QUADS);
        for (int i = 0; i < (lives <= 5 ? lives : 1); i++)
        {
            glTexCoord2f(0.0f, 0.0f); glVertex2f(x - i * 35, y);
            glTexCoord2f(1.0f, 0.0f); glVertex2f(x + 32 - i * 35, y);
            glTexCoord2f(1.0f, 1.0f); glVertex2f(x + 32 - i * 35, y + 32);
            glTexCoord2f(0.0f, 1.0f); glVertex2f(x - i * 35, y + 32);
        }
        glEnd();
        
        if (lives > 5) LD32.font.drawString(x - 40, LD32.WH - 5, lives + "", 0.9f, -0.9f);
        
        glColor3f(1.0f, 1.0f, 1.0f);
        glBindTexture(GL_TEXTURE_2D, LD32.textureMissile);
        
        glBegin(GL_QUADS);
        y -= 35;
        for (int i = 0; i < (missiles <= 5 ? missiles : 1); i++)
        {
            glTexCoord2f(0.0f, 0.0f); glVertex2f(x - i * 35, y);
            glTexCoord2f(1.0f, 0.0f); glVertex2f(x + 32 - i * 35, y);
            glTexCoord2f(1.0f, 1.0f); glVertex2f(x + 32 - i * 35, y + 32);
            glTexCoord2f(0.0f, 1.0f); glVertex2f(x - i * 35, y + 32);
        }
        glEnd();
        
        if (missiles > 5) LD32.font.drawString(x - 40, LD32.WH - 40, missiles + "", 0.9f, -0.9f);
    }
}
