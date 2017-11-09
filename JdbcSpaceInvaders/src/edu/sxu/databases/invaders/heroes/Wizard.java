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
        double lifeSpan = 0.2;
        for (ShotSprite s:this.shotsToBlowUp)
        {
            //Blow up the shot
            ShotSprite up, left, right;
            up = new ShotSprite("ooo",1,lifeSpan);
            up.setPosition(s.getBoundary().getMinX(), s.getBoundary().getMinY());
            up.setVelocity(0, -400);
            left = new ShotSprite("ooo",3,lifeSpan);
            left.setPosition(s.getBoundary().getMinX(), s.getBoundary().getMinY());
            left.setVelocity(-400, 0);
            right = new ShotSprite("ooo",3,lifeSpan);
            right.setPosition(s.getBoundary().getMinX(), s.getBoundary().getMinY());
            right.setVelocity(400, 0);
            this.addShot(up);
            this.addShot(left);
            this.addShot(right);
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
            super(imageBits, 5, 5);
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
