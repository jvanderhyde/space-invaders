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
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class InvadersGame extends javafx.application.Application
{
    public static void main(String[] args) 
    {
        launch(args);
    }
 
    @Override
    public void start(Stage theStage) 
    {
        theStage.setTitle("Invaders");
        Group root = new Group();
        Scene theScene = new Scene( root );
        theStage.setScene( theScene );
        
        Sprite player = new PlayerSprite();
        player.setPosition(10, 350);

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

        Canvas canvas = new Canvas( 400, 400 );
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
                
                double t = (currentNanoTime - startNanoTime) / 1e9; 

                double x = 180 + 128 * Math.cos(t);
                double y = 180 + 128 * Math.sin(t);

                player.setVelocity(0,0);
                if (input.contains("LEFT"))
                    player.addVelocity(-50,0);
                if (input.contains("RIGHT"))
                    player.addVelocity(50,0);
                
                player.update(elapsedTime);
                
                //clear canvas
                gc.setFill( Color.BLACK );
                gc.fillRect(0, 0, 1000, 1000);
                //draw earth and sun
                gc.setFill( Color.BLUE );
                gc.fillOval(x, y, 40, 40);
                gc.setFill( Color.ORANGE );
                gc.fillOval(160, 160, 80, 80);
                
                player.render(gc);
            }
        }.start();
        
        theStage.show();
    }    

}
