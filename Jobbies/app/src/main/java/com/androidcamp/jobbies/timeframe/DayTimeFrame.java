package com.androidcamp.jobbies.timeframe;

/**
 * Created by karolina on 8/4/16.
 */
public class DayTimeFrame {
    private Day day;
    private String date; //format : --/--/--
    private HourFrame[] hourFrames;

    public Day getDay(){
        return day;
    }

    public String getDate(){
        return date;
    }

    public HourFrame[] getHourFrames(){
        return hourFrames;
    }
}
