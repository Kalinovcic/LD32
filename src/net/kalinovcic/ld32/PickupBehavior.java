package net.kalinovcic.ld32;

public abstract class PickupBehavior implements Behavior
{
    @Override
    public void init(Enemy pickup)
    {
        pickup.isPickup = true;
        pickup.ang = 0.0f;
    }
}
