package com.cititmobilechallenge.citifit.modal;

import android.graphics.Bitmap;

/**
 * Created by ashwiask on 10/25/2015.
 */
public class RewardHolder {

    private String name;
    private String price;
    private String pointsNeeded;
    private String days;
    private Bitmap image;

    public RewardHolder(String name, String price, String pointsNeeded, String days, Bitmap image) {
        this.name = name;
        this.price = price;
        this.pointsNeeded = pointsNeeded;
        this.days = days;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getPointsNeeded() {
        return pointsNeeded;
    }

    public String getDays() {
        return days;
    }

    public Bitmap getImage() {
        return image;
    }
}
