package com.matrimony.bd.Models;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class Friend {

    private String name, bioDataNumber, user_id;
    private @ServerTimestamp
    Date timestamp;

    public Friend() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBioDataNumber() {
        return bioDataNumber;
    }

    public void setBioDataNumber(String bioDataNumber) {
        this.bioDataNumber = bioDataNumber;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
