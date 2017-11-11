//Main game class using JavaFX
//Created by James Vanderhyde, 4 November 2017

package edu.sxu.databases.invaders;

import static edu.sxu.databases.invaders.GameConstants.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class InvadersGame extends javafx.application.Application
{
    public static void main(String[] args) 
    {
        InvadersGame.launch(args);
    }
    
    private final Collection<String> input = new HashSet<>();
    private GraphicsContext gc;
    private HUD hud;
    private PlayerSprite player;
    private AlienGroup alienMatrix;
    private PixelSprite ground;
    private int level = 0;
    private int playerScore = 0;
    private int playerType = 0;
 
    @Override
    public void start(Stage stage) 
    {
        stage.setTitle("Invaders");
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        
        scene.setOnKeyPressed((KeyEvent e) ->
        {
            String code = e.getCode().toString();
            if (!input.contains(code))
                input.add(code);
        });
 
        scene.setOnKeyReleased((KeyEvent e) ->
        {
            String code = e.getCode().toString();
            input.remove(code);
        });

        Canvas canvas = new Canvas((BOARD_WIDTH+BORDER_RIGHT+BORDER_LEFT)*PIXEL_SCALE, BOARD_HEIGHT*PIXEL_SCALE);
        root.setCenter(canvas);
        gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, 1000, 1000);
        
        hud = new HUD();
        root.setBottom(hud);
        hud.addCallback((Object... data) -> 
        {
            playerType = (Integer)data[0];
            InvadersGame.this.startLevel();
            canvas.requestFocus();
            return null;
        });

        stage.show();
    }    
    
    private void startLevel()
    {
        level++;
        player = PlayerSprite.createPlayer(playerType);
        player.setPosition((BOARD_WIDTH/2+BORDER_LEFT)*PIXEL_SCALE, 
                           GROUND*PIXEL_SCALE-player.getBoundary().getHeight());
        alienMatrix = new AlienGroup();
        alienMatrix.setPosition(BORDER_LEFT*PIXEL_SCALE, 0);
        char[] groundGraphic = new char[BOARD_WIDTH+BORDER_RIGHT+BORDER_LEFT];
        for (int i=0; i<groundGraphic.length; i++) groundGraphic[i]='o';
        ground = new PixelSprite(new String(groundGraphic),groundGraphic.length);
        ground.setPosition(0, GROUND*PIXEL_SCALE);
 
        (new GameLoop()).start();
    }
    
    private void gameOver()
    {
        //Display score
        System.out.println("Game over!");
        System.out.println("Score: "+playerScore);
        
        //Update database
        
    }

    private class GameLoop extends AnimationTimer
    {
        long lastNanoTime = System.nanoTime();
        int numAliens = AlienGroup.NUM_ALIENS_ACROSS*AlienGroup.NUM_ALIENS_DOWN;
        
        @Override
        public void handle(long currentNanoTime)
        {
            // calculate time since last update.
            double elapsedTime = (currentNanoTime - lastNanoTime) / 1e9;
            lastNanoTime = currentNanoTime;

            //Update player
            player.update(elapsedTime,input.contains("LEFT"),input.contains("RIGHT"),
                    input.contains("UP"),input.contains("DOWN"),
                    input.contains("SPACE"),input.contains("SHIFT"));

            //Update aliens
            alienMatrix.update(elapsedTime);

            //Update player shots
            for (ShotSprite s:player.getShots())
                s.update(elapsedTime);
            
            //Update alien bombs
            alienMatrix.checkBombSpawn(level);
            for (AlienSprite a:alienMatrix.getAliens())
                a.getBomb().update(elapsedTime);

            //Check for alien/shot collisions
            for (ShotSprite s:player.getShots())
                for (AlienSprite a:alienMatrix.getAliens())
                    if (s.intersects(a))
                    {
                        a.kill();
                        s.kill();
                        alienMatrix.killAlien(a);
                        numAliens--;
                        playerScore += a.points();
                        hud.updateScore(playerScore);
                    }
            
            //Check for bomb/shot collisions
            for (ShotSprite s:player.getShots())
                for (AlienSprite a:alienMatrix.getAliens())
                    if (s.intersects(a.getBomb()))
                    {
                        a.getBomb().kill();
                        s.kill();
                    }
            
            //Clean up dead player shots
            Iterator<ShotSprite> it = player.getShots().iterator();
            while (it.hasNext())
            {
                ShotSprite s = it.next();
                if (!s.isAlive())
                    it.remove();
            }            
            
            //Check for bomb/player collision
            for (AlienSprite a:alienMatrix.getAliens())
                if (a.getBomb().intersects(player))
                {
                    a.getBomb().kill();
                    player.kill();
                    this.stop();
                    gameOver();
                }
            
            //Check for aliens reaching the ground
            if (alienMatrix.getBoundary().getMaxY()>=GROUND*PIXEL_SCALE)
            {
                this.stop();
                gameOver();
            }
            
            //Check for level finished
            if (numAliens==0)
            {
                this.stop();
                startLevel();
            }

            //Clear canvas
            gc.setFill(Color.BLACK);
            gc.fillRect(0, 0, 1000, 1000);

            //Draw everything
            player.render(gc);
            alienMatrix.render(gc);
            for (ShotSprite s:player.getShots())
                s.render(gc);
            for (AlienSprite a:alienMatrix.getAliens())
                a.getBomb().render(gc);
            ground.render(gc);
        }
    }

}
