//Sprite for the player character
//Created by James Vanderhyde, 4 November 2017

package edu.sxu.databases.invaders;

import java.util.Collection;
import java.util.HashSet;
import javafx.geometry.Rectangle2D;

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
    
    private boolean readyToFire = true;
    private final double minTimeBetweenShots = 0.5;
    private double timeUntilNextShot = 0;
    
    private HashSet<ShotSprite> shots = new HashSet<>();
    
    public PlayerSprite()
    {
        super(imageBits,imageBitsWidth);
    }
    
    public void update(double time, boolean left, boolean right, boolean up, boolean down, boolean fire1, boolean fire2)
    {
        this.setVelocity(0,0);
        if (left)
            this.addVelocity(-50,0);
        if (right)
            this.addVelocity(50,0);
        
        if (timeUntilNextShot > 0)
        {
            timeUntilNextShot -= time;
            readyToFire = false;
        }
        else if (fire1)
        {
            if (readyToFire)
                fireShot();
        }
        else
            readyToFire = true;
        
        this.update(time);
    }

    private void fireShot()
    {
        timeUntilNextShot = minTimeBetweenShots;
        readyToFire = false;
        
        ShotSprite shot = new ShotSprite();
        Rectangle2D bd = this.getBoundary();
        double x = (bd.getMinX()+bd.getMaxX())/2;
        double y = bd.getMinY();
        //System.out.println("Fire! ("+x+","+y+")");
        shot.setPosition(x, y);
        shots.add(shot);
    }
    
    public Collection<ShotSprite> getShots()
    {
        return shots;
    }
}
