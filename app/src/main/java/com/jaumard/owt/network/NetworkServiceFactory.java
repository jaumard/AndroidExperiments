package com.jaumard.owt.network;

import com.jaumard.owt.BuildConfig;
import com.jaumard.owt.network.helpers.ApiKeyInterceptor;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public final class NetworkServiceFactory {
    private static HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor()
            .setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);

    private static OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(new ApiKeyInterceptor())
            .build();

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_API_URL)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static <T> T createService(Class type) {
        return (T) retrofit.create(type);
    }

    private NetworkServiceFactory() {
    }
}
