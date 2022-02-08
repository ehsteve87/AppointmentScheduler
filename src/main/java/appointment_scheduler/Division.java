package appointment_scheduler;

public class Division extends Updatable{
    private int id;
    private String name;
    private int countryId;

    public Division(int id, String name, int countryId) {
        super();
        this.id = id;
        this.name = name;
        this.countryId = countryId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setName(String name) {
        this.name = name;
        update();
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
        update();
    }
}
