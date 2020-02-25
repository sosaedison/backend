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
 */



public class Main {
	public static void main(String[] args) {
		
        port(80);

		staticFiles.location("/public/build"); //Sets the location of static files

		get("/",new Route() { // home route
			@Override
			public Object handle(Request request, Response response) throws Exception {
				response.type("text/html");
				response.redirect("index.html", 201);
				return null;
			}
		});
	}
}
