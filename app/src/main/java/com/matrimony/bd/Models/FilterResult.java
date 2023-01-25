package com.matrimony.bd.Models;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class FilterResult {

    private String name, permanentDivision, maritalCondition, educationMedium, prayerPerDay, parents, id;
    private @ServerTimestamp
    Date timestamp;

    public FilterResult() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPermanentDivision() {
        return permanentDivision;
    }

    public void setPermanentDivision(String permanentDivision) {
        this.permanentDivision = permanentDivision;
    }

    public String getMaritalCondition() {
        return maritalCondition;
    }

    public void setMaritalCondition(String maritalCondition) {
        this.maritalCondition = maritalCondition;
    }

    public String getEducationMedium() {
        return educationMedium;
    }

    public void setEducationMedium(String educationMedium) {
        this.educationMedium = educationMedium;
    }

    public String getPrayerPerDay() {
        return prayerPerDay;
    }

    public void setPrayerPerDay(String prayerPerDay) {
        this.prayerPerDay = prayerPerDay;
    }

    public String getParents() {
        return parents;
    }

    public void setParents(String parents) {
        this.parents = parents;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
