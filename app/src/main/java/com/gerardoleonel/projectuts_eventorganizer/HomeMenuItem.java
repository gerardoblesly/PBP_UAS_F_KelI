package com.gerardoleonel.projectuts_eventorganizer;

import android.graphics.drawable.Drawable;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class HomeMenuItem extends BaseObservable {

    private Drawable menuIcon;
    private String menuTitle;

    public HomeMenuItem(Drawable menuIcon, String menuTitle) {
        this.menuIcon = menuIcon;
        this.menuTitle = menuTitle;
    }

    @Bindable
    public Drawable getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(Drawable menuIcon) {
        this.menuIcon = menuIcon;
        notifyPropertyChanged(BR.menuIcon);
    }

    @Bindable
    public String getMenuTitle() {
        return menuTitle;
    }

    public void setMenuTitle(String menuTitle) {
        this.menuTitle = menuTitle;
        notifyPropertyChanged(BR.menuTitle);
    }
}
