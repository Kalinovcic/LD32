package net.kalinovcic.ld32;

public interface Behavior
{
    public int getTexture();
    public float getSize();
    public float getSpeedMul();
    
    public void init(Enemy enemy);
    public void update(Enemy enemy, double timeDelta);
}
