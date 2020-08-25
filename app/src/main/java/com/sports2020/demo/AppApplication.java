package com.sports2020.demo;

import android.app.Application;

import com.sports2020.demo.db.User;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class AppApplication extends Application {

    private static AppApplication mApplication;
    private User currentUser;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        AppConfig.setUrl();
    }

    public static AppApplication getInstance() {
        return mApplication;
    }

    public User getCurrentUser() {
        if (currentUser == null)
            currentUser = new User();
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
