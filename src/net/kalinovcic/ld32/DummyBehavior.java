package net.kalinovcic.ld32;

import static org.lwjgl.opengl.GL11.*;

public class DummyBehavior implements Behavior
{
    private int texture;
    private float size;
    private float r, g, b;
    private float x, y;
    private boolean isp;
    
    public DummyBehavior(int texture, float size, float r, float g, float b, float x, float y, boolean isp)
    {
        this.texture = texture;
        this.size = size;
        this.r = r; this.g = g; this.b = b;
        this.x = x; this.y = y;
        this.isp = isp;
    }
    
    @Override
    public int getTexture()
    {
        return texture;
    }
    
    @Override
    public float getSize()
    {
        return size;
    }
    
    @Override
    public float getSpeedMul()
    {
        return 0;
    }
    
    @Override
    public void labelColor()
    {
        glColor3f(r, g, b);
    }
    
    @Override
    public void init(Enemy enemy)
    {
        enemy.x = x;
        enemy.y = y;
        enemy.isPickup = isp;
        if (isp) enemy.ang = 0;
    }
    
    @Override
    public void update(Enemy enemy, double timeDelta) {}
}
