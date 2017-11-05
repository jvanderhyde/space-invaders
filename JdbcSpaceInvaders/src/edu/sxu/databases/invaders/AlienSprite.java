//The invading aliens
//Created by James Vanderhyde, 4 November 2017

package edu.sxu.databases.invaders;

public class AlienSprite extends PixelSprite
{
    private static final String imageBits1 = 
            "  o     o  "+
            "   o   o   "+
            "  ooooooo  "+
            " oo ooo oo "+
            "ooooooooooo"+
            "o ooooooo o"+
            "o o     o o"+
            "   oo oo   ";
    private static final int imageBitsWidth1 = 11;
    private static double speed = 10;
    
    private double direction = 1;
    private Bomb bomb;
    
    public AlienSprite(double x, double y, int type)
    {
        super(imageBits1,imageBitsWidth1);
        if ((type!=1) && (type!=2) && (type!=3))
            throw new IllegalArgumentException("Type must be 1, 2, or 3. ("+type+")");
        
        this.setPosition(x, y);
        this.setVelocity(direction*speed, 0);
        bomb = new Bomb(x, y);
    }
    
    public void moveDown()
    {
        this.setVelocity(0, 10*speed);
    }
    
    public void reverseDirection()
    {
        this.direction = -1*this.direction;
        this.setVelocity(direction*speed, 0);
    }
    
    public Bomb getBomb()
    {
        return bomb;
    }
    
    public class Bomb extends PixelSprite
    {
        private boolean destroyed = true; 
        
        public Bomb(double x, double y)
        {
            super(" oo  oo  oo  oo ",2);
            this.setPosition(x, y);
        }
        
        public boolean isDestroyed()
        {
            return destroyed;
        }
        
        public void setDestroyed(boolean destroyed)
        {
            this.destroyed = destroyed;
        }
    }
    
}
