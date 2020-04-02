package AutoGarcon;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Restaurant: This is a class that represents
 *          the information and functions needed
 *          for a restaurant.
 * @author Sosa Edison
 */
public class Restaurant {

    private int restaurantID;
    private String restaurantName;
    private String restaurantAddress;
    private List<Menu> availableMenus;
    private List<Table> restaurantTables;

    public Restaurant(int restaurantID, String restaurantName, String restaurantAddress) {
        this.restaurantID = restaurantID;
        this.restaurantName = restaurantName;
        this.restaurantAddress = restaurantAddress;
        this.availableMenus = new ArrayList<>();
        this.restaurantTables = new ArrayList<>();

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
