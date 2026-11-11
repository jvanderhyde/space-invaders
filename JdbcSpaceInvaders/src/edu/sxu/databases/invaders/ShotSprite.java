//Sprite for the bullet the player shoots
//Created by James Vanderhyde, 7 November 2017

package edu.sxu.databases.invaders;

public class ShotSprite extends PixelSprite
{
    double lifeSpan;

    public ShotSprite(String pixels, int width, double lifeSpan)
    {
        super(pixels,width);
        this.lifeSpan = lifeSpan;
    }
    
    @Override
    public void update(double time)
    {
        super.update(time);
        
        //Kill when it goes off the screen
        if (this.getBoundary().getMaxY()<0)
            this.kill();
        
        //Kill after a specific time
        lifeSpan -= time;
        if (lifeSpan <= 0)
            this.kill();
    }
    
}
