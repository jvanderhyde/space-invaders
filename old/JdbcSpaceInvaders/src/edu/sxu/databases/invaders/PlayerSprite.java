//Sprite for the player character
//Created by James Vanderhyde, 4 November 2017

package edu.sxu.databases.invaders;

import java.util.Collection;
import java.util.HashSet;
import javafx.geometry.Rectangle2D;
import edu.sxu.databases.invaders.heroes.*;

public abstract class PlayerSprite extends PixelSprite
{
    private boolean readyToFire = true;
    private final double minTimeBetweenShots;
    private double timeUntilNextShot = 0;
    
    private final HashSet<ShotSprite> shots = new HashSet<>();
    
    public static PlayerSprite createPlayer(int type)
    {
        if ((type!=1) && (type!=2) && (type!=3))
            throw new IllegalArgumentException("Type must be 1, 2, or 3. ("+type+")");
        if (type==1)
            return new Fighter();
        else if (type==2)
            return new Rogue();
        else
            return new Wizard();
    }
    
    protected PlayerSprite(String pixels, int width, double minTimeBetweenShots)
    {
        super(pixels,width);
        this.minTimeBetweenShots = minTimeBetweenShots;
    }
    
    public void update(double time, boolean left, boolean right, boolean up, boolean down, boolean fire1, boolean fire2)
    {
        this.setVelocity(0,0);
        this.motion(left,right,up,down);
        
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
    
    protected void motion(boolean left, boolean right, boolean up, boolean down)
    {
        if (left)
            this.addVelocity(-50,0);
        if (right)
            this.addVelocity(50,0);
    }

    private void fireShot()
    {
        timeUntilNextShot = minTimeBetweenShots;
        readyToFire = false;
        
        ShotSprite shot = this.createShot();
        Rectangle2D bd = this.getBoundary();
        double x = (bd.getMinX()+bd.getMaxX())/2;
        double y = bd.getMinY();
        //System.out.println("Fire! ("+x+","+y+")");
        this.positionShot(shot,x,y);
        addShot(shot);
    }

    protected void addShot(ShotSprite shot)
    {
        shots.add(shot);
    }
    
    protected abstract ShotSprite createShot();
    
    protected void positionShot(ShotSprite shot, double x, double y)
    {
        shot.setPosition(x, y);
    }
    
    public Collection<ShotSprite> getShots()
    {
        return shots;
    }
}
