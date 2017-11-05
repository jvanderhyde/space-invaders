//Sprite that uses a string of bits to define the image
//Created by James Vanderhyde, 4 November 2017

package edu.sxu.databases.invaders;

import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class PixelSprite extends Sprite
{
    public PixelSprite(String pixels, int width)
    {
        Color monochrome = Color.WHITE;
        WritableImage im = new WritableImage(width,pixels.length()/width);
        for (int i=0; i<pixels.length(); i++)
        {
            if (pixels.charAt(i)==' ')
                im.getPixelWriter().setColor(i%width, i/width, Color.TRANSPARENT);
            else
                im.getPixelWriter().setColor(i%width, i/width, monochrome);
        }
        this.setImage(im);
    }
}
