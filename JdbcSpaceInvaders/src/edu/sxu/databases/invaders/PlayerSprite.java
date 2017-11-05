//Sprite for the player character
//Created by James Vanderhyde, 4 November 2017

package edu.sxu.databases.invaders;

public class PlayerSprite extends PixelSprite
{
    private static final String imageBits = 
            "      o      "+
            "     ooo     "+
            "     ooo     "+
            " ooooooooooo "+
            "ooooooooooooo"+
            "ooooooooooooo"+
            "ooooooooooooo";
    private static final int imageBitsWidth = 13;
    
    public PlayerSprite()
    {
        super(imageBits,imageBitsWidth);
    }
}
