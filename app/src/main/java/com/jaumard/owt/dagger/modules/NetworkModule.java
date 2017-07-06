package com.jaumard.owt.dagger.modules;

import com.google.firebase.auth.FirebaseAuth;
import com.jaumard.owt.network.NetworkServiceFactory;
import com.jaumard.owt.network.services.AuthService;
import com.jaumard.owt.network.services.CatalogService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class NetworkModule {
    @Provides
    @Singleton
    CatalogService provideCatalogService() {
        return NetworkServiceFactory.createService(CatalogService.class);
    }

    @Provides
    AuthService provideAuthService(FirebaseAuth firebaseAuth) {
        return new AuthService(firebaseAuth);
    }
}
