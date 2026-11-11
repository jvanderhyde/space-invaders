//Space Invaders, the Dice Game
//Created by James Vanderhyde, 13 November 2018

package edu.sxu.databases.invaders.dicegame;

import edu.sxu.databases.invaders.backend.ScoreUpdater;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Space Invaders, the Dice Game.
 * 
 * Invaders are coming from space! One hero stands between Earth and destruction.
 * You are that hero! You play as one of the classic video game archetypes:
 * strong fighter, rogue archer, or intelligent wizard.
 * 
 * Roll three 6-sided dice. The number of pips is the number of invaders you manage
 * to repel this turn. If a 1 shows up on any dice, that shows the invaders bombing
 * you. If three dice show a 1, you are surrounded by bombs and the game is over.
 * The game gets harder as you go, because the invaders are moving faster.
 * 
 * At any point in the game, you can stop and "bank" your score. Your score is
 * doubled, but the game is over.
 * 
 * Hero special abilities:
 *   Fighter: Bomb deflect. For any 1 rolled, re-roll the die (unless it was all three).
 *            Keep re-rolling until it's not a 1.
 *   Rogue:   Rapid fire. For any 2 or 3 rolled, duplicate the die.
 *   Wizard:  Exploding shots. For any 6 rolled, count the 6 and re-roll. Continue as 
 *            long as 6s are rolled.
 * 
 * Mathematical analysis of expected values of a single die roll:
 *   Fighter: Starts at 4, degrades to 3+2/3
 *   Rogue:   Starts at 4+1/3, degrades to 3+1/6
 *   Wizard:  Starts at 4+1/5, degrades to 3+1/30
 * 
 * @author James Vanderhyde
 */
public class DiceGame
{
    private static final Scanner cin = new Scanner(System.in);
    private static final Random rng = new Random();
    private static final String diceChars = " ⚀⚁⚂⚃⚄⚅";
    
    public static void main(String[] args)
    {
        DiceGame game = new DiceGame();
        game.start();
    }
    
    private static String prompt(String message)
    {
        System.out.print(message);
        return cin.nextLine();
    }
    
    private static int promptChoices(String message, String term, String... choices)
    {
        System.out.print(message);
        int c = 0;
        for (String s:choices)
        {
            c++;
            System.out.print(""+c+") "+s+" ");
        }
        System.out.print(term);
        int response = cin.nextInt();
        cin.nextLine();
        return response;
    }
    
    private static int rollDie(int score)
    {
        int roll = rng.nextInt(6)+1;
        if (roll==4 && score>rng.nextInt(400))
            roll=1;
        if (roll==5 && score>rng.nextInt(1200))
            roll=1;
        return roll;
    }
    
    private static void printDie(int r)
    {
        System.out.print(diceChars.charAt(r));
    }
    
    private String screenname;
    private int playerScore;
    private int playerType; //1, 2, or 3
    final private ScoreUpdater database = new ScoreUpdater();
    
    private void start()
    {
        //Set up player
        screenname = prompt("Please enter your screenname: ");
        playerType = promptChoices("Please choose your character: "," ","Acton † ","Ellis D-> ","Currer ΣO ");
        playerScore = 0;
        
        //Play the game
        int playerMove = 1;
        while (playerMove == 1)
        {
            //Roll
            List<Integer> roll = new ArrayList<>();
            roll.add(rollDie(playerScore));
            roll.add(rollDie(playerScore));
            roll.add(rollDie(playerScore));
            roll.sort(null);
            
            //Check for loss
            int sum = 0;
            for (int r:roll)
                sum+=r;
            if (sum==3)
            {
                playerMove = 3;
                for (int r:roll)
                {
                    System.out.print(" ");
                    printDie(r);
                }
                break;
            }
            
            //Special moves
            List<Integer> bonusRoll = new ArrayList<>();
            for (int r:roll)
            {
                System.out.print(" ");
                printDie(r);
                if (playerType==1 && r==1)
                {
                    sum-=r;
                    int newRoll = r;
                    while (newRoll==1)
                    {
                        System.out.print("†");
                        newRoll = rollDie(playerScore);
                        printDie(newRoll);
                    }
                    bonusRoll.add(newRoll);
                }
                if (playerType==2 && (r==2 || r==3))
                {
                    System.out.print("->");
                    printDie(r);
                    bonusRoll.add(r);
                }
                if (playerType==3 && r==6)
                {
                    int newRoll = r;
                    while (newRoll==6)
                    {
                        System.out.print("ΣO");
                        newRoll = rollDie(0);
                        printDie(newRoll);
                        bonusRoll.add(newRoll);
                    }
                }
            }
            for (int r:bonusRoll)
                sum+=r;
            System.out.println("  "+sum+" more points.");
            
            //Next move
            playerScore += sum;
            playerMove = promptChoices("Your score is "+playerScore+". Will you ","? ","Roll","Bank");
        }
        
        //Game is finished
        if (playerMove == 2)
        {
            System.out.println("Score banked.");
            playerScore *= 2;
        }
        else
        {
            System.out.println("  Boom!");
        }
        gameOver();
    }
    
    private void gameOver()
    {
        //Display score
        System.out.println("Game over!");
        System.out.println("Score: "+playerScore);
        
        //Update database
        database.updateDatabase(screenname,playerScore,playerType);
    }

}
