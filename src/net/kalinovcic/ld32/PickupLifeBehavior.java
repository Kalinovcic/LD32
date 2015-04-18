package net.kalinovcic.ld32;

import static org.lwjgl.opengl.GL11.*;

public class PickupLifeBehavior extends PickupBehavior
{
    public static Behavior instance = new PickupLifeBehavior();
    
    private PickupLifeBehavior() {}
    
    @Override
    public int getTexture()
    {
        return LD32.textureLife;
    }

    @Override
    public float getSize()
    {
        return 48;
    }

    @Override
    public float getSpeedMul()
    {
        return 1.0f;
    }

    @Override
    public void labelColor()
    {
        glColor3f(0.0f, 0.9f, 0.0f);
    }
    
    @Override
    public void update(Enemy pickup, double timeDelta)
    {
        if (pickup.health == 0)
            pickup.game.lives++;
    }
}
