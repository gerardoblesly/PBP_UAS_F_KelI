package com.gerardoleonel.projectuts_eventorganizer.db.entity;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.gerardoleonel.projectuts_eventorganizer.BR;

@Entity
public class User extends BaseObservable {

    @PrimaryKey(autoGenerate = true)
    private Integer userId;
    private String username;
    private String password;

    private String email;
    private String noHP;
    private String alamat;

    private String userAvatar;

    @Bindable
    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
        notifyPropertyChanged(BR.alamat);
    }

    @Bindable
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
        notifyPropertyChanged(BR.userId);
    }

    @Bindable
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        notifyPropertyChanged(BR.username);
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
    }

    @Bindable
    public String getNoHP() {
        return noHP;
    }

    public void setNoHP(String noHP) {
        this.noHP = noHP;
        notifyPropertyChanged(BR.noHP);
    }

    @Bindable
    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
        notifyPropertyChanged(BR.userAvatar);
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);
    }
}
