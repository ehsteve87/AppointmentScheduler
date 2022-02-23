package appointment_scheduler;

import java.util.ArrayList;

/**
 * Class for initializing Customer objects
 */
public class Customer extends Updatable{
    /**
     * customer ID
     */
    private int id;
    /**
     * customer name
     */
    private String name;
    /**
     * customer address
     */
    private String address;
    /**
     * customer postal code
     */
    private String postalCode;
    /**
     * customer phone
     */
    private String phone;
    /**
     * ID of customer division
     */
    private int divisionId;
    /**
     * string representation of customer division
     */
    private String divisionString;
    /**
     * string representation of customer country
     */
    private String countryString;
    /**
     * customer appointments
     */
    private ArrayList<Appointment> appointments = new ArrayList<>();
    /**
     * customer to update, create an appointment, or delete
     */
    private static Customer selectedCustomer;


    /**
     * Constructor for customer objects
     * @param id the id
     * @param name the name
     * @param address the address
     * @param postalCode the postal code
     * @param phone the phone number
     * @param divisionId the division ID
     * @param divisionString the division name
     * @param countryString the country name
     */
    public Customer(int id, String name, String address, String postalCode, String phone, int divisionId, String divisionString, String countryString) {
        super();
        this.id = id;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionId = divisionId;
    }

    /**
     * constructor that's used when loading customers from the database
     * @param loadFromDatabase set to true when loading from database
     * @param id the id
     * @param name the name
     * @param address the address
     * @param postalCode the postal code
     * @param phone the phone number
     * @param divisionId the division ID
     * @param divisionString the division name
     * @param countryString the country name
     */
    public Customer(boolean loadFromDatabase, int id, String name, String address, String postalCode, String phone, int divisionId, String divisionString, String countryString) {
        super(loadFromDatabase);
        this.id = id;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionId = divisionId;
        this.divisionString = divisionString;
        this.countryString = countryString;
    }

    /**
     * Makes default String representation the customer name
     * @return the name
     */
    @Override
    public String toString(){
        return this.name;
    }

    /**
     * getter for customer ID
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * getter for customer name
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * getter for customer address
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * getter for postal code
     * @return the postal code
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * getter for phone number
     * @return the phone number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * getter for division id
     * @return the division id
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * getter for division name
     * @return division name
     */
    public String getDivisionString() {
        return divisionString;
    }

    /**
     * getter for country name
     * @return country name
     */
    public String getCountryString() {
        return countryString;
    }

    /**
     * getter for appointment list
     * @return the appointment list
     */
    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }

    /**
     * getter for selected customer
     * @return selected customer
     */
    public static Customer getSelectedCustomer() {
        return selectedCustomer;
    }

    /**
     * setter for name
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
        update();
    }

    /**
     * Setter for ID
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Setter for selected customer
     * @param customer the selected customer
     */
    public static void setSelectedCustomer(Customer customer) {
        Customer.selectedCustomer = customer;
    }
}
