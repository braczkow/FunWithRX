package com.example.raczkowbar.funwithrx;

import android.app.Application;
import android.support.annotation.NonNull;

import com.example.raczkowbar.funwithrx.base.CustomDisposableObserver;
import com.example.raczkowbar.funwithrx.debounce.LifecycleObserver;

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

        LifecycleObserver.get().getAppStateRx()
                .subscribeWith(new CustomDisposableObserver<LifecycleObserver.AppState>(){
                    @Override
                    public void onNext(@NonNull LifecycleObserver.AppState appState) {
                        Timber.d("Activity state: " + appState);
                    }
                });
    }
}
