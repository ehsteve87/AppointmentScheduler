package appointment_scheduler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;

public class ControllerNewAppointment {
    public void initialize(){

        //disable past dates
        //LAMBDA
        dpStartDate.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) < 0 );
            }
        });
        //disable past dates
        //LAMBDA
        dpEndDate.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) < 0 );
            }
        });
    }

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
    private DatePicker dpEndDate;

    @FXML
    private DatePicker dpStartDate;

    @FXML
    private TextField tfApptId;

    @FXML
    private ComboBox<Integer> cboCustomerId;

    @FXML
    private TextField tfDescription;

    @FXML
    private TextField tfLocation;

    @FXML
    private TextField tfTitle;

    @FXML
    private ComboBox<Integer> cboUserId;

    @FXML
    void cancelButtonNewAppointment(ActionEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    void saveButtonNewAppointment(ActionEvent event) {

    }

}