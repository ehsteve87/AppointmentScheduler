package appointment_scheduler;

import java.util.ArrayList;

public class Country extends Updatable{
    private int id;
    private String name;
    private ArrayList<Division> divisionList;

    public Country(int id, String name) {
        super();
        this.id = id;
        this.name = name;
        this.divisionList = new ArrayList<>();
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

    public ArrayList<Division> getDivisionList() {
        return divisionList;
    }
}
