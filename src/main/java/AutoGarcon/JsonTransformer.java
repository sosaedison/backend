package AutoGarcon; 

import com.google.gson.Gson;
import spark.ResponseTransformer;

/*
 * Basic Json Converter from an object. 
 * Feilds marked transient or synthetic are not included in JSON. 
 * As well as any Annonymous classes or local classes. 
 *
 */
public class JsonTransformer implements ResponseTransformer {
    private Gson gson = new Gson(); 

    public String render( Object model ){
        return gson.toJson(model); 
    }
}
