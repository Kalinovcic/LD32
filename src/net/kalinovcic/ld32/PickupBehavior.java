package net.kalinovcic.ld32;

public abstract class PickupBehavior implements Behavior
{
    @Override
    public void init(Enemy pickup)
    {
        pickup.isPickup = true;
        pickup.ang = 0.0f;
    }
    
    @Override
    public void update(Enemy pickup, double timeDelta)
    {
        if (pickup.health == 0)
            onPickup(pickup);
    }
    
    public abstract void onPickup(Enemy pickup);
}
