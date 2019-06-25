package com.jayshah.newsapp;

import android.app.Application;

import com.facebook.stetho.Stetho;

public class MainApplication extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        appComponent = buildComponent();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    protected AppComponent buildComponent() {
        // modules instantiated here, whatever module need give it to them here.
        return DaggerAppComponent.builder()
                .netModule(new NetModule(Constants.baseUrl, this))
                .build();
    }



}
