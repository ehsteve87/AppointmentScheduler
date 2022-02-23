package appointment_scheduler;

/**
 * Class for creating Division objects
 */
public class Division extends Updatable{
    /**
     * The division ID
     */
    private int id;
    /**
     * The division name
     */
    private String name;
    /**
     * Country ID for the division's country
     */
    private int countryId;

    /**
     * Constructor for Division objects
     * @param id divison ID
     * @param name division name
     * @param countryId country ID
     */
    public Division(int id, String name, int countryId) {
        super();
        this.id = id;
        this.name = name;
        this.countryId = countryId;
    }

    /**
     * changes string representation of Division objects
     * @return the name
     */
    @Override
    public String toString(){
        return this.name;
    }

    /**
     * Getter for division ID
     * @return the ID
     */
    public int getId() {
        return id;
    }

    /**
     * Getter for division name
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for country ID
     * @return country ID
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * Setter for the division name
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
        update();
    }
}
