package AutoGarcon;

public interface UserDao {

    public void addUser(User user);

    public User getUser(String userID);

    public User updateUser(User user);

    public void deleteUser(String userID);

    public boolean userExist(String userID);

}
