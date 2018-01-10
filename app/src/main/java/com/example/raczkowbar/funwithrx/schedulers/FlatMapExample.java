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

    Observable<String> spaceSplitterRx(String s) {
        return Observable.fromArray(s.split(" "));
    }

    Observable<String> charSplitterRx(String s) {
        return Observable.fromArray(s.split(""));
    }

    @Override
    public void execute() {
        String task = "Split every char in this string";

        spaceSplitterRx(task)
                .flatMap(new Function<String, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(String s) throws Exception {
                        return charSplitterRx(s);
                    }
                })
                .subscribeWith(new CustomDisposableObserver<String>(){
                    @Override
                    public void onNext(@NonNull String s) {
                        Timber.d("#: " + s);
                    }
                });
    }
}
