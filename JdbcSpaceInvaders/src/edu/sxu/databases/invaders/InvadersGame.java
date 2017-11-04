

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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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
        
        WritableImage playerImage = new WritableImage(40,20);
        for (int i=0; i<40; i++) for (int j=0; j<20; j++)
            playerImage.getPixelWriter().setArgb(i, j, 0xffffffff);
        Sprite player = new Sprite();
        player.setImage(playerImage);
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
                
                if (input.contains("LEFT"))
                {
                    gc.setFill( Color.RED );
                    gc.setStroke( Color.WHITE );
                    gc.setLineWidth(2);
                    Font theFont = Font.font( "Times New Roman", FontWeight.BOLD, 48 );
                    gc.setFont( theFont );
                    gc.fillText( "Hello, World!", 60, 50 );
                    gc.strokeText( "Hello, World!", 60, 50 );
                }
                
                player.render(gc);
            }
        }.start();
        
        theStage.show();
    }    

}
