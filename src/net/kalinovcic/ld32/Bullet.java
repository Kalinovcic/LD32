package net.kalinovcic.ld32;

import static org.lwjgl.opengl.GL11.*;

public class Bullet extends Sprite
{
    public char wl;
    public Enemy target;
    public float speed;
    
    public boolean removeMe = false;
    
    public Bullet(char wl, float x, float y, Enemy target, float speed)
    {
        super(0, x, y, 32, 32, 0);
        this.wl = wl;
        this.target = target;
        this.speed = speed;
    }
    
    public void update(double timeDelta)
    {
        double dx = target.x - x;
        double dy = target.y - y;
        double pt = Math.sqrt(dx * dx + dy * dy);
        dx /= pt;
        dy /= pt;
        
        x += dx * speed * timeDelta;
        y += dy * speed * timeDelta;
        
        double ndx = target.x - x;
        double ndy = target.y - y;
        if (Math.signum(dx) != Math.signum(ndx) || Math.signum(dy) != Math.signum(ndy))
        {
            removeMe = true;
            target.vely -= 50;
            target.health--;
            if (target.health <= 0)
                target.removeMe = true;
        }
    }
    
    @Override
    public void render()
    {
        glPushMatrix();
        glTranslatef(x, y, 0.0f);
        glRotatef(ang, 0.0f, 0.0f, 1.0f);
        
        /*
        float w = LD32.font.getTotalWidth(wl + "") * 1.3f + 2;
        float h = LD32.font.getHeight() * 1.3f;

        glBindTexture(GL_TEXTURE_2D, 0);
        glColor4f(0.3f, 0.3f, 0.3f, 0.7f);
        glBegin(GL_QUADS);
        glVertex2f(-w / 2.0f, 0.0f);
        glVertex2f(w / 2.0f, 0.0f);
        glVertex2f(w / 2.0f, -h);
        glVertex2f(-w / 2.0f, -h);
        glEnd();
        */

        glColor3f(1.0f, 1.0f, 1.0f);
        LD32.font.drawString(-4, 0.0f, wl + "", 1.3f, -1.3f, TrueTypeFont.ALIGN_CENTER);
        
        glPopMatrix();
    }
}
