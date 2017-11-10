package com.example.raczkowbar.funwithrx.examples;

import io.reactivex.Completable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import timber.log.Timber;

public class SchedulersPublisherExample extends ExecutableExample {
    final PublishSubject<String> callerDependentSubject = PublishSubject.create();
    public SchedulersPublisherExample() {
        callerDependentSubject
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
        return "Schedulers with publisher";
    }

    @Override
    public void execute() {
        callerDependentSubject.onNext("1st");

        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                callerDependentSubject.onNext("2nd");
            }
        }).subscribeOn(Schedulers.newThread()).subscribe();
    }
}
