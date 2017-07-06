package com.jaumard.owt.dagger.modules;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.jaumard.owt.R;
import com.jaumard.owt.dagger.scopes.ActivityScope;
import com.jaumard.owt.viewmodels.CatalogViewModel;
import com.jaumard.owt.viewmodels.DetailsViewModel;
import com.jaumard.owt.viewmodels.HistoryViewModel;
import com.jaumard.owt.viewmodels.LoginViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {
    private final AppCompatActivity activity;

    public ActivityModule(AppCompatActivity appCompatActivity) {
        this.activity = appCompatActivity;
    }

    @Provides
    CatalogViewModel provideCatalogViewModel() {
        return ViewModelProviders.of(activity).get(CatalogViewModel.class);
    }

    @Provides
    LoginViewModel provideLoginViewModel() {
        return ViewModelProviders.of(activity).get(LoginViewModel.class);
    }

    @Provides
    HistoryViewModel provideHistoryViewModel() {
        return ViewModelProviders.of(activity).get(HistoryViewModel.class);
    }

    @Provides
    DetailsViewModel provideDetailsViewModel() {
        return ViewModelProviders.of(activity).get(DetailsViewModel.class);
    }

    @Provides
    @ActivityScope
    GoogleApiClient provideGoogleApiClient(GoogleSignInOptions gso) {
        return new GoogleApiClient.Builder(activity)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @Provides
    @ActivityScope
    GoogleSignInOptions provideGoogleSignInOptions(Context context) {
        return new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
    }
}
