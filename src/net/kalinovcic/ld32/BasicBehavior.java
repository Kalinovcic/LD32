package net.kalinovcic.ld32;

public class BasicBehavior implements Behavior
{
    public static Behavior instance = new BasicBehavior();
    
    private BasicBehavior() {}

    @Override
    public int getTexture()
    {
        return LD32.texturePL;
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
    public void update(Enemy enemy, double timeDelta)
    {
        
    }
}
