package net.kalinovcic.ld32;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Dictionary
{
    public static List<String> words = new ArrayList<String>();
    
    public static void load()
    {
        try
        {
            InputStream is = LD32.class.getResourceAsStream("/res/dict.txt");
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader bR = new BufferedReader(isr);
            String word;
            while ((word = bR.readLine()) != null)
                words.add(word);
            bR.close();
        }
        catch (IOException e)
        {
            LD32.report("Failed to load dictionary.", e);
        }
    }
}
