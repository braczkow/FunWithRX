package com.example.raczkowbar.funwithrx;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.raczkowbar.funwithrx.schedulers.SchedulersExamplesActivity;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class MainActivity extends BaseActivity {
    private static final String RX_TAG = "rx2";

    PublishSubject<Integer> m_publisher = PublishSubject.create();

    Completable fixedInTheBackground() {

        Schedulers.io();
        Schedulers.computation();
        Schedulers.newThread();
        Schedulers.trampoline();

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Schedulers.from(executorService);

        AndroidSchedulers.mainThread();


        return Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                //cannot be changed
            }
        }).subscribeOn(Schedulers.io());
    }

    void doMap() {
        Observable<Integer> just = Observable.just(1);

        Observable<String> map = just.map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                return String.valueOf(integer);
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startActivity(new Intent(getApplicationContext(), SchedulersExamplesActivity.class));
    }

    private void test_publishSubject() {
//        Log.d(RX_TAG, "---------- publishSubject,  fired from background");
//
//        m_publisher
//                .subscribe(new Consumer<Integer>() {
//                    @Override
//                    public void accept(Integer integer) throws Exception {
//                        Log.d(RX_TAG, "test_publishSubject: accept thread: " + Thread.currentThread().getName());
//                    }
//                });
//
//        publishFromIOThread();
//
//        Log.d(RX_TAG, "---------- publishSubject, observeOn main  fired from background");
//
//        m_publisher
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<Integer>() {
//                    @Override
//                    public void accept(Integer integer) throws Exception {
//                        Log.d(RX_TAG, "test_publishSubject: accept thread: " + Thread.currentThread().getName());
//                    }
//                });
//
//        publishFromIOThread();

//        Log.d(RX_TAG, "---------- publishSubject, observeOn computation  fired from background");
//        m_publisher
//                .observeOn(Schedulers.computation())
//                .subscribe(new Consumer<Integer>() {
//                    @Override
//                    public void accept(Integer integer) throws Exception {
//                        Log.d(RX_TAG, "test_publishSubject: accept thread: " + Thread.currentThread().getName());
//                    }
//                });
//
//        publishFromIOThread();

//        Log.d(RX_TAG, "---------- publishSubject");
//        m_publisher
//                .subscribe(new Consumer<Integer>() {
//                    @Override
//                    public void accept(Integer integer) throws Exception {
//                        Log.d(RX_TAG, "test_publishSubject: accept thread: " + Thread.currentThread().getName());
//                    }
//                });
//
//        publishFromIOThread();

        Log.d(RX_TAG, "---------- publishSubject");
        m_publisher
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(RX_TAG, "test_publishSubject: accept thread: " + Thread.currentThread().getName());
                    }
                });

        publishFromIOThread();


    }

    private void publishFromIOThread() {
        Observable.fromCallable(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Log.d(RX_TAG, "Calling m_publisher.onNext() from: " + Thread.currentThread().getName());
                m_publisher.onNext(0);
                return 0;
            }
        })
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

//    private void test_makeCallable_subscribeOn_io() {
//        Log.d(RX_TAG, "---------- makeCallable_subscribeOn_io, no threads");
//        makeCallable_subscribeOn_io().subscribe(new Consumer<Integer>() {
//            @Override
//            public void accept(Integer integer) throws Exception {
//                Log.d(RX_TAG, "makeCallable_subscribeOn_io, no threads: accept " + Thread.currentThread().getName());
//            }
//        });
//
//
//        Log.d(RX_TAG, "---------- makeCallable_subscribeOn_io, observeOn main");
//        makeCallable_subscribeOn_io()
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<Integer>() {
//                    @Override
//                    public void accept(Integer integer) throws Exception {
//                        Log.d(RX_TAG, "makeCallable_subscribeOn_io, observeOn main: accept " + Thread.currentThread().getName());
//                    }
//                });
//
//        Log.d(RX_TAG, "---------- makeCallable_subscribeOn_io, observeOn main, subscribeOn main!");
//        makeCallable_subscribeOn_io()
//                .subscribeOn(AndroidSchedulers.mainThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<Integer>() {
//                    @Override
//                    public void accept(Integer integer) throws Exception {
//                        Log.d(RX_TAG, "makeCallable_subscribeOn_io, observeOn main, subscribeOn main: accept " + Thread.currentThread().getName());
//                    }
//                });
//    }
//
//    private void test_makeCallable() {
//        Log.d(RX_TAG, "---------- makeCallable, no threads");
//        makeCallable().subscribe(new Consumer<Integer>() {
//            @Override
//            public void accept(Integer integer) throws Exception {
//                Log.d(RX_TAG, "makeCallable, no threads: accept " + Thread.currentThread().getName());
//            }
//        });
//
//        Log.d(RX_TAG, "---------- makeCallable, subscribeOn io");
//        makeCallable()
//                .subscribeOn(Schedulers.io())
//                .subscribe(new Consumer<Integer>() {
//                    @Override
//                    public void accept(Integer integer) throws Exception {
//                        Log.d(RX_TAG, "makeCallable, subscribeOn io: accept " + Thread.currentThread().getName());
//                    }
//                });
//
//
//        Log.d(RX_TAG, "---------- makeCallable, subscribeOn io, observeOn main");
//        makeCallable()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<Integer>() {
//                    @Override
//                    public void accept(Integer integer) throws Exception {
//                        Log.d(RX_TAG, "makeCallable, subscribeOn io, observeOn main: accept " + Thread.currentThread().getName());
//                    }
//                });
//    }
//
//    private void sleepSome() {
//        try {
//            Thread.sleep(500);
//        } catch (InterruptedException e) {
//
//        }
//    }
}
