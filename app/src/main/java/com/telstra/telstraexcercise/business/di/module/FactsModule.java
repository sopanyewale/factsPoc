package com.telstra.telstraexcercise.business.di.module;

import android.util.Log;

import com.telstra.telstraexcercise.BuildConfig;
import com.telstra.telstraexcercise.business.api.ConsumeService;
import com.telstra.telstraexcercise.business.api.FactsApi;
import com.telstra.telstraexcercise.business.model.FactsDataRepository;
import com.telstra.telstraexcercise.ui.mvp.FactsView;

import java.io.IOException;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Sopan on 14-02-18.
 */
@Module
public class FactsModule {
    private FactsView factsView;

    public FactsModule(FactsView factsView) {
        this.factsView = factsView;
    }

    @Provides
    Retrofit providesRetrofit(@Named("baseUrl") String baseUrl) {
        Log.d("FactsModule", "baseUrl = " + baseUrl);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();

                        // Customize the request
                        Request request = original.newBuilder()
                                .header("Content-Type", "application/json")
                                .build();

                        okhttp3.Response response = chain.proceed(request);
                        response.cacheResponse();

                        return response;
                    }
                })
                .build();

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    FactsDataRepository FactsDataRepository() {
        return new FactsDataRepository();
    }

    @Named("baseUrl")
    @Provides
    @Singleton
    String providesBaseUrl() {
        // Endpoint for Json data
        return BuildConfig.BASEURL;
    }

    @Provides
    @Singleton
    public FactsApi providesNetworkService(
            Retrofit retrofit) {
        return retrofit.create(FactsApi.class);
    }

    @Provides
    @Singleton
    public ConsumeService providesService(
            FactsApi factsApi) {
        return new ConsumeService(factsApi);
    }

    @Provides
    public FactsView providesFactsView() {
        return factsView;
    }
}
