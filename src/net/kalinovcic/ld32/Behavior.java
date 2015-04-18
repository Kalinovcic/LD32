package net.kalinovcic.ld32;

public interface Behavior
{
    public int getTexture();
    public float getSize();
    public float getSpeedMul();
    
    public void update(Enemy enemy, double timeDelta);
}
