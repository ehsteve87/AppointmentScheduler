package appointment_scheduler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControllerNewAppointment {

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnSave;

    @FXML
    private ComboBox<?> cboContact;

    @FXML
    private ComboBox<?> cboEndTime;

    @FXML
    private ComboBox<?> cboStartTime;

    @FXML
    private ComboBox<?> cboType;

    @FXML
    private DatePicker dbEndDate;

    @FXML
    private DatePicker dpStartDate;

    @FXML
    private TextField tfApptId;

    @FXML
    private TextField tfCustomerId;

    @FXML
    private TextField tfDescription;

    @FXML
    private TextField tfLocation;

    @FXML
    private TextField tfTitle;

    @FXML
    private TextField tfUserId;

    @FXML
    void cancelButtonNewAppointment(ActionEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    void saveButtonNewAppointment(ActionEvent event) {

    }

}