package appointment_scheduler;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Holds methods for converting between different time zones
 */
public abstract class TimeConverter {

    /**
     * Converts SQL timestamps to UTC ZonedDateTimes
     * @param timestamp the SQL timestamp
     * @return
     */
    public static ZonedDateTime extractTimestampToUtc(Timestamp timestamp){
        LocalDateTime ldt = timestamp.toLocalDateTime();
        ZonedDateTime zdt = ZonedDateTime.of(ldt, ZoneId.systemDefault());
        return zdt.withZoneSameInstant(ZoneId.of("Etc/UTC"));
    }

    /**
     * full date and time formatter
     */
    public static DateTimeFormatter fullFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy h:mm a");

    /**
     * month and year time formatter
     */
    public static DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MMMM yyyy");

    /**
     * Converts utc time to local time
     * @param time the utc time
     * @return
     */
    public static ZonedDateTime utcToLocal(ZonedDateTime time){
        return time.withZoneSameInstant(ZoneId.systemDefault());
    }

    /**
     * Converts local time to UTC time
     * @param ldt the local time
     * @return
     */
    public static ZonedDateTime localToUtc(LocalDateTime ldt){
        ZonedDateTime zdt = ZonedDateTime.of(ldt, ZoneId.systemDefault());
        ZonedDateTime utcTime = zdt.withZoneSameInstant(ZoneId.of("Etc/UTC"));
        return utcTime;
    }

    /**
     * Method for getting current time in utc
     * @return the current time in utc
     */
    public static ZonedDateTime getNowInUtc(){
        LocalDateTime rightNow = LocalDateTime.now();
        ZonedDateTime zonedNow = ZonedDateTime.of(rightNow, ZoneId.systemDefault());
        return zonedNow.withZoneSameInstant(ZoneId.of("Etc/UTC"));
    }
}
