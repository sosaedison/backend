package AutoGarcon; 
import java.lang.reflect.Type; 
import java.sql.ResultSet; 
import java.sql.SQLException;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;


/**
 * Menu:  Class that represents information pertaining 
 *      to menus for particular restaurants. 
 * @author Tyler Beverley
 */
public class Menu { 

    public enum MenuType {
        DRINKS, 
        BREAKFAST, 
        BRUNCH, 
        LUNCH,
        DINNER, 
        SPECIALS, 
        DESSERT, 
        OTHER;
    }

    public enum MenuStatus { 
        INACTIVE, 
        ACTIVE, 
        DELETED;
    }

    private int menuID; 

    private MenuStatus status; 
    private MenuType type;  
    private int restaurantID; 
    private TimeRange timeRanges[]; //current db only holds one time range. 
    private MenuItem menuItems[];  
    private String name; 



    /**
     * Menu: Constructor to create an empty Menu Class.
     * @return A new Menu Instance with no initalized fields. 
     * Use isEmpty() to check if the Menu Instance has been initalized or not. 
     */
    public Menu(){
        this.menuID = 0; 
        this.restaurantID = 0; 
        this.name = ""; 
        this.status = MenuStatus.INACTIVE;
    }

    /**
     * Menu:   Constructor for Menu class. 
     * @param restaurantID id of the restaurant that owns wanted menu. 
     * @param menuID id of the wanted menu.  
     * @return New Menu Object with 
     *  feilds generated from querying the database. 
     */
    public Menu( int restaurantID, int menuID ){

        ResultSet menu = DBUtil.getMenu( restaurantID, menuID ); 

        if( menu != null ){
            try {
                this.restaurantID = restaurantID; 
                this.menuID = menu.getInt( "menuID" );  
                //this.type = MenuType.valueOf( menu.getString( ).toUpperCase() ); 
                this.name = menu.getString("menuName"); 
                this.timeRanges = new TimeRange[] {
                     new TimeRange( 
                            menu.getInt( "startTime" ), 
                            menu.getInt( "stopTime" )
                    )};
                int statusInt = menu.getInt("menuStatus");
                this.status = MenuStatus.values()[statusInt];

            } catch (SQLException e){
                System.out.printf("Failed to get the required feilds while creating a menu Object.\n" + 
                       "Exception: " + e.toString() );
            }
        }
    }

    /**
     * menuFromJson: Create a new Menu objet from Json.
     * @param body JSON String representing the request paramaters for 
     *  a new Menu Object.
     * @exception JsonSyntaxException Throws a syntax exception when Gson can
     *  not deserialize into a Menu Object. 
     * @return A new Menu Instance from the json Request body. 
     *
     */
    public static Menu menuFromJson( String body ) {

        Gson gson = new Gson();
        Menu menu = new Menu(); 

        try { 
            menu = gson.fromJson( body, Menu.class);
        } catch( JsonSyntaxException e  ){
            System.out.printf("Failed to deserialize the request data into a Menu Object.\n" + 
                    "Request body: %s.\n Exception: %s\n", body, e.toString() );
        }
        return menu; 
    }


    /**
     * save: Saves the current state of the menu to the databse. 
     */
    public void save(){
        DBUtil.saveMenu( this );
    }

    public int getMenuID(){
        return this.menuID; 
    }

    public int getRestaurantID(){
        return this.restaurantID; 
    }

    public TimeRange[] getTimeRange(){
        return this.timeRanges;
    }

    public String getName(){
        return this.name; 
    }

    public MenuItem[] getMenuItems(){
        return this.menuItems; 
    }


    /**
     * isEmpty: Checks if this instance of Menu was initalized
     * without any data. 
     * @return true if the instance has no initalized data
     *  false if otherwise. 
     *
     * The instance will not initalized with data when using the constructor with no arguments.  
     * The Database starts menuIDs at 1. 
     */
    public boolean isEmpty() {
        if( this.menuID == 0){
            return true; 
        } else {
            return false; 
        }
    }


    /**
     * addMenuItem: adds a menuItem to the menu. 
     * @param newItem menuItem to add
     */
    public void addMenuItem(MenuItem newItem){
        System.out.println("addMenuItem is not implemented yet"); 
    }

    /**
     * @return menuType
     */
    public MenuType getMenuType(){
        System.out.println("addMenuType is not implemented yet"); 
        return null;
    }

    /**
     * toString: creates a human-readable representation of the 
     *      full menu with all categories and corresponding menu items
     * @Author:   Mitchell Nelson
     * @return formatted string
     */
    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        str.append("menuType: " + getMenuType().toString() + "\n");
        str.append("menuItems:\n");

        return str.toString();
    }

}
