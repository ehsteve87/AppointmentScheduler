package appointment_scheduler;

import java.time.ZonedDateTime;

public class Appointment extends Updatable{
    private int id;
    private String title;
    private String description;
    private String location;
    private String type;
    private ZonedDateTime startTime;
    private ZonedDateTime endTime;
    private int customerId;
    private int userId;
    private int contactId;

    public Appointment(int id, String title, String description, String location, String type, ZonedDateTime startTime, ZonedDateTime endTime, int customerId, int userId, int contactId) {
        super();
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getType() {
        return type;
    }

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public ZonedDateTime getEndTime() {
        return endTime;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getUserId() {
        return userId;
    }

    public int getContactId() {
        return contactId;
    }

    public void setTitle(String title) {
        this.title = title;
        update();
    }

    public void setDescription(String description) {
        this.description = description;
        update();
    }

    public void setLocation(String location) {
        this.location = location;
        update();
    }

    public void setType(String type) {
        this.type = type;
        update();
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
        update();
    }

    public void setEndTime(ZonedDateTime endTime) {
        this.endTime = endTime;
        update();
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
        update();
    }

    public void setUserId(int userId) {
        this.userId = userId;
        update();
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
        update();
    }
}
