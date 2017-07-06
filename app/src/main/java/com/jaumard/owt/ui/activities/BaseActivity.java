package com.jaumard.owt.ui.activities;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.jaumard.owt.CatalogApplication;
import com.jaumard.owt.dagger.components.ActivityComponent;
import com.jaumard.owt.dagger.modules.ActivityModule;

import io.reactivex.disposables.CompositeDisposable;


public abstract class BaseActivity extends AppCompatActivity {
    public static final String EVENT_ERROR = "EVENT_ERROR";
    protected ActivityComponent activityComponent;
    protected CompositeDisposable compositeDisposable;

    @CallSuper
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent = CatalogApplication.getAppComponent().plusActivityComponent(
                getActivityModule()
        );
    }

    @Override
    protected void onPause() {
        super.onPause();
        compositeDisposable.dispose();
    }

    @Override
    protected void onResume() {
        super.onResume();
        compositeDisposable = new CompositeDisposable();
    }

    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }
}
