//Test the connection to the database
//Created by James Vanderhyde, 11 November 2015

package jdbctest;

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
        //Load database driver
        try
        {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        }
        catch (Exception e)
        {
            System.err.println("Error when loading DB driver:");
            System.err.println(e);
        }
        
        //Establish database connection
        Connection conn = null;
        try
        {
            final String url = "jdbc:mysql://csmaster.sxu.edu/hafh";
            final String username = prompt("Username: ");
            final String password = promptHidden("Password: ");
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("Connection to database established.");
        }
        catch (SQLException e)
        {
            System.err.println("SQL exception: " + e.getMessage());
            System.err.println("SQL state: " + e.getSQLState());
            System.err.println("Error code: " + e.getErrorCode());
        }

        if (conn != null)
        {
            try
            {
                //Set up the SQL statement (with a parameter)
                PreparedStatement p = conn.prepareStatement(
                      "SELECT mfname, mlname "
                    + "FROM manager "
                    + "WHERE managerid = ?");

                //Declare variables for data from the record
                ResultSet r;
                String firstName, lastName;
                
                //Set the SQL statement parameter
                p.setString(1, "M23");
                
                //Execute the query
                r = p.executeQuery();
                
                //Loop over the result set
                while (r.next())
                {
                    //Get values from the current row
                    firstName = r.getString("mfname");
                    lastName = r.getString("mlname");
                    
                    //Display the result
                    System.out.println("Manager: "+firstName+" "+lastName);
                }
            
                //Close database connection
                conn.close();
                System.out.println("Database connection closed.");
            }
            catch (SQLException e)
            {
                System.err.println("SQL exception: " + e.getMessage());
                System.err.println("SQL state: " + e.getSQLState());
                System.err.println("Error code: " + e.getErrorCode());
            }
        }
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
            return prompt(message);
        }
    }
}
