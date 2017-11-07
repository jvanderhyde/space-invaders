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
    
    private static final String imageBits3 = 
            "  o     o  "+
            "   o   o   "+
            "  ooooooo  "+
            " oo ooo oo "+
            "ooooooooooo"+
            "o ooooooo o"+
            "o o     o o"+
            "   oo oo   ";
    private static final int imageBitsWidth3 = 11;
    
    private Bomb bomb;
    
    public AlienSprite(int type)
    {
        super(imageBits2,imageBitsWidth2);
        if ((type!=1) && (type!=2) && (type!=3))
            throw new IllegalArgumentException("Type must be 1, 2, or 3. ("+type+")");
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
