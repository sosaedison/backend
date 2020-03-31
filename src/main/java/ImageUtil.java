package AutoGarcon; 
import java.io.File;
import java.io.FileOutputStream; 
import java.io.IOException;
import java.util.Base64; 
import com.google.gson.JsonElement; 
import com.google.gson.JsonPrimitive;


public class ImageUtil { 


    public static final String basePath = "./images/menus/";

    public static File saveImage( int menuID, int menuItemID, byte[] bytes ){

        String path = String.format( basePath + "%d/%d", menuID, menuItemID );
        createMenuFolder( menuID ); 
        File image = new File( path  ); 
        FileOutputStream fos = null; 

        try{ 
            fos = new FileOutputStream( image ); 
            fos.write( bytes );
        } catch (IOException ioe){
            System.out.printf("IOException while trying to save an image.\n" + 
                    "Exception: %s\n", ioe.toString() );
        }
        return image; 
    }

    public static File getImage( int menuID, int menuItemID ) {

        String path = String.format( basePath + "%d/%d", menuID, menuItemID );
        File image = new File( path ); 

        if( !image.exists() ){
            System.out.printf("Failed to get the requested image for: " + 
                    "MenuID: %d, MenuItem: %d\n", menuID, menuItemID ); 
        }
        return image; 
    }

    public static void createMenuFolder( int menuID ){
        File dir = new File( basePath + Integer.toString( menuID ) );
        boolean result = dir.mkdir(); 
    }

    public static byte[] deserialize( JsonElement json ) {
        Base64.Decoder decoder = Base64.getDecoder(); 
        return decoder.decode(json.getAsString() );
    }
     
    public static byte[] deserialize( String bytes ){
        Base64.Decoder decoder = Base64.getDecoder(); 
        return decoder.decode( bytes );
    }

    public static JsonElement serialize(byte[] src ){
        Base64.Encoder encoder = Base64.getEncoder(); 
        return new JsonPrimitive(encoder.encodeToString(src));
    }

}
