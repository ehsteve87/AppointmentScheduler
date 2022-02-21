package appointment_scheduler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.sql.Timestamp;

public class ControllerUpdateCustomer {
    //various lambdas
    public void initialize(){
        cboCountry.getItems().clear();
        //lambda
        DatabaseLists.getCountryList().forEach(c -> cboCountry.getItems().add(c));
        //lambda
        Country country = DatabaseLists.findByProperty(DatabaseLists.getCountryList(), c -> c.getName().equals(Customer.getSelectedCustomer().getCountryString()));
        Division division = DatabaseLists.findByProperty(country.getDivisionList(), d -> d.getId() == Customer.getSelectedCustomer().getDivisionId());
        tfCustomerId.setText(String.valueOf(Customer.getSelectedCustomer().getId()));
        tfName.setText(Customer.getSelectedCustomer().getName());
        tfAddress.setText(Customer.getSelectedCustomer().getAddress());
        cboCountry.setValue(country);
        populateDivisionComboBox();
        cboDivision.setValue(division);
        tfPostal.setText(Customer.getSelectedCustomer().getPostalCode());
        tfPhone.setText(Customer.getSelectedCustomer().getPhone());
        Customer.setSelectedCustomer(null);
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
        int customerId = Integer.valueOf(tfCustomerId.getText());
        String name = tfName.getText();
        String address = tfAddress.getText();
        Country country = cboCountry.getValue();
        Division division = cboDivision.getValue();
        String postal = tfPostal.getText();
        String phone = tfPhone.getText();

        String sql = "UPDATE customers\n" +
                "SET\n" +
                "Customer_Name = '" + name + "',\n" +
                "Address = '" + address + "',\n" +
                "Postal_Code = '" + postal + "',\n" +
                "Phone = '" + phone + "',\n" +
                "Last_Update = '" + Timestamp.valueOf(TimeConverter.getNowInUtc().toLocalDateTime()) + "',\n" +
                "Last_Updated_By = '" + User.getLoggedInUser().getUsername() + "',\n" +
                "Division_ID = " + division.getId() + "\n" +
                "WHERE Customer_ID = " + customerId + ";";

        try(var ps = JDBC.conn.prepareStatement(sql)){
            ps.executeUpdate();
            cancelButtonUpdateCustomer(event);
        } catch (SQLException e){
            System.out.println(e);
        }
    }
}