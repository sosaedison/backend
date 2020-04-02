package AutoGarcon; 
import com.google.gson.Gson; 
import com.google.gson.JsonSyntaxException;
import com.google.gson.JsonElement; 
import java.io.File;
import java.sql.ResultSet; 
import java.util.ArrayList; 
import java.sql.SQLException; 

/**
 * @author Tyler Beverley 
 *
 * Represents information pertaining to items
 * that are conatined within a menu.
 */
public class MenuItem { 

    /**
     * Allergen - What allergens that the menuItem may contain.
     */
    public enum Allergen { 
        MEAT, 
        DAIRY, 
        GLUTEN, 
        NUTS, 
        SOY, 
        OTHER 
    }

    private int itemID; 
    private Allergen allergens[]; 
    private String category; 
    private String description; 
    private String imageBytes;
    private String name; 
    private float price; 
    private transient File image; 


    /**
     * menuItemFromJson - create a menuItem from a JSON string. 
     * @param body - the string containing the menuItem in JSON format. 
     * @return a MenuItem with the feilds conatined from the JSON string. 
     */
    public static MenuItem menuItemFromJson( String body ) {
        Gson gson = new Gson(); 
        MenuItem item = new MenuItem(); 

        try { 
            item = gson.fromJson( body, MenuItem.class );
        } catch( JsonSyntaxException e ){
            System.out.printf("Failed to deserialze the request body into a MenuItem object.\n" + 
                    "Request body: %s\n. Exception: %s\n", body, e.toString() );
        }
        return item; 
    }

    /**
     * Consruct an empty menu item.
     */
    public MenuItem(){
        this.itemID = -1; 
        this.allergens = new Allergen[0]; 
    }

    /** 
     * MenuItem - Create a menuitem object from a sql query row. 
     * @param ResultSet -  a sql result set from a database query to turn into a MenuItem object. 
     * @return a full menuItem object. 
     */
    public MenuItem( ResultSet rs ){

        ArrayList<Allergen> allergens = new ArrayList<Allergen>(); 

        try{ 
            this.itemID = rs.getInt( "itemID" );  
            this.name = rs.getString("itemName"); 
            this.category = rs.getString("category"); 
            this.description = rs.getString("description"); 
            
            if( rs.getBoolean("gluten") ){
                allergens.add( Allergen.GLUTEN );
            }
            if( rs.getBoolean("meat") ){
                allergens.add( Allergen.MEAT ); 
            }
            if( rs.getBoolean("dairy") ){
                allergens.add( Allergen.DAIRY); 
            }
            if( rs.getBoolean("nuts") ){
                allergens.add( Allergen.NUTS ); 
            }
            if( rs.getBoolean("soy") ) {
                allergens.add( Allergen.SOY ); 
            }
            this.allergens = allergens.toArray( new Allergen[ allergens.size() ]);
        } catch( SQLException e) {
            System.out.printf("Failed to get the required fields while creating a menuItem object.\n" + 
                    "Exception: %s\n", e.toString() );
        }
    }

    /**
     * menuItems - a factory method that creates a list of menuItems 
     * that are contained in given menuID. 
     * @param menuID - the menu that you want to get menuItems from. 
     * @return MenuItem[] - an array of menuItems. 
     */
    public static MenuItem[] menuItems( int menuID ){

        ResultSet rs = DBUtil.getMenuItems( menuID ); 
        ArrayList<MenuItem> result = new ArrayList<MenuItem>(); 

        try { 
            while( rs.next() ){
                MenuItem mItem = new MenuItem( rs ); 
                result.add( mItem ); 
            }
        } catch( SQLException e ){
            System.out.printf("Failed to get next row in result set.\n Exception: %s\n", e.toString() );
            return null; 
        }

        return result.toArray( new MenuItem[  result.size() ] );
    }

    public void saveImage( int menuID ){
        if( this.imageBytes == null ){
            return; 
        }
        if( menuID == -1 || this.itemID == -1 ){
            return; 
        }
        byte[] bytes = ImageUtil.deserialize( this.imageBytes );
        this.image = ImageUtil.saveImage( menuID, this.itemID, bytes);   
    }

    public void setItemID( int id ) {
        this.itemID = id; 
    }

    public String getName() {
        return this.name; 
    }
    
    public String getDescription(){
        return this.description; 
    }

    public String getCategory() {
        return this.category; 
    }

    public Allergen[] getAllergens(){
        return this.allergens; 
    }

    public float getPrice() {
        return this.price; 
    }
    

    @Override 
    public String toString() {
        if( this.name == null ){
            return "Empty MenuItem.\n";
        }
        StringBuilder str = new StringBuilder(); 
        str.append("\t" + this.category + "\n"); 
        str.append("\t" + this.description + "\n"); 
        //str.append(imageBytes)
        str.append("\t" + this.name + "\n"); 
        str.append("\t" + String.valueOf(this.price) + "\n");
        str.append("\tallergens: \n" );
        for( int i = 0; i < this.allergens.length; i++ ){
            if( this.allergens[i] == null ){
                System.out.printf("Empty Allergen for menuItem: %s\n", this.name ); 
                continue; 
            }
            str.append( "\t\t" + this.allergens[i].name() + "\n"  ); 
        }
        return str.toString();
    }
}

