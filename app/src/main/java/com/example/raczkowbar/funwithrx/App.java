package com.example.raczkowbar.funwithrx;

import android.app.Application;

import timber.log.Timber;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree(){
            @Override
            protected String createStackElementTag(StackTraceElement element) {
                return "BRBR (" + Thread.currentThread().getName() + ") " + super.createStackElementTag(element);
            }
        });
    }
}
