package com.gerardoleonel.projectuts_eventorganizer.db.entity;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.gerardoleonel.projectuts_eventorganizer.BR;

import java.io.Serializable;

@Entity
public class EventPlace extends BaseObservable implements Serializable {

    @NonNull
    @PrimaryKey
    private String id;
    private String placeName;
    private String placePicture;
    private String placeDescription;

    public EventPlace(@NonNull String id, String placeName, String placePicture, String placeDescription) {
        this.id = id;
        this.placeName = placeName;
        this.placePicture = placePicture;
        this.placeDescription = placeDescription;
    }

    @Bindable
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
    }

    @Bindable
    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
        notifyPropertyChanged(BR.placeName);
    }

    @Bindable
    public String getPlacePicture() {
        return placePicture;
    }

    public void setPlacePicture(String placePicture) {
        this.placePicture = placePicture;
        notifyPropertyChanged(BR.placePicture);
    }

    @Bindable
    public String getPlaceDescription() {
        return placeDescription;
    }

    public void setPlaceDescription(String placeDescription) {
        this.placeDescription = placeDescription;
        notifyPropertyChanged(BR.placeDescription);
    }
}
