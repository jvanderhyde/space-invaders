//Panel of buttons and score display
//Created by James Vanderhyde, 10 November 2017

package edu.sxu.databases.invaders;

import java.util.ArrayList;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class HUD extends HBox
{
    private final Label screennameLabel = new Label("Screen name");
    private final TextField screennameText = new TextField();
    private final Label scoreLabel = new Label("Score");
    private final Label scoreDisplay = new Label("0");
    private Button hero1, hero2, hero3;
    private final String style = "-fx-background-color: #000000; -fx-text-fill: #ffffff;";
    private StartGameCallback startGameCallback;
    
    public HUD()
    {
        scoreDisplay.setPrefWidth(100);
        scoreDisplay.setAlignment(Pos.TOP_RIGHT);
        screennameLabel.setStyle(style);
        screennameText.setStyle(style);
        screennameLabel.setAlignment(Pos.BOTTOM_LEFT);
        scoreLabel.setStyle(style);
        scoreDisplay.setStyle(style);
        
        screennameText.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) ->
        {
            if (newValue.trim().length() > 1)
                enableButtons(true);
            else
                enableButtons(false);
        });
        
        initButtons(); 
        
        this.getChildren().addAll(screennameLabel, screennameText, scoreLabel, scoreDisplay,
                hero1, hero2, hero3);
        this.setStyle(style);
    }

    private void initButtons()
    {
        String sword =
                "  o  "+
                "  o  "+
                "  o  "+
                "  o  "+
                " ooo "+
                "  o  ";
        
        String bow = 
                "oo   "+
                "o o  "+
                "o  o "+
                "o  o "+
                "o o  "+
                "oo   ";
        
        String fireball = 
                " ooo "+
                "ooooo"+
                "ooooo"+
                " ooo "+
                " ooo "+
                "  o  ";
        
        hero1 = new Button("Acton", new ImageView(PixelSprite.createPixelImage(sword,5)));
        hero2 = new Button("Ellis", new ImageView(PixelSprite.createPixelImage(bow,5)));
        hero3 = new Button("Currer", new ImageView(PixelSprite.createPixelImage(fireball,5)));
        
        hero1.setStyle(style+" -fx-border-color: #ffffff;");
        hero2.setStyle(style+" -fx-border-color: #ffffff;");
        hero3.setStyle(style+" -fx-border-color: #ffffff;");
        
        enableButtons(false);
        
        class StartGameHandler implements EventHandler<ActionEvent>
        {
            private final int hero;
            public StartGameHandler(int hero) {this.hero=hero;}
            @Override
            public void handle(ActionEvent event)
            {
                ArrayList<Button> toHide = new ArrayList<>();
                toHide.add(hero1);
                toHide.add(hero2);
                toHide.add(hero3);
                toHide.get(hero-1).setDisable(true);
                toHide.get(hero-1).setStyle(style);
                toHide.remove(hero-1);
                for (Button b:toHide) b.setVisible(false);
                
                screennameText.setDisable(true);
                
                startGameCallback.call(hero);
            }
        };
        hero1.setOnAction(new StartGameHandler(1));
        hero2.setOnAction(new StartGameHandler(2));
        hero3.setOnAction(new StartGameHandler(3));
    }
    
    private void enableButtons(boolean enable)
    {
        hero1.setDisable(!enable);
        hero2.setDisable(!enable);
        hero3.setDisable(!enable);
    }
    
    public void updateScore(int score)
    {
        scoreDisplay.setText(String.valueOf(score));
    }
    
    public void disableScore()
    {
        scoreDisplay.setDisable(true);
    }
    
    public void setStartGameCallback(StartGameCallback c)
    {
        startGameCallback = c;
    }
    
    public static interface StartGameCallback
    {
        public void call(int hero);
    }
}
