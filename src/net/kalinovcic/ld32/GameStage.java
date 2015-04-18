package net.kalinovcic.ld32;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;
import java.util.List;

public class GameStage implements Stage
{
    private Sprite player;
    private List<Enemy> enemies = new ArrayList<Enemy>();

    private double spawnTime = 2;
    private double spawnCountdown = spawnTime;
    
    public GameStage()
    {
        player = new Sprite(LD32.texturePL, 180, LD32.WH - 40, 64, 64, 0.0f);
    }
    
    @Override
    public void update(double timeDelta)
    {
        List<Enemy> removeList = new ArrayList<Enemy>();
        for (Enemy e : enemies)
        {
            e.update(timeDelta);
            if (e.y > (LD32.WH + e.h / 2.0))
                removeList.add(e);
        }
        enemies.removeAll(removeList);
        
        spawnCountdown -= timeDelta;
        while (spawnCountdown < 0)
        {
            enemies.add(new Enemy("happiness", LD32.texturePL, 16 + LD32.random.nextFloat() * 344, 32, 100));
            spawnCountdown += spawnTime;
        }
    }
    
    @Override
    public void render()
    {
        glBindTexture(GL_TEXTURE_2D, 0);
        
        glBegin(GL_QUADS);
        glColor3f(1.0f, 1.0f, 0.0f);
        glVertex2f(0.0f, 0.0f);
        glVertex2f(LD32.WW, 0.0f);
        glColor3f(0.0f, 1.0f, 1.0f);
        glVertex2f(LD32.WW, LD32.WH);
        glVertex2f(0.0f, LD32.WH);
        glEnd();
        
        player.render();
        for (Enemy e : enemies)
            e.render();
    }
}
