package AutoGarcon;

import com.google.gson.Gson; 
import com.google.gson.JsonSyntaxException; 
import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Restaurant: This is a class that represents
 *  the information and functions needed for a restaurant.
 * @author Sosa Edison
 * @author Tyler Beverley
 */
public class Restaurant {

    private int restaurantID;
    private String restaurantName;
    private String restaurantAddress;
    private List<Menu> availableMenus;
    private List<Table> restaurantTables;


    /**
     * Restaurant - Creates a restaurant object. 
     * @param restaurantID
     * @param restaurantName 
     * @param restaurantAddress
     */
    public Restaurant(int restaurantID, String restaurantName, String restaurantAddress) {
        this.restaurantID = restaurantID;
        this.restaurantName = restaurantName;
        this.restaurantAddress = restaurantAddress;
        this.availableMenus = new ArrayList<>();
        this.restaurantTables = new ArrayList<>();

    }

    /**
     * Restaurant - empty restaurant constructor.  
     * Used for 1st step in json deserialization. 
     */
    public Restaurant(){
        this.restaurantID = -1; 
        this.restaurantName = "Default Restaurant"; 
        this.restaurantAddress = ""; 
    }

    public Restaurant RestaurantFromJson( String body){

        Gson gson = new Gson(); 
        Restaurant restaurant = new Restaurant(); 

        try { 
            restaurant = gson.fromJson( body, Restaurant.class );
        } catch( JsonSyntaxException e ){
            System.out.printf("Failed to deserialze the request body into a MenuItem object.\n" + 
                    "Request body: %s\n. Exception: %s\n", body, e.toString() );
        }
        return restaurant; 
    }

    /**
     *
     * @return The restaurantID.
     */
    public int getRestaurantID() {
        return restaurantID;
    }

    /**
     *
     * @return All restaurant menus.
     */
    public List<Menu> getAvailableMenus() { return availableMenus; }

    /**
     *
     * @return List of restaurant tables.
     */
    public List<Table> getRestaurantTables() { return restaurantTables; }
    /**
     *
     * @param menuId: ID of the menu
     * @return The menu if it was found
     */
    public Optional<Menu> getMenuById(int menuId) {
        return Optional.empty();
    }

    /**
     *
     * @return Restaurant name.
     */
    public String getRestaurantName() {
        return restaurantName;
    }

    /**
     *
     * @return Restaurant address as a string.
     */
    public String getRestaurantAddress() {
        return restaurantAddress;
    }

    /**
     *
     * @return String representation of a Restaurant.
     */
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("restaurantID: " + this.getRestaurantID() + "\n");
        str.append("restaurantName: " + this.getRestaurantName()+ "\n");
        str.append("restaurantAddress: " + this.getRestaurantAddress() + "\n");
        return str.toString();
    }

} // Class
