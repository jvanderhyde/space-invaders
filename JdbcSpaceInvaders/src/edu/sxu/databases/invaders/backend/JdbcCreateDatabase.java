//Create the SQLite table for the high score list
//Created by James Vanderhyde, 12 November 2025

package edu.sxu.databases.invaders.backend;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcCreateDatabase
{
    public static void main(String[] args)
    {
        final String url = "jdbc:sqlite:invaders.db";
        try (Connection conn = DriverManager.getConnection(url))
        {
            System.out.println("Connection to database established.");
            
            String dropCommand = "DROP TABLE invaders";
            
            String createCommand = """
                CREATE TABLE invaders (
                    id INTEGER PRIMARY KEY,
                    player VARCHAR(20) NOT NULL,
                    hero INTEGER NOT NULL,
                    gametime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                    score INTEGER NOT NULL); """;
            try (Statement stmt = conn.createStatement()) 
            {
                stmt.executeUpdate(createCommand);
            }
            
            String insertCommand = """
                INSERT 
                INTO invaders (player, hero, score)
                VALUES ('crono', 1, 50); """;
            try (Statement stmt = conn.createStatement()) 
            {
                stmt.executeUpdate(insertCommand);
            }
        }
        catch (SQLException e)
        {
            System.err.println("SQL exception: " + e.getMessage());
            System.err.println("SQL state: " + e.getSQLState());
            System.err.println("Error code: " + e.getErrorCode());
        }
        System.out.println("Database connection closed.");
    }
    
}
