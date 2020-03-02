import static spark.Spark.*; 
import spark.Request;
import spark.Response;


/*
 * This progam embodies the backend business logic, and
 * database interface for the various Auto-Garcon User Interfaces.
 * 
 * @author Tyler Beverley, 
 * @author Sosa Edison  
 * @version 0.1
 * @since 2/24/20
 * @see <a href="https://github.com/auto-garcon/documentation/blob/master/APISpecification.md">Documentation</a>;  For API Endpoints. 
 * @see <a href="https://github.com/auto-garcon/backend">README</a>; For build instructions. 
 * 
 *
 * You *can* refer to the documentation 
 * for information about this API, but as always, 
 * the code is the first source of truth. 
 * Meaning that it's possible for the documentation to be out of date 
 * But the code will always be current. 
 *
 *
 */


public class Main {

    /*
     * Can implement custom 501 handling in the future. 
     */
    public static Object EndpointNotImplemented( Request req, Response res ){
        res.status(501); 
        return res; 
    }

    public static Object ServeStatic(Request req, Response res) {
        res.type("text/html");
        res.redirect("index.html", 201);
        return null;
    }
    
    public static void InitRouter(){

		get("/", Main::ServeStatic);

        path("/api", () -> {
            path("/users", () -> {
                post("/newuser",  Main::EndpointNotImplemented ); 
                path("/:userid", () -> {
                    get("", Main::EndpointNotImplemented );
                    get("/favorites", Main::EndpointNotImplemented);
                    get("/orders", Main::EndpointNotImplemented); 
                });
            });
            path("/restaurant", () -> {
                path("/:resturantid", () -> {
                    get("", Main::EndpointNotImplemented); 
                    get("/orders", Main::EndpointNotImplemented);
                    get("/tables", Main::EndpointNotImplemented); 
                    post("/sitdown",Main::EndpointNotImplemented); 
                    post("/orders/submit", Main::EndpointNotImplemented); 
                    post("orders/complete", Main::EndpointNotImplemented); 
                    path("/menu", () -> {
                        get("", Main::EndpointNotImplemented ); 
                        post("/add", Main::EndpointNotImplemented); 
                        post("/remove", Main::EndpointNotImplemented); 
                    });
                });
            });
        });
    }


	public static void main(String[] args) {

        port(80);
		staticFiles.location("/public/build"); //Sets the location of static files
        InitRouter(); 

	}
}



