package com.jaumard.owt.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.jaumard.owt.CatalogApplication;
import com.jaumard.owt.viewmodels.BaseViewModel;

import javax.inject.Inject;

import timber.log.Timber;


public abstract class LoggedActivity<T extends BaseViewModel> extends BaseActivity {
    @Inject
    FirebaseAuth firebaseAuth;

    private FirebaseAuth.AuthStateListener authListener = firebase -> {
        FirebaseUser user = firebase.getCurrentUser();
        if (user != null) {
            // User is signed in
            Timber.d("onAuthStateChanged:signed_in:");
        } else {
            // User is signed out
            Timber.d("onAuthStateChanged:signed_out");
            logout();
        }
    };

    protected void logout() {
        Intent intent = new Intent(getBaseContext(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @CallSuper
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent = CatalogApplication.getAppComponent().plusActivityComponent(
                getActivityModule()
        );
        activityComponent.inject(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authListener);
        getViewModel().bind();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            firebaseAuth.removeAuthStateListener(authListener);
        }
        getViewModel().unbind();
    }

    protected abstract T getViewModel();

}
