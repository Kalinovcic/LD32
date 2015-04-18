package net.kalinovcic.ld32;

import static org.lwjgl.opengl.GL11.*;

public class Enemy extends Sprite
{
    public char origc;
    public String word;
    public float speed;
    public float vely;

    public boolean removeMe = false;
    public boolean alive = true;
    public int health;
    
    public Enemy(String word, int texture, float x, float size, float speed)
    {
        super(texture, x, -size / 2.0f, size, size, 180.0f);
        this.origc = word.charAt(0);
        this.word = word;
        this.speed = vely = speed;
        health = word.length();
    }
    
    public void update(double timeDelta)
    {
        if (vely < speed)
        {
            vely += timeDelta * speed * 4;
            if (vely > speed) vely = speed;
        }
        y += vely * timeDelta;
    }
    
    @Override
    public void render()
    {
        super.render();

        if (word.length() <= 0) return;
        
        glPushMatrix();
        glTranslatef(x, y - h / 2, 0.0f);

        float w = LD32.font.getTotalWidth(word) + 8;
        float h = LD32.font.getHeight();

        if (x - w / 2.0f < 0) glTranslatef(-(x - w / 2.0f), 0.0f, 0.0f);
        if (x + w / 2.0f > LD32.WW) glTranslatef(LD32.WW - (x + w / 2.0f), 0.0f, 0.0f);

        glBindTexture(GL_TEXTURE_2D, 0);
        glColor4f(0.3f, 0.3f, 0.3f, 0.7f);
        glBegin(GL_QUADS);
        glVertex2f(-w / 2.0f, 0.0f);
        glVertex2f(w / 2.0f, 0.0f);
        glVertex2f(w / 2.0f, -h);
        glVertex2f(-w / 2.0f, -h);
        glEnd();
        
        glColor3f(1.0f, 1.0f, 1.0f);
        LD32.font.drawString(-4, 0.0f, word, 1.0f, -1.0f, TrueTypeFont.ALIGN_CENTER);
        
        glPopMatrix();
    }
}
