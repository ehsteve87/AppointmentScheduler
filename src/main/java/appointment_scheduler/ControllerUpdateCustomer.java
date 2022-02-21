package appointment_scheduler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControllerUpdateCustomer {

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnSave;

    @FXML
    private ComboBox<Country> cboCountry;

    @FXML
    private ComboBox<Division> cboDivision;

    @FXML
    private TextField tfAddress;

    @FXML
    private TextField tfCustomerId;

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfPhone;

    @FXML
    private TextField tfPostal;

    //lambda
    @FXML
    private void populateDivisionComboBox(){
        if(cboCountry.getValue() != null){
            cboDivision.getItems().clear();
            Country country  = cboCountry.getValue();
            country.getDivisionList().forEach(d -> cboDivision.getItems().add(d));
            cboDivision.setDisable(false);
        }
    }

    @FXML
    void cancelButtonUpdateCustomer(ActionEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    void saveButtonUpdateCustomer(ActionEvent event) {

    }

}
