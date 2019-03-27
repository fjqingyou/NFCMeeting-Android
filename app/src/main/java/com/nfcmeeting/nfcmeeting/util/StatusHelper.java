package com.nfcmeeting.nfcmeeting.util;

import com.nfcmeeting.nfcmeeting.dao.Meeting;
import com.nfcmeeting.nfcmeeting.mvp.model.Repository;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public enum StatusHelper {
    INSTANCE;
    Map<String,Integer> status = new HashMap<String,Integer>(){
        {
            put("Going",0xFF0000);
            put("Unstart",0x00FF00);
            put("Finished",0x363636);

        }
    };

    public Map<String,Object> getStatus(Date date, Repository meeting){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        if (calendar.before(meeting.getBeginTime())){
            return new HashMap<String,Object>(){
                {
                    put("status","Unstart");
                    put("color",0x00FF00);
                }
            };
        } else if (calendar.after(meeting.getEndTime())){
            return new HashMap<String,Object>(){
                {
                    put("status","Finished");
                    put("color",0x363636);
                }
            };
        }else {
            return new HashMap<String,Object>(){
                {
                    put("status","Going");
                    put("color",0xFF0000);
                }
            };
        }
    }
}
