package com.gerardoleonel.projectuts_eventorganizer.db.entity;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.gerardoleonel.projectuts_eventorganizer.BR;

import java.io.Serializable;

@Entity
public class EventPackage extends BaseObservable implements Serializable {

    @NonNull
    @PrimaryKey
    private String packageId;
    private String packageName;
    private String packagePrice;
    private String packageDescription;

    public EventPackage(@NonNull String packageId, String packageName, String packagePrice, String packageDescription) {
        this.packageId = packageId;
        this.packageName = packageName;
        this.packagePrice = packagePrice;
        this.packageDescription = packageDescription;
    }

    @Bindable
    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
        notifyPropertyChanged(BR.packageName);
    }

    @Bindable
    public String getPackagePrice() {
        return packagePrice;
    }

    public void setPackagePrice(String packagePrice) {
        this.packagePrice = packagePrice;
        notifyPropertyChanged(BR.packagePrice);
    }

    @Bindable
    public String getPackageDescription() {
        return packageDescription;
    }

    public void setPackageDescription(String packageDescription) {
        this.packageDescription = packageDescription;
        notifyPropertyChanged(BR.packageDescription);
    }

    @Bindable
    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
        notifyPropertyChanged(BR.packageId);
    }
}
