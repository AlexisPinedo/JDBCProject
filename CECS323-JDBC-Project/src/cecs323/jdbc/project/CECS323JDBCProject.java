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
    static final String displayFormat="%-30s%-45s%-30s%-20s%-20s%-30s%-30s%-30s%-30s%-30s%-30s\n";
    static final String displayFormat2="%-30s%-30s%-30s%-30s\n";
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
                            
                            //in case user enters any number besides 1, 2, or 3
                            int tableChoice = in.nextInt();
                             while (tableChoice < 1 || tableChoice > 3){
                                System.out.println("Please enter 1, 2, or 3 \n");
                                tableChoice = in.nextInt();
                            }
                    switch (tableChoice) {
                        //book titles from book table are displayed
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
                            //publisher names from publisher table are displayed
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
                            //writing group names from writing group table are displayed
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
                            //if the input(s) is/are not found, then this will remain false and
                            //the user will know that their entry is not in the table
                            boolean isFound = false;
                            
                            //Table to draw information from is chosen
                            System.out.println("Which tables would you like to draw information from?");
                            System.out.println("1. Books");
                            System.out.println("2. Publishers");
                            System.out.println("3. Writing Groups");
                            
                            int infoChoice = in.nextInt();
                            
                            //validation that the number is 1, 2, or 3
                             while (infoChoice < 1 || infoChoice > 3){
                                System.out.println("Please enter 1, 2, or 3 \n");
                                infoChoice = in.nextInt();
                            }
                             in.nextLine();
                             
                             switch(infoChoice){
                                 //user enters book name, and writing group to obtain info about book
                                 case 1:
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
                                    //prepared statement creation
                                    sql= "SELECT groupName, bookTitle, publisherName, yearPublished, numberPages, publisherAddress, publisherPhone, publisherEmail headWriter, yearFormed, subject "
                                            + "FROM Book NATURAL JOIN Publisher NATURAL JOIN WritingGroup"
                                            + " WHERE bookTitle = ? AND groupName = ?";
                                    PreparedStatement pstmt = conn.prepareStatement(sql);
                                    
                                    //the title and group name are set for the query
                                    pstmt.setString(1,enteredTitle);
                                    pstmt.setString(2,enteredGroup);
                                    //query to search is executed for the prepared statement
                                     rs = pstmt.executeQuery();
                                     
                                     System.out.println();
                                     //formatting for headers is displayed
                                    System.out.printf(displayFormat, "Group Name", "Book Title", "Publisher Name", "Year", "Number of Pages", "Publisher Address", "Publisher Phone", "Publisher Email", "Head Writer", "Year Formed", "Subject");
                                    while (rs.next()) {
                                        //Retrieve by column name
                                        String groupName = rs.getString("groupName");
                                        String bookTitle = rs.getString("bookTitle");
                                        String publisherName = rs.getString("publisherName");
                                        String yearPublished = rs.getString("yearPublished");
                                        String numberPages = rs.getString("numberPages");
                                        String publisherAddress = rs.getString("publisherAddress");
                                        String publisherPhone = rs.getString("publisherPhone");
                                        String publisherEmail = rs.getString("publisherEmail");
                                        String headWriter = rs.getString("headWriter");
                                        String yearFormed = rs.getString("yearFormed");
                                        String subject = rs.getString("subject");
                                        
                                        
                                        if(bookTitle.equals(enteredTitle) && groupName.equals(enteredGroup)){
                                            isFound = true;
                                        }
                                        //Display values
                                        System.out.printf(displayFormat, 
                                                dispNull(groupName), dispNull(bookTitle), dispNull(publisherName), dispNull(yearPublished)
                                                ,dispNull(numberPages), dispNull(publisherAddress), dispNull(publisherPhone),dispNull(publisherEmail)
                                                ,dispNull(headWriter),dispNull(yearFormed),dispNull(subject));
                                    }
                                    //in case the entered information is not matching anything in the database,
                                    //this is displayed
                                    if(isFound == false){
                                        System.out.println("Either the book title, " + enteredTitle 
                                                + ", the associated writing group, " + enteredGroup 
                                                + ", or a combination of the two were not found in the database");
                                    }
                                    System.out.println();
                                    break;
                                 case 2:
                                    System.out.println("Please enter the publisher name");
                                    String enteredPublisher = dispNull(in.nextLine());
                                    while (enteredPublisher.equals("N/A")){
                                        System.out.println("Please enter a valid publisher \n");
                                        enteredPublisher = dispNull(in.nextLine());
                            }
                                    //prepared statement creation
                                    sql= "SELECT publisherName, publisherAddress, publisherPhone, publisherEmail FROM Publisher"
                                            + " WHERE publisherName = ?";
                                    pstmt = conn.prepareStatement(sql);
                                    
                                    //the publisher is set for the query
                                    pstmt.setString(1,enteredPublisher);
                                    //query to search is executed for the prepared statement
                                     rs = pstmt.executeQuery();
                                     
                                     System.out.println();
                                     //formatting for headers is displayed 
                                    System.out.printf(displayFormat2, "Publisher Name", "Publisher Address", "Publisher Phone", "Publisher Email");
                                    while (rs.next()) {
                                        //Retrieve by column name
                                        String publisherName = rs.getString("publisherName");
                                        String publisherAddress = rs.getString("publisherAddress");
                                        String publisherPhone = rs.getString("publisherPhone");
                                        String publisherEmail = rs.getString("publisherEmail");
                                        
                                        if(publisherName.equals(enteredPublisher)){
                                            isFound = true;
                                        }
                                        //Display values
                                        System.out.printf(displayFormat2, 
                                                dispNull(publisherName), dispNull(publisherAddress), dispNull(publisherPhone), dispNull(publisherEmail));
                                    }
                                    //in case the entered information is not matching anything in the database,
                                    //this is displayed
                                    if(isFound == false){
                                        System.out.println("The publisher name, " + enteredPublisher + ", was not found in the database");
                                    }
                                    System.out.println();
                                     break;
                                 case 3:
                                    System.out.println("Please enter the writing group");
                                    enteredGroup = dispNull(in.nextLine());
                                    
                                    while (enteredGroup.equals("N/A")){
                                        System.out.println("Please enter a valid writing group \n");
                                        enteredGroup = dispNull(in.nextLine());
                            }
                                    //prepared statement creation
                                    sql= "SELECT groupName, headWriter, yearFormed, subject FROM WritingGroup"
                                            + " WHERE groupName = ?";
                                    pstmt = conn.prepareStatement(sql);
                                    
                                    //the group name is set for the query
                                    pstmt.setString(1,enteredGroup);
                                    //query to search is executed for the prepared statement
                                     rs = pstmt.executeQuery();
                                
                                    System.out.println();
                                    //formatting for header is displayed
                                    System.out.printf(displayFormat2, "Group Name", "Head Writer", "Year Formed", "Subject");
                                    while (rs.next()) {
                                        //Retrieve by column name
                                        String groupName = rs.getString("groupName");
                                        String headWriter = rs.getString("headWriter");
                                        String yearFormed = rs.getString("yearFormed");
                                        String subject = rs.getString("subject");
                                        
                                        if(groupName.equals(enteredGroup)){
                                            isFound = true;
                                        }
                                        //Display values
                                        System.out.printf(displayFormat2, 
                                                dispNull(groupName), dispNull(headWriter), dispNull(yearFormed), dispNull(subject));
                                    }
                                    //in case the entered information is not matching anything in the database,
                                    //this is displayed
                                    if(isFound == false){
                                        System.out.println("The writing group, " + enteredGroup + ", was not found in the database");
                                    }
                                    System.out.println();
                                     break; 
                             }
                             
                             break;
                             
                        case 3:
                             //Table to add information into is selected
                            System.out.println("Which table would you like to add information into?");
                            System.out.println("1. Books");
                            System.out.println("2. Publishers");
                            System.out.println("3. Writing Groups");
                            
                            int insertChoice = in.nextInt();
                            
                            switch(insertChoice){
                                case 1:
                                    break;
                                case 2:
                                    break;
                                case 3:
                                    break;
                            }
                        
                             break;
                             
                        case 4:
                            //used to clear space that comes from the in.nextInt() from previous menu
                            in.nextLine();
                            //used as a flag to make sure that the entered information is in the database
                            boolean removeFound = false;
                            //title request
                             System.out.println("Please enter the title of the book you would like to remove");
                                    //makes sure input is valid
                                    String removeTitle = dispNull(in.nextLine());
                                    while (removeTitle.equals("N/A")){
                                        System.out.println("Please enter a valid title \n");
                                        removeTitle = dispNull(in.nextLine());
                            }
                                    System.out.println("Please enter the writing group for the book");
                                    String removeGroup = dispNull(in.nextLine());
                                    while (removeGroup.equals("N/A")){
                                        System.out.println("Please enter a valid writing group \n");
                                        removeGroup = dispNull(in.nextLine());
                                    }
                                    
                                    stmt = conn.createStatement();
                                    //sql for query on group name and book title
                                    String removeSearchSql= "SELECT groupName, bookTitle"
                                            + "FROM Book ";

                                    //execution of query
                                    rs = stmt.executeQuery(removeSearchSql);
                                    while (rs.next()) {
                                        //Retrieve by column name
                                        String groupName = rs.getString("groupName");
                                        String bookTitle = rs.getString("bookTitle");
                                        
                                    if(bookTitle.equals(removeTitle) && groupName.equals(removeGroup)){
                                            removeFound = true;
                                        }
                                    }
                                    if(removeFound==true){
                                        String deleteBookSql= "DELETE"
                                                + " FROM Book"
                                                + " WHERE bookTitle = ? AND groupName = ?";
                                        PreparedStatement pstmt = conn.prepareStatement(deleteBookSql);

                                        //the title and group name are set for the query
                                        pstmt.setString(1,removeTitle);
                                        pstmt.setString(2,removeGroup);
                                        //delete query is executed
                                        pstmt.executeUpdate();
                                    }
                                    else{
                                        System.out.println("Either the book title, " + removeTitle 
                                                + ", the associated writing group, " + removeGroup 
                                                + ", or a combination of the two were not found in the database");
                                    }
                                    System.out.println();
                                     
                                     System.out.println();
                             break;
                             
                        case 5:
                             run = false;
                             break;
                     }
             } while(run!=false);

            //STEP 4: Execute a query
//            System.out.println("Creating statement...");
//            stmt = conn.createStatement();
//            String sql;
//            sql = "SELECT groupName, bookTitle, publisherName, yearPublished, numberPages FROM Book";
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

