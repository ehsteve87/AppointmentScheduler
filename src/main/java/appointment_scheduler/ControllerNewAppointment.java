package appointment_scheduler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * This class controls the New Appointment window
 */
public class ControllerNewAppointment {
    /**
     * Initializes the New Appointment window.
     * This method uses several lambda expressions. Two are used to disable past dates so they can't be chosen by the date picker,
     * and the rest are in forEach methods.
     */
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

        //populate contact combo box
        cboContact.getItems().clear();
        //lambda
        DatabaseLists.getContactList().forEach(c -> cboContact.getItems().add(c));

        //populate customerId combo box
        cboCustomer.getItems().clear();
        //lambda
        DatabaseLists.getCustomerList().forEach(c -> cboCustomer.getItems().add(c));
        if(Customer.getSelectedCustomer() != null){
            cboCustomer.setValue(Customer.getSelectedCustomer());
            cboCustomer.setDisable(true);
            Customer.setSelectedCustomer(null);

        }

        //populate UserId combo box
        cboUser.getItems().clear();
        //lambda
        DatabaseLists.getUserList().forEach(u -> cboUser.getItems().add(u));

        //populate startTime combo box
        cboStartTime.getItems().clear();
        //lambda
        BusinessHours.getValidTimes().forEach(t -> cboStartTime.getItems().add(t.format(DateTimeFormatter.ofPattern("h:mm a"))));

        //populate endTime combo box
        cboEndTime.getItems().clear();
        //lambda
        BusinessHours.getValidTimes().forEach(t -> cboEndTime.getItems().add(t.format(DateTimeFormatter.ofPattern("h:mm a"))));
    }

    /**
     * the Cancel button
     */
    @FXML
    private Button btnCancel;

    /**
     * the Contact combo box
     */
    @FXML
    private ComboBox<Contact> cboContact;

    /**
     * The End Time combo box
     */
    @FXML
    private ComboBox<String> cboEndTime;

    /**
     * The Start Time combo box
     */
    @FXML
    private ComboBox<String> cboStartTime;

    /**
     * The Type field
     */
    @FXML
    private TextField tfType;

    /**
     * The End Date datepicker
     */
    @FXML
    private DatePicker dpEndDate;

    /**
     * the Start Date datepicker
     */
    @FXML
    private DatePicker dpStartDate;

    /**
     * The Appointment ID field
     */
    @FXML
    private TextField tfApptId;

    /**
     * the Customer combo box
     */
    @FXML
    private ComboBox<Customer> cboCustomer;

    /**
     * The Description field
     */
    @FXML
    private TextField tfDescription;

    /**
     * The Location field
     */
    @FXML
    private TextField tfLocation;

    /**
     * The Title field
     */
    @FXML
    private TextField tfTitle;

    /**
     * the User combo box
     */
    @FXML
    private ComboBox<User> cboUser;

    /**
     * Handles the cancel button
     * @param event
     */
    @FXML
    void cancelButtonNewAppointment(ActionEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    /**
     * handles the Save button
     * @param event
     */
    @FXML
    void saveButtonNewAppointment(ActionEvent event) {
        StringBuilder problems = new StringBuilder();
        String title = tfTitle.getText().trim();
        String description = tfDescription.getText().trim();
        String location = tfLocation.getText().trim();
        String type = tfType.getText().trim();
        Contact contact = cboContact.getValue();
        LocalDate startDate = dpStartDate.getValue();
        String startTimeString = cboStartTime.getValue();
        LocalDate endDate = dpEndDate.getValue();
        String endTimeString = cboEndTime.getValue();
        Customer customer = cboCustomer.getValue();
        User user = cboUser.getValue();

        if(title.trim().equals("") || description.trim().equals("") || location.trim().equals("") || type.trim().equals("")
                || contact == null || startDate == null || startTimeString == null || endDate == null || endTimeString == null || customer == null || user == null){
            problems.append("Blank fields are not allowed.\n");
        } else {
            LocalDateTime startTime = LocalDateTime.of(startDate, LocalTime.parse(startTimeString, DateTimeFormatter.ofPattern("h:mm a")));
            LocalDateTime endTime = LocalDateTime.of(endDate, LocalTime.parse(endTimeString, DateTimeFormatter.ofPattern("h:mm a")));
            if(startTime.isAfter(endTime) || startTime.isEqual(endTime)){
                problems.append("End time must be after start time.\n");
            }
            ZonedDateTime startInUtc = TimeConverter.localToUtc(startTime);
            ZonedDateTime endInUtc = TimeConverter.localToUtc(endTime);
            String conflict = "Customer already has an appointment scheduled for this time.";
            for(Appointment appt : customer.getAppointments()) {
                if ((startInUtc.isAfter(appt.getStartTime()) || startInUtc.isEqual(appt.getStartTime())) && startInUtc.isBefore(appt.getEndTime())) {
                    problems.append(conflict);
                    break;
                }
                if ((endInUtc.isBefore(appt.getEndTime()) || endInUtc.isEqual(appt.getEndTime())) && endInUtc.isAfter(appt.getStartTime())) {
                    problems.append(conflict);
                    break;
                }
                if(startInUtc.isBefore(appt.getStartTime()) && endInUtc.isAfter(appt.getEndTime())
                        && Integer.parseInt(tfApptId.getText()) != appt.getId()){
                    problems.append(conflict);
                    break;
                }
            }
            if(startTime.isBefore(LocalDateTime.now())){
                System.out.println("got here!");
                problems.append("Start time cannot be before current time.\n");
            }
        }
        if(!problems.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("The following problems were found");
            alert.setContentText(problems.toString());
            alert.showAndWait();
        } else {
            String sql = "INSERT INTO appointments(Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID)\n" +
                    "VALUES(?, ?, ?, ?, ?, ?, '" +
                    Timestamp.valueOf(TimeConverter.getNowInUtc().toLocalDateTime()).toString() + "', '" +
                    User.getLoggedInUser().getUsername() + "', '" +
                    Timestamp.valueOf(TimeConverter.getNowInUtc().toLocalDateTime()).toString() + "', '" +
                    User.getLoggedInUser().getUsername() +
                    "', ?, ?, ?);";
            try(var ps = JDBC.conn.prepareStatement(sql)){
                ZonedDateTime startTime = TimeConverter.localToUtc(LocalDateTime.of(startDate, LocalTime.parse(startTimeString, DateTimeFormatter.ofPattern("h:mm a"))));
                ZonedDateTime endTime = TimeConverter.localToUtc(LocalDateTime.of(endDate, LocalTime.parse(endTimeString, DateTimeFormatter.ofPattern("h:mm a"))));
                ps.setString(1,title);
                ps.setString(2,description);
                ps.setString(3, location);
                ps.setString(4, type);
                ps.setString(5,Timestamp.valueOf(startTime.toLocalDateTime()).toString());
                ps.setString(6, Timestamp.valueOf(endTime.toLocalDateTime()).toString());
                ps.setInt(7, customer.getId());
                ps.setInt(8, user.getId());
                ps.setInt(9, contact.getId());
                ps.executeUpdate();
                cancelButtonNewAppointment(event);

            } catch (SQLException e){
                System.out.println(e);
            }
        }
    }
}