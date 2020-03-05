package AutoGarcon; 
import static spark.Spark.*; 
import spark.Request;
import spark.Response;


/**
 * Main: This progam embodies the backend business logic, and
 * database interface for the various Auto-Garcon User Interfaces.
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

    public Object addMenu( Request req, Response res){

        res.status(200);
        return res; 
    }

    public static void initRouter(){

		get("/", Main::serveStatic);

        path("/api", () -> {
            path("/users", () -> {
                post("/newuser",  Main::endpointNotImplemented ); 
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
                        post("/add", Main::endpointNotImplemented); 
                        post("/remove", Main::endpointNotImplemented); 
                    });
                });
            });
        });
    }


    public static void startServer() {

        port(80);
		staticFiles.location("/public/build"); 
        initRouter(); 
        DBUtil.connectToDB(); 
    }

	public static void main(String[] args) {
        startServer(); 

	}
}



