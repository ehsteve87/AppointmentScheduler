package appointment_scheduler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.ResourceBundle;

public class ControllerLoginScreen {

    @FXML
    private Button btnLogin;

    @FXML
    private Label lblPassword;

    @FXML
    private Label lblTitle;

    @FXML
    private Label lblUsername;

    @FXML
    private Label lblZoneId;

    @FXML
    private PasswordField pfPassword;

    @FXML
    private TextField tfUsername;

    private ResourceBundle localizedText = ResourceBundle.getBundle("languageBundle/loginScreen");

    public void initialize(){
        lblTitle.setText(localizedText.getString("title"));
        lblUsername.setText(localizedText.getString("username"));
        lblPassword.setText(localizedText.getString("password"));
        btnLogin.setText(localizedText.getString("login"));

        lblZoneId.setText("ZoneId: " + ZoneId.systemDefault().toString());


    }

    @FXML
    void loginButton(ActionEvent event) throws IOException {
        String loginQuery = "SELECT User_ID, User_Name, Password FROM users WHERE User_Name = ?;";
        try(PreparedStatement ps = JDBC.conn.prepareStatement(loginQuery)){
            ps.setString(1, tfUsername.getText());
            ResultSet rs = ps.executeQuery();
            rs.next();
            if(rs.getString("Password").equals(pfPassword.getText())){
                User.setLoggedInUser(new User(rs.getInt("User_ID"),rs.getString("User_Name"),rs.getString("Password")));
                System.out.println("Logged in!");
                Parent root = FXMLLoader.load(getClass().getResource("mainPage.fxml"));
                Scene scene = new Scene(root, 1280, 720);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();

            } else {
                Alert invalid = new Alert(Alert.AlertType.ERROR);
                invalid.setTitle(localizedText.getString("noLogIn"));
                invalid.setContentText(localizedText.getString("errorMessage"));
                invalid.showAndWait();
            }
        } catch (SQLException e) {
            Alert invalid = new Alert(Alert.AlertType.ERROR);
            invalid.setHeaderText(localizedText.getString("noLogIn"));
            invalid.setContentText(localizedText.getString("errorMessage"));
            invalid.showAndWait();
        }

    }

}




