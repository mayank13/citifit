package com.cititmobilechallenge.citifit;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;

/**
 * Created by Mayank on 23-10-2015.
 */
public class CitiFitApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, "3kVzn5ywKHuEzmrmhrbpAVZK7Qj39qHbaermdroW", "ngrluVLztOHAXHeqhcFJwJeiiRltpUuMxtB4KJat");
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }
}
