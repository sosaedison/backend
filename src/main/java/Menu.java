package AutoGarcon; 

/*
 * @author Tyler Beverley
 * Class that represents information pertaining 
 * to menus for particular restaurants. 
 *
 */

public class Menu { 

    public enum Type{ DINNER, BREAKFAST, BRUNCH, LUNCH, DRINK }
    public enum Status{ DRAFT, ACTIVE, DELETED }

    private int menuID; 
    private Type type;  
    private TimeRange timeRanges[]; 
    private MenuItem menuItems[];  
}
