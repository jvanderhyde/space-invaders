//Test the connection to the database
//Created by James Vanderhyde, 12 November 2025

package edu.sxu.databases.invaders.backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcTest
{
    public static void main(String[] args)
    {
        //Establish database connection
        final String url = "jdbc:sqlite:invaders.db";
        try (Connection conn = DriverManager.getConnection(url))
        {
            System.out.println("Connection to database established.");
            
            String query = """
                SELECT id, player, hero, gametime, score 
                FROM invaders; """;
            try (PreparedStatement p = conn.prepareStatement(query))
            {
                ResultSet r = p.executeQuery();
                
                while (r.next())
                {
                    System.out.println(r.getInt("id")+r.getString("player")+r.getInt("hero")+r.getTimestamp("gametime")+r.getInt("score"));
                }
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
