package appointment_scheduler;

import java.time.ZoneId;
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
    private String startTimeString;
    private String endTimeString;
    private String contactName;
    private static Appointment apptToUpdate;



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
        this.startTimeString = TimeConverter.fullFormatter.format(this.startTime.withZoneSameInstant(ZoneId.systemDefault()));
        this.endTimeString = TimeConverter.fullFormatter.format(this.endTime.withZoneSameInstant(ZoneId.systemDefault()));
    }

    public Appointment(boolean loadFromDatabase, int id, String title, String description, String location, String type, ZonedDateTime startTime, ZonedDateTime endTime, int customerId, int userId, int contactId) {
        super(loadFromDatabase);
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
        this.startTimeString = TimeConverter.fullFormatter.format(this.startTime.withZoneSameInstant(ZoneId.systemDefault()));
        this.endTimeString = TimeConverter.fullFormatter.format(this.endTime.withZoneSameInstant(ZoneId.systemDefault()));
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

    public String getStartTimeString() {
        return startTimeString;
    }

    public String getEndTimeString() {
        return endTimeString;
    }

    public String getContactName() {
        return contactName;
    }

    public static Appointment getApptToUpdate() {
        return apptToUpdate;
    }

    public static void setApptToUpdate(Appointment apptToUpdate) {
        Appointment.apptToUpdate = apptToUpdate;
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
        this.startTimeString = TimeConverter.fullFormatter.format(startTime.withZoneSameInstant(ZoneId.systemDefault()));
        update();
    }

    public void setEndTime(ZonedDateTime endTime) {
        this.endTime = endTime;
        this.endTimeString = TimeConverter.fullFormatter.format(endTime.withZoneSameInstant(ZoneId.systemDefault()));
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

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
}
