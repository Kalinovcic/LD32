package net.kalinovcic.ld32;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.input.Keyboard;

public class CongratulationsStage implements Stage
{
    private long sector;
    
    public CongratulationsStage(long sector)
    {
        this.sector = sector;
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

        glColor3f(1.0f, 1.0f, 0.9f);
        LD32.font.drawString(LD32.WW / 2, 200, "Congratulations!", 2.4f, -2.4f, TrueTypeFont.ALIGN_CENTER);
        glColor3f(1.0f, 1.0f, 1.0f);
        LD32.font.drawString(LD32.WW / 2, 260, "You got to sector " + sector + ".", 1, -1, TrueTypeFont.ALIGN_CENTER);

        glTranslatef(0, (float) Math.sin(System.currentTimeMillis() / 250.0) * 4, 0);
        glColor3f(1.0f, 1.0f, 1.0f);
        LD32.font.drawString(LD32.WW / 2, LD32.WH - 100, "continue [space]", 1, -1, TrueTypeFont.ALIGN_CENTER);
    }
}
