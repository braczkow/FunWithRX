package com.example.raczkowbar.funwithrx.examples;

import android.support.annotation.NonNull;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class SchedulersWithMapOp2Example extends ExecutableExample {

    @Override
    public String getName() {
        return "Schedulers with map (2)";
    }

    @Override
    public void execute() {
        Observable.fromCallable(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Timber.d("IN fromCallable");
                return 1;
            }
        })

                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Exception {
                        Timber.d("IN map");
                        return String.valueOf(integer + 2);
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Timber.d("IN doOnNext");
                    }
                })
                .subscribe();
    }
}
