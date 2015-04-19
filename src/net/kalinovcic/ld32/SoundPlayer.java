package net.kalinovcic.ld32;

import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundPlayer
{
    private static class CC
    {
        public int cc;
        public Clip[] clips;
    }
    
    private static Map<String, CC> clips = new HashMap<String, CC>();
    
    public static void playSound(String fn)
    {
        try
        {
            if (!clips.containsKey(fn))
            {
                CC cc = new CC();
                cc.cc = 0;
                cc.clips = new Clip[16];
                for (int i = 0; i < 16; i++)
                {
                    AudioInputStream audioIn = AudioSystem.getAudioInputStream(LD32.class.getResource(fn));
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioIn);
                    cc.clips[i] = clip;
                }
                clips.put(fn, cc);
            }
            
            CC cc = clips.get(fn);
            Clip clip = cc.clips[(++cc.cc) % cc.clips.length];
            if (clip.isRunning())
                clip.stop();
            clip.setFramePosition(0);
            clip.start();
        }
        catch (Exception e)
        {
            LD32.report("Failed to load sound \"" + fn + "\".", e);
        }
    }
}
