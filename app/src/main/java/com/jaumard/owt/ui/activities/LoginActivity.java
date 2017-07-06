package com.jaumard.owt.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.jaumard.owt.R;
import com.jaumard.owt.databinding.ActivityLoginBinding;
import com.jaumard.owt.viewmodels.LoginViewModel;

import javax.inject.Inject;

public class LoginActivity extends BaseActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {
    public static final String EVENT_LOGGED = "EVENT_LOGGED";
    private static final int GOOGLE_SIGN_IN = 1;

    @Inject
    LoginViewModel loginViewModel;
    @Inject
    GoogleApiClient googleApiClient;

    private ActivityLoginBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        activityComponent.inject(this);
        binding.setViewModel(loginViewModel);

        setTitle(R.string.screen_title_login);
        binding.password.setOnEditorActionListener((v, actionId, event) -> {
            loginViewModel.login();
            return true;
        });

        googleApiClient.registerConnectionFailedListener(this);
        binding.signInButton.setOnClickListener(this);
    }

    private void signIn() {
        InputMethodManager imMethod = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imMethod.hideSoftInputFromWindow(binding.email.getWindowToken(), 0);
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN);
    }

    @Override
    protected void onResume() {
        super.onResume();

        compositeDisposable.add(loginViewModel.getNavigationBus()
                .doOnNext(event -> {
                    if (EVENT_LOGGED.equals(event.getType())) {
                        goToCatalog();
                    }
                })
                .subscribe());
    }

    @Override
    protected void onStart() {
        super.onStart();
        loginViewModel.bind();
    }

    @Override
    protected void onStop() {
        super.onStop();
        loginViewModel.unbind();
        loginViewModel.isRequestPending.set(false);
    }

    private void goToCatalog() {
        startActivity(CatalogActivity.getIntent(this));
        finish();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, R.string.auth_failed, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GOOGLE_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                loginViewModel.loginWithGoogle(account);
            } else {
                Toast.makeText(this, R.string.auth_failed, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        signIn();
    }
}
