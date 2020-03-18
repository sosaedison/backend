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
 * Represetns information pertaining to items
 * that are conatined within a menu.
 *
 */
public class MenuItem { 

    public enum Allergen { 
        MEAT, 
        DIARY, 
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

    public MenuItem(){
        this.itemID = -1; 
        this.allergens = new Allergen[0]; 
    }

    public MenuItem( ResultSet rs ){

        ArrayList<Allergen> allergens = new ArrayList<Allergen>(); 

        try{ 
            this.itemID = rs.getInt( "itemID" );  
            this.category = rs.getString("category"); 
            this.description = rs.getString("description"); 
            this.name = rs.getString("itemName"); 
            
            if( rs.getBoolean("gluten") ){
                allergens.add( Allergen.GLUTEN );
            }
            if( rs.getBoolean("meat") ){
                allergens.add( Allergen.MEAT ); 
            }
            if( rs.getBoolean("diary") ){
                allergens.add( Allergen.DIARY ); 
            }
            if( rs.getBoolean("nuts") ){
                allergens.add( Allergen.NUTS ); 
            }
            if( rs.getBoolean("soy") ) {
                allergens.add( Allergen.SOY ); 
            }
            this.allergens = (Allergen[])allergens.toArray();
        } catch( SQLException e) {
            System.out.printf("Failed to get the required fields while creating a menuItem object.\n" + 
                    "Exception: %s\n", e.toString() );
        }

    }

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

        return (MenuItem[]) result.toArray();
    }

    public void saveImage( int menuID ){
        byte[] bytes = ImageUtil.deserialize( this.imageBytes );
        this.image = ImageUtil.saveImage( menuID, this.itemID, bytes);   
    }

    @Override 
    public String toString() {
        StringBuilder str = new StringBuilder(); 
        str.append("allergens: \n" );
        for( int i = 0; i < this.allergens.length; i++ ){
            str.append( allergens[i].toString() ); 
        }
        str.append(this.category + "\n"); 
        str.append(this.description + "\n"); 
        //str.append(imageBytes)
        str.append(this.name + "\n"); 
        str.append(String.valueOf(this.price ));
        return str.toString();
    }
}

