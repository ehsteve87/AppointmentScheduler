package appointment_scheduler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ControllerMainPage {

    public void initialize(){
        //Load appointments from Database
        try(PreparedStatement ps = JDBC.conn.prepareStatement("SELECT * FROM appointments;");
            ResultSet rs = ps.executeQuery()){
            while(rs.next()) {
                Appointment apptToAdd = new Appointment(
                        rs.getInt("Appointment_ID"),
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getString("Location"),
                        rs.getString("Type"),
                        TimeConverter.extractTimestampToUtc(rs.getTimestamp("Start")),
                        TimeConverter.extractTimestampToUtc(rs.getTimestamp("End")),
                        rs.getInt("Customer_ID"),
                        rs.getInt("User_ID"),
                        rs.getInt("Contact_ID")
                );
                apptToAdd.setCreateDate(TimeConverter.extractTimestampToUtc(rs.getTimestamp("Create_Date")));
                apptToAdd.setLastUpdate(TimeConverter.extractTimestampToUtc(rs.getTimestamp("Last_Update")));
            DatabaseLists.getApptList().add(apptToAdd);
            }
        } catch (SQLException e){
            System.out.println(e);
        }

        tfSearchAppointments.setText("");
        tblAppointments.setItems(DatabaseLists.getApptList());
        colApptId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colApptTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colApptDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colApptLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
//        colApptContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colApptStart.setCellValueFactory(new PropertyValueFactory<>("startTimeString"));
        colApptEnd.setCellValueFactory(new PropertyValueFactory<>("endTimeString"));
        colApptCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colApptUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));

    }

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
    private TableColumn<Appointment, String> colApptContact;

    @FXML
    private TableColumn<Appointment, Integer> colApptCustomerId;

    @FXML
    private TableColumn<Appointment, String> colApptDescription;

    @FXML
    private TableColumn<Appointment, String> colApptEnd;

    @FXML
    private TableColumn<Appointment, Integer> colApptId;

    @FXML
    private TableColumn<Appointment, String> colApptLocation;

    @FXML
    private TableColumn<Appointment, String> colApptStart;

    @FXML
    private TableColumn<Appointment, String> colApptTitle;

    @FXML
    private TableColumn<Appointment, Integer> colApptUserId;

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
    private TableView<Appointment> tblAppointments;

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

