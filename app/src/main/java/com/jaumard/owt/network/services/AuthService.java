package com.jaumard.owt.network.services;

import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

public class AuthService {
    private final FirebaseAuth firebaseAuth;

    @Inject
    public AuthService(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }

    public Single<AuthResult> login(String email, String password) {
        return Single.create(emitter -> firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                    manageAuthResult(emitter, task);
                })
        );
    }

    private void manageAuthResult(SingleEmitter<AuthResult> emitter, Task<AuthResult> task) {
        if (task.isSuccessful()) {
            emitter.onSuccess(task.getResult());
        } else {
            Log.w(TAG, "auth request:failed", task.getException());
            emitter.onError(task.getException());
        }
    }

    public Single<AuthResult> register(String email, String password) {
        return Single.create(emitter -> firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
                    manageAuthResult(emitter, task);
                })
        );
    }

    public Single<AuthResult> loginWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        return Single.create(emitter -> firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());
                    manageAuthResult(emitter, task);
                })
        );
    }
}
