package net.kalinovcic.ld32;

import static org.lwjgl.opengl.GL11.*;

public class BasicBehavior implements Behavior
{
    public static Behavior instance = new BasicBehavior();
    
    private BasicBehavior() {}

    @Override
    public int getTexture()
    {
        return LD32.textureBasic;
    }
    
    @Override
    public float getSize()
    {
        return 32;
    }
    
    @Override
    public float getSpeedMul()
    {
        return 1.0f;
    }
    
    @Override
    public void labelColor()
    {
        glColor3f(1.0f, 1.0f, 1.0f);
    }
    
    @Override
    public void init(Enemy enemy)
    {
        
    }
    
    @Override
    public void update(Enemy enemy, double timeDelta)
    {
        
    }
}
