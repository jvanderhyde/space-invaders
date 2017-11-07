//The invading aliens
//Created by James Vanderhyde, 4 November 2017

package edu.sxu.databases.invaders;

public class AlienSprite extends PixelSprite
{
    private static final String imageBits2 = 
            "  o     o  "+
            "   o   o   "+
            "  ooooooo  "+
            " oo ooo oo "+
            "ooooooooooo"+
            "o ooooooo o"+
            "o o     o o"+
            "   oo oo   ";
    private static final int imageBitsWidth2 = 11;
    private static final double speed = 50;
    
    private double direction = 1;
    private Bomb bomb;
    
    public AlienSprite(double x, double y, int type)
    {
        super(imageBits2,imageBitsWidth2);
        if ((type!=1) && (type!=2) && (type!=3))
            throw new IllegalArgumentException("Type must be 1, 2, or 3. ("+type+")");
        
        this.setPosition(x, y);
        this.setVelocity(direction*speed, 0);
        bomb = new Bomb(x, y);
    }
    
    public void moveDown()
    {
        this.setPosition(this.getBoundary().getMinX(), this.getBoundary().getMinY()+GameConstants.ALIEN_HEIGHT*pixelMultiplier);
    }
    
    public void reverseDirection()
    {
        this.direction = -1*this.direction;
        this.setVelocity(direction*speed, 0);
    }
    
    @Override
    public void update(double time)
    {
        if (((this.direction>0) && this.getBoundary().getMaxX()>(GameConstants.BOARD_WIDTH-GameConstants.BORDER_RIGHT)*pixelMultiplier) ||
            ((this.direction<0) && this.getBoundary().getMinX()<(0+GameConstants.BORDER_LEFT)*pixelMultiplier))
        {
            this.moveDown();
            this.reverseDirection();
        }
        super.update(time);
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
