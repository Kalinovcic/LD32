package net.kalinovcic.ld32;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.util.Random;

import javax.swing.JOptionPane;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import static org.lwjgl.opengl.GL11.*;

public class LD32
{
    public static int WW = 720;
    public static int WH = 720;
    public static Random random = new Random();
    public static TrueTypeFont font;
    public static int texturePL;
    public static int textureSpawner;
    public static int textureBomb;
    public static int textureBeam;
    
    public static void main(String[] args)
    {
        try
        {
            Display.setTitle("Integral type");
            Display.setResizable(false);
            Display.setVSyncEnabled(true);
            Display.setDisplayMode(new DisplayMode(WW, WH));
            Display.setFullscreen(false);
            Display.create();
            Keyboard.create();
        }
        catch (LWJGLException e)
        {
            LD32.report("Failed to create the display.", e);
        }
        
        Dictionary.load();

        texturePL = TextureLoader.load("res/pl.png");
        textureSpawner = TextureLoader.load("res/spawner.png");
        textureBomb = TextureLoader.load("res/bomb.png");
        textureBeam = TextureLoader.load("res/beam.png");
        
        try
        {
            Font rf = Font.createFont(Font.TRUETYPE_FONT, new File("res/FreeSans.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(rf);
            font = new TrueTypeFont(new Font("FreeSans", Font.PLAIN, 24), true, null);
        }
        catch (Exception e)
        {
            LD32.report("Failed to load the font.", e);
        }
        
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0.0f, WW, WH, 0, -1.0f, 1.0f);
        
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

        glDeleteTextures(texturePL);
        glDeleteTextures(textureSpawner);
        glDeleteTextures(textureBomb);
        glDeleteTextures(textureBeam);
        font.destroy();
        
        Keyboard.destroy();
        Display.destroy();
    }
    
    public static void report(String msg, Exception e)
    {
        JOptionPane.showMessageDialog(null, msg + "\nException: " + e, "Error!", JOptionPane.ERROR_MESSAGE);
        System.exit(1);
    }
}
