package com.gerardoleonel.projectuts_eventorganizer.fragment.home;

import android.graphics.drawable.Drawable;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.gerardoleonel.projectuts_eventorganizer.BR;

public class CarouselItem extends BaseObservable {

    public Drawable carouselBackground;
    public String carouselDescription;

    public CarouselItem(Drawable carouselBackground, String carouselDescription) {
        this.carouselBackground = carouselBackground;
        this.carouselDescription = carouselDescription;
    }

    @Bindable
    public Drawable getCarouselBackground() {
        return carouselBackground;
    }

    public CarouselItem setCarouselBackground(Drawable carouselBackground) {
        this.carouselBackground = carouselBackground;
        notifyPropertyChanged(BR.carouselBackground);
        return this;
    }

    @Bindable
    public String getCarouselDescription() {
        return carouselDescription;
    }

    public CarouselItem setCarouselDescription(String carouselDescription) {
        this.carouselDescription = carouselDescription;
        notifyPropertyChanged(BR.carouselDescription);
        return this;
    }
}
