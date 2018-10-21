package cecs323.jdbc.project;
import java.sql.*;
import java.util.Scanner;
/**
 *
 * @author Kishen
 */
public class CECS323JDBCProject {
static String USER = "k";
    static String PASS = "k";
    static String DBNAME = "JDBCProject";
    //This is the specification for the printout that I'm doing:
    //each % denotes the start of a new field.
    //The - denotes left justification.
    //The number indicates how wide to make the field.
    //The "s" denotes that it's a string.  All of our output in this test are 
    //strings, but that won't always be the case.
    static final String displayFormat="%-30s%-50s%-30s%-30s%-30s\n";
    static final String JDBC_DRIVER = "org.apache.derby.jdbc.ClientDriver" ;
    static String DB_URL = "jdbc:derby://localhost:1527/" + DBNAME + ";user="
            + USER + ";password=" + PASS;
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
  
        Connection conn = null; //initialize the connection
        Statement stmt = null;  //initialize the statement that we're using
        
        try {
            //STEP 2: Register JDBC driver
            Class.forName("org.apache.derby.jdbc.ClientDriver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            ResultSet rs;
            conn = DriverManager.getConnection(DB_URL);
            boolean run = true;
            int choice = 0;
             do{
                    // Menu options
                    System.out.println("1. List all names from a table");
                    System.out.println("2. List info about specific entries");
                    System.out.println("3. Insert information into a table");
                    System.out.println("4. Remove a book");
                    System.out.println("5. Quit\n");
                    choice = in.nextInt();

                    // Checks whether the user's input matches is of the desired type
                    
        

                    // Checks if the user inputs a number between 1 and 5
                    while (choice < 1 || choice > 5){
                        System.out.println("Please enter 1, 2, 3, 4, or 5 \n");
			choice = in.nextInt();
                    }
                     switch(choice){
                         
                         case 1:
                             //User chooses which table to display names from
                            System.out.println("Which tables names would you like to display?");
                            System.out.println("1. Books");
                            System.out.println("2. Publishers");
                            System.out.println("3. Writing Groups");
                            
                            int tableChoice = in.nextInt();
                             while (tableChoice < 1 || tableChoice > 3){
                                System.out.println("Please enter 1, 2, or 3 \n");
                                tableChoice = in.nextInt();
                            }
                    switch (tableChoice) {
                        case 1:
                            stmt = conn.createStatement();
                            String bookSql;
                            bookSql = "SELECT bookTitle FROM Book";
                            ResultSet bookRs = stmt.executeQuery(bookSql);
                            System.out.println("Book Title");
                            while (bookRs.next()) {
                                String bookTitle = bookRs.getString("bookTitle");
                                System.out.println(dispNull(bookTitle));
                            }
                            System.out.println(" ");
                            bookRs.close();
                            break;
                        case 2:
                            stmt = conn.createStatement();
                            String publisherSql;
                            publisherSql = "SELECT publisherName FROM Publisher";
                            ResultSet publisherRs = stmt.executeQuery(publisherSql);
                            System.out.println("Publisher Name");
                            while (publisherRs.next()) {
                                String publisherName = publisherRs.getString("publisherName");
                                System.out.println(dispNull(publisherName));
                            }
                            System.out.println(" ");
                            publisherRs.close();
                            break;
                        case 3:
                              stmt = conn.createStatement();
                            String writingGroupSql;
                            writingGroupSql = "SELECT groupName FROM WritingGroup";
                            ResultSet writingGroupRs = stmt.executeQuery(writingGroupSql);
                            System.out.println("Writing Group");
                            while (writingGroupRs.next()) {
                                String groupName = writingGroupRs.getString("groupName");
                                System.out.println(dispNull(groupName));
                            }
                            System.out.println(" ");
                            writingGroupRs.close();
                            break;
                    }

                            break;
                             
                        case 2:
                            String sql;
                            
                            
                            System.out.println("Which tables would you like to draw information from?");
                            System.out.println("1. Books");
                            System.out.println("2. Publishers");
                            System.out.println("3. Writing Groups");
                            
                            int infoChoice = in.nextInt();
                            
                             while (infoChoice < 1 || infoChoice > 3){
                                System.out.println("Please enter 1, 2, or 3 \n");
                                infoChoice = in.nextInt();
                            }
                             in.nextLine();
                             
                             switch(infoChoice){
                                 case 1:
                                    stmt = conn.createStatement();
                                    System.out.println("Please enter the title of the book");
                                    String enteredTitle = dispNull(in.nextLine());
                                    while (enteredTitle.equals("N/A")){
                                        System.out.println("Please enter a valid title \n");
                                        enteredTitle = dispNull(in.nextLine());
                            }
                                    System.out.println("Please enter the writing group for the book");
                                    String enteredGroup = dispNull(in.nextLine());
                                    while (enteredGroup.equals("N/A")){
                                        System.out.println("Please enter a valid writing group \n");
                                        enteredGroup = dispNull(in.nextLine());
                            }

                                    
//                                    System.out.println( "Please enter the writing group for the book" );
//                                    enteredGroup = dispNull(in.nextLine());
                                    
                                    sql= "SELECT groupName, bookTitle, publisherName, yearPublished, numberPages FROM Book"
                                            + " WHERE bookTitle =" + "\'" + enteredTitle +"\'" +"AND groupName="+ "\'" + enteredGroup +"\'";
                                     rs = stmt.executeQuery(sql);
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
                                    
           
                                     break;
                                 case 2:
                                     break;
                                 case 3:
                                     break; 
                             }
                             
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
//            ResultSet rs = stmt.executeQuery(sql);
//
//            //STEP 5: Extract data from result set
//            System.out.printf(displayFormat, "Group Name", "Book Title", "Publisher Name", "Year", "Number of Pages");
//            while (rs.next()) {
//                //Retrieve by column name
//                String groupName = rs.getString("groupName");
//                String bookTitle = rs.getString("bookTitle");
//                String publisherName = rs.getString("publisherName");
//                int yearPublished = rs.getInt("yearPublished");
//                int numberPages = rs.getInt("numberPages");
//
//                //Display values
//                System.out.printf(displayFormat, 
//                        dispNull(groupName), dispNull(bookTitle), dispNull(publisherName), yearPublished, numberPages);
//            }
//            //STEP 6: Clean-up environment
//            rs.close();
//            stmt.close();
//            conn.close();
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

