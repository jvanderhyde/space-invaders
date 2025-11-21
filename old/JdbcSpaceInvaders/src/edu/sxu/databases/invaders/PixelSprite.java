//Sprite that uses a string of bits to define the image
//Created by James Vanderhyde, 4 November 2017

package edu.sxu.databases.invaders;

import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class PixelSprite extends Sprite
{
    public static int pixelMultiplier = GameConstants.PIXEL_SCALE;
    
    public PixelSprite(String pixels, int width)
    {
        this.setImage(createPixelImage(pixels, width));
    }

    public static final WritableImage createPixelImage(String pixels, int width)
    {
        Color monochrome = Color.WHITE;
        WritableImage im = new WritableImage(pixelMultiplier*width,pixelMultiplier*pixels.length()/width);
        for (int i=0; i<pixels.length(); i++)
        {
            if (pixels.charAt(i)==' ')
                colorPixel(im, i%width, i/width, Color.TRANSPARENT);
            else
                colorPixel(im, i%width, i/width, monochrome);
        }
        return im;
    }
    
    private static void colorPixel(WritableImage im, int x, int y, Color c)
    {
        for (int i=0; i<pixelMultiplier; i++)
            for (int j=0; j<pixelMultiplier; j++)
                im.getPixelWriter().setColor(pixelMultiplier*x+j, pixelMultiplier*y+i, c);
    }
}
