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


    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }


}
