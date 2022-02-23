package appointment_scheduler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * Controls the login screen
 */
public class ControllerLoginScreen {

    /**
     * Used to determine whether to show upcoming appointments. Gets set to false after first login.
     */
    private static boolean fromLoginScreen = true;

    /**
     * Getter for fromLoginScreen
     * @return fromLoginScreen
     */
    public static boolean getFromLoginScreen(){return fromLoginScreen;}

    /**
     * The login button
     */
    @FXML
    private Button btnLogin;

    /**
     * The password label
     */
    @FXML
    private Label lblPassword;

    /**
     * The title label
     */
    @FXML
    private Label lblTitle;

    /**
     * The username label
     */
    @FXML
    private Label lblUsername;

    /**
     * The ZoneId label
     */
    @FXML
    private Label lblZoneId;

    /**
     * The password field
     */
    @FXML
    private PasswordField pfPassword;

    /**
     * The username field
     */
    @FXML
    private TextField tfUsername;

    /**
     * The ResourceBundle with localized strings for English and French
     */
    private ResourceBundle localizedText = ResourceBundle.getBundle("languageBundle/loginScreen");

    /**
     * Initializes the Login form
     */
    public void initialize(){
        lblTitle.setText(localizedText.getString("title"));
        lblUsername.setText(localizedText.getString("username"));
        lblPassword.setText(localizedText.getString("password"));
        btnLogin.setText(localizedText.getString("login"));

        lblZoneId.setText("ZoneId: " + ZoneId.systemDefault().toString());


    }

    /**
     * Handles the behavior of the login button, including cases for valid and invalid login attempts and logging attempts to login_activity.txt
     *
     *
     * @param event clicking the button
     * @throws IOException
     */
    @FXML
    void loginButton(ActionEvent event) throws IOException {
        StringBuilder logText = new StringBuilder();
        logText.append(String.format("Timestamp:\t" + TimeConverter.getNowInUtc().format(DateTimeFormatter.ISO_DATE_TIME) + "\t"));
        logText.append("Username:\t" + tfUsername.getText() + "\t");
        logText.append("Login:\t");

        String loginQuery = "SELECT User_ID, User_Name, Password FROM users WHERE User_Name = ?;";
        try(PreparedStatement ps = JDBC.conn.prepareStatement(loginQuery)){
            ps.setString(1, tfUsername.getText());
            ResultSet rs = ps.executeQuery();
            rs.next();
            if(rs.getString("Password").equals(pfPassword.getText())){
                User.setLoggedInUser(new User(rs.getInt("User_ID"),rs.getString("User_Name"),rs.getString("Password")));
                logText.append("Success");
                try(BufferedWriter writer = new BufferedWriter(new FileWriter("login_activity.txt", true))){
                    writer.append(logText.toString());
                    writer.newLine();
                }
                System.out.println("Logged in!");
                Parent root = FXMLLoader.load(getClass().getResource("mainPage.fxml"));
                Scene scene = new Scene(root, 1280, 720);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
                fromLoginScreen =false;

            } else {
                logText.append("Failure");
                try(BufferedWriter writer = new BufferedWriter(new FileWriter("login_activity.txt", true))){
                    writer.append(logText.toString());
                    writer.newLine();
                }
                Alert invalid = new Alert(Alert.AlertType.ERROR);
                invalid.setTitle(localizedText.getString("noLogIn"));
                invalid.setContentText(localizedText.getString("errorMessage"));
                invalid.showAndWait();
            }
        } catch (SQLException e) {
            logText.append("Failure");
            try(BufferedWriter writer = new BufferedWriter(new FileWriter("login_activity.txt", true))){
                writer.append(logText.toString());
                writer.newLine();
            }
            Alert invalid = new Alert(Alert.AlertType.ERROR);
            invalid.setHeaderText(localizedText.getString("noLogIn"));
            invalid.setContentText(localizedText.getString("errorMessage"));
            invalid.showAndWait();
        }

    }

}




