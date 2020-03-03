package AutoGarcon; 
import java.sql.*; 



/*
 * @author Tyler Beverley
 * 
 * Utility functions for interacting with the datbase. 
 *
 * Enviroment variables DB_USER and DB_PASS hold the db creditentials.
 * HOST_NAME is used to determine if the program is running on the host. 
 *
 * connection string = protocol//user:password@[hosts][/database][?properties]
 * Note that the connection obj is not thread safe. So for now we will create 
 * a new one everytime. 
 *
 */
public class DBUtil {

    public final static String HOST_URL = "auto-garcon-database.cd4hzqa9i8mi.us-east-1.rds.amazonaws.com";
    private Connection connection; 


    public static Object getMenu( int restaurantID ){
        return null; 
    }

    public static Connection connectToDB(){

        Connection c = null; 
        String baseURL, userName, password, connString; 
        boolean isHost; 
        
        baseURL = "jdbc:mysql//%s:%s@%s/AutoGarcon"; 
        userName = getUserName(); 
        password = getPass(); 
        isHost = isHost(); 


        if( isHost ){
            connString = String.format(baseURL, userName, password, HOST_URL); 
        } else {
            connString = String.format(baseURL, userName, password, "localhost");   
        }

        try { 

            Class.forName("com.mysql.jdbc.Driver");
            c = DriverManager.getConnection( connString ); 

        } catch( SQLException e ){
            System.out.printf("SQL Exception while trying to connect to the Database.\n" +  
                    "Exception: " + e.toString() ); 

        } catch( ClassNotFoundException e ){
            System.out.printf("Failed to find the Java Databse Driver for Mysql.\n"); 
            System.exit(1);   
        }
        
        return c; 
    }

    private static String getUserName() {
        return System.getenv("DB_USER"); 
    }

    private static String getPass(){
        return System.getenv("DB_PASS");
    }
    
    private static boolean isHost(){
        if( System.getenv("HOST_NAME").isEmpty()) {
            return false; 
        } else {
            return true; 
        }
    }

}


