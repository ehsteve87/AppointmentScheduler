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

        //populate contact combo box
        cboContact.getItems().clear();
        //lambda
        DatabaseLists.getContactList().forEach(c -> cboContact.getItems().add(c));

        //populate customerId combo box
        cboCustomerId.getItems().clear();
        //lambda
        DatabaseLists.getCustomerList().forEach(c -> cboCustomerId.getItems().add(c.getId()));

        //populate UserId combo box
        cboUserId.getItems().clear();
        //lambda
        DatabaseLists.getUserList().forEach(u -> cboUserId.getItems().add(u.getId()));

        //populate startTime combo box
        cboStartTime.getItems().clear();
        //lambda
        BusinessHours.getValidTimes().forEach(t -> cboStartTime.getItems().add(t.format(DateTimeFormatter.ofPattern("h:mm a"))));

        //populate endTime combo box
        cboEndTime.getItems().clear();
        //lambda
        BusinessHours.getValidTimes().forEach(t -> cboEndTime.getItems().add(t.format(DateTimeFormatter.ofPattern("h:mm a"))));
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
    private void pickedStartDate(ActionEvent event){

    }

    @FXML
    private void pickedEndDate(ActionEvent event){

    }

    @FXML
    void cancelButtonNewAppointment(ActionEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    void saveButtonNewAppointment(ActionEvent event) {
        StringBuilder problems = new StringBuilder();
        String title = tfTitle.getText();
        String description = tfDescription.getText();
        String location = tfLocation.getText();
        String type = tfType.getText();
        Contact contact = cboContact.getValue();
        LocalDate startDate = dpStartDate.getValue();
        String startTimeString = cboStartTime.getValue();
        LocalDate endDate = dpEndDate.getValue();
        String endTimeString = cboEndTime.getValue();
        Integer customerId = cboCustomerId.getValue();
        Integer userId = cboUserId.getValue();

        if(title.trim().equals("") || description.trim().equals("") || location.trim().equals("") || type.trim().equals("")
                || contact == null || startDate == null || startTimeString == null || endDate == null || endTimeString == null || customerId == null || userId == null){
            problems.append("Blank fields are not allowed.\n");
        } else {
            LocalDateTime startTime = LocalDateTime.of(startDate, LocalTime.parse(startTimeString, DateTimeFormatter.ofPattern("h:mm a")));
            LocalDateTime endTime = LocalDateTime.of(endDate, LocalTime.parse(endTimeString, DateTimeFormatter.ofPattern("h:mm a")));
            if(startTime.isAfter(endTime) || startTime.isEqual(endTime)){
                problems.append("End time must be after start time.\n");
            }
            ZonedDateTime startInUtc = TimeConverter.localToUtc(startTime);
            ZonedDateTime endInUtc = TimeConverter.localToUtc(endTime);
            //lambda
            Customer customer = DatabaseLists.findByProperty(DatabaseLists.getCustomerList(),c -> c.getId() == customerId);
            for(Appointment appt : customer.getAppointments()){
                System.out.println("startInUtc: " + startInUtc);
                System.out.println("appt.getStartTime(): " + appt.getStartTime());
                if((startInUtc.isAfter(appt.getStartTime()) || startInUtc.isEqual(appt.getStartTime())) && startInUtc.isBefore(appt.getEndTime())){
                    problems.append("Customer already has an appointment scheduled for this time.");
                    break;
                }
                if((endInUtc.isBefore(appt.getEndTime()) || endInUtc.isEqual(appt.getEndTime())) && endInUtc.isAfter(appt.getStartTime())){
                    problems.append("Customer already has an appointment scheduled for this time.");
                    break;
                }
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
                ps.setInt(7, customerId);
                ps.setInt(8, userId);
                ps.setInt(9, contact.getId());
                System.out.println(ps.toString());
                ps.executeUpdate();

            } catch (SQLException e){
                System.out.println(e);
            }
        }





    }

}