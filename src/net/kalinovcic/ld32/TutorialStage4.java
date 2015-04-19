package net.kalinovcic.ld32;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.input.Keyboard;

public class TutorialStage4 implements Stage
{
    public TutorialStage4()
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


        glColor3f(0.7f, 0.7f, 0.7f);
        LD32.font.drawString(LD32.WW / 2, LD32.WH / 2 - 25, "Please note that the words are being randomly picked out of", 1, -1, TrueTypeFont.ALIGN_CENTER);
        LD32.font.drawString(LD32.WW / 2, LD32.WH / 2, "the UNIX dictionary. Inappropriate words may come up.", 1, -1, TrueTypeFont.ALIGN_CENTER);
        LD32.font.drawString(LD32.WW / 2, LD32.WH / 2 + 25, "They are not meant to offend or insult anybody.", 1, -1, TrueTypeFont.ALIGN_CENTER);

        glTranslatef(0, (float) Math.sin(System.currentTimeMillis() / 250.0) * 4, 0);
        glColor3f(1.0f, 1.0f, 1.0f);
        LD32.font.drawString(LD32.WW / 2, LD32.WH - 100, "[space]", 1, -1, TrueTypeFont.ALIGN_CENTER);
    }
}
