package AutoGarcon; 
import com.google.gson.Gson; 
import com.google.gson.JsonSyntaxException;

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

    private Allergen allergens[]; 
    private String category; 
    private String description; 
    private String imageBytes;
    private String name; 
    private float price; 

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

