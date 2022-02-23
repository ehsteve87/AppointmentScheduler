package appointment_scheduler;

/**
 * Class for creating Contact objects
 */
public class Contact {
    /**
     * Contact ID
     */
    private int id;
    /**
     * Contact name
     */
    private String name;
    /**
     * Contact email
     */
    private String email;

    /**
     * Constructor for Contact objects
     * @param id the id
     * @param name the name
     * @param email the email
     */
    public Contact(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    /**
     * Alternate constructor for Contact objects
     * @param name the name
     * @param email the email
     */
    public Contact(String name, String email) {
        this.name = name;
        this.email = email;
    }

    /**
     * Sets string representation of Contact objects
     * @return the name
     */
    @Override
    public String toString(){
        return this.name;
    }

    /**
     * Getter for contact ID
     * @return the ID
     */
    public int getId() {
        return id;
    }

    /**
     * Setter for the ID
     * @param id the ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter for contact name
     * @return the ma,e
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for contact name
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }
}
