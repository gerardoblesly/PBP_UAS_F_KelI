package com.gerardoleonel.projectuts_eventorganizer.db.entity;

import java.io.Serializable;

public class Event implements Serializable {
    private int id;
    private String eventType;
    private String eventPlace;
    private String eventPackage;
    private String eventDescription;

    public Event(int id, String eventType, String eventPlace, String eventPackage, String eventDescription) {
        this.id = id;
        this.eventType = eventType;
        this.eventPlace = eventPlace;
        this.eventPackage = eventPackage;
        this.eventDescription = eventDescription;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventPlace() {
        return eventPlace;
    }

    public void setEventPlace(String eventPlace) {
        this.eventPlace = eventPlace;
    }

    public String getEventPackage() {
        return eventPackage;
    }

    public void setEventPackage(String eventPackage) {
        this.eventPackage = eventPackage;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }
}
