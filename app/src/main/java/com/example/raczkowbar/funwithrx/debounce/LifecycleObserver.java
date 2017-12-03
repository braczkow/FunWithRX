package com.example.raczkowbar.funwithrx.debounce;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.raczkowbar.funwithrx.base.CustomDisposableObserver;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.subjects.PublishSubject;

public class LifecycleObserver {
    private static LifecycleObserver s_instance = new LifecycleObserver();
    private ActivityAction m_lastActivityState;
    private Observable<AppState> m_appStateRx;
    PublishSubject<ActivityAction> m_internalPublisher = PublishSubject.create();

    public static LifecycleObserver get() {
        return s_instance;
    }

    public enum ActivityAction {
        RESMED,
        PAUSED
    }

    public enum AppState {
        FOREGROUND,
        BACKGROUND
    }

    private LifecycleObserver() {
        observeActivityState();
    }

    public Observable<AppState> getAppStateRx() {
        return m_appStateRx;
    }

    public void onActivityPaused() {
        m_internalPublisher.onNext(ActivityAction.PAUSED);
    }

    public void onActivityResumed() {
        m_internalPublisher.onNext(ActivityAction.RESMED);
    }

    private void observeActivityState() {
        m_appStateRx = m_internalPublisher
                .debounce(200, TimeUnit.MILLISECONDS)
                .filter(new Predicate<ActivityAction>() {
                    @Override
                    public boolean test(@NonNull ActivityAction action) throws Exception {
                        return m_lastActivityState != action;
                    }
                })
                .doOnNext(new Consumer<ActivityAction>() {
                    @Override
                    public void accept(ActivityAction activityAction) throws Exception {
                        m_lastActivityState = activityAction;
                    }
                })
                .map(new Function<ActivityAction, AppState>() {
                    @Override
                    public AppState apply(ActivityAction activityAction) throws Exception {
                        switch (activityAction) {
                            case RESMED:
                                return AppState.FOREGROUND;
                            case PAUSED:
                                return AppState.BACKGROUND;
                        }

                        throw new IllegalArgumentException("Not a valid ActivityAction");
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }


}
