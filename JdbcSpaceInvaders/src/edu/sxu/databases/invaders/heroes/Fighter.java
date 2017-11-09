//Fighter (strong) class
//Created by James Vanderhyde, 9 November 2017

package edu.sxu.databases.invaders.heroes;

import edu.sxu.databases.invaders.GameConstants;
import edu.sxu.databases.invaders.PlayerSprite;
import edu.sxu.databases.invaders.ShotSprite;

public class Fighter extends PlayerSprite
{
    private static final String imageBits = 
            "      o      "+
            "     ooo     "+
            "     ooo     "+
            " ooooooooooo "+
            "ooooooooooooo"+
            "ooooooooooooo"+
            " ooooooooooo ";
    private static final int imageBitsWidth = 13;
    private Sword sword;
    
    public Fighter()
    {
        super(imageBits,imageBitsWidth,0.25);
        sword = new Sword();
        sword.kill(); //start sheathed
    }

    @Override
    protected void motion(boolean left, boolean right, boolean up, boolean down)
    {
        final double speed = 80;
        if (!sword.isAlive())
        {
            if (left)
                this.addVelocity(-speed,0);
            if (right)
                this.addVelocity(speed,0);
            if (up)
                this.addVelocity(0,-speed);
            if (down)
                this.addVelocity(0,speed);
        }
    }

    @Override
    protected ShotSprite createShot()
    {
        sword = new Sword();
        return sword;
    }
    
    @Override
    protected void positionShot(ShotSprite shot, double x, double y)
    {
        shot.setPosition(x-1*GameConstants.PIXEL_SCALE, y-6*GameConstants.PIXEL_SCALE);
    }
    
    private static class Sword extends ShotSprite
    {
        public Sword()
        {
            super(" o  o  o ooo o ", 3, 0.2);
            this.setVelocity(0, 0);
        }
        
    }
    
}
