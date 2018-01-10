package com.example.raczkowbar.funwithrx.dispose;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.raczkowbar.funwithrx.BaseActivity;
import com.example.raczkowbar.funwithrx.R;
import com.example.raczkowbar.funwithrx.base.CustomDisposableObserver;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class DisposeActivity extends BaseActivity {

    @BindView(R.id.dispose_start_nodispose)
    public TextView m_startSingleText;

    @BindView(R.id.dispose_info)
    public TextView m_info;

    @BindView(R.id.dispose_main_view)
    public View m_mainView;

    private CompositeDisposable m_disposables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispose);
        ButterKnife.bind(this);
        m_disposables = new CompositeDisposable();
    }

    @Override
    protected void onDestroy() {
        Timber.d("onDestroy");
        super.onDestroy();
        m_disposables.dispose();
    }

    @OnClick(R.id.dispose_start_nodispose)
    public void onStartNodisposeClick() {
        CustomDisposableObserver<Integer> disposable = Observable.fromArray(1, 2)
                .observeOn(Schedulers.io())
                .subscribeWith(new CustomDisposableObserver<Integer>() {
                    @Override
                    public void onNext(@NonNull Integer integer) {
                        Timber.d("onNext: " + integer);

                        while (true) {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                Timber.d("Interrupted");
                                e.printStackTrace();
                            }

                            Timber.d("In loop");
                        }
                    }
                });

        m_disposables.add(disposable);
    }

    @OnClick(R.id.dispose_start_dispose)
    public void onStartDisposeClick() {
        CustomDisposableObserver<Integer> disposable = Observable.fromArray(1, 2)
                .observeOn(Schedulers.io())
                .subscribeWith(new CustomDisposableObserver<Integer>() {
                    @Override
                    public void onNext(@NonNull Integer integer) {
                        Timber.d("onNext: " + integer);
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            Timber.d("Interrupted");
                            e.printStackTrace();
                        }

                        Timber.d("After sleep");
                    }
                });

        m_disposables.add(disposable);
    }
}
