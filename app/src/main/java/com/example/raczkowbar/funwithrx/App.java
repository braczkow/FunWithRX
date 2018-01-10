package com.example.raczkowbar.funwithrx;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.support.annotation.NonNull;

import com.example.raczkowbar.funwithrx.base.CustomDisposableObserver;
import com.example.raczkowbar.funwithrx.db.AppDb;
import com.example.raczkowbar.funwithrx.debounce.LifecycleObserver;

import timber.log.Timber;

public class App extends Application {
    public static AppDb DB;

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree(){
            @Override
            protected String createStackElementTag(StackTraceElement element) {
                return "BRBR (" + Thread.currentThread().getName() + ") " + super.createStackElementTag(element);
            }
        });

        DB = Room
                .databaseBuilder(getApplicationContext(), AppDb.class, "history.db")
                .build();


        LifecycleObserver.get().getAppStateRx()
                .subscribeWith(new CustomDisposableObserver<LifecycleObserver.AppState>(){
                    @Override
                    public void onNext(@NonNull LifecycleObserver.AppState appState) {
                        Timber.d("Application state: " + appState);
                    }
                });
    }
}
