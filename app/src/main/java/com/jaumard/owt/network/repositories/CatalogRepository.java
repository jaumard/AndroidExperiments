package com.jaumard.owt.network.repositories;

import com.jaumard.owt.models.CatalogEntry;
import com.jaumard.owt.network.repositories.datasources.LocalCatalogDataSource;
import com.jaumard.owt.network.repositories.datasources.NetworkCatalogDataSource;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

public class CatalogRepository {
    private final NetworkCatalogDataSource networkCatalogDataSource;
    private final LocalCatalogDataSource localCatalogDataSource;

    @Inject
    CatalogRepository(NetworkCatalogDataSource networkCatalogDataSource, LocalCatalogDataSource localCatalogDataSource) {
        this.networkCatalogDataSource = networkCatalogDataSource;
        this.localCatalogDataSource = localCatalogDataSource;
    }

    public Single<List<CatalogEntry>> search(String queryTitle) {
        return networkCatalogDataSource.search(queryTitle);
    }

    public Single<List<CatalogEntry>> discovery() {
        return networkCatalogDataSource.discovery();
    }

    public Completable insertOrUpdate(CatalogEntry catalogEntry) {
        return localCatalogDataSource.insert(catalogEntry);
    }

    public Single<List<CatalogEntry>> getHistory() {
        return localCatalogDataSource.retrieve();
    }
}
