package com.intel.gjust.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Notifications {
    public String msg;
    public String title;
    public Long timestamp;

    public Notifications(){}
    public Notifications(String message,String title,Long timestamp){
        this.msg  = message;
        this.timestamp = timestamp;
        this.title = title;
    }
    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return msg;
    }

    public void setMessage(String message) {
        this.msg = message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
