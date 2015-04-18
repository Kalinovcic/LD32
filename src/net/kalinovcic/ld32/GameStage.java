package net.kalinovcic.ld32;

import static org.lwjgl.opengl.GL11.*;

public class GameStage implements Stage
{
    @Override
    public void update(double timeDelta)
    {
        
    }
    
    @Override
    public void render()
    {
        glBegin(GL_QUADS);
        glColor3f(1.0f, 1.0f, 0.0f);
        glVertex2f(0.0f, 0.0f);
        glVertex2f(360.0f, 0.0f);
        glColor3f(0.0f, 1.0f, 1.0f);
        glVertex2f(360.0f, 720.0f);
        glVertex2f(0.0f, 720.0f);
        glEnd();
    }
}
