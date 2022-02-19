package appointment_scheduler;

import java.time.*;
import java.util.ArrayList;

public abstract class BusinessHours {

    private static boolean isDuringBusinessHours(LocalDateTime ldt){
        ZonedDateTime zdt = ZonedDateTime.of(ldt, ZoneId.systemDefault());
        ZonedDateTime bostonTime = zdt.withZoneSameInstant(ZoneId.of("America/New_York"));
        if(bostonTime.getHour()>= 8 && (bostonTime.getHour() <= 21 || (bostonTime.getHour() == 22 && bostonTime.getMinute() == 00))){
            return true;
        }
        return false;
    }

    public static ArrayList<LocalTime> getValidTimes(){
        ArrayList<LocalTime> validTimes = new ArrayList<>();
        LocalDateTime timeToCheck = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
        for(int i = 0; i < 96; i++){
            if(isDuringBusinessHours(timeToCheck)){
                LocalTime timeToAdd = LocalTime.of(timeToCheck.getHour(), timeToCheck.getMinute());
                validTimes.add(timeToAdd);
            }
            timeToCheck = timeToCheck.plusMinutes(15);
        }
        return validTimes;
    }


}
