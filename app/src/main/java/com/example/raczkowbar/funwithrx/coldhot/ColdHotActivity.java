package com.example.raczkowbar.funwithrx.coldhot;

import android.support.annotation.NonNull;
import android.os.Bundle;

import com.example.raczkowbar.funwithrx.BaseActivity;
import com.example.raczkowbar.funwithrx.R;
import com.example.raczkowbar.funwithrx.base.CustomDisposableObserver;

import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import timber.log.Timber;

public class ColdHotActivity extends BaseActivity {

    private Observable<Boolean> m_hotTimeout;
    private Observable<Boolean> m_coldTimeout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cold_hot);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startHotTimeout();
        createColdTimeout();
    }

    private void createColdTimeout() {
        m_coldTimeout = Observable
                .timer(10, TimeUnit.SECONDS)
                .flatMap(new Function<Long, ObservableSource<Boolean>>() {
                    @Override
                    public ObservableSource<Boolean> apply(@NonNull Long aLong) throws Exception {
                        return Observable.just(true);
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread());
    }

    private void startHotTimeout() {
        Timber.d("Starting hot timeout.");
        m_hotTimeout = Observable
                .timer(10, TimeUnit.SECONDS)
                .flatMap(new Function<Long, ObservableSource<Boolean>>() {
                    @Override
                    public ObservableSource<Boolean> apply(@NonNull Long aLong) throws Exception {
                        return Observable.just(true);
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .publish()
                .autoConnect();

        m_hotTimeout
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new CustomDisposableObserver<Boolean>() {
                    @Override
                    public void onNext(@NonNull Boolean dummy) {
                        Timber.d("10s hot timeout expired.");
                    }
                });

    }

    @OnClick(R.id.coldhot_subscribe)
    public void onSubscribeClicked() {
        Timber.d("subscribe hot.");
        if (m_hotTimeout != null) {
            m_hotTimeout
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new CustomDisposableObserver<Boolean>(){
                @Override
                public void onNext(@NonNull Boolean aBoolean) {
                    Timber.d("Click subscribe expired.");
                }
            });
        }
    }

    @OnClick(R.id.coldhot_subscribe_cold)
    public void onSubscribeColdClicked(){
        Timber.d("subscribe cold.");
        m_coldTimeout
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new CustomDisposableObserver<Boolean>(){
            @Override
            public void onNext(@NonNull Boolean aBoolean) {
                Timber.d("Cold timeout finished.");
            }
        });
    }
}
