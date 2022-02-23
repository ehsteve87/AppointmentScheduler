package appointment_scheduler;

/**
 * Class for creating User objects
 */
public class User {
    /**
     * user ID
     */
    private int id;
    /**
     * the username
     */
    private String username;
    /**
     * the password
     */
    private String password;

    /**
     * The user who is currently logged in
     */
    private static User loggedInUser;

    /**
     * Constructor for new users
     * @param id the userID
     * @param username the username
     * @param password the password
     */
    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    /**
     * sets string representation of User objects
     * @return the username
     */
    @Override
    public String toString(){
        return this.username;
    }

    /**
     * getter for logged in user
     * @return the logged in user
     */
    public static User getLoggedInUser() {
        return loggedInUser;
    }

    /**
     * getter for userID
     * @return the userID
     */
    public int getId() {
        return id;
    }

    /**
     * getter for username
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter for userID
     * @param id the userID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * setter for the logged in user
     * @param loggedInUser the logged in user
     */
    public static void setLoggedInUser(User loggedInUser) {
        User.loggedInUser = loggedInUser;
    }
}
