package com.jaumard.owt.network.repositories.datasources;

import com.jaumard.owt.models.CatalogEntry;
import com.jaumard.owt.models.CatalogResults;
import com.jaumard.owt.network.services.CatalogService;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class NetworkCatalogDataSource {
    private final CatalogService catalogServiceService;

    @Inject
    NetworkCatalogDataSource(CatalogService catalogServiceService) {
        this.catalogServiceService = catalogServiceService;
    }

    public Single<List<CatalogEntry>> search(String queryTitle) {
        return catalogServiceService.searchMovies(queryTitle).map(CatalogResults::getResults);
    }

    public Single<List<CatalogEntry>> discovery() {
        return catalogServiceService.discoveryMovies().map(CatalogResults::getResults);
    }
}
