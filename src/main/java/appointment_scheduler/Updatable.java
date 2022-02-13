package appointment_scheduler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public abstract class Updatable {
    private ZonedDateTime createDate;
    private User createdBy;
    private ZonedDateTime lastUpdate;
    private User lastUpdatedBy;
    private String createDateString;
    private String lastUpdateString;
    private String createdByString;
    private String lastUpdatedByString;

    public Updatable() {
        this.createDate = TimeConverter.getNowInUtc();
        this.createDateString = TimeConverter.fullFormatter.format(this.getCreateDate().withZoneSameInstant(ZoneId.systemDefault()));
        this.createdBy = User.getLoggedInUser();
        this.createdByString = this.createdBy.getUsername();
        this.lastUpdate = TimeConverter.getNowInUtc();
        this.lastUpdateString = TimeConverter.fullFormatter.format(this.getLastUpdate().withZoneSameInstant(ZoneId.systemDefault()));
        this.lastUpdatedBy = User.getLoggedInUser();
        this.lastUpdatedByString = this.lastUpdatedBy.getUsername();
        }


    public Updatable(Boolean loadFromDatabase) {

    }


    public void update(){
        this.lastUpdate = TimeConverter.getNowInUtc();
        this.lastUpdatedBy = User.getLoggedInUser();
        this.lastUpdateString = TimeConverter.fullFormatter.format(this.lastUpdate);
        this.lastUpdatedByString = this.lastUpdatedBy.getUsername();
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

    public String getCreateDateString() {
        return createDateString;
    }

    public String getLastUpdateString() {
        return lastUpdateString;
    }

    public String getCreatedByString() {
        return createdByString;
    }

    public String getLastUpdatedByString() {
        return lastUpdatedByString;
    }

    //These setters should only be used when creating objects from database imports
    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
        this.createDateString = TimeConverter.fullFormatter.format(this.createDate);
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
        this.createdByString = this.createdBy.getUsername();
    }

    public void setLastUpdate(ZonedDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
        this.lastUpdateString = TimeConverter.fullFormatter.format(this.lastUpdate);
    }

    public void setLastUpdatedBy(User lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
        this.lastUpdatedByString = this.lastUpdatedBy.getUsername();
    }


}
