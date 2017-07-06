package com.jaumard.owt.network.repositories.datasources;

import com.jaumard.owt.database.CatalogEntryDao;
import com.jaumard.owt.models.CatalogEntry;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

public class LocalCatalogDataSource {
    private final CatalogEntryDao entryDao;

    @Inject
    LocalCatalogDataSource(CatalogEntryDao entryDao) {
        this.entryDao = entryDao;
    }

    public Completable insert(CatalogEntry entry) {
        return Completable.fromAction(() -> entryDao.insertAll(entry));
    }

    public Single<List<CatalogEntry>> retrieve() {
        return entryDao.getAll().first(Collections.emptyList());
    }
}
