package com.jaumard.owt.network.services;

import com.jaumard.owt.models.CatalogResults;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CatalogService {
    @GET("search/movie")
    Single<CatalogResults> searchMovies(@Query("query") String query);

    @GET("discover/movie?sort_by=popularity.desc")
    Single<CatalogResults> discoveryMovies();
}
