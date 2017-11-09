//Wizard (intelligent) class
//Created by James Vanderhyde, 9 November 2017

package edu.sxu.databases.invaders.heroes;

import edu.sxu.databases.invaders.GameConstants;
import edu.sxu.databases.invaders.PlayerSprite;
import edu.sxu.databases.invaders.ShotSprite;
import java.util.ArrayList;

public class Wizard extends PlayerSprite
{
    private static final String imageBits = 
            "      o      "+
            "     ooo     "+
            "o    ooo    o"+
            " ooooooooooo "+
            "ooooooooooooo"+
            "ooooooooooooo"+
            "ooooooooooooo";
    private static final int imageBitsWidth = 13;
    private final ArrayList<ShotSprite> shotsToBlowUp = new ArrayList<>();
    
    public Wizard()
    {
        super(imageBits,imageBitsWidth,1);
    }
    
    @Override
    protected ShotSprite createShot()
    {
        return new Fireball();
    }

    @Override
    protected void positionShot(ShotSprite shot, double x, double y)
    {
        shot.setPosition(x-2*GameConstants.PIXEL_SCALE, y-7*GameConstants.PIXEL_SCALE);
    }
    
    private void blowUpShot(ShotSprite shot)
    {
        this.shotsToBlowUp.add(shot);
    }
    
    @Override
    public void update(double time)
    {
        super.update(time);
        for (ShotSprite s:this.shotsToBlowUp)
        {
            //Blow up the shot
            Fireball extension = new Fireball();
            extension.setPosition(s.getBoundary().getMinX(), s.getBoundary().getMinY());
            this.addShot(extension);
        }
        this.shotsToBlowUp.clear();
    }

    private class Fireball extends ShotSprite
    {
        private static final String imageBits = 
                " ooo "+
                "ooooo"+
                "ooooo"+
                " ooo "+
                "  o  ";
        
        public Fireball()
        {
            super(imageBits, 5);
            this.setVelocity(0, -300);
        }
        
        @Override
        public void kill()
        {
            super.kill();
            if (!(this.getBoundary().getMaxY() < 0))
                Wizard.this.blowUpShot(this);
        }
    }
}
