import static spark.Spark.*;
/**
 * Some documentation should go here...
 * Run this in one of the two following editors:
 * IntelliJ from JetBrains
 * 			OR
 * Eclipse.
 *
 * If you run into any errors, be sure to set the language
 * to type 8 - Lambda expressions and also be sure to
 * use java SDK 1.8 or higher.
 *
 *
 */
public class Main {
	public static void main(String[] args) {
		get("/", (req, res) -> "Hello World");
	}
}
