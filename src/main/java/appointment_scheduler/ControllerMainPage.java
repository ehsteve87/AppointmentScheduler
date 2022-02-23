package appointment_scheduler;

import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Controls the main page of the application, which has three tabs: Appointments, Customers, and Reports.
 */
public class ControllerMainPage {

    /**
     * Initializes the main page. First, it makes sure that all users that exist in non-user tables also exist in the Users table.
     * Then, it fetches records from the databse and loads them into Java classes.
     * Next, it populates the various tables and reports on the main page.
     * Finally, it shows an alert about upcoming appointments.
     *
     * This method uses several lambda expressions. Some are to make use of the DatabaseLists.findByProperty static method,
     * and others are to use the forEach method.
     */
    public void initialize(){
        //Create Set of all possible users
        Set<String> possibleUsers = new HashSet<>();
        String[] tables = {"appointments", "countries", "customers", "first_level_divisions", "users"};
        for (String table : tables) {
            try (PreparedStatement ps = JDBC.conn.prepareStatement("SELECT Created_By, Last_Updated_By FROM " + table + ";");
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    possibleUsers.add(rs.getString("Created_By"));
                    possibleUsers.add(rs.getString("Last_Updated_By"));
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }

        //Create Set of all Users
        Set<String> currentUsers = new HashSet<>();
        try (PreparedStatement ps = JDBC.conn.prepareStatement("SELECT User_Name FROM users;");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                currentUsers.add(rs.getString("User_Name"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        //Create any users that should exist but don't.
        ArrayList<String> pu = new ArrayList<>();
        ArrayList<String> cu = new ArrayList<>();
        for(String x : possibleUsers){
            pu.add(x);
        }
        for(String y : currentUsers){
            cu.add(y);
        }
        for(int i = 0; i < pu.size(); i++){
            for(int j = 0; j < cu.size(); j++){
                //if potential user not in current users, create potential user
                if(!cu.contains(pu.get(i))){
                    String userToCreate = pu.get(i);
                    String sql = "INSERT INTO users(User_Name, Password, Create_Date, Created_By, Last_Update, Last_Updated_By)\n" +
                            "VALUES('" + userToCreate + "', '" + userToCreate + "', NOW(), '" + userToCreate + "', NOW(), '" + userToCreate + "');";
                    try {
                        JDBC.conn.prepareStatement(sql).executeUpdate();
                    } catch (SQLException e) {

                    }
                }
            }
        }


        //Load users from Database
        DatabaseLists.getUserList().clear();
        String userQuery = "SELECT * FROM users;";
        try(PreparedStatement ps = JDBC.conn.prepareStatement(userQuery);
            ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                User userToAdd = new User(
                        rs.getInt("User_ID"),
                        rs.getString("User_Name"),
                        rs.getString("Password")
                    );
                DatabaseLists.getUserList().add(userToAdd);
                }
        } catch (SQLException e){
            System.out.println(e);
        }

        //Load appointments from Database
        DatabaseLists.getApptList().clear();
        String apptQuery = "SELECT a.Appointment_ID, a.Title, a.Description, a.Location, a.Type, a.Start, \n" +
                            "a.End, a.Create_Date, a.Created_By, a.Last_Update, a.Last_Updated_By,\n" +
                            "a.Customer_ID, a.User_ID, a.Contact_ID, c.Contact_Name\n" +
                            "FROM appointments a\n" +
                            "INNER JOIN contacts c\n" +
                            "ON a.Contact_ID = c.Contact_ID;";
        try(PreparedStatement ps = JDBC.conn.prepareStatement(apptQuery);
            ResultSet rs = ps.executeQuery()){
            while(rs.next()) {
                Appointment apptToAdd = new Appointment(
                        true,
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
                apptToAdd.setContactName(rs.getString("Contact_Name"));
                String createdBy = rs.getString("Created_By");
                //LAMBDA
                apptToAdd.setCreatedBy(DatabaseLists.findByProperty(DatabaseLists.getUserList(),u -> u.getUsername().equals(createdBy)));
                String updatedBy = rs.getString("Last_Updated_By");
                //LAMBDA
                apptToAdd.setLastUpdatedBy(DatabaseLists.findByProperty(DatabaseLists.getUserList(),u->u.getUsername().equals(updatedBy)));
            DatabaseLists.getApptList().add(apptToAdd);
            }
        } catch (SQLException e){
            System.out.println(e);
        }

        //load Customers from database
        DatabaseLists.getCustomerList().clear();
        String customerQuery = "SELECT c.Customer_ID, c.Customer_Name, c.Address, c.Postal_Code, c.Phone, \n" +
                "c.Create_Date, c.Created_By, c.Last_Update, c.Last_Updated_By, c.Division_ID,\n" +
                "d.Division, countries.Country\n" +
                "FROM customers c\n" +
                "INNER JOIN first_level_divisions d\n" +
                "ON d.Division_ID = c.Division_ID\n" +
                "INNER JOIN countries\n" +
                "ON d.Country_ID = countries.Country_ID;";
        try(PreparedStatement ps = JDBC.conn.prepareStatement(customerQuery);
            ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                Customer customerToAdd = new Customer(
                    true,
                    rs.getInt("Customer_ID"),
                    rs.getString("Customer_Name"),
                    rs.getString("Address"),
                    rs.getString("Postal_Code"),
                    rs.getString("Phone"),
                    rs.getInt("Division_ID"),
                    rs.getString("Division"),
                    rs.getString("Country")
                );
                customerToAdd.setCreateDate(TimeConverter.extractTimestampToUtc(rs.getTimestamp("Create_Date")));
                customerToAdd.setLastUpdate(TimeConverter.extractTimestampToUtc(rs.getTimestamp("Last_Update")));
                String createdBy = rs.getString("Created_By");
                //LAMBDA
                customerToAdd.setCreatedBy(DatabaseLists.findByProperty(DatabaseLists.getUserList(),u->u.getUsername().equals(createdBy)));
                String updatedBy = rs.getString("Last_Updated_By");
                //LAMBDA
                customerToAdd.setLastUpdatedBy(DatabaseLists.findByProperty(DatabaseLists.getUserList(),u->u.getUsername().equals(updatedBy)));
                DatabaseLists.getCustomerList().add(customerToAdd);
            }
        } catch (SQLException e){
            System.out.println(e);
        }

        //Load contacts from database
        DatabaseLists.getContactList().clear();
        String contactQuery = "SELECT * FROM contacts;";
        try(PreparedStatement ps = JDBC.conn.prepareStatement(contactQuery);
            ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                Contact contact = new Contact(
                        rs.getInt("Contact_ID"),
                        rs.getString("Contact_Name"),
                        rs.getString("email")
                );
                DatabaseLists.getContactList().add(contact);
            }
        } catch (SQLException e){
            System.out.println(e);
        }


        //Load countries from database
        DatabaseLists.getCountryList().clear();
        String countryQuery = "SELECT * FROM countries;";
        try(PreparedStatement ps = JDBC.conn.prepareStatement(countryQuery);
            ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                Country country = new Country(
                        rs.getInt("Country_ID"),
                        rs.getString("Country")
                );
                DatabaseLists.getCountryList().add(country);
            }
        } catch (SQLException e){
            System.out.println(e);
        }

        //Load divisions from database
        String divisionQuery = "SELECT Division_ID, Division, Country_ID FROM first_level_divisions ORDER BY Division;";
        try(PreparedStatement ps = JDBC.conn.prepareStatement(divisionQuery);
            ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                Division division = new Division(
                        rs.getInt("Division_ID"),
                        rs.getString("Division"),
                        rs.getInt("Country_ID")
                );
                Country country = DatabaseLists.findByProperty(DatabaseLists.getCountryList(), c -> c.getId() == division.getCountryId());
                country.getDivisionList().add(division);
            }
        } catch (SQLException e){
            System.out.println(e);
        }


        //populate contact combo box on reports tab
        cboContactSchedule.getItems().clear();
        //lambda
        DatabaseLists.getContactList().forEach(c -> cboContactSchedule.getItems().add(c.getName()));

        //populate individual customer appointment lists
        DatabaseLists.getCustomerList().forEach(c -> c.getAppointments().clear());
        DatabaseLists.getApptList().forEach(a -> DatabaseLists.findByProperty(DatabaseLists.getCustomerList(), c -> c.getId() == a.getCustomerId())
                                                            .getAppointments().add(a));
        resetPromptText(cboContactSchedule, "Select a Contact to view their schedule");


        //populate type combo boxes on reports tab
        cboCounterType.getItems().clear();
        cboCounterType.getItems().add("All Types");
        cboLengthType.getItems().clear();
        cboLengthType.getItems().add("All Types");
        Set<String> apptTypes = new HashSet<>();
        //LAMBDA
        DatabaseLists.getApptList().forEach(a -> apptTypes.add(a.getType()));
        ArrayList<String> types = new ArrayList<>();
        for(String x : apptTypes){
            types.add(x);
        }
        //LAMBDA
        types.forEach(t -> cboCounterType.getItems().add(t));
        //LAMBDA
        types.forEach(t -> cboLengthType.getItems().add(t));
        resetPromptText(cboCounterType, "Pick a Type");
        resetPromptText(cboLengthType, "Pick a Type");

        //Populate months combo box on reports page
        cboCounterMonth.getItems().clear();
        HashSet<YearMonth> months = new HashSet<>();
        DatabaseLists.getApptList().forEach(a -> months.add(YearMonth.from(a.getStartTime())));
        ArrayList<YearMonth> monthsList = new ArrayList();
        for(YearMonth y : months){
            monthsList.add(y);
        }
        Collections.sort(monthsList);
        monthsList.forEach(m -> cboCounterMonth.getItems().add(m.format(TimeConverter.monthFormatter)));
        resetPromptText(cboCounterMonth, "Pick a Month");



        rdoAllTime.setSelected(true);

        //populate appointments table
        tblAppointments.setItems(DatabaseLists.getApptList());
        colApptId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colApptTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colApptDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colApptLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        colApptContact.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        colApptStart.setCellValueFactory(new PropertyValueFactory<>("startTimeString"));
        colApptEnd.setCellValueFactory(new PropertyValueFactory<>("endTimeString"));
        colApptCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colApptUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        tblAppointments.sort();

        //populate customers table
        tblCustomers.setItems(DatabaseLists.getCustomerList());
        colCustId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCustName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCustAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colCustPostal.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        colCustDivision.setCellValueFactory(new PropertyValueFactory<>("divisionString"));
        colCustCountry.setCellValueFactory(new PropertyValueFactory<>("countryString"));
        colCustPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colCustCreateDate.setCellValueFactory(new PropertyValueFactory<>("createDateString"));
        colCustCreatedBy.setCellValueFactory(new PropertyValueFactory<>("createdByString"));
        colCustLastUpdate.setCellValueFactory(new PropertyValueFactory<>("lastUpdateString"));
        colCustUpdatedBy.setCellValueFactory(new PropertyValueFactory<>("lastUpdatedByString"));
        tblCustomers.sort();

        //populate contact schedule table
        tblContactSchedule.setItems(DatabaseLists.getApptList());
        colScheduleApptId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colScheduleStart.setCellValueFactory(new PropertyValueFactory<>("startTimeString"));
        colScheduleEnd.setCellValueFactory(new PropertyValueFactory<>("endTimeString"));
        colScheduleTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colScheduleType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colScheduleDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colScheduleCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        tblContactSchedule.sort();

        //Upcoming appointments alert
        if(ControllerLoginScreen.getFromLoginScreen()) {
            StringBuilder upcomingAppts = new StringBuilder();
            for (Appointment appt : DatabaseLists.getApptList()) {
                LocalDateTime ldt = TimeConverter.utcToLocal(appt.getStartTime()).toLocalDateTime();
                if (ldt.isAfter(LocalDateTime.now()) && ldt.isBefore(LocalDateTime.now().plusMinutes(15))) {
                    upcomingAppts.append("Time: " + TimeConverter.utcToLocal(appt.getStartTime()).format(DateTimeFormatter.ofPattern("MMMM d, h:mm a")) + "\tAppointment ID: " + appt.getId() + "\n");
                }
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Upcoming Appointments");
            if (upcomingAppts.isEmpty()) {
                alert.setHeaderText("No upcoming appointments!");
                alert.show();
            } else {
                alert.setHeaderText("The following appointments begin in the next 15 minutes:");
                alert.setContentText(upcomingAppts.toString());
                alert.show();
            }
        }
    }

    /**
     * Combo box to choose between contacts to view their schedule
     */
    @FXML
    private ComboBox<String> cboContactSchedule;

    /**
     * Month combo box for appointment counter report
     */
    @FXML
    private ComboBox<String> cboCounterMonth;

    /**
     * Type combo box for appointment counter report
     */
    @FXML
    private ComboBox<String> cboCounterType;

    /**
     * Type combo box for appointment length report
     */
    @FXML
    private ComboBox<String> cboLengthType;

    /**
     * Table column for contacts
     */
    @FXML
    private TableColumn<Appointment, String> colApptContact;

    /**
     * Table column for customer IDs
     */
    @FXML
    private TableColumn<Appointment, Integer> colApptCustomerId;

    /**
     * Table column for appointment descriptions
     */
    @FXML
    private TableColumn<Appointment, String> colApptDescription;

    /**
     * Table column for appointment end times
     */
    @FXML
    private TableColumn<Appointment, String> colApptEnd;

    /**
     * Table column for appointment IDs
     */
    @FXML
    private TableColumn<Appointment, Integer> colApptId;

    /**
     * Table column for locations
     */
    @FXML
    private TableColumn<Appointment, String> colApptLocation;

    /**
     * Table column for appointment start times
     */
    @FXML
    private TableColumn<Appointment, String> colApptStart;

    /**
     * Table column for appointment titles
     */
    @FXML
    private TableColumn<Appointment, String> colApptTitle;

    /**
     * Table column for user IDs
     */
    @FXML
    private TableColumn<Appointment, Integer> colApptUserId;

    /**
     * Table to show customers
     */
    @FXML
    private TableView<Customer> tblCustomers;

    /**
     * Table column for addresses
     */
    @FXML
    private TableColumn<Customer, String> colCustAddress;

    /**
     * Table column for countries
     */
    @FXML
    private TableColumn<Customer, String> colCustCountry;

    /**
     * Table column for customer creation dates
     */
    @FXML
    private TableColumn<Customer, String> colCustCreateDate;

    /**
     * Table column for the user that created the customer
     */
    @FXML
    private TableColumn<Customer, String> colCustCreatedBy;

    /**
     * Table column for divisions
     */
    @FXML
    private TableColumn<Customer, String> colCustDivision;

    /**
     * Table column for customer IDs
     */
    @FXML
    private TableColumn<Customer, Integer> colCustId;

    /**
     * Table column for date of last update
     */
    @FXML
    private TableColumn<Customer, String> colCustLastUpdate;

    /**
     * Table column for customer names
     */
    @FXML
    private TableColumn<Customer, String> colCustName;

    /**
     * Table column for customer phone numbers
     */
    @FXML
    private TableColumn<Customer, String> colCustPhone;

    /**
     * Table column for postal codes
     */
    @FXML
    private TableColumn<Customer, String> colCustPostal;

    /**
     * Table column for user who last updated customer
     */
    @FXML
    private TableColumn<Customer, String> colCustUpdatedBy;

    /**
     * Table column for customer IDs
     */
    @FXML
    private TableColumn<Appointment, Integer> colScheduleCustomerId;

    /**
     * Table column for appointment IDs
     */
    @FXML
    private TableColumn<Appointment, Integer> colScheduleApptId;

    /**
     * Table column for descriptions
     */
    @FXML
    private TableColumn<Appointment, String> colScheduleDescription;

    /**
     * Table column for appointment end times
     */
    @FXML
    private TableColumn<Appointment, String> colScheduleEnd;

    /**
     * Table column for appointment start times
     */
    @FXML
    private TableColumn<Appointment, String> colScheduleStart;

    /**
     * Table column for appointment titles
     */
    @FXML
    private TableColumn<Appointment, String> colScheduleTitle;

    /**
     * Table column for appointment types
     */
    @FXML
    private TableColumn<Appointment, String> colScheduleType;

    /**
     * All time radio button
     */
    @FXML
    private RadioButton rdoAllTime;

    /**
     * Table column for customer IDs
     */
    @FXML
    private TableView<Appointment> tblAppointments;

    /**
     * Contact Schedule table
     */
    @FXML
    private TableView<Appointment> tblContactSchedule;

    /**
     * Button to calculate average appointment length
     */
    @FXML
    private Button btnAverageLength;

    /**
     * Button to count appointments
     */
    @FXML
    private Button btnCountAppts;

    /**
     * This method fixes a javafx bug where the combo box prompt goes away if you reinitialize a scene while a value is selected.
     */

    @FXML
    private void resetPromptText(ComboBox cbo, String prompt) {
        cbo.setPromptText(prompt);
        cbo.setButtonCell(new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(prompt);
                } else {
                    setText(item);
                }
            }
        });
    }

    /**
     * This method filters a tableview based on some criteria.
     * It uses a lambda expression to choose which collection elements are shown in the table.
     */
    @FXML
    private void filterContactSchedule(ActionEvent event) {
        tblContactSchedule.setItems(new FilteredList<>(DatabaseLists.getApptList(),
                                        a -> a.getContactName().equals(cboContactSchedule.getValue())));
    }

    /**
     * Handles the delete appointment button
     * @param event
     */
    @FXML
    private void deleteAppointmentButton(ActionEvent event) {
        Appointment selectedAppt = tblAppointments.getSelectionModel().getSelectedItem();
        if(selectedAppt != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Appointment");
            alert.setHeaderText("Are you sure you want to delete " + selectedAppt.getTitle() + "?");
            Optional<ButtonType> answer = alert.showAndWait();
            if(answer.get() == ButtonType.OK){
                String sql = "DELETE FROM appointments WHERE Appointment_ID=" + selectedAppt.getId() + ";";
                try(var ps = JDBC.conn.prepareStatement(sql)){
                    ps.executeUpdate();
                    Alert done = new Alert(Alert.AlertType.INFORMATION);
                    done.setTitle("Delete Appointment");
                    done.setHeaderText("Successfully deleted appointment");
                    done.setContentText("Appointment ID: " + selectedAppt.getId() + "\nAppointment Type: " + selectedAppt.getType());
                    done.show();
                    initialize();
                } catch (SQLException e){
                    System.out.println(e);
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Delete Appointment");
            alert.setHeaderText("No Appointment Selected");
            alert.showAndWait();
        }

    }

    /**
     * Handles the delete customer button.
     *
     * @param event
     */
    @FXML
    private void deleteCustomerButton(ActionEvent event) {
        Customer customerToDelete = tblCustomers.getSelectionModel().getSelectedItem();
        if(customerToDelete == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Delete Customer");
            alert.setContentText("No customer selected");
            alert.showAndWait();
        } else {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Delete Customer");
            confirmation.setHeaderText("Are you sure you want to delete " + customerToDelete.getName() + " and all their appointments?");
            Optional<ButtonType> answer = confirmation.showAndWait();
            if(answer.get() == ButtonType.OK){
                String deleteSQL = "DELETE FROM appointments WHERE Appointment_ID = ?;";
                for(int i=0; i<customerToDelete.getAppointments().size(); i++){
                    try(var ps = JDBC.conn.prepareStatement(deleteSQL)){
                        ps.setInt(1, customerToDelete.getAppointments().get(i).getId());
                        ps.executeUpdate();
                    } catch (SQLException e){
                        System.out.println(e);
                    }
                }
                try(var ps = JDBC.conn.prepareStatement("DELETE FROM customers WHERE Customer_ID = " + customerToDelete.getId())){
                    ps.executeUpdate();
                    Alert done = new Alert(Alert.AlertType.INFORMATION);
                    done.setTitle("Delete Customer");
                    done.setHeaderText("Successfully deleted " + customerToDelete.getName() + " and all their appointments.");
                    done.show();
                    initialize();
                } catch (SQLException e){
                    System.out.println(e);
                }
            }
        }
    }

    /**
     * Handles the New Appointment button
     * @param event
     * @throws IOException
     */
    @FXML
    private void newAppointmentButton(ActionEvent event) throws IOException {
        openModal("NewAppointment.fxml", "New Appointment", 750, event);
    }

    /**
     * Handles the New Customer button
     * @param event
     * @throws IOException
     */
    @FXML
    private void newCustomerButton(ActionEvent event) throws IOException {
        openModal("newCustomer.fxml", "New Customer", 600, event);
    }

    /**
     * Handles the Create Appointment for Selected Customer button
     * @param event
     * @throws IOException
     */
    @FXML
    private void createApptForCustomerButton(ActionEvent event) throws IOException {
        if(tblCustomers.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("No customer selected.");
            alert.showAndWait();
        } else {
            Customer.setSelectedCustomer(tblCustomers.getSelectionModel().getSelectedItem());
            newAppointmentButton(event);
        }
    }

    /**
     * Handles the This Month Radio Button
     * Uses a lambda expression to choose which records appear on the table
     * @param event
     */
    @FXML
    private void thisMonthRadioButton(ActionEvent event) {
        tblAppointments.setItems(new FilteredList<>(DatabaseLists.getApptList(),
                a -> (a.getStartTime().getMonth() == LocalDateTime.now().getMonth()) && a.getStartTime().getYear() == LocalDateTime.now().getYear()));
    }

    /**
     * Handles the This Week Radio Button
     * Uses a lambda expression to choose which records appear on the table
     * @param event
     */
    @FXML
    private void thisWeekRadioButton(ActionEvent event) {
        tblAppointments.setItems(new FilteredList<>(DatabaseLists.getApptList(),
                a -> a.getStartTime().isAfter(TimeConverter.getNowInUtc())
                    && a.getStartTime().isBefore(TimeConverter.getNowInUtc().plusWeeks(1))));
    }

    /**
     * Handles the All Time Radio Button
     * Uses a lambda expression to choose which records appear on the table
     * @param event
     */
    @FXML
    private void allTimeRadioButton(ActionEvent event) {
        tblAppointments.setItems(new FilteredList<>(DatabaseLists.getApptList(), a -> true));
    }

    /**
     * Method that opens a new modal window, such as to add or edit appointments or customers.
     * Whenever a modal window is closed, this method reinitializes the main page.
     * @param fxml
     * @param windowTitle
     * @param width
     * @param event
     * @throws IOException
     */
    @FXML
    private void openModal(String fxml, String windowTitle, double width, ActionEvent event) throws IOException {
        Stage newWindow = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml));
        Scene updateAppointmentPage = new Scene(fxmlLoader.load(), width, 400);

        newWindow.setTitle(windowTitle);
        newWindow.setScene(updateAppointmentPage);
        newWindow.initModality(Modality.WINDOW_MODAL);
        newWindow.initOwner(((Node) event.getTarget()).getScene().getWindow());
        newWindow.setOnHidden(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                Customer.setSelectedCustomer(null);
                initialize();
            }
        });
        newWindow.show();
    }

    /**
     * Handles the Update Appointment button
     * @param event
     * @throws IOException
     */
    @FXML
    private void updateAppointmentButton(ActionEvent event) throws IOException {
        if(tblAppointments.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Update Appointment");
            alert.setHeaderText("No appointment selected");
            alert.showAndWait();
        } else {
            Appointment.setApptToUpdate(tblAppointments.getSelectionModel().getSelectedItem());
            openModal("updateAppointment.fxml", "Update Appointment", 750, event);
        }
    }

    /**
     * Handles the Update Customer button
     * @param event
     * @throws IOException
     */
    @FXML
    private void updateCustomerButton(ActionEvent event) throws IOException {
        if(tblCustomers.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Update Customer");
            alert.setContentText("No customer selected");
            alert.showAndWait();
        } else {
            Customer.setSelectedCustomer(tblCustomers.getSelectionModel().getSelectedItem());
            openModal("updateCustomer.fxml", "Update Customer", 600, event);
        }
    }

    /**
     * By default, the Average Length button is disabled. When a type is chosen, this method enables the button.
     * @param event
     */
    @FXML
    private void enableAverageLengthButton(ActionEvent event){
        if(cboLengthType.getValue() != null){
            btnAverageLength.setDisable(false);
        }
    }

    /**
     * By default, the Count Appointments button is disabled. When appropriate values are chosen, this method enables it.
     * @param event
     */
    @FXML
    private void enableCountApptsButton(ActionEvent event){
        if(cboCounterMonth.getValue() != null && cboCounterType.getValue() != null){
            btnCountAppts.setDisable(false);
        }
    }

    /**
     * Handles the button for the Average Appointment Length report
     * @param event
     */
    @FXML
    private void calculateAverageAppointmentLength(ActionEvent event){
        String typeValue = cboLengthType.getValue();
        int counter = 0;
        int lengths = 0;
        if(typeValue == "All Types") {
            try (var ps = JDBC.conn.prepareStatement("SELECT Start, End FROM appointments;");
                 var rs = ps.executeQuery()) {
                while(rs.next()){
                    counter++;
                    LocalDateTime from = rs.getTimestamp("Start").toLocalDateTime();
                    LocalDateTime to = rs.getTimestamp("End").toLocalDateTime();
                    Duration duration = Duration.between(from, to);
                    lengths += duration.toMinutes();
                }

            } catch (SQLException e) {
                System.out.println(e);
            }
        } else {
            try (var ps = JDBC.conn.prepareStatement("SELECT Start, End FROM appointments WHERE Type = ?;")){
                ps.setString(1, typeValue);
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    counter++;
                    LocalDateTime from = rs.getTimestamp("Start").toLocalDateTime();
                    LocalDateTime to = rs.getTimestamp("End").toLocalDateTime();
                    Duration duration = Duration.between(from, to);
                    lengths += duration.toMinutes();
                }

            } catch (SQLException e){
                System.out.println(e);
            }
        }
        int average = lengths/counter;

        Alert report = new Alert(Alert.AlertType.INFORMATION);
        report.setTitle("Report");
        report.setHeaderText("Average length of appointment type: " + typeValue);
        report.setContentText(String.valueOf(average) + " minutes");
        report.showAndWait();
    }

    /**
     * Handles the button for the Count Appointments By Month report
     * @param event
     */
    @FXML
    private void countAppointmentsByMonth(ActionEvent event){
        String monthString = cboCounterMonth.getValue();
        String selectedType = cboCounterType.getValue();
        int finalCount = 0;
        if(!selectedType.equals("All Types")) {
            try (var ps = JDBC.conn.prepareStatement("SELECT count(Appointment_ID) as count FROM appointments WHERE date_format(Start, \"%M %Y\") = ? AND Type = ?")) {
                ps.setString(1, monthString);
                ps.setString(2, selectedType);
                ResultSet rs = ps.executeQuery();
                rs.next();
                finalCount = rs.getInt("count");
            } catch (SQLException e) {
                System.out.println(e);
            }
            Alert report = new Alert(Alert.AlertType.INFORMATION);
            report.setTitle("Report");
            report.setHeaderText("Total " + selectedType + " appointments in " + monthString + ":");
            report.setContentText(String.valueOf(finalCount));
            report.showAndWait();
        } else {
            try (var ps = JDBC.conn.prepareStatement("SELECT count(Appointment_ID) as count FROM appointments WHERE date_format(Start, \"%M %Y\") = ?")) {
                ps.setString(1, monthString);
                ResultSet rs = ps.executeQuery();
                rs.next();
                finalCount = rs.getInt("count");
            } catch (SQLException e) {
                System.out.println(e);
            }
            Alert report = new Alert(Alert.AlertType.INFORMATION);
            report.setTitle("Report");
            report.setHeaderText("Total appointments in " + monthString + ":");
            report.setContentText(String.valueOf(finalCount));
            report.showAndWait();
        }
    }
}

