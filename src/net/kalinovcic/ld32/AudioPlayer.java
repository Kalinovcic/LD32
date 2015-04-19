package net.kalinovcic.ld32;

import java.io.BufferedInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.util.WaveData;

public class AudioPlayer
{   
    private static float defaultGain = 1.0f;
    private static float defaultPitch = 1.0f;
    
    private static float gain = 1.0f;
    private static float pitch = 1.0f;
    
    private static Map<String, Integer> buffers;
    private static Map<String, Integer> sources;
    private static Map<String, WaveData> waveData;
    
    public static void openPlayer()
    {
        try
        {
            AL.create();
        }
        catch (LWJGLException e)
        {
            e.printStackTrace();
        }

        buffers = new HashMap<String, Integer>();
        sources = new HashMap<String, Integer>();
        waveData = new HashMap<String, WaveData>();
    }
    
    public static void addWaveSound(String name, String filepath)
    {
        WaveData waveData = WaveData.create(new BufferedInputStream(ClassLoader.class.getResourceAsStream(filepath)));
        AudioPlayer.waveData.put(name, waveData);
        generateWaveSound(name, waveData);
    }
    
    private static void generateWaveSound(String name, WaveData data)
    {
        int buffer = AL10.alGenBuffers();
        AL10.alBufferData(buffer,data.format,data.data,data.samplerate);
        buffers.put(name, buffer);
        
        int source = AL10.alGenSources();
        AL10.alSourcei(source,AL10.AL_BUFFER,buffer);
        sources.put(name, source);
    }
    
    public static void setLooping(int source, boolean flag)
    {
        AL10.alSourcei(source,AL10.AL_LOOPING,flag ? AL10.AL_TRUE : AL10.AL_FALSE);
    }
    
    public static void setWaveGain(int source, float gain)
    {
        AL10.alSourcef(source,AL10.AL_GAIN,gain);
    }
    
    public static void setGain(float gain)
    {
        AudioPlayer.gain = gain;
    }
    
    public static void setPitch(float pitch)
    {
        AudioPlayer.pitch = pitch;
    }
    
    public static void setDefaultGain(float gain)
    {
        AudioPlayer.defaultGain = gain;
    }
    
    public static void setDefaultPitch(float pitch)
    {
        AudioPlayer.defaultPitch = pitch;
    }
    
    public static void playWaveSound(String name)
    {
        if (LD32.sound)
        {
            int source = sources.get(name);
            
            AL10.alSourcef(source, AL10.AL_PITCH, pitch);
            AL10.alSourcef(source, AL10.AL_GAIN, gain);
    
            gain = defaultGain;
            pitch = defaultPitch;
            
            AL10.alSourcePlay(source);
        }
    }
    
    public static void pauseWaveSound(String name)
    {
        int source = sources.get(name);
        
        AL10.alSourcePause(source);
    }
    
    public static void stopWaveSound(String name)
    {
        int source = sources.get(name);
        
        AL10.alSourceStop(source);
    }
    
    public static void closePlayer()
    {
        Set<String> names = sources.keySet();
        for(String name : names)
        {
            waveData.get(name).dispose();
            AL10.alDeleteSources(sources.get(name));
            AL10.alDeleteBuffers(buffers.get(name));
        }
        sources.clear();
        
        AL.destroy();
    }
}
