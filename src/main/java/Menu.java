package AutoGarcon; 
import java.sql.ResultSet; 
import java.util.ArrayList; 
import java.sql.SQLException;

/**
 * Menu:  Class that represents information pertaining 
 *      to menus for particular restaurants. 
 *
 * @author Tyler Beverley
 * @author Mitchel Nelson
 */

//public class Menu implements Serializable { 
public class Menu { 

    public enum Type {
        DRINKS, 
        BREAKFAST, 
        BRUNCH, 
        LUNCH,
        DINNER, 
        SPECIALS, 
        DESSERT, 
        OTHER;
    }


    public enum Status { 
        INACTIVE, 
        ACTIVE, 
        DELETED;
    }

    private int menuID; 
    private int restaurantID; 
    private Status status; 
    private Type type;  
    private TimeRange timeRanges[]; 
    private MenuItem menuItems[];  //current db only holds one time range. 
    private String name; 


    /**
     * Menu:   Constructor for Menu class. 
     *
     * @param restaurantID id of the restaurant that owns wanted menu. 
     * @param menuID id of the wanted menu.  
     * @return New Menu Object with 
     *  feilds generated from querying the database. 
     */
    public Menu( int restaurantID, int menuID ){

        ResultSet menu = DBUtil.getMenu(restaurantID, menuID ); 

        if( menu != null ){
            try {
                this.restaurantID = restaurantID; 
                this.menuID = menu.getInt("menuID");  
                //this.type = Type.valueOf( menu.getString( ).toUpperCase() ); 
                this.name = menu.getString("menuName"); 
                this.timeRanges = new TimeRange[] {
                     new TimeRange( 
                            menu.getInt( "startTime" ), 
                            menu.getInt( "stopTime" )
                    )};
                int statusInt = menu.getInt("menuStatus");
                this.status = Status.values()[statusInt];

            } catch (SQLException e){
                System.out.printf("Failed to get the required feilds while creating a menu Object.\n" + 
                       "Exception: " + e.toString() );
            }
        }
    }

    /**
     * getAllMenuItems: returns an ArrayList of all menuItems
     *
     * @return menuItems ArrayList
     */
    public MenuItem[] getAllMenuItems(){
        ArrayList<MenuItem> allItems = new ArrayList<>();
        return (MenuItem[]) allItems.toArray();
    }

    /**
     * addMenuItem: adds a menuItem to the menu. 
     *
     * @param newItem menuItem to add
     */
    public void addMenuItem(MenuItem newItem){
        System.out.println("addMenuItem is not implemented yet"); 
    }

    /**
     * @return menuType
     */
    public Type getMenuType(){
        System.out.println("addMenuType is not implemented yet"); 
        return null;
    }

    /**
     * toString: creates a human-readable representation of the full menu with all categories and corresponding menu items
     * Author:   Mitchell Nelson
     *
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

