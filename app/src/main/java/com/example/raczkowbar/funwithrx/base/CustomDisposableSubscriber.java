package com.example.raczkowbar.funwithrx.base;

import io.reactivex.subscribers.DisposableSubscriber;

public class CustomDisposableSubscriber<T> extends DisposableSubscriber<T> {
    @Override
    public void onNext(T t) {

    }

    @Override
    public void onError(Throwable t) {
        t.printStackTrace();
    }

    @Override
    public void onComplete() {

    }
}
