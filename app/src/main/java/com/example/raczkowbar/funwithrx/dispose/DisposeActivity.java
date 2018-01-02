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
        m_info.setText("Wait a sec..");
        Observable.timer(5, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new CustomDisposableObserver<Long>(){
                    @Override
                    public void onNext(@NonNull Long aLong) {
                        showDialog();
                    }
                });
    }

    @OnClick(R.id.dispose_start_dispose)
    public void onStartDisposeClick() {
        m_info.setText("Wait a sec..");
        CustomDisposableObserver<Long> disposable = Observable.timer(5, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new CustomDisposableObserver<Long>() {
                    @Override
                    public void onNext(@NonNull Long aLong) {
                        showDialog();
                    }
                });

        m_disposables.add(disposable);
    }

    private void showDialog() {
        Timber.d("showDialog");

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Yes, I'm a dialog.");
        builder.setPositiveButton("Ok, go away.", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();

    }
}
