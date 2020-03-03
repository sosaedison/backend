package AutoGarcon; 



/*
 * @author Tyler Beverley 
 *
 * Represetns information pertaining to items
 * that are conatined within a menu.
 *
 */
public class MenuItem { 

    public enum Allergen { MEAT, DIARY, GLUTEN, NUTs, SOY, OTHER }
    private String category; 
    private String name; 
    private String description; 
    private float price; 
    private Allergen allergens[]; 

}

