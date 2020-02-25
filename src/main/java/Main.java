import spark.Request;
import spark.Response;
import spark.Route;
import static spark.Spark.*;/**
 * Some documentation should go here...
 * 
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

		get("/home",new Route() { // home route
			@Override
			public Object handle(Request request, Response response) throws Exception {
				response.type("text/html");
				response.redirect("index.html", 201);
				return null;
			}
		});
	}
}
