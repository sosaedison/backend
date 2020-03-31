package AutoGarcon; 
import static spark.Spark.*;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import spark.Request;
import spark.Response;
import com.google.gson.JsonSyntaxException;
import spark.Route;

import java.lang.reflect.Type;


/**
 * Main:  This class contains all of the logic for 
 * API routes and their respective handlers.
 * 
 * 
 * @author  Tyler Beverley, 
 * @author Sosa Edison  
 * @version 0.1
 * @since 2/24/20
 * @see <a href="https://github.com/auto-garcon/documentation/blob/master/APISpecification.md">Documentation</a>;  For API Endpoints. 
 * @see <a href="https://github.com/auto-garcon/backend">README</a>; For build instructions. 
 * 
 * You *can* refer to the documentation 
 * for information about this API, but as always, 
 * the code is the primary source of truth. 
 * Meaning that it's possible for the documentation to be out of date 
 * But the code will always be current. 
 *
 *
 */
public class Main {


    /*
     * Can implement custom 501 handling in the future. 
     */
    public static Object endpointNotImplemented( Request req, Response res ){
        res.status(501); 
        return res; 
    }

    public static Object serveStatic(Request req, Response res) {
        res.type("text/html");
        res.redirect("index.html", 201);
        return res;
    }



    public static Object addMenu( Request req, Response res) {
  
        Menu menu = Menu.menuFromJson( req.body() );   
        menu.save(); 
        System.out.println(menu.toString());

        if (menu.isEmpty()) {
            res.status(200);
        } else {
            res.status(500); 
        }

        return res; 
    }

    public static Object addUser( Request req, Response response) {

        User user = User.userFromJson( req.body() );
        JsonObject object = new JsonParser().parse(req
        .body()).getAsJsonObject();
        user.setToken(object.get("tokenObj").getAsJsonObject().get("access_token").toString().substring(0,100));
        user.setFirstName(object.get("profileObj").getAsJsonObject().get("givenName").toString());
        user.setLastName(object.get("profileObj").getAsJsonObject().get("familyName").toString());
        user.setEmail(object.get("profileObj").getAsJsonObject().get("email").toString());
        user.addUser();



        response.type("application/json");
        JsonObject jo = new JsonObject();
        jo.addProperty("firstName", user.getFirstName());
        jo.addProperty("lastName", user.getLastName());
        jo.addProperty("email", user.getEmail());
        jo.addProperty("token", user.getToken());

        return jo;
    }

    public static void initRouter(){

//		get("/", Main::serveStatic);

        path("/api", () -> {
            path("/users", () -> {
                post("/newuser", "*/*, application/json", Main::addUser);
                path("/:userid", () -> {
                    get("", Main::endpointNotImplemented );
                    get("/favorites", Main::endpointNotImplemented);
                    get("/orders", Main::endpointNotImplemented); 
                });
            });
            path("/restaurant", () -> {
                path("/:resturantid", () -> {
                    get("", Main::endpointNotImplemented); 
                    get("/orders", Main::endpointNotImplemented);
                    get("/tables", Main::endpointNotImplemented); 
                    post("/sitdown",Main::endpointNotImplemented); 
                    post("/orders/submit", Main::endpointNotImplemented); 
                    post("orders/complete", Main::endpointNotImplemented); 
                    path("/menu", () -> {
                        get("", Main::endpointNotImplemented ); 
                        post("/add", "application/json", Main::addMenu); 
                        post("/remove", Main::endpointNotImplemented); 
                    });
                });
            });
        });
    }


    public static void startServer() {

        //port(80);
        port(443); // HTTPS port
		staticFiles.location("/public");
        secure("/home/ubuntu/env/keystore.jks","autogarcon", null, null); // HTTPS key configuration for spark
        initRouter(); 
        //DBUtil.connectToDB();

        //Menu test = new Menu( 1, 1); 
    }

	public static void main(String[] args) {
        startServer(); 

	}
}



