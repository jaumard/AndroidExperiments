package com.jaumard.owt;

import android.app.Application;
import android.os.StrictMode;

import com.google.firebase.FirebaseApp;
import com.jaumard.owt.dagger.components.AppComponent;
import com.jaumard.owt.dagger.components.DaggerAppComponent;
import com.jaumard.owt.dagger.modules.AppModule;
import com.jaumard.owt.dagger.modules.NetworkModule;


public class CatalogApplication extends Application {
    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
        appComponent = initDagger(this);

        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyDeath()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .penaltyDeath()
                    .build());
        }
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    protected AppComponent initDagger(CatalogApplication application) {
        return DaggerAppComponent.builder().appModule(new AppModule(application))
                .networkModule(new NetworkModule()).build();
    }
}
