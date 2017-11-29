package com.example.raczkowbar.funwithrx.schedulers;

import android.support.annotation.NonNull;

import com.example.raczkowbar.funwithrx.base.CustomDisposableObserver;
import com.example.raczkowbar.funwithrx.base.ExecutableExample;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class FlatMapExample extends ExecutableExample {
    @Override
    public String getName() {
        return "flatMap example";
    }

    private int counter = 0;

    @Override
    public void execute() {
        Observable.just("Separate me. One word each!")
                .flatMap(new Function<String, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(String s) throws Exception {
                        return Observable.fromArray(s.split(" "));
                    }
                })
                .subscribeWith(new CustomDisposableObserver<String>(){
                    @Override
                    public void onNext(@NonNull String s) {
                        Timber.d("#%d item: %s", counter++, s);
                    }
                });
    }
}
