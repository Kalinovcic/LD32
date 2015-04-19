package net.kalinovcic.ld32;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.input.Keyboard;

public class TutorialStage1 implements Stage
{
    public TutorialStage1()
    {
        
    }
    
    @Override
    public void update(double timeDelta)
    {
        while (Keyboard.next())
        {
            if (Keyboard.getEventKeyState() && Keyboard.getEventKey() == Keyboard.KEY_SPACE)
            {
                StageManager.stages.pop();
                StageManager.stages.push(new TutorialStage2());
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


        glColor3f(1.0f, 1.0f, 1.0f);
        float x = 0;
        LD32.font.drawString(40, x += 250, "Aliens are invading the Milky Way!", 1, -1, TrueTypeFont.ALIGN_LEFT);
        LD32.font.drawString(40, x += 40, "You, along with countless other Earth fighters, are on a", 1, -1, TrueTypeFont.ALIGN_LEFT);
        LD32.font.drawString(40, x += 25, "mission to destroy all alien ships before they reach Earth.", 1, -1, TrueTypeFont.ALIGN_LEFT);
        LD32.font.drawString(40, x += 40, "However, your ship is equipped with an experimental weapon.", 1, -1, TrueTypeFont.ALIGN_LEFT);
        LD32.font.drawString(40, x += 25, "Your instruments assign a word to each enemy ship you come across.", 1, -1, TrueTypeFont.ALIGN_LEFT);
        LD32.font.drawString(40, x += 25, "Once the word is assigned, type that word using your TypeRifle 3000", 1, -1, TrueTypeFont.ALIGN_LEFT);
        LD32.font.drawString(40, x += 25, "to destroy them.", 1, -1, TrueTypeFont.ALIGN_LEFT);

        glTranslatef(0, (float) Math.sin(System.currentTimeMillis() / 250.0) * 4, 0);
        glColor3f(1.0f, 1.0f, 1.0f);
        LD32.font.drawString(LD32.WW - 20, LD32.WH - 20, "next [space]", 1, -1, TrueTypeFont.ALIGN_RIGHT);
    }
}
