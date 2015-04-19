package net.kalinovcic.ld32;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.input.Keyboard;

public class SectorClearStage implements Stage
{
    private double duration;
    private long shipsDestroyed;
    private long hits;
    private long misses;
    private long shipsDestroyedByMissiles;
    private long missilesFired;
    private long pickupsCollected;
    private long livesLost;
    
    public SectorClearStage(double duration, long shipsDestroyed, long hits, long misses,
            long shipsDestroyedByMissiles, long missilesFired, long pickupsCollected, long livesLost)
    {
        this.duration = duration;
        this.shipsDestroyed = shipsDestroyed;
        this.hits = hits;
        this.misses = misses;
        this.shipsDestroyedByMissiles = shipsDestroyedByMissiles;
        this.missilesFired = missilesFired;
        this.pickupsCollected = pickupsCollected;
        this.livesLost = livesLost;
    }
    
    @Override
    public void update(double timeDelta)
    {
        while (Keyboard.next())
        {
            if (Keyboard.getEventKeyState() && Keyboard.getEventKey() == Keyboard.KEY_SPACE)
            {
                StageManager.stages.pop();
                AudioPlayer.playWaveSound("shoot");
                break;
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

        glColor3f(1.0f, 1.0f, 0.9f);
        LD32.font.drawString(LD32.WW / 2, 95, "Sector clear!", 2.4f, -2.4f, TrueTypeFont.ALIGN_CENTER);
        
        float x = 0;
        glColor3f(1.0f, 1.0f, 1.0f); LD32.font.drawString(LD32.WW / 2 - 10, x += 180, "Time:", 1, -1, TrueTypeFont.ALIGN_RIGHT);
        glColor3f(1.0f, 0.4f, 0.1f); LD32.font.drawString(LD32.WW / 2 + 10, x, (long) duration + "s", 1, -1, TrueTypeFont.ALIGN_LEFT);
        
        glColor3f(1.0f, 1.0f, 1.0f); LD32.font.drawString(LD32.WW / 2 - 10, x += 35, "Alien ships destroyed:", 1, -1, TrueTypeFont.ALIGN_RIGHT);
        glColor3f(1.0f, 0.4f, 0.1f); LD32.font.drawString(LD32.WW / 2 + 10, x, shipsDestroyed + "", 1, -1, TrueTypeFont.ALIGN_LEFT);
        
        glColor3f(1.0f, 1.0f, 1.0f); LD32.font.drawString(LD32.WW / 2 - 10, x += 35, "Ships destroyed by missiles:", 1, -1, TrueTypeFont.ALIGN_RIGHT);
        glColor3f(1.0f, 0.4f, 0.1f); LD32.font.drawString(LD32.WW / 2 + 10, x, shipsDestroyedByMissiles + "", 1, -1, TrueTypeFont.ALIGN_LEFT);
        
        glColor3f(1.0f, 1.0f, 1.0f); LD32.font.drawString(LD32.WW / 2 - 10, x += 35, "Missiles fired:", 1, -1, TrueTypeFont.ALIGN_RIGHT);
        glColor3f(1.0f, 0.4f, 0.1f); LD32.font.drawString(LD32.WW / 2 + 10, x, missilesFired + "", 1, -1, TrueTypeFont.ALIGN_LEFT);
        
        glColor3f(1.0f, 1.0f, 1.0f); LD32.font.drawString(LD32.WW / 2 - 10, x += 35, "Hits:", 1, -1, TrueTypeFont.ALIGN_RIGHT);
        glColor3f(1.0f, 0.4f, 0.1f); LD32.font.drawString(LD32.WW / 2 + 10, x, hits + "", 1, -1, TrueTypeFont.ALIGN_LEFT);
        
        glColor3f(1.0f, 1.0f, 1.0f); LD32.font.drawString(LD32.WW / 2 - 10, x += 35, "Misses:", 1, -1, TrueTypeFont.ALIGN_RIGHT);
        glColor3f(1.0f, 0.4f, 0.1f); LD32.font.drawString(LD32.WW / 2 + 10, x, misses + "", 1, -1, TrueTypeFont.ALIGN_LEFT);
        
        glColor3f(1.0f, 1.0f, 1.0f); LD32.font.drawString(LD32.WW / 2 - 10, x += 35, "Pickups collected:", 1, -1, TrueTypeFont.ALIGN_RIGHT);
        glColor3f(1.0f, 0.4f, 0.1f); LD32.font.drawString(LD32.WW / 2 + 10, x, pickupsCollected + "", 1, -1, TrueTypeFont.ALIGN_LEFT);
        
        glColor3f(1.0f, 1.0f, 1.0f); LD32.font.drawString(LD32.WW / 2 - 10, x += 35, "Lives lost:", 1, -1, TrueTypeFont.ALIGN_RIGHT);
        glColor3f(1.0f, 0.4f, 0.1f); LD32.font.drawString(LD32.WW / 2 + 10, x, livesLost + "", 1, -1, TrueTypeFont.ALIGN_LEFT);

        glTranslatef(0, (float) Math.sin(System.currentTimeMillis() / 250.0) * 4, 0);
        glColor3f(1.0f, 1.0f, 1.0f);
        LD32.font.drawString(LD32.WW / 2, LD32.WH - 100, "continue [space]", 1, -1, TrueTypeFont.ALIGN_CENTER);
    }
}
