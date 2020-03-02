import spark.Request;
import spark.Response;
import spark.Route;
import static spark.Spark.*;


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
 *
 * <!--- move build instructions to README --> 
 * You can run this code in any IDE or IDE-like editors,
 * Just be sure to know how to set up the necessary Maven
 * and executable files if you use any other IDE/editor
 * other than eclipse or IntelliJ from JetBrains.
 * 
 * If you run into any errors, be sure to set the language
 * to type 8 - Lambda expressions and also be sure to
 * use java SDK 1.8 or higher.
 *
 *
 * Notes:
 * ------
 *  Use a response transformer to transform handler's results into json. 
 *  Spark 
 *
 *  import com.google.gson.Gson;
 *  public class JsonTransformer implements ResponseTransformer {
 *      private Gson gson = new Gson();
 *       @Override
 *      public String render(Object model) {
 *          return gson.toJson(model);
 *      }
 *
 * }
 *
 *
 */


public class Main {

    /*
     * Can implement custom 501 handling in the future. 
     */
    public static Object EndpointNotImplemented( Request req, Response res ){
        res.status(501); 
        return null; 
    }


	public static void main(String[] args) {

        port(80);
		staticFiles.location("/public/build"); //Sets the location of static files

        /*
         * Routes
         *
         */ 

		get("/" ,new Route() { // home route
			@Override
			public Object handle(Request request, Response response) throws Exception {
				response.type("text/html");
				response.redirect("index.html", 201);
				return null;
			}
		});

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
}
