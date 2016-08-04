package com.androidcamp.jobbies.timeframe;

import com.androidcamp.jobbies.timeframe.Day;

/**
 * Created by Karolina Pawlikowska on 8/4/16.
 *
 * Object which holds all hour frames for all days
 * and information if event is recurrent
 */
public class TimeFrame {

    private boolean isEventReccurent;
    private DayTimeFrame[] dayTimeFrames;

    public boolean isEventReccurent(){
        return isEventReccurent;
    }

    public DayTimeFrame[] getDayTimeFrames() {
        return dayTimeFrames;
    }

    public String[] getDates(){
        String[] dates=new String[dayTimeFrames.length]; //part of the array might contain empty strings!!!
        int index=0;
        for(int i=0;i<dayTimeFrames.length;i++){
            String date=dayTimeFrames[i].getDate();
            if(checkExistingDate(dates, date)){
                continue;
            }
            else{
                dates[index]=date;
                index++;
            }
        }
        return dates;
    }

    private boolean checkExistingDate(String[] dates, String date){
        for(int i=0;i<dates.length;i++){
            String currdate=dates[i];
            if(currdate.equals(date)){
                return true;
            }
        }
        return false;
    }
}

