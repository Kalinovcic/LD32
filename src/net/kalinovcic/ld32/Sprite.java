package net.kalinovcic.ld32;

import static org.lwjgl.opengl.GL11.*;

public class Sprite
{
    public int texture;
    public float x, y;
    public float w, h;
    public float ang;
    
    public Sprite(int texture, float x, float y, float w, float h, float ang)
    {
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.ang = ang;
    }
    
    public void render()
    {
        glPushMatrix();
        glTranslatef(x, y, 0.0f);
        glRotatef(ang, 0.0f, 0.0f, 1.0f);

        glColor3f(1.0f, 1.0f, 1.0f);
        glBindTexture(GL_TEXTURE_2D, texture);
        glBegin(GL_QUADS);
        glTexCoord2f(0.0f, 0.0f); glVertex2f(-w / 2.0f, -h / 2.0f);
        glTexCoord2f(1.0f, 0.0f); glVertex2f(w / 2.0f, -h / 2.0f);
        glTexCoord2f(1.0f, 1.0f); glVertex2f(w / 2.0f, h / 2.0f);
        glTexCoord2f(0.0f, 1.0f); glVertex2f(-w / 2.0f, h / 2.0f);
        glEnd();
        
        glPopMatrix();
    }
}
