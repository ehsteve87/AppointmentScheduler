package appointment_scheduler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;

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
    private void newAppointmentButton(ActionEvent event) {

    }

    @FXML
    private void newCustomerButton(ActionEvent event) {

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
    private void updateAppointmentButton(ActionEvent event) {

    }

    @FXML
    private void updateCustomerButton(ActionEvent event) {

    }

}

