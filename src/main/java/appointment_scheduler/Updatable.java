package appointment_scheduler;

import java.time.ZonedDateTime;

public abstract class Updatable {
    private ZonedDateTime createDate;
    private User createdBy;
    private ZonedDateTime lastUpdate;
    private User lastUpdatedBy;

    public Updatable() {
        this.createDate = TimeConverter.getNowInUtc();
        this.createdBy = User.getLoggedInUser();
        this.lastUpdate = TimeConverter.getNowInUtc();
        this.lastUpdatedBy = User.getLoggedInUser();
    }

    public void update(){
        this.lastUpdate = TimeConverter.getNowInUtc();
        this.lastUpdatedBy = User.getLoggedInUser();
    }

    public ZonedDateTime getCreateDate() {
        return this.createDate;
    }

    public User getCreatedBy() {
        return this.createdBy;
    }

    public ZonedDateTime getLastUpdate() {
        return this.lastUpdate;
    }

    public User getLastUpdatedBy() {
        return this.lastUpdatedBy;
    }

    //These setters should only be used when creating objects from database imports
    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public void setLastUpdate(ZonedDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public void setLastUpdatedBy(User lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }
}
