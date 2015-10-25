package com.cititmobilechallenge.citifit.modal;

import android.graphics.Bitmap;

/**
 * Created by ashwiask on 10/25/2015.
 */
public class RewardHolder {

    private String name;
    private String price;
    private String pointsNeeded;
    private String goalsCount;
    private Bitmap image;

    public RewardHolder(String name, String price, String pointsNeeded, String goalsCount, Bitmap image) {
        this.name = name;
        this.price = price;
        this.pointsNeeded = pointsNeeded;
        this.goalsCount = goalsCount;
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

    public String getGoalsCount() {
        return goalsCount;
    }

    public Bitmap getImage() {
        return image;
    }
}
