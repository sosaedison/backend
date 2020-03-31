package AutoGarcon;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class User {

    private int userID;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String hashedPass;
    private String token;

    public User(int userID, String firstName, String lastName, String email) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public User(){
        this.userID = 0;
        this.firstName = "";
        this.lastName = "";
        this.email = "";
    }
    public int getUserID(){return this.userID;}

    public String getFirstName(){return this.firstName;}

    public String getLastName(){return this.lastName;}

    public String getName(){return this.firstName+ " " + this.lastName;}

    public String getEmail(){return this.email;}

    public void setHashedPass(String hashedPass) {this.hashedPass = hashedPass;}

    public void setToken(String token) {this.token = token;}

    public void setFirstName(String firstName) {this.firstName = firstName;}

    public void setLastName(String lastName) {this.lastName = lastName;}

    public void setEmail(String email) {this.email = email;}

    public String getToken() {return this.token;}

    public static User userFromJson( String body ) {

        Gson gson = new Gson();
        User user = new User();

        try {
            user = gson.fromJson( body, User.class );
        } catch ( JsonSyntaxException e ) {
            System.out.printf("Failed to deserialize the request data into a User Object.\n" +
                    "Request body: %s.\n Exception: %s\n", body, e.toString() );
        }

        return user;
    }

    public void addUser() { DBUtil.addUser(this);}

    public String toString(){
        StringBuilder str = new StringBuilder();
        str.append("firstName: ").append(this.firstName).append("\n");
        str.append("lastName: ").append(this.lastName).append("\n");
        str.append("email: ").append(this.email).append("\n");
        str.append("token: ").append(this.token);
        return str.toString();
    }

}
