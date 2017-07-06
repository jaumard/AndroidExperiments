package com.jaumard.owt.dagger.modules;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.jaumard.owt.BuildConfig;
import com.jaumard.owt.database.CatalogEntryDao;
import com.jaumard.owt.database.CatalogHistoryDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {
    @Provides
    @Singleton
    CatalogHistoryDatabase provideHistoryDatabase(Context context) {
        return Room.databaseBuilder(context, CatalogHistoryDatabase.class, BuildConfig.DATABASE_NAME).build();
    }

    @Provides
    @Singleton
    CatalogEntryDao provideCatalogEntryDao(CatalogHistoryDatabase db) {
        return db.catalogEntryDao();
    }
}
