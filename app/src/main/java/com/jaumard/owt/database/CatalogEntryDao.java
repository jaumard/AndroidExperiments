package com.jaumard.owt.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.jaumard.owt.models.CatalogEntry;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface CatalogEntryDao {
    @Query("SELECT * FROM catalog_entries ORDER BY title")
    Flowable<List<CatalogEntry>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(CatalogEntry... entries);
}
