package net.kalinovcic.ld32;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.input.Keyboard;

public class TutorialStage3 implements Stage
{
    private Enemy pickup1 = new Enemy(null, "life", 0.0f, new DummyBehavior(LD32.textureLife, 48, 0, 0.9f, 0, LD32.WW / 2, 220, true));
    private Enemy pickup2 = new Enemy(null, "missile", 0.0f, new DummyBehavior(LD32.textureMissile, 48, 0, 0.9f, 0, LD32.WW / 2, 430, true));
    
    public TutorialStage3()
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
                StageManager.stages.push(new TutorialStage4());
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
        LD32.font.drawString(40, 50, "In space you will also find resources from previous expeditions.", 1, -1, TrueTypeFont.ALIGN_LEFT);
        LD32.font.drawString(40, 100, "Extra life", 1.1f, -1.1f, TrueTypeFont.ALIGN_LEFT);
        LD32.font.drawString(100, 130, "If any enemy ships manage to escape,", 1, -1, TrueTypeFont.ALIGN_LEFT);
        LD32.font.drawString(100, 155, "an extra life will save you.", 1, -1, TrueTypeFont.ALIGN_LEFT);
        LD32.font.drawString(40, 300, "Missile", 1.1f, -1.1f, TrueTypeFont.ALIGN_LEFT);
        LD32.font.drawString(100, 330, "Use this device to destroy all visible enemies", 1, -1, TrueTypeFont.ALIGN_LEFT);
        LD32.font.drawString(100, 355, "before you are overwhelmed.", 1, -1, TrueTypeFont.ALIGN_LEFT);
        LD32.font.drawString(40, 500, "Your lives and missiles are displayed in the bottom right corner.", 1, -1, TrueTypeFont.ALIGN_LEFT);
        
        glPushMatrix();
        glTranslatef(0, (float) Math.sin(System.currentTimeMillis() / 250.0) * 4, 0);
        pickup1.render();
        pickup2.render();
        glPopMatrix();
        

        float x = LD32.WW - 37;
        float y = LD32.WH - 37;
        
        glColor3f(1.0f, 1.0f, 1.0f);
        glBindTexture(GL_TEXTURE_2D, LD32.textureLife);
        
        glBegin(GL_QUADS);
        for (int i = 0; i < 4; i++)
        {
            glTexCoord2f(0.0f, 0.0f); glVertex2f(x - i * 35, y);
            glTexCoord2f(1.0f, 0.0f); glVertex2f(x + 32 - i * 35, y);
            glTexCoord2f(1.0f, 1.0f); glVertex2f(x + 32 - i * 35, y + 32);
            glTexCoord2f(0.0f, 1.0f); glVertex2f(x - i * 35, y + 32);
        }
        glEnd();
        
        glColor3f(1.0f, 1.0f, 1.0f);
        glBindTexture(GL_TEXTURE_2D, LD32.textureMissile);
        
        glBegin(GL_QUADS);
        y -= 35;
        for (int i = 0; i < 2; i++)
        {
            glTexCoord2f(0.0f, 0.0f); glVertex2f(x - i * 35, y);
            glTexCoord2f(1.0f, 0.0f); glVertex2f(x + 32 - i * 35, y);
            glTexCoord2f(1.0f, 1.0f); glVertex2f(x + 32 - i * 35, y + 32);
            glTexCoord2f(0.0f, 1.0f); glVertex2f(x - i * 35, y + 32);
        }
        glEnd();
        
        glTranslatef(0, (float) Math.sin(System.currentTimeMillis() / 250.0) * 4, 0);
        glColor3f(1.0f, 1.0f, 1.0f);
        LD32.font.drawString(LD32.WW / 2, 555, "next [space]", 1, -1, TrueTypeFont.ALIGN_CENTER);
    }
}
