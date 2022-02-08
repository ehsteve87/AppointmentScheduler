package appointment_scheduler;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TimeConverter {
    public static final ZoneId utcZone = ZoneId.of("Etc/UTC");

    public static ZonedDateTime convertToUtc(LocalDateTime localTime){
        ZonedDateTime localTimeWithZone = ZonedDateTime.of(localTime, ZoneId.systemDefault());
        ZonedDateTime convertedToUtc = localTimeWithZone.withZoneSameInstant(utcZone);
        return convertedToUtc;
    }

    public static ZonedDateTime convertToLocal(ZonedDateTime time){
        return time.withZoneSameInstant(ZoneId.systemDefault());
    }

    public static ZonedDateTime getNowInUtc(){
        LocalDateTime rightNow = LocalDateTime.now();
        ZonedDateTime zonedNow = ZonedDateTime.of(rightNow, ZoneId.systemDefault());
        return zonedNow.withZoneSameInstant(utcZone);
    }
}
