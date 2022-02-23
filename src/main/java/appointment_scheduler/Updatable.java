package appointment_scheduler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Parent class for items that store update data in the databse
 */
public abstract class Updatable {
    /**
     * date and time of creation
     */
    private ZonedDateTime createDate;
    /**
     * user who created the record
     */
    private User createdBy;
    /**
     * date and time of last update
     */
    private ZonedDateTime lastUpdate;
    /**
     * User who performed last update
     */
    private User lastUpdatedBy;
    /**
     * String representation of createDate
     */
    private String createDateString;
    /**
     * String representation of lastUpdate
     */
    private String lastUpdateString;
    /**
     * String representation of createdBy
     */
    private String createdByString;
    /**
     * String representation of lastUpdatedBy
     */
    private String lastUpdatedByString;

    /**
     * Constructor for Updatable objects
     */
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


    /**
     * Sets updated date to now and updating user to logged in user
     */
    public void update(){
        this.lastUpdate = TimeConverter.getNowInUtc();
        this.lastUpdatedBy = User.getLoggedInUser();
        this.lastUpdateString = TimeConverter.fullFormatter.format(this.lastUpdate);
        this.lastUpdatedByString = this.lastUpdatedBy.getUsername();
    }

    /**
     * Getter for create date
     * @return the create date
     */
    public ZonedDateTime getCreateDate() {
        return this.createDate;
    }

    /**
     * Setter for lastUpdate
     * @return the date of last update
     */
    public ZonedDateTime getLastUpdate() {
        return this.lastUpdate;
    }


    //These setters should only be used when creating objects from database imports

    /**
     * Setter for createDate
     * @param createDate the date to set
     */
    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
        this.createDateString = TimeConverter.fullFormatter.format(this.createDate);
    }

    /**
     * Setter for createdBy
     * @param createdBy user who created record
     */
    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
        this.createdByString = this.createdBy.getUsername();
    }

    /**
     * setter for lastUpdate
     * @param lastUpdate date and time of last update
     */
    public void setLastUpdate(ZonedDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
        this.lastUpdateString = TimeConverter.fullFormatter.format(this.lastUpdate);
    }

    /**
     * Setter for last updated by
     * @param lastUpdatedBy user who performed update
     */
    public void setLastUpdatedBy(User lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
        this.lastUpdatedByString = this.lastUpdatedBy.getUsername();
    }


}
