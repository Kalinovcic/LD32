package net.kalinovcic.ld32;

import static org.lwjgl.opengl.GL11.*;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

public class TextureLoader
{
    public static int load(String resource)
    {
        return load(resource, 0, 0, -1, -1);
    }
    
    public static int load(String resource, int offx, int offy, int width, int height)
    {
        try
        {
            InputStream fis = LD32.class.getResourceAsStream(resource);
            BufferedImage image = ImageIO.read(fis);
            fis.close();
            
            if (width == -1) width = image.getWidth();
            if (height == -1) height = image.getHeight();
            int[] pixels = image.getRGB(offx, offy, width, height, null, 0, width);
            boolean hasAlpha = image.getColorModel().hasAlpha();
            
            ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * 4);
            for(int y = 0; y < height; y++)
                for(int x = 0; x < width; x++)
                {
                    int pixel = pixels[y * width + x];
                    
                    buffer.put((byte)((pixel >> 16) & 0xFF));
                    buffer.put((byte)((pixel >> 8) & 0xFF));
                    buffer.put((byte)((pixel) & 0xFF));
                    if(hasAlpha)
                        buffer.put((byte)((pixel >> 24) & 0xFF));
                    else
                        buffer.put((byte)(0xFF));
                }
            buffer.flip();
            
            int texture = glGenTextures();
            glBindTexture(GL_TEXTURE_2D, texture);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
            return texture;
        }
        catch (IOException e)
        {
            LD32.report("Failed to load texture.", e);
            return 0;
        }
    }
}
