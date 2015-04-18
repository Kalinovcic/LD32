package net.kalinovcic.ld32;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Dictionary
{
    public static List<String> words = new ArrayList<String>();
    
    public static void load()
    {
        try
        {
            BufferedReader bR = new BufferedReader(new FileReader("res/dict.txt"));
            String word;
            while ((word = bR.readLine()) != null)
            {
                char fc = word.charAt(0);
                words.add(word);
            }
            bR.close();
        }
        catch (IOException e)
        {
            LD32.report("Failed to load dictionary.", e);
        }
    }
}
