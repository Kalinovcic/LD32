package net.kalinovcic.ld32;

public class Enemy extends Sprite
{
    private float speed;
    
    public Enemy(int texture, float x, float size, float speed)
    {
        super(texture, x, -size / 2.0f, size, size, 180.0f);
        this.speed = speed;
    }
    
    public void update(double timeDelta)
    {
        y += speed * timeDelta;
    }
}
