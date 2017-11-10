package com.example.raczkowbar.funwithrx.examples;

import com.example.raczkowbar.funwithrx.examples.ExecutableExample;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class SchedulersWithMapOpExample extends ExecutableExample {
    @Override
    public String getName() {
        return "Schedulers with map";
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
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Exception {
                        Timber.d("IN map");
                        return String.valueOf(integer + 3);
                    }
                })

                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Timber.d("IN doOnNext");
                    }
                })
                .subscribe();
    }
}
