package appointment_scheduler;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public abstract class TimeConverter {
    public static final ZoneId utcZone = ZoneId.of("Etc/UTC");
    public static final ZoneId easternTime = ZoneId.of("America/New_York");

    public static ZonedDateTime convertToUtc(LocalDateTime localTime){
        ZonedDateTime localTimeWithZone = ZonedDateTime.of(localTime, ZoneId.systemDefault());
        ZonedDateTime convertedToUtc = localTimeWithZone.withZoneSameInstant(utcZone);
        return convertedToUtc;
    }

    public static ZonedDateTime extractTimestampToUtc(Timestamp timestamp){
        LocalDateTime ldt = timestamp.toLocalDateTime();
        ZonedDateTime zdt = ZonedDateTime.of(ldt, ZoneId.systemDefault());
        return zdt.withZoneSameInstant(utcZone);
    }

    public static DateTimeFormatter fullFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy h:mm a");

    public static DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MMMM yyyy");

    public static ZonedDateTime convertToLocal(ZonedDateTime time){
        return time.withZoneSameInstant(ZoneId.systemDefault());
    }

    public static ZonedDateTime getNowInUtc(){
        LocalDateTime rightNow = LocalDateTime.now();
        ZonedDateTime zonedNow = ZonedDateTime.of(rightNow, ZoneId.systemDefault());
        return zonedNow.withZoneSameInstant(utcZone);
    }
}
