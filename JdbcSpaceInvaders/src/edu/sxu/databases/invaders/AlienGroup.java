//The matrix of aliens
//Created by James Vanderhyde, 7 November 2017

package edu.sxu.databases.invaders;

import static edu.sxu.databases.invaders.GameConstants.*;
import static edu.sxu.databases.invaders.PixelSprite.pixelMultiplier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import javafx.geometry.Rectangle2D;

public class AlienGroup extends Sprite
{
    public static final int NUM_ALIENS_ACROSS = 11;//11
    public static final int NUM_ALIENS_DOWN = 5;//5
    public static final Random rng = new Random();
    
    private final ArrayList<AlienSprite> aliens = new ArrayList<>();
    private final ArrayList<AlienSprite> aliveAliens = new ArrayList<>();
    private Rectangle2D boundary;
    private double speed = 20;
    private double direction = 1;
    private double timeToNextBomb = 0;
    
    public AlienGroup()
    {
        int[] alienTypes = {3, 2, 2, 1, 1};
        for (int i=0; i<NUM_ALIENS_DOWN*NUM_ALIENS_ACROSS; i++)
            aliens.add(new AlienSprite(alienTypes[i/NUM_ALIENS_ACROSS]));
        aliveAliens.addAll(aliens);
        this.updateAlienLocations();
        this.calcBoundary();
        this.setVelocity(direction*speed, 0);
    }
    
    private void updateAlienLocations()
    {
        Rectangle2D bd = super.getBoundary();
        int index = 0;
        for (int j=0; j<NUM_ALIENS_DOWN; j++)
            for (int i=0; i<NUM_ALIENS_ACROSS; i++)
            {
                AlienSprite a = aliens.get(index);
                a.setPosition(bd.getMinX()+i*ALIEN_WIDTH*PIXEL_SCALE, bd.getMinY()+j*ALIEN_HEIGHT*PIXEL_SCALE);
                index++;
            }
    }
    
    public void increaseSpeed()
    {
        speed++;
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
        this.updateAlienLocations();
        this.calcBoundary();
        this.timeToNextBomb -= time;
    }
    
    private void moveDown()
    {
        this.setPosition(super.getBoundary().getMinX(), super.getBoundary().getMinY()+GameConstants.ALIEN_HEIGHT*pixelMultiplier);
    }
    
    private void reverseDirection()
    {
        this.direction = -1*this.direction;
        this.setVelocity(direction*speed, 0);
    }
    
    void checkBombSpawn(int level)
    {
        if (this.timeToNextBomb <= 0 && !aliveAliens.isEmpty())
        {
            //spawn bombs
            for (int i=0; i<level; i++)
            {
                AlienSprite bomber = aliveAliens.get(rng.nextInt(aliveAliens.size()));
                if (!bomber.getBomb().isAlive())
                    bomber.launchBomb();
            }
            
            //reset time
            this.timeToNextBomb += 0.5+rng.nextDouble();
        }
    }
    
    @Override
    public void render(javafx.scene.canvas.GraphicsContext gc)
    {
        for (AlienSprite a:aliens)
            a.render(gc);
    }
    
    public void invalidateBoundary(AlienSprite killedAlien)
    {
        aliveAliens.remove(killedAlien);
        this.calcBoundary();
    }
    
    private void calcBoundary()
    {
        double minX=BOARD_WIDTH, minY=BOARD_HEIGHT, maxX=0, maxY=0;
        boolean alive = false;
        for (AlienSprite a:aliens)
            if (a.isAlive())
            {
                Rectangle2D bd = a.getBoundary();
                if (bd.getMinX()<minX) minX=bd.getMinX();
                if (bd.getMaxX()>maxX) maxX=bd.getMaxX();
                if (bd.getMinY()<minY) minY=bd.getMinY();
                if (bd.getMaxY()>maxY) maxY=bd.getMaxY();
                alive = true;
            }  
        if (alive)
            this.boundary = new Rectangle2D(minX,minY,maxX-minX,maxY-minY);
    }
    
    @Override
    public Rectangle2D getBoundary()
    {
        return boundary;
    }

    public Collection<AlienSprite> getAliens()
    {
        return aliens;
    }

}
