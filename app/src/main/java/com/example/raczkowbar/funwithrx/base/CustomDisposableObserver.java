package com.example.raczkowbar.funwithrx.base;

import android.support.annotation.NonNull;

import io.reactivex.observers.DisposableObserver;

public class CustomDisposableObserver<T> extends DisposableObserver<T> {
    @Override
    public void onNext(@NonNull T t) {

    }

    @Override
    public void onError(@NonNull Throwable e) {
        e.printStackTrace();
    }

    @Override
    public void onComplete() {

    }
}
