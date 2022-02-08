package appointment_scheduler;

public class Customer extends Updatable{
    private int id;
    private String name;
    private String address;
    private String postalCode;
    private String phone;
    private int divisionId;

    public Customer(int id, String name, String address, String postalCode, String phone, int divisionId) {
        super();
        this.id = id;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionId = divisionId;
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
}
