package appointment_scheduler;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Collection;
import java.util.function.Predicate;

public class DatabaseLists {

    private static ObservableList<Customer> customerList = FXCollections.observableArrayList();
    private static ObservableList<User> userList = FXCollections.observableArrayList();
    private static ObservableList<Appointment> apptList = FXCollections.observableArrayList();
    private static ObservableList<Client> clientList = FXCollections.observableArrayList();
    private static ObservableList<Country> countryList = FXCollections.observableArrayList();

    public static <T> T findByProperty(Collection<T> col, Predicate<T> filter) {
        return col.stream().filter(filter).findFirst().orElse(null);
    }


    public static ObservableList<Customer> getCustomerList() {
        return customerList;
    }

    public static ObservableList<User> getUserList() {
        return userList;
    }

    public static ObservableList<Appointment> getApptList() {
        return apptList;
    }

    public static ObservableList<Client> getClientList() {
        return clientList;
    }

    public static ObservableList<Country> getCountryList() {
        return countryList;
    }
}
