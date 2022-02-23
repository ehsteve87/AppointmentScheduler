package appointment_scheduler;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Collection;
import java.util.function.Predicate;

/**
 * This class stores information from the Database in Observable ArrayLists
 */
public abstract class DatabaseLists {

    /**
     * List of customers
     */
    private static ObservableList<Customer> customerList = FXCollections.observableArrayList();
    /**
     * List of users
     */
    private static ObservableList<User> userList = FXCollections.observableArrayList();
    /**
     * List of appointments
     */
    private static ObservableList<Appointment> apptList = FXCollections.observableArrayList();
    /**
     * List of contacts
     */
    private static ObservableList<Contact> contactList = FXCollections.observableArrayList();
    /**
     * List of countries
     */
    private static ObservableList<Country> countryList = FXCollections.observableArrayList();

    /**
     * This method searches through a collection and finds the first object that has a given property
     * @param col the collection to search through
     * @param filter a lambda expression that specifies the property to search for
     * @param <T> the type of object to search for
     * @return the found object
     */
    public static <T> T findByProperty(Collection<T> col, Predicate<T> filter) {
        return col.stream().filter(filter).findFirst().orElse(null);
    }

    /**
     * Getter for the customer list
     * @return the customer list
     */
    public static ObservableList<Customer> getCustomerList() {
        return customerList;
    }

    /**
     * Getter for the user list
     * @return the user list
     */
    public static ObservableList<User> getUserList() {
        return userList;
    }

    /**
     * Getter for the appointment list
     * @return the appointment list
     */
    public static ObservableList<Appointment> getApptList() {
        return apptList;
    }

    /**
     * Getter for the contact list
     * @return the contact list
     */
    public static ObservableList<Contact> getContactList() {
        return contactList;
    }

    /**
     * Getter for the country list
     * @return the country list
     */
    public static ObservableList<Country> getCountryList() {
        return countryList;
    }
}
