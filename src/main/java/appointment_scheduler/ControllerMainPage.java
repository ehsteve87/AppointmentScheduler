package appointment_scheduler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerMainPage {

    @FXML
    private Button btnCreateApptForCustomer;

    @FXML
    private Button btnDeleteAppointment;

    @FXML
    private Button btnDeleteCustomer;

    @FXML
    private Button btnNewAppointment;

    @FXML
    private Button btnNewCustomer;

    @FXML
    private Button btnUpdateAppointment;

    @FXML
    private Button btnUpdateCustomer;

    @FXML
    private ComboBox<?> cboClientSchedule;

    @FXML
    private ComboBox<?> cboCounterMonth;

    @FXML
    private ComboBox<?> cboCounterType;

    @FXML
    private ComboBox<?> cboLengthType;

    @FXML
    private ComboBox<?> cboSearchAppointments;

    @FXML
    private ComboBox<?> cboSearchCustomers;

    @FXML
    private CheckBox ckbxPastAppointments;

    @FXML
    private TableColumn<?, ?> colApptContact;

    @FXML
    private TableColumn<?, ?> colApptCustomerId;

    @FXML
    private TableColumn<?, ?> colApptDescription;

    @FXML
    private TableColumn<?, ?> colApptEnd;

    @FXML
    private TableColumn<?, ?> colApptId;

    @FXML
    private TableColumn<?, ?> colApptLocation;

    @FXML
    private TableColumn<?, ?> colApptStart;

    @FXML
    private TableColumn<?, ?> colApptTitle;

    @FXML
    private TableColumn<?, ?> colApptUserId;

    @FXML
    private TableColumn<?, ?> colCustomerId;

    @FXML
    private TableColumn<?, ?> colScheduleApptId;

    @FXML
    private TableColumn<?, ?> colScheduleDescription;

    @FXML
    private TableColumn<?, ?> colScheduleEnd;

    @FXML
    private TableColumn<?, ?> colScheduleStart;

    @FXML
    private TableColumn<?, ?> colScheduleTitle;

    @FXML
    private TableColumn<?, ?> colScheduleType;

    @FXML
    private Label lblAverageLength;

    @FXML
    private Label lblCount;

    @FXML
    private RadioButton rdoAllTime;

    @FXML
    private RadioButton rdoThisMonth;

    @FXML
    private RadioButton rdoThisWeek;

    @FXML
    private Tab tabAppointments;

    @FXML
    private Tab tabCustomers;

    @FXML
    private TableView<?> tblAppointments;

    @FXML
    private TableView<?> tblClientSchedule;

    @FXML
    private TextField tfSearchAppointments;

    @FXML
    private TextField tfSearchCustomers;

    @FXML
    private ToggleGroup timeFilter;

    @FXML
    private void allTimeRadioButton(ActionEvent event) {

    }

    @FXML
    private void appointmentSearchCombo(ActionEvent event) {

    }

    @FXML
    private void appointmentSearchField(KeyEvent event) {

    }

    @FXML
    private void deleteAppointmentButton(ActionEvent event) {

    }

    @FXML
    private void deleteCustomerButton(ActionEvent event) {

    }

    @FXML
    private void newAppointmentButton(ActionEvent event) throws IOException {
        Stage newWindow = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("newAppointment.fxml"));
        Scene newAppointmentPage = new Scene(fxmlLoader.load(), 600,400);
        newWindow.setTitle("New Appointment");
        newWindow.setScene(newAppointmentPage);
        newWindow.initModality(Modality.WINDOW_MODAL);
        newWindow.initOwner(((Node) event.getTarget()).getScene().getWindow());
        newWindow.show();
    }

    @FXML
    private void newCustomerButton(ActionEvent event) throws IOException {
        Stage newWindow = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("newCustomer.fxml"));
        Scene newCustomerPage = new Scene(fxmlLoader.load(), 600,400);
        newWindow.setTitle("New Customer");
        newWindow.setScene(newCustomerPage);
        newWindow.initModality(Modality.WINDOW_MODAL);
        newWindow.initOwner(((Node) event.getTarget()).getScene().getWindow());
        newWindow.show();
    }

    @FXML
    private void createApptForCustomerButton(ActionEvent event) throws IOException {
        newAppointmentButton(event);
    }

    @FXML
    private void searchCustomersCombo(ActionEvent event) {

    }

    @FXML
    private void searchCustomersTextField(KeyEvent event) {

    }

    @FXML
    private void showPastAppointmentsCheckBox(ActionEvent event) {

    }

    @FXML
    private void thisMonthRadioButton(ActionEvent event) {

    }

    @FXML
    private void thisWeekRadioButton(ActionEvent event) {

    }

    @FXML
    private void updateAppointmentButton(ActionEvent event) throws IOException {
        Stage newWindow = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("updateAppointment.fxml"));
        Scene updateAppointmentPage = new Scene(fxmlLoader.load(), 600,400);
        newWindow.setTitle("Update Appointment");
        newWindow.setScene(updateAppointmentPage);
        newWindow.initModality(Modality.WINDOW_MODAL);
        newWindow.initOwner(((Node) event.getTarget()).getScene().getWindow());
        newWindow.show();
    }

    @FXML
    private void updateCustomerButton(ActionEvent event) throws IOException {
        Stage newWindow = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("updateCustomer.fxml"));
        Scene updateCustomerPage = new Scene(fxmlLoader.load(), 600,400);
        newWindow.setTitle("Update Customer");
        newWindow.setScene(updateCustomerPage);
        newWindow.initModality(Modality.WINDOW_MODAL);
        newWindow.initOwner(((Node) event.getTarget()).getScene().getWindow());
        newWindow.show();
    }

}

