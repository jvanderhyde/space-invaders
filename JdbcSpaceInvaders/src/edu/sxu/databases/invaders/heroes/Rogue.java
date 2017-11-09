//Rogue (charming joker) class
//Created by James Vanderhyde, 9 November 2017

package edu.sxu.databases.invaders.heroes;

import edu.sxu.databases.invaders.PlayerSprite;
import edu.sxu.databases.invaders.ShotSprite;

public class Rogue extends PlayerSprite
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
    
    public Rogue()
    {
        super(imageBits,imageBitsWidth,0.5);
    }
    
    @Override
    protected ShotSprite createShot()
    {
        return new Arrow();
    }

    private static class Arrow extends ShotSprite
    {
        public Arrow()
        {
            super("oooo", 1, 5);
            this.setVelocity(0, -300);
        }
    }
}
