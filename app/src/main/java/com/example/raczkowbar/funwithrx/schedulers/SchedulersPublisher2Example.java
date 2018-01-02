package com.example.raczkowbar.funwithrx.schedulers;

import com.example.raczkowbar.funwithrx.base.ExecutableExample;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import timber.log.Timber;

public class SchedulersPublisher2Example extends ExecutableExample {
    final PublishSubject<String> callerIndependentSubject = PublishSubject.create();
    public SchedulersPublisher2Example() {
        callerIndependentSubject
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Timber.d("IN doOnNext: " + s);
                    }
                })
                .subscribe();
    }

    @Override
    public String getName() {
        return "Schedulers with publisher(2)";
    }

    @Override
    public void execute() {
        callerIndependentSubject.onNext("1st");

        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                callerIndependentSubject.onNext("2nd");
            }
        }).subscribeOn(Schedulers.io()).subscribe();
    }
}
