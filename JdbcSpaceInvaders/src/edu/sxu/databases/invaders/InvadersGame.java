

package edu.sxu.databases.invaders;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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

        Canvas canvas = new Canvas( 400, 400 );
        root.getChildren().add( canvas );

        final GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setFill( Color.RED );
        gc.setStroke( Color.BLACK );
        gc.setLineWidth(2);
        Font theFont = Font.font( "Times New Roman", FontWeight.BOLD, 48 );
        gc.setFont( theFont );
        gc.fillText( "Hello, World!", 60, 50 );
        gc.strokeText( "Hello, World!", 60, 50 );    
        
        final long startNanoTime = System.nanoTime();
 
        new AnimationTimer()
        {
            @Override
            public void handle(long currentNanoTime)
            {
                double t = (currentNanoTime - startNanoTime) / 1e9; 

                double x = 180 + 128 * Math.cos(t);
                double y = 180 + 128 * Math.sin(t);

                //clear canvas
                gc.setFill( Color.BLACK );
                gc.fillRect(0, 0, 1000, 1000);
                //draw earth and sun
                gc.setFill( Color.BLUE );
                gc.fillOval(x, y, 40, 40);
                gc.setFill( Color.ORANGE );
                gc.fillOval(160, 160, 80, 80);
            }
        }.start();
        
        theStage.show();
    }    

}
