//Sprite for the bullet the player shoots
//Created by James Vanderhyde, 7 November 2017

package edu.sxu.databases.invaders;

public abstract class ShotSprite extends PixelSprite
{
    public ShotSprite(String pixels, int width)
    {
        super(pixels,width);
    }
    
    @Override
    public void update(double time)
    {
        super.update(time);
        
        //kill when it goes off the screen
        if (this.getBoundary().getMaxY()<0)
            this.kill();
    }
    
}
