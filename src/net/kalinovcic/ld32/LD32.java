package net.kalinovcic.ld32;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import static org.lwjgl.opengl.GL11.*;

public class LD32
{
    public static void main(String[] args)
    {
        try
        {
            Display.setTitle("Integral type");
            Display.setResizable(false);
            Display.setVSyncEnabled(true);
            Display.setDisplayMode(new DisplayMode(360, 720));
            Display.setFullscreen(false);
            Display.create();
        }
        catch (LWJGLException e)
        {
            LD32.report("Failed to create the display.", e);
        }
        
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0.0f, 360, 720, 0, -1.0f, 1.0f);
        
        glMatrixMode(GL_MODELVIEW);
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        
        StageManager.stages.push(new GameStage());
        
        long prevTime = System.currentTimeMillis();
        
        while (!(Display.isCloseRequested() || StageManager.stages.isEmpty()))
        {
            glClear(GL_COLOR_BUFFER_BIT);
            glLoadIdentity();
            
            long currTime = System.currentTimeMillis();
            long delta = currTime - prevTime;
            prevTime = currTime;

            StageManager.update(delta / 1000.0);
            StageManager.render();
            
            Display.update();
            Display.sync(60);
        }
        
        Display.destroy();
    }
    
    public static void report(String msg, Exception e)
    {
        
    }
}
