package com.codepath.instagram;

import android.app.Application;

import com.parse.Parse;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("72xPNoBP0TWbu15vownybUw58F3TZ0YCPKJ0vLp7")
                .clientKey("ZrpApaiZTgQ4ikS0bZ4ObIAm4cbpmuGdXx6abttA")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
