package com.example.raczkowbar.funwithrx;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.raczkowbar.funwithrx.debounce.LifecycleObserver;

import butterknife.ButterKnife;

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onResume() {
        super.onResume();
        LifecycleObserver.get().onActivityResumed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        LifecycleObserver.get().onActivityPaused();
    }
}
