//Main game class using JavaFX
//Created by James Vanderhyde

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

public class InvadersGame extends javafx.application.Application
{
    public static void main(String[] args) 
    {
        InvadersGame.launch(args);
    }
 
    @Override
    public void start(Stage theStage) 
    {
        theStage.setTitle("Invaders");
        Group root = new Group();
        Scene theScene = new Scene( root );
        theStage.setScene( theScene );
        
        PlayerSprite player = new PlayerSprite();
        player.setPosition(10, 350);
        AlienSprite alien = new AlienSprite(10,100,2);

        Collection<String> input = new HashSet<>();
 
        theScene.setOnKeyPressed((KeyEvent e) ->
        {
            String code = e.getCode().toString();
            
            // only add once... prevent duplicates
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

        final GraphicsContext gc = canvas.getGraphicsContext2D();

        final long startNanoTime = System.nanoTime();
 
        new AnimationTimer()
        {
            long lastNanoTime = startNanoTime;
            
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
                
                //clear canvas
                gc.setFill( Color.BLACK );
                gc.fillRect(0, 0, 1000, 1000);
                
                player.render(gc);
                alien.render(gc);
            }
        }.start();
        
        theStage.show();
    }    

}
