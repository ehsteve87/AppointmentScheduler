module com.steve.appointmentscheduler {
    requires javafx.controls;
    requires javafx.fxml;


    opens appointment_scheduler to javafx.fxml;
    exports appointment_scheduler;
}