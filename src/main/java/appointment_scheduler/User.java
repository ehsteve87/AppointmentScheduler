package appointment_scheduler;

public class User {
    private int id;
    private String username;
    private String password;

    private static User loggedInUser;

    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString(){
        return this.username;
    }

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static void setLoggedInUser(User loggedInUser) {
        User.loggedInUser = loggedInUser;
    }
}
