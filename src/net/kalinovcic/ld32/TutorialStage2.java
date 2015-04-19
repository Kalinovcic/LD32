package net.kalinovcic.ld32;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.input.Keyboard;

public class TutorialStage2 implements Stage
{
    private Enemy enemy1 = new Enemy(null, "fighter", 0.0f, new DummyBehavior(LD32.textureBasic, 32, 1, 1, 1, LD32.WW / 2, 220, false));
    private Enemy enemy2 = new Enemy(null, "mothership", 0.0f, new DummyBehavior(LD32.textureSpawner, 64, 1, 1, 0, LD32.WW / 2, 430, false));
    private Enemy enemy3 = new Enemy(null, "beacon", 0.0f, new DummyBehavior(LD32.textureBomb, 96, 1, 0, 0, LD32.WW / 2, 640, false));
    
    public TutorialStage2()
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
                StageManager.stages.push(new TutorialStage3());
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
        LD32.font.drawString(40, 50, "Three types of enemies have been identified so far.", 1, -1, TrueTypeFont.ALIGN_LEFT);
        LD32.font.drawString(40, 100, "Fighter", 1.1f, -1.1f, TrueTypeFont.ALIGN_LEFT);
        LD32.font.drawString(100, 130, "The simplest enemy ship.", 1, -1, TrueTypeFont.ALIGN_LEFT);
        LD32.font.drawString(100, 155, "Its purpose is to transport alien troops.", 1, -1, TrueTypeFont.ALIGN_LEFT);
        LD32.font.drawString(40, 300, "Mothership", 1.1f, -1.1f, TrueTypeFont.ALIGN_LEFT);
        LD32.font.drawString(100, 330, "An enemy ship that spawns simple fighters over time.", 1, -1, TrueTypeFont.ALIGN_LEFT);
        LD32.font.drawString(100, 355, "It is believed that alien infestation specialists live on them.", 1, -1, TrueTypeFont.ALIGN_LEFT);
        LD32.font.drawString(40, 500, "Beacon", 1.1f, -1.1f, TrueTypeFont.ALIGN_LEFT);
        LD32.font.drawString(100, 530, "A beacon is a device that calls reinforcement when activated.", 1, -1, TrueTypeFont.ALIGN_LEFT);
        LD32.font.drawString(100, 555, "Destroy it before it activates!", 1, -1, TrueTypeFont.ALIGN_LEFT);
        
        glPushMatrix();
        glTranslatef(0, (float) Math.sin(System.currentTimeMillis() / 250.0) * 4, 0);
        enemy1.render();
        enemy2.render();
        enemy3.render();
        glPopMatrix();
        
        glTranslatef(0, (float) Math.sin(System.currentTimeMillis() / 250.0) * 4, 0);
        glColor3f(1.0f, 1.0f, 1.0f);
        LD32.font.drawString(LD32.WW - 20, LD32.WH - 20, "next [space]", 1, -1, TrueTypeFont.ALIGN_RIGHT);
    }
}
