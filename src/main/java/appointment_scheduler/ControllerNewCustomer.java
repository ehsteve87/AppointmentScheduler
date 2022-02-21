package appointment_scheduler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControllerNewCustomer {
    //lambda
    public void initialize(){
    cboCountry.getItems().clear();
    DatabaseLists.getCountryList().forEach(c -> cboCountry.getItems().add(c));
    }

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
    private void cancelButtonNewCustomer(ActionEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void saveButtonNewCustomer(ActionEvent event) {
        String name = tfName.getText().trim();
        String address = tfName.getText().trim();
        String postal = tfPostal.getText().trim();
        Country country = cboCountry.getValue();
        Division division = cboDivision.getValue();
        String phone = tfPhone.getText().trim();

        StringBuilder problems = new StringBuilder();
        if(name.equals("") || address.equals("") || postal.equals("") || country.equals("") || country == null || division == null || phone.equals("")){
            problems.append("Blank fields are not allowed");
        }


    }

}
