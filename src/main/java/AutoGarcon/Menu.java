package AutoGarcon; 
import java.util.ArrayList; 
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

    public enum MenuStatus { 
        INACTIVE, 
        ACTIVE, 
    }

    private int menuID; 
    private MenuStatus status; 
    private int restaurantID; 
    private TimeRange[] timeRanges; 
    private MenuItem[] menuItems;  
    private String menuName; 

    /**
     * Menu: Constructor to create an empty Menu Class.
     * @return A new Menu Instance with no initalized fields. 
     * Use isEmpty() to check if the Menu Instance has been initalized or not. 
     */
    public Menu(){
        this.menuID = -1; 
        this.restaurantID = -1; 
        this.menuName = "Default Menu"; 
        this.status = MenuStatus.INACTIVE;
        this.timeRanges = new TimeRange[0]; 
        this.menuItems = new MenuItem[0];
    }



    /**
     * Menu:   Constructor for Menu class. 
     * @param restaurantID id of the restaurant that owns wanted menu. 
     * @param menuID id of the wanted menu.  
     * @return New Menu Object with 
     *  feilds generated from querying the database. 
     */
    public Menu( int menuID, int restaurantID ){

        ResultSet menu = DBUtil.getMenu( menuID, restaurantID ); 

        if( menu != null ){
            try {
                this.restaurantID = restaurantID; 
                this.menuID = menu.getInt( "menuID" );  
                this.menuName = menu.getString("menuName"); 
                int statusInt = menu.getInt("menuStatus");
                this.status = MenuStatus.values()[statusInt];

                try {
                    this.timeRanges = new TimeRange[] {
                         new TimeRange( 
                                menu.getInt( "startTime" ), 
                                menu.getInt( "stopTime" )
                        )};
                } catch (SQLException e){
                    System.out.println("No timerange field, using default range.");
                    this.timeRanges = new TimeRange[] { TimeRange.defaultRange() };
                }

                this.menuItems = MenuItem.menuItems( this.menuID );

            } catch (SQLException e){
                System.out.printf("Failed to get the required fields while creating a menu Object.\n" + 
                       "Exception: %s.\n", e.toString() );
            }
        }
    }

    public Menu( ResultSet qresult ){

        try {
            this.restaurantID = qresult.getInt("restaurantID"); 
            this.menuID = qresult.getInt("menuID"); 
            int statusInt = qresult.getInt("menuStatus"); 
            this.status = MenuStatus.values()[statusInt];  
            this.menuName = qresult.getString("menuName"); 
            this.menuItems = MenuItem.menuItems( this.menuID ); 
            this.timeRanges = TimeRange.timeRanges( this.menuID ); 
        }
        catch( SQLException e){
            System.out.printf("Failed to get the required fields while creating a menu Object.\n" + 
                   "Exception: %s.\n", e.toString() );
        }

    }

    /**
     * allMenus: Get all of the menus in an array 
     * for the specified restaurant. 
     * @param restaurantID the restaurant to get menus for. 
     * @return An array of menus. 
     *
     */
    public static Menu[] allMenus( int restaurantID ){

        ResultSet menus = DBUtil.getMenus( restaurantID ); 
        ArrayList<Menu> list = new ArrayList<Menu>();  
        boolean hasResult = false; 

        try{ 
            hasResult = menus.next(); 
            while( hasResult ){
                    Menu menu = new Menu( menus ); 
                    list.add(menu); 
                    hasResult = menus.next(); 
            }
        }
        catch( SQLException e ){
            System.out.printf("Failed to get next row in result set.\n" + 
                    "Exception: %s\n", e.toString() );
        }

        return list.toArray( new Menu[ list.size() ] ); 
    }

    /**
     * menuFromJson: Create a new Menu objet from Json.
     * @param body JSON String representing the request paramaters for 
     *  a new Menu Object.
     * @exception JsonSyntaxException Throws a syntax exception when Gson can
     *  not deserialize into a Menu Object. 
     * @return A new Menu Instance from the json Request body. 
     */
    public static Menu menuFromJson( String body ) {

        Gson gson = new Gson();
        Menu menu = new Menu(); 

        try { 
            menu = gson.fromJson( body, Menu.class);
            //int menuID = menu.menuID;

        } catch( JsonSyntaxException e  ){
            System.out.printf("Failed to deserialize the request data into a Menu Object.\n" + 
                    "Request body: %s.\n Exception: %s\n", body, e.toString() );
        }
        return menu; 
    }


    /**
     * save: Saves the state of the menu to the databse, 
     * and saves the images to the file system.
     */
    public boolean save(){
        DBUtil.saveMenu( this );
        for( MenuItem mItem : this.menuItems ){
            boolean itemSaved; 
            itemSaved = DBUtil.saveMenuItem( this.menuID, this.restaurantID, mItem ); 
            mItem.saveImage( menuID );

            if( !itemSaved ){ 
                return false; 
            }
        }
        return true; 
    }

    public int getMenuID(){
        return this.menuID; 
    }

    public void setMenuID(int menuID){
        this.menuID = menuID; 
    }

    public int getRestaurantID(){
        return this.restaurantID; 
    }

    public TimeRange[] getTimeRanges(){
        return this.timeRanges;
    }

    public String getName(){
        return this.menuName; 
    }

    public MenuItem[] getMenuItems(){
        return this.menuItems; 
    }

    public int getStatus(){
        return this.status.ordinal();
    }

    public String getStartTimes(){
        return TimeRange.getStartTimeString( this.timeRanges ); 
    }

    public String getEndTimes(){
        return TimeRange.getEndTimeString( this.timeRanges ); 
    }


    /**
     * isDefault: Checks if this instance of Menu was initalized
     * without any data. 
     * @return true if the instance has no initalized data
     *  false if otherwise. 
     *
     * The instance will not initalized with data when using the constructor with no arguments.  
     * The Database starts menuIDs at 1. 
     */
    public boolean isDefault() {
        if( this.menuName.equals("Default Menu")){
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
     * toString: creates a human-readable representation of the 
     *      full menu with all categories and corresponding menu items
     * @return formatted string
     */
    @Override
    public String toString(){
        if (this.status == null ){
            return  "Empty Menu Object\n";
        }
        StringBuilder str = new StringBuilder();
        if( this.menuID != -1 ){ 
            str.append("menuID: " + Integer.toString(this.menuID) + "\n");   
        }
        str.append("restaurantID: " + Integer.toString( this.restaurantID ) + "\n" );
        str.append("status: " + this.status.name() + "\n" ); 
        str.append("MenuName: " + this.menuName + "\n");
        str.append("Time Ranges:\n");
        for( int i = 0; i < this.timeRanges.length; i++ ){
            str.append(this.timeRanges[i].toString() + "\n");
        }
        str.append("menuItems:\n");
        for( int i = 0; i < this.menuItems.length; i++ ){
            String item = String.format("item: %s,\n", this.menuItems[i].toString() );
            str.append( item );
        }
        return str.toString();
    }

}
