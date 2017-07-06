package com.jaumard.owt.dagger.components;

import com.jaumard.owt.CatalogApplication;
import com.jaumard.owt.dagger.modules.ActivityModule;
import com.jaumard.owt.dagger.modules.AppModule;
import com.jaumard.owt.dagger.modules.DatabaseModule;
import com.jaumard.owt.dagger.modules.NetworkModule;
import com.jaumard.owt.dagger.modules.ViewModelModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class, DatabaseModule.class})
public interface AppComponent {
    ActivityComponent plusActivityComponent(ActivityModule activityModule);

    ViewModelComponent plusViewModelComponent(ViewModelModule viewModelModule);

    void inject(CatalogApplication catalogApplication);
}
