//The invading aliens
//Created by James Vanderhyde, 4 November 2017

package edu.sxu.databases.invaders;

import javafx.geometry.Rectangle2D;

public class AlienSprite extends PixelSprite
{
    private static final String imageBits1 = 
            "    oooo    "+
            " oooooooooo "+
            "oooooooooooo"+
            "ooo  oo  ooo"+
            "oooooooooooo"+
            "  ooo  ooo  "+
            " oo  oo  oo "+
            "  oo    oo  ";
    private static final int imageBitsWidth1 = 12;
    
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
            "   oo   "+
            "  oooo  "+
            " oooooo "+
            "oo oo oo"+
            "oooooooo"+
            "  o  o  "+
            " o oo o "+
            "o o  o o";
    private static final int imageBitsWidth3 = 8;
    
    private Bomb bomb;
    private final int points;
    
    private AlienSprite(String pixels, int width, int points)
    {
        super(pixels,width);
        bomb = new Bomb(); //bomb is never null
        bomb.kill();
        this.points = points;
    }
    
    public static AlienSprite createAlien(int type)
    {
        if ((type!=1) && (type!=2) && (type!=3))
            throw new IllegalArgumentException("Type must be 1, 2, or 3. ("+type+")");
        if (type==1)
            return new AlienSprite(imageBits1, imageBitsWidth1, 10);
        else if (type==2)
            return new AlienSprite(imageBits2, imageBitsWidth2, 20);
        else
            return new AlienSprite(imageBits3, imageBitsWidth3, 30);
    }
    
    public int points()
    {
        return points;
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
