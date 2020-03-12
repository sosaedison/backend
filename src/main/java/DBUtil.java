package AutoGarcon; 
import java.sql.*; 
import java.io.File; 



/**
 * DBUtil: Utility functions for interacting with the datbase. 
 * @author Tyler Beverley
 * @param DB_USER - System Property that holds username for the database. 
 * @param DB_PASS - System Property that holds the Password for the database. 
 * 
 * Enviroment variables DB_USER and DB_PASS hold the db creditentials.
 * HOST_NAME is used to determine if the program is running on the host. 
 *
 * connection string = protocol//user:password@[hosts][/database][?properties]
 * Note that the connection obj is not thread safe. So for now we will create 
 * a new one everytime. 
 *
 * TODO: ?implement Binary Search on a result set for finding a specified menuID. 
 *
 */
public class DBUtil {

    public final static String HOST_URL = "auto-garcon-database.cd4hzqa9i8mi.us-east-1.rds.amazonaws.com";
    private Connection connection; 


    /** 
     * uploadImage: Uploads the image to google's api. 
     *
     *
     */
    public static void uploadImage( File file ){

    }


    /**
     * getMenu: Gets all the menus offered by a restaurant. 
     * @param restaurantID the id of the restaurant.
     * @return SQL result set containing menu data. 
     *
     * Result set's cursor will start just before the first row. 
     * So use ResultSet.next() to get to the first row.
     */
    public static ResultSet getMenus( int restaurantID ){
        ResultSet result = null;
        Connection c = connectToDB(); 
        CallableStatement stmt;

        try { 
            stmt = c.prepareCall("{call GetMenusByRestaurantId(?)}" ); 
            stmt.setInt( "id", restaurantID );  

            boolean hasResult = stmt.execute(); 

            if(hasResult){
                result = stmt.getResultSet(); 
                result.beforeFirst(); 
            }

        } catch( SQLException e ){
            System.out.printf("Failed to exectue GetMenusByRestaurantId stored procedure.\n" + 
                    "Exception: " + e.toString() );
            System.exit(1); 
        }

        return result; 
    }

    /**
     * getMenu: Gets a specifed menu from a specifed restaurant. 
     * @author Tyler Beverley
     * @param restaurantID
     * @param menuID 
     * @return SQL result representing the restaurantID, and menuID.  
     */
    public static ResultSet getMenu(int restaurantID, int menuID ){

        ResultSet menus; 
        boolean hasResult = false ;
        menus = getMenus( restaurantID ); 

        if( menus == null){
            hasResult = false; 
            System.out.printf("Failed to find menuid: %d.\n", menuID);
        }

        int resultID; 
        do{ 
            try {
                hasResult = menus.next(); 
                resultID = menus.getInt( "menuID" ); 

                if( resultID == menuID ){
                    return menus;  
                } else {
                    hasResult = menus.next(); 
                }

            } catch (SQLException e ){
                System.out.printf("SQL Excpetion when trying to get next row in result set.\n" + 
                        "Exception: " + e.toString() );
                System.exit(1); 
            }
        } while( hasResult );
        return menus;
    }

    public static void saveMenu( Menu menu ){
        System.out.println("saveMenu is not implemented yet"); 
    }

    public static ResultSet getMenuItems( int menuID ) {

        System.out.println("getMenuItems is not implemented yet");
        ResultSet result = null; 
        Connection c = connectToDB(); 
        CallableStatement stmt;

        try { 
            stmt = c.prepareCall("{call GetMenuItemsByMenuId(?)}" ); 
            stmt.setInt( "id", menuID);  
        } catch (SQLException e){
            System.out.printf("SQL Exception while preparing getMenu stored Proceedure\n"); 
        }
        return result;

    }

    public static Connection connectToDB(){

        Connection c = null; 
        String baseURL, userName, password, connString; 
        boolean isHost; 
        
        baseURL = "jdbc:mysql://%s:%s@%s/AutoGarcon"; 
        userName = getUserName(); 
        password = getPass(); 
        isHost = isHost(); 


        if( isHost ){
            connString = String.format(baseURL, userName, password, HOST_URL); 
        } else {
            //just use the hosted db for now. 
            connString = String.format(baseURL, userName, password, HOST_URL);   
        }

        try { 
            Class.forName("com.mysql.cj.jdbc.Driver");
            c = DriverManager.getConnection( connString ); 

        } catch( SQLException e ){
            System.out.printf("SQL Exception while trying to connect to the Database.\n" +  
                    "Exception: " + e.toString() + "\n" ); 
            System.exit(1);   

        } catch( ClassNotFoundException e ){
            System.out.printf("Failed to find the Java Databse Driver for Mysql.\n"); 
            System.exit(1);   
        }
        System.out.printf("Connected to the hosted database!\n");
        
        return c; 
    }

    private static String getUserName() {
        String username = System.getProperty("DB_USER"); 
        if( username == null ){
            System.out.println("WARNING! DB_USER is null."); 
        }
        return username;  
    }

    private static String getPass(){
        String password = System.getProperty("DB_PASS"); 
        if( password == null ){
            System.out.println("WARNING! DB_PASS is null."); 
        }
        return password;  
    }
    
    private static boolean isHost(){
        if( System.getProperty("HOST_NAME") == null) {
            return false; 
        } else {
            return true; 
        }
    }



}





