//Test the connection to the database
//Created by James Vanderhyde, 11 November 2015
//Modified by James Vanderhyde, 12 November 2018
//  Removed explicit call to load JDBC driver
//  Used try-with-resources

package edu.sxu.databases.invaders.backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class JdbcTest
{
    private static final Scanner cin = new Scanner(System.in);

    public static void main(String[] args)
    {
        //Establish database connection
        final String url = "jdbc:mysql://csmaster.sxu.edu/hafh";
        final String username = prompt("Username: ");
        final String password = promptHidden("Password: ");
        try (Connection conn = DriverManager.getConnection(url, username, password))
        {
            System.out.println("Connection to database established.");
            
            //Set up the SQL statement (with a parameter)
            try (PreparedStatement p = conn.prepareStatement(
                      "SELECT mfname, mlname FROM manager WHERE managerid = ?"))
            {
                //Declare variables for data from the record
                ResultSet r;
                String firstName, lastName;
                
                //Set the SQL statement parameter
                p.setString(1, "M23");
                
                //Execute the query
                r = p.executeQuery();
                
                //If the result set has something in it, print.
                if (r.next())
                {
                    //Get values from the current row
                    firstName = r.getString("mfname");
                    lastName = r.getString("mlname");
                    
                    //Display the result
                    System.out.println("Manager: "+firstName+" "+lastName);
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

    private static String prompt(String message)
    {
        System.out.print(message);
        return cin.nextLine();
    }

    private static String promptHidden(String message)
    {
        //Attempt to use the password feature of the system console
        java.io.Console console = System.console();
        if (console != null)
        {
            return new String(console.readPassword(message));
        }
        else
        {
            //Fall back to a non-hidden prompt
            return prompt(message+" WARNING: password will be visible:");
        }
    }
}
