//The class that updates the database with the new score
//Created by James Vanderhyde, 11 November 2017

package edu.sxu.databases.invaders.backend;

public class ScoreUpdater
{
    public void updateDatabase(String screenname, int score, int hero)
    {
        //Print message to console
        String[] heroNames = {"Acton", "Ellis", "Currer"};
        System.out.print(screenname+" achieved a score of "+score+ " while playing ");
        System.out.println(heroNames[hero-1]);
        
        //Open connection to database
        
        //Record new score in database
        
        //Display high scores and leaderboards
        
    }
}
