package cecs323.jdbc.project;
import java.sql.*;
import java.util.Scanner;
/**
 *
 * @author Kishen
 */
public class CECS323JDBCProject {
static String USER;
    static String PASS;
    static String DBNAME;
    //This is the specification for the printout that I'm doing:
    //each % denotes the start of a new field.
    //The - denotes left justification.
    //The number indicates how wide to make the field.
    //The "s" denotes that it's a string.  All of our output in this test are 
    //strings, but that won't always be the case.
    static final String displayFormat="%-30s%-50s%-30s%-30s%-30s\n";
// JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.apache.derby.jdbc.ClientDriver";
    static String DB_URL = "jdbc:derby://localhost:1527/";
//            + "testdb;user=";
/**
 * Takes the input string and outputs "N/A" if the string is empty or null.
 * @param input The string to be mapped.
 * @return  Either the input string or "N/A" as appropriate.
 */
    public static String dispNull (String input) {
        //because of short circuiting, if it's null, it never checks the length.
        if (input == null || input.length() == 0)
            return "N/A";
        else
            return input;
    }
    
    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        //Prompt the user for the database name, and the credentials.
        //If your database has no credentials, you can update this code to 
        //remove that from the connection string.
        DBNAME = "JDBCProject";
        USER = "k";
        PASS = "k";
        //Constructing the database URL connection string
        DB_URL = DB_URL + DBNAME + ";user="+ USER + ";password=" + PASS;
        Connection conn = null; //initialize the connection
        Statement stmt = null;  //initialize the statement that we're using
        try {
            //STEP 2: Register JDBC driver
            Class.forName("org.apache.derby.jdbc.ClientDriver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL);
            boolean run = true;
            int choice = 0;
             do{
                    // Menu options
                    System.out.println("1.  List a table");
                    System.out.println("2.  List specific data from a table");
                    System.out.println("3.  Insert information into a table");
                    System.out.println("4.  Remove a book");
                    System.out.println("5.  Quit\n");
                    choice = in.nextInt();

                    // Checks whether the user's input matches is of the desired type
                    
        

                    // Checks if the user inputs a number between 1 and 5
                    while (choice < 1 || choice > 5){
                        System.out.println("Please enter 1, 2, 3, 4, or 5 \n");
			choice = in.nextInt();
                    }
                     switch(choice){
                         
                         case 1:
                             System.out.println("hello1");
                             break;
                             
                        case 2:
                             System.out.println("hello2");
                             break;
                             
                        case 3:
                             System.out.println("hello3");
                             break;
                             
                        case 4:
                             System.out.println("hello4");
                             break;
                             
                        case 5:
                             run = false;
                             break;
                     }
             } while(run!=false);

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT groupName, bookTitle, publisherName, yearPublished, numberPages FROM Book";
            ResultSet rs = stmt.executeQuery(sql);

            //STEP 5: Extract data from result set
            System.out.printf(displayFormat, "Group Name", "Book Title", "Publisher Name", "Year", "Number of Pages");
            while (rs.next()) {
                //Retrieve by column name
                String groupName = rs.getString("groupName");
                String bookTitle = rs.getString("bookTitle");
                String publisherName = rs.getString("publisherName");
                int yearPublished = rs.getInt("yearPublished");
                int numberPages = rs.getInt("numberPages");

                //Display values
                System.out.printf(displayFormat, 
                        dispNull(groupName), dispNull(bookTitle), dispNull(publisherName), yearPublished, numberPages);
            }
            //STEP 6: Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
    }//end main
}//end FirstExample}

