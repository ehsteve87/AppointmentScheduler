package appointment_scheduler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.sql.Timestamp;

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
        String address = tfAddress.getText().trim();
        String postal = tfPostal.getText().trim();
        Country country = cboCountry.getValue();
        Division division = cboDivision.getValue();
        String phone = tfPhone.getText().trim();

        StringBuilder problems = new StringBuilder();
        if(name.equals("") || address.equals("") || postal.equals("") || country.equals("") || country == null || division == null || phone.equals("")){
            problems.append("Blank fields are not allowed\n");
        }
        if(!problems.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("The following problems were found:");
            alert.setContentText(problems.toString());
            alert.showAndWait();
        } else {
            String sql = "INSERT INTO customers (`Customer_Name`, `Address`, `Postal_Code`, `Phone`, `Create_Date`, `Created_By`, `Last_Update`, `Last_Updated_By`, `Division_ID`)\n" +
                    "VALUES(\n" +
                    "'" + name + "',\n" +
                    "'" + address + "',\n" +
                    "'" + postal + "',\n" +
                    "'" + phone + "',\n" +
                    "'" + Timestamp.valueOf(TimeConverter.getNowInUtc().toLocalDateTime()) + "',\n" +
                    "'" + User.getLoggedInUser().getUsername() + "',\n" +
                    "'" + Timestamp.valueOf(TimeConverter.getNowInUtc().toLocalDateTime()) + "',\n" +
                    "'" + User.getLoggedInUser().getUsername() + "',\n" +
                    "'" + division.getId() + "'\n" +
                    ");";
            System.out.println(sql);
            try(var ps = JDBC.conn.prepareStatement(sql)){
                ps.executeUpdate();
                cancelButtonNewCustomer(event);
            } catch (SQLException e){
                System.out.println(e);
            }
        }
    }
}
