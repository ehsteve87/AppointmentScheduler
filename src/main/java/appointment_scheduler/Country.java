package appointment_scheduler;

import java.time.ZonedDateTime;
import java.util.ArrayList;

public class Country {
    private int id;
    private String name;
    private ZonedDateTime createDate;
    private User createdBy;
    private ZonedDateTime lastUpdate;
    private User lastUpdatedBy;
    private ArrayList<Division> divisionList;

    public Country(int id, String name) {
        this.id = id;
        this.name = name;
        this.createDate = TimeConverter.getNowInUtc();
        this.createdBy = User.getLoggedInUser();
        this.lastUpdate = TimeConverter.getNowInUtc();
        this.lastUpdatedBy = User.getLoggedInUser();
        this.divisionList = divisionList;
    }
}
