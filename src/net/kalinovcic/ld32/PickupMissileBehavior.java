package net.kalinovcic.ld32;

import static org.lwjgl.opengl.GL11.*;

public class PickupMissileBehavior extends PickupBehavior
{
    public static Behavior instance = new PickupMissileBehavior();
    
    private PickupMissileBehavior() {}
    
    @Override
    public int getTexture()
    {
        return LD32.textureMissile;
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
    public void onPickup(Enemy pickup)
    {
        pickup.game.missiles++;
    }
}
