//Main game class using JavaFX
//Created by James Vanderhyde, 4 November 2017

package edu.sxu.databases.invaders;

import java.util.Collection;
import java.util.HashSet;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import static edu.sxu.databases.invaders.GameConstants.*;
import java.util.Iterator;

public class InvadersGame extends javafx.application.Application
{
    public static void main(String[] args) 
    {
        InvadersGame.launch(args);
    }
    
    private final Collection<String> input = new HashSet<>();
    private GraphicsContext gc;
    private PlayerSprite player;
    private AlienSprite alien;
 
    @Override
    public void start(Stage theStage) 
    {
        theStage.setTitle("Invaders");
        Group root = new Group();
        Scene theScene = new Scene( root );
        theStage.setScene( theScene );
        
        theScene.setOnKeyPressed((KeyEvent e) ->
        {
            String code = e.getCode().toString();
            if ( !input.contains(code) )
                input.add( code );
        });
 
        theScene.setOnKeyReleased((KeyEvent e) ->
        {
            String code = e.getCode().toString();
            input.remove( code );
        });

        Canvas canvas = new Canvas((BOARD_WIDTH+BORDER_RIGHT+BORDER_LEFT)*PIXEL_SCALE, BOARD_HEIGHT*PIXEL_SCALE);
        root.getChildren().add( canvas );

        gc = canvas.getGraphicsContext2D();

        this.startLevel();
        
        theStage.show();
    }    
    
    private void startLevel()
    {
        player = new PlayerSprite();
        player.setPosition(10, 350);
        alien = new AlienSprite(2);
        alien.setPosition(10, 100);
 
        (new GameLoop()).start();
    }

    private class GameLoop extends AnimationTimer
    {
        long lastNanoTime = System.nanoTime();
        int numAliens = 1;

        @Override
        public void handle(long currentNanoTime)
        {
            // calculate time since last update.
            double elapsedTime = (currentNanoTime - lastNanoTime) / 1e9;
            lastNanoTime = currentNanoTime;

            player.update(elapsedTime,input.contains("LEFT"),input.contains("RIGHT"),
                    input.contains("UP"),input.contains("DOWN"),
                    input.contains("SPACE"),input.contains("SHIFT"));

            alien.update(elapsedTime);

            Iterator<ShotSprite> it = player.getShots().iterator();
            while (it.hasNext())
            {
                ShotSprite s = it.next();
                s.update(elapsedTime);
                if (!s.isAlive())
                    it.remove();
            }

            //Check for alien/shot collisions
            for (ShotSprite s:player.getShots())
            {
                if (s.intersects(alien))
                {
                    alien.kill();
                    s.kill();
                    numAliens--;
                }
            }
            
            //Check for level finished
            if (numAliens==0)
            {
                this.stop();
                startLevel();
            }

            //clear canvas
            gc.setFill( Color.BLACK );
            gc.fillRect(0, 0, 1000, 1000);

            player.render(gc);
            alien.render(gc);
            for (ShotSprite s:player.getShots())
                s.render(gc);
        }
    }

}
