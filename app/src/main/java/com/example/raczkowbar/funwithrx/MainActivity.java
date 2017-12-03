package com.example.raczkowbar.funwithrx;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.raczkowbar.funwithrx.coldhot.ColdHotActivity;
import com.example.raczkowbar.funwithrx.dispose.DisposeActivity;
import com.example.raczkowbar.funwithrx.schedulers.SchedulersExamplesActivity;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class MainActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.main_start_dispose)
    public void onStartDisposeClicked() {
        startActivity(new Intent(this, DisposeActivity.class));
    }

    @OnClick(R.id.main_start_schedulers)
    public void onStartSchedulersClicked() {
        startActivity(new Intent(this, SchedulersExamplesActivity.class));
    }

    @OnClick(R.id.main_start_coldhot)
    public void onColdhotClicked() {
        startActivity(new Intent(this, ColdHotActivity.class));
    }
}
