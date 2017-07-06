package com.jaumard.owt.viewmodels;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.text.TextUtils;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.jaumard.owt.R;
import com.jaumard.owt.network.services.AuthService;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

import static com.jaumard.owt.ui.activities.LoginActivity.EVENT_LOGGED;

public class LoginViewModel extends BaseViewModel {
    @Inject
    AuthService authService;

    public final ObservableField<String> email = new ObservableField<>();
    public final ObservableField<String> password = new ObservableField<>();
    public final ObservableInt emailError = new ObservableInt();
    public final ObservableInt passwordError = new ObservableInt();
    public final ObservableInt genericError = new ObservableInt();
    public final ObservableBoolean isRequestPending = new ObservableBoolean();

    public LoginViewModel() {
        viewModelComponent.inject(this);
    }

    public void login() {
        resetErrors();
        if (checkInputs()) {
            isRequestPending.set(true);
            compositeDisposable.add(authService.login(email.get(), password.get())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe((authResult, throwable) -> {
                        isRequestPending.set(false);
                        if (throwable == null) {
                            navigationBus.onNext(new NavigationEvent(EVENT_LOGGED));
                        } else {
                            manageError(throwable);
                        }
                    }));
        }
    }

    public void register() {
        resetErrors();
        if (checkInputs()) {
            isRequestPending.set(true);
            compositeDisposable.add(authService.register(email.get(), password.get())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe((authResult, throwable) -> {
                        isRequestPending.set(false);
                        if (throwable == null) {
                            navigationBus.onNext(new NavigationEvent(EVENT_LOGGED));
                        } else {
                            manageError(throwable);
                        }
                    }));
        }
    }

    private boolean checkInputs() {
        boolean errorFound = false;
        if (TextUtils.isEmpty(email.get())) {
            emailError.set(R.string.error_field_required);
            errorFound = true;
        }
        if (TextUtils.isEmpty(password.get())) {
            passwordError.set(R.string.error_field_required);
            errorFound = true;
        }
        return !errorFound;
    }

    private void manageError(Throwable throwable) {
        if (throwable instanceof FirebaseNetworkException) {
            genericError.set(R.string.error_network);
        } else if (throwable instanceof FirebaseAuthWeakPasswordException) {
            passwordError.set(R.string.error_invalid_password);
        } else if (throwable instanceof FirebaseAuthUserCollisionException) {
            emailError.set(R.string.error_existed_email);
        } else {
            genericError.set(R.string.auth_failed);
        }
        Timber.e(throwable);
    }

    private void resetErrors() {
        passwordError.set(0);
        emailError.set(0);
        genericError.set(0);
    }

    @Override
    public void inject() {
        viewModelComponent.inject(this);
    }

    public void loginWithGoogle(GoogleSignInAccount account) {
        isRequestPending.set(true);
        compositeDisposable.add(authService.loginWithGoogle(account).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((authResult, throwable) -> {
                    isRequestPending.set(false);
                    if (throwable == null) {
                        navigationBus.onNext(new NavigationEvent(EVENT_LOGGED));
                    } else {
                        manageError(throwable);
                    }
                })
        );
    }
}
