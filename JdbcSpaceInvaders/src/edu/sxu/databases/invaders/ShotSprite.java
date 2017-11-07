//Sprite for the bullet the player shoots
//Created by James Vanderhyde, 7 November 2017

package edu.sxu.databases.invaders;

public class ShotSprite extends PixelSprite
{
    private boolean alive = true;
    
    public ShotSprite()
    {
        super("oooo", 1);
        this.setVelocity(0, -100);
    }
    
    @Override
    public void update(double time)
    {
        super.update(time);
        
        //kill when it goes off the screen
        if (this.getBoundary().getMaxY()<0)
            this.alive = false;
    }
    
    public boolean isAlive()
    {
        return alive;
    }
    
}
