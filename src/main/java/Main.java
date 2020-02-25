import spark.Request;
import spark.Response;
import spark.Route;
import static spark.Spark.*;

/**
 * Spark Hello world
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