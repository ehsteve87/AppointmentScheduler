package appointment_scheduler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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

    public void initialize(){
        ResourceBundle localizedText = ResourceBundle.getBundle("languageBundle/loginScreen");
        System.out.println(localizedText);
        lblTitle.setText(localizedText.getString("title"));
        lblUsername.setText(localizedText.getString("username"));
        lblPassword.setText(localizedText.getString("password"));
        btnLogin.setText(localizedText.getString("login"));


    }

    @FXML
    void loginButton(ActionEvent event) {

    }

}




