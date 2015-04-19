package net.kalinovcic.ld32;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.input.Keyboard;

public class TitleStage implements Stage
{
    public TitleStage()
    {
        
    }
    
    @Override
    public void update(double timeDelta)
    {
        while (Keyboard.next())
        {
            if (Keyboard.getEventKeyState() && Keyboard.getEventKey() == Keyboard.KEY_T)
            {
                StageManager.stages.push(new TutorialStage1());
                AudioPlayer.playWaveSound("shoot");
                break;
            }
            if (Keyboard.getEventKeyState() && Keyboard.getEventKey() == Keyboard.KEY_P)
            {
                StageManager.stages.push(new GameStage());
                AudioPlayer.playWaveSound("shoot");
                break;
            }
            if (Keyboard.getEventKeyState() && Keyboard.getEventKey() == Keyboard.KEY_S)
            {
                LD32.sound = !LD32.sound;
                AudioPlayer.playWaveSound("shoot");
                break;
            }
            if (Keyboard.getEventKeyState() && Keyboard.getEventKey() == Keyboard.KEY_E)
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

        float x = 0;
        glColor3f(1.0f, 0.6f, 0.2f); LD32.font.drawString(100, x += 400, "T", 1.4f, -1.4f, TrueTypeFont.ALIGN_LEFT);
        glColor3f(1.0f, 1.0f, 1.0f); LD32.font.drawString(130, x, "utorial", 1.4f, -1.4f, TrueTypeFont.ALIGN_LEFT);
        
        glColor3f(1.0f, 0.6f, 0.2f); LD32.font.drawString(100, x += 40, "P", 1.4f, -1.4f, TrueTypeFont.ALIGN_LEFT);
        glColor3f(1.0f, 1.0f, 1.0f); LD32.font.drawString(130, x, "lay", 1.4f, -1.4f, TrueTypeFont.ALIGN_LEFT);
        
        glColor3f(1.0f, 0.6f, 0.2f); LD32.font.drawString(100, x += 80, "S", 1.4f, -1.4f, TrueTypeFont.ALIGN_LEFT);
        glColor3f(1.0f, 1.0f, 1.0f); LD32.font.drawString(130, x, "ound: " + (LD32.sound ? "on" : "off"), 1.4f, -1.4f, TrueTypeFont.ALIGN_LEFT);
        
        glColor3f(1.0f, 0.6f, 0.2f); LD32.font.drawString(100, x += 40, "E", 1.4f, -1.4f, TrueTypeFont.ALIGN_LEFT);
        glColor3f(1.0f, 1.0f, 1.0f); LD32.font.drawString(130, x, "xit", 1.4f, -1.4f, TrueTypeFont.ALIGN_LEFT);

        glTranslatef(LD32.WW / 2, 200 + (float) Math.sin(System.currentTimeMillis() / 250.0) * 20, 0);
        glRotatef((float) Math.cos(System.currentTimeMillis() / 250.0) * 5, 0, 0, 1);
        glColor3f(1.0f, 0.6f, 0.2f); LD32.font.drawString(0, 0, "SpaceType", 3, -3, TrueTypeFont.ALIGN_CENTER);
    }
}
