package com.jaumard.owt.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.jaumard.owt.models.CatalogEntry;

@Database(entities = {CatalogEntry.class}, version = 1)
public abstract class CatalogHistoryDatabase extends RoomDatabase {
    public abstract CatalogEntryDao catalogEntryDao();
}
