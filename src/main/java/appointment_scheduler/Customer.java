package appointment_scheduler;

import java.util.ArrayList;

public class Customer extends Updatable{
    private int id;
    private String name;
    private String address;
    private String postalCode;
    private String phone;
    private int divisionId;
    private String divisionString;
    private String countryString;
    private ArrayList<Appointment> appointments = new ArrayList<>();
    private static Customer selectedCustomer;



    public Customer(int id, String name, String address, String postalCode, String phone, int divisionId, String divisionString, String countryString) {
        super();
        this.id = id;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionId = divisionId;
    }

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

    @Override
    public String toString(){
        return this.name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public int getDivisionId() {
        return divisionId;
    }

    public String getDivisionString() {
        return divisionString;
    }

    public String getCountryString() {
        return countryString;
    }

    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }

    public static Customer getSelectedCustomer() {
        return selectedCustomer;
    }

    public void setName(String name) {
        this.name = name;
        update();
    }

    public void setAddress(String address) {
        this.address = address;
        update();
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
        update();
    }

    public void setPhone(String phone) {
        this.phone = phone;
        update();
    }

    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
        update();
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDivisionString(String divisionString) {
        this.divisionString = divisionString;
    }

    public void setCountryString(String countryString) {
        this.countryString = countryString;
    }

    public static void setSelectedCustomer(Customer customer) {
        Customer.selectedCustomer = customer;
    }
}
