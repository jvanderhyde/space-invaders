//The invading aliens
//Created by James Vanderhyde, 4 November 2017

package edu.sxu.databases.invaders;

import javafx.geometry.Rectangle2D;

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
        bomb = new Bomb(); //bomb is never null
        bomb.kill();
    }
    
    public int points()
    {
        return 10;
    }
    
    public Bomb getBomb()
    {
        return bomb;
    }
    
    public void launchBomb()
    {
        bomb = new Bomb();
        Rectangle2D bd = AlienSprite.this.getBoundary();
        double x = (bd.getMinX()+bd.getMaxX())/2;
        double y = bd.getMaxY();
        bomb.setPosition(x, y);
    }
    
    public class Bomb extends PixelSprite
    {
        public Bomb()
        {
            super(" oo  oo  oo  oo ",2);
            this.setVelocity(0, 150);
        }
        
        @Override
        public void update(double time)
        {
            super.update(time);

            //kill when it goes off the screen
            if (this.getBoundary().getMaxY()>GameConstants.GROUND*GameConstants.PIXEL_SCALE)
                this.kill();
        }

    }
    
}
