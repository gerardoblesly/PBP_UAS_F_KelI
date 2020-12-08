package com.gerardoleonel.projectuts_eventorganizer.db.entity;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.gerardoleonel.projectuts_eventorganizer.BR;

import java.io.Serializable;

@Entity
public class EventType extends BaseObservable implements Serializable {

    @NonNull
    @PrimaryKey
    private String eventId;
    private String eventPhoto;
    private String eventName;

    public EventType(String eventId, String eventPhoto, String eventName) {
        this.eventId = eventId;
        this.eventPhoto = eventPhoto;
        this.eventName = eventName;
    }

    @Bindable
    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
        notifyPropertyChanged(BR.eventId);
    }

    @Bindable
    public String getEventPhoto() {
        return eventPhoto;
    }

    public void setEventPhoto(String eventPhoto) {
        this.eventPhoto = eventPhoto;
        notifyPropertyChanged(BR.eventPhoto);
    }

    @Bindable
    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
        notifyPropertyChanged(BR.eventName);
    }
}
