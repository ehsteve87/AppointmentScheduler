package appointment_scheduler;

import java.util.ArrayList;

/**
 * This class is for initializing Country objects
 */
public class Country extends Updatable{
    /**
     * the country ID
     */
    private int id;
    /**
     * the country name
     */
    private String name;
    /**
     * the country's divisions
     */
    private ArrayList<Division> divisionList;

    /**
     * constructor for Country objects
     * @param id country ID
     * @param name country name
     */
    public Country(int id, String name) {
        super();
        this.id = id;
        this.name = name;
        this.divisionList = new ArrayList<>();
    }

    /**
     * Makes country's default String representation the country name
     * @return
     */
    @Override
    public String toString(){
        return this.name;
    }

    /**
     * getter for ID field
     * @return the ID
     */
    public int getId() {
        return id;
    }

    /**
     * getter for name field
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * getter for the division list
     * @return the division list
     */
    public ArrayList<Division> getDivisionList() {
        return divisionList;
    }
}
