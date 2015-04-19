package net.kalinovcic.ld32;

import static org.lwjgl.opengl.GL11.*;

public class Enemy extends Sprite
{
    public GameStage game;
    
    public char origc;
    public String word;
    public float speed;
    public float vely;

    public boolean removeMe = false;
    public boolean alive = true;
    public int health;
    
    public Behavior behavior;
    public double cooldown;
    
    public boolean isPickup = false;
    
    public Enemy(GameStage game, String word, float speed, Behavior behavior)
    {
        this(game, word, behavior.getSize() / 2 + LD32.random.nextFloat() * (LD32.WW - behavior.getSize()), -behavior.getSize(), speed, behavior);
    }
    
    public Enemy(GameStage game, String word, float x, float y, float speed, Behavior behavior)
    {
        super(behavior.getTexture(), x, y, behavior.getSize(), behavior.getSize(), 180.0f);
        this.game = game;
        this.origc = word.charAt(0);
        this.word = word;
        health = word.length();
        this.behavior = behavior;
        this.speed = vely = speed * behavior.getSpeedMul();
        behavior.init(this);
    }
    
    public void update(double timeDelta)
    {
        if (vely < speed)
        {
            vely += timeDelta * speed * 4;
            if (vely > speed) vely = speed;
        }
        if (vely > speed)
        {
            vely -= timeDelta * speed;
            if (vely < speed) vely = speed;
        }
        y += vely * timeDelta;
        
        behavior.update(this, timeDelta);
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
        if (y - this.h / 2 - h < 0) glTranslatef(0.0f, -(y - this.h / 2 - h), 0.0f);

        /*
        glBindTexture(GL_TEXTURE_2D, 0);
        glColor4f(0.3f, 0.3f, 0.3f, 0.7f);
        glBegin(GL_QUADS);
        glVertex2f(-w / 2.0f, 0.0f);
        glVertex2f(w / 2.0f, 0.0f);
        glVertex2f(w / 2.0f, -h);
        glVertex2f(-w / 2.0f, -h);
        glEnd();
        */
        
        behavior.labelColor();
        LD32.font.drawString(-4, 0.0f, word, 1.0f, -1.0f, TrueTypeFont.ALIGN_CENTER);
        
        glPopMatrix();
    }
    
    public void renderSpecial()
    {
        super.render();

        if (word.length() <= 0) return;
        
        glPushMatrix();
        glTranslatef(x, y - h / 2, 0.0f);

        float w = LD32.font.getTotalWidth(word) + 8;
        float h = LD32.font.getHeight();

        if (x - w / 2.0f < 0) glTranslatef(-(x - w / 2.0f), 0.0f, 0.0f);
        if (x + w / 2.0f > LD32.WW) glTranslatef(LD32.WW - (x + w / 2.0f), 0.0f, 0.0f);
        if (y - this.h / 2 - h < 0) glTranslatef(0.0f, -(y - this.h / 2 - h), 0.0f);
        
        /*
        glBindTexture(GL_TEXTURE_2D, 0);
        glBegin(GL_QUADS);
        glVertex2f(-w / 2.0f, 0.0f);
        glVertex2f(w / 2.0f, 0.0f);
        glVertex2f(w / 2.0f, -h);
        glVertex2f(-w / 2.0f, -h);
        glEnd();
        */

        glColor3f(0.4f, 0.4f, 1.0f);
        LD32.font.drawString(-4, 0.0f, word, 1, -1, TrueTypeFont.ALIGN_CENTER);
        
        glPopMatrix();
    }
}
