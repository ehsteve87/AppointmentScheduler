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

public class ControllerUpdateAppointment {
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



        //Set initial values
        tfApptId.setText(String.valueOf(Appointment.getApptToUpdate().getId()));
        tfTitle.setText(Appointment.getApptToUpdate().getTitle());
        tfDescription.setText(Appointment.getApptToUpdate().getDescription());
        tfLocation.setText(Appointment.getApptToUpdate().getLocation());
        tfType.setText(Appointment.getApptToUpdate().getType());
        cboContact.setValue(DatabaseLists.findByProperty(DatabaseLists.getContactList(), c -> c.getId() == Appointment.getApptToUpdate().getCustomerId()));
        dpStartDate.setValue(TimeConverter.utcToLocal(Appointment.getApptToUpdate().getStartTime()).toLocalDate());
        cboStartTime.setValue(TimeConverter.utcToLocal(Appointment.getApptToUpdate().getStartTime()).toLocalTime().format(DateTimeFormatter.ofPattern("h:mm a")));
        dpEndDate.setValue(TimeConverter.utcToLocal(Appointment.getApptToUpdate().getEndTime()).toLocalDate());
        cboEndTime.setValue(TimeConverter.utcToLocal(Appointment.getApptToUpdate().getEndTime()).toLocalTime().format(DateTimeFormatter.ofPattern("h:mm a")));
        //lambda
        cboCustomer.setValue(DatabaseLists.findByProperty(DatabaseLists.getCustomerList(), c -> c.getId() == Appointment.getApptToUpdate().getCustomerId()));
        //lambda
        cboUser.setValue(DatabaseLists.findByProperty(DatabaseLists.getUserList(), u -> u.getId() == Appointment.getApptToUpdate().getUserId()));
    }

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnSave;

    @FXML
    private ComboBox<Contact> cboContact;

    @FXML
    private ComboBox<String> cboEndTime;

    @FXML
    private ComboBox<String> cboStartTime;

    @FXML
    private TextField tfType;

    @FXML
    private DatePicker dpEndDate;

    @FXML
    private DatePicker dpStartDate;

    @FXML
    private TextField tfApptId;

    @FXML
    private ComboBox<Customer> cboCustomer;

    @FXML
    private TextField tfDescription;

    @FXML
    private TextField tfLocation;

    @FXML
    private TextField tfTitle;

    @FXML
    private ComboBox<User> cboUser;

    @FXML
    void cancelButtonUpdateAppointment(ActionEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    void saveButtonUpdateAppointment(ActionEvent event) {
        StringBuilder problems = new StringBuilder();
        Integer apptId = Integer.parseInt(tfApptId.getText());
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
            for(Appointment appt : customer.getAppointments()) {
                String conflict = "Customer already has an appointment scheduled for this time.";
                if ((startInUtc.isAfter(appt.getStartTime()) || startInUtc.isEqual(appt.getStartTime())) && startInUtc.isBefore(appt.getEndTime())
                        && Integer.parseInt(tfApptId.getText()) != appt.getId()) {
                    problems.append(conflict);
                    break;
                }
                if ((endInUtc.isBefore(appt.getEndTime()) || endInUtc.isEqual(appt.getEndTime())) && endInUtc.isAfter(appt.getStartTime())
                        && Integer.parseInt(tfApptId.getText()) != appt.getId()) {
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
                problems.append("Start time cannot be before current time.\n");
            }
        }
        if(!problems.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("The following problems were found");
            alert.setContentText(problems.toString());
            alert.showAndWait();
        } else {
            ZonedDateTime startTime = TimeConverter.localToUtc(LocalDateTime.of(startDate, LocalTime.parse(startTimeString, DateTimeFormatter.ofPattern("h:mm a"))));
            ZonedDateTime endTime = TimeConverter.localToUtc(LocalDateTime.of(endDate, LocalTime.parse(endTimeString, DateTimeFormatter.ofPattern("h:mm a"))));
            String sql = "UPDATE appointments " +
                    "SET Title = '" + title + "', Description = '" + description + "', Location = '" + location + "', Type = '" + type +
                    "', Start = '" + Timestamp.valueOf(startTime.toLocalDateTime()) +
                    "', End = '" + Timestamp.valueOf(endTime.toLocalDateTime()) +
                    "', Last_Update = '" + Timestamp.valueOf(TimeConverter.getNowInUtc().toLocalDateTime()) +
                    "', Last_Updated_By = '" + User.getLoggedInUser().getUsername() +
                    "', Customer_ID = " + customer.getId() + ", User_ID = " + user.getId() + ", Contact_ID = " + contact.getId() +
                    " WHERE Appointment_ID = " + apptId + ";";

            try(var ps = JDBC.conn.prepareStatement(sql)){
                ps.executeUpdate();
                cancelButtonUpdateAppointment(event);
            } catch (SQLException e){
                System.out.println(e);
            }
        }

    }
}
