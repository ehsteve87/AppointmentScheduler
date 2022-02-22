package appointment_scheduler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Models Appointment events
 */
public class Appointment extends Updatable{
    /**
     * Appointment ID
     */
    private int id;
    /**
     * Appointment title
     */
    private String title;
    /**
     * Appointment description
     */
    private String description;
    /**
     * Appointment location
     */
    private String location;
    /**
     * Appointment type
     */
    private String type;
    /**
     * Appointment start time in UTC
     */
    private ZonedDateTime startTime;
    /**
     * Appointment end time in UTC
     */
    private ZonedDateTime endTime;
    /**
     * ID for customer associated with appointment
     */
    private int customerId;
    /**
     * ID for user associated with appointment
     */
    private int userId;
    /**
     * ID for contact associated with appointment
     */
    private int contactId;
    /**
     * String representation of start time used to populate TableView
     */
    private String startTimeString;
    /**
     * String representation of end time used to populate TableView
     */
    private String endTimeString;
    /**
     * String representation of contact name. Used for TableView.
     */
    private String contactName;
    /**
     * Static variable used to keep track of which appointment is being updated
     */
    private static Appointment apptToUpdate;


    /**
     * Constructor for new appointments
     * @param id the ID
     * @param title the title
     * @param description the description
     * @param location the location
     * @param type the type
     * @param startTime start time in UTC
     * @param endTime end time in UTC
     * @param customerId customer ID
     * @param userId user ID
     * @param contactId contact ID
     */
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

    /**
     * Constructor for Appointment objects. Only used when importing from database.
     * @param loadFromDatabase True if loading from database
     * @param id the ID
     * @param title the title
     * @param description the description
     * @param location the location
     * @param type the type
     * @param startTime start time in UTC
     * @param endTime end time in UTC
     * @param customerId customer ID
     * @param userId user ID
     * @param contactId contact ID
     */
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

    /**
     * Getter for id
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Getter for title
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Getter for description
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Getter for location
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Getter for type
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Getter for start time
     * @return the start time
     */
    public ZonedDateTime getStartTime() {
        return startTime;
    }

    /**
     * Getter for end time
     * @return the the end time
     */
    public ZonedDateTime getEndTime() {
        return endTime;
    }

    /**
     * Getter for customer ID
     * @return the customer ID
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Getter for user ID
     * @return the user ID
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Getter for contact ID
     * @return the contact ID
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * Getter for start time string
     * @return the start time string
     */
    public String getStartTimeString() {
        return startTimeString;
    }

    /**
     * Getter for end time string
     * @return the end time string
     */
    public String getEndTimeString() {
        return endTimeString;
    }

    /**
     * Getter for contact name
     * @return the contact name
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Getter for static appointment to update
     * @return the appointment to update
     */
    public static Appointment getApptToUpdate() {
        return apptToUpdate;
    }

    /**
     * Setter for static appointment to update
     * @param apptToUpdate the appointment to update
     */
    public static void setApptToUpdate(Appointment apptToUpdate) {
        Appointment.apptToUpdate = apptToUpdate;
    }

    /**
     * Setter for the title
     * @return the title
     */
    public void setTitle(String title) {
        this.title = title;
        update();
    }

    /**
     * Setter for contact name
     * @param contactName the contact name
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
}
