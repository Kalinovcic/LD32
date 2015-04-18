package net.kalinovcic.ld32;

import java.util.Stack;

public class StageManager
{
    public static Stack<Stage> stages = new Stack<Stage>();

    public static void update(double timeDelta)
    {
        if (!stages.isEmpty())
            stages.peek().update(timeDelta);
    }
    
    public static void render()
    {
        if (!stages.isEmpty())
            stages.peek().render();
    }
}
