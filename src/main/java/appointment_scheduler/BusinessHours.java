package appointment_scheduler;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public abstract class BusinessHours {

    /**
     * This method generates a list of all available appointment times, from 8:00 AM until 10:00 PM
     * @return list of times
     */
    public static ObservableList<String> generate() {
        ObservableList<String> listOfTimes = FXCollections.observableArrayList();
        String startHour = "8";
        String[] minutes = {"00", "15", "30", "45"};
        for (int i = 8; i < 22; i++){
            for(int j = 0; j < 4; j++){
                StringBuilder time = new StringBuilder();
                if(i < 13){
                    time.append(i);
                } else {
                    time.append(i-12);
                }
                time.append(":");
                time.append(minutes[j]);
                time.append(" ");
                if(i < 12){
                    time.append("AM");
                } else {
                    time.append("PM");
                }
                listOfTimes.add(time.toString());
            }

        }
        listOfTimes.add("10:00 PM");
        return listOfTimes;
    }

//  This is an example of how to convert a string to Eastern time. Delete it before you turn this assignment in, yeah?
//    String timeString = "8:00 AM";
//    ZoneId eastern = ZoneId.of("America/New_York");
//    LocalTime time = LocalTime.parse(timeString, DateTimeFormatter.ofPattern("h:mm a"));
//    LocalDate date = LocalDate.now();
//    ZonedDateTime zdt = ZonedDateTime.of(date, time, eastern);
//    ZonedDateTime mountainTime = zdt.withZoneSameInstant(ZoneId.systemDefault());



}
