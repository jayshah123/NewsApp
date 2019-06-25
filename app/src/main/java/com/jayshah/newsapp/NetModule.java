package com.jayshah.newsapp;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.room.Room;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jayshah.newsapp.newsData.NewsDatabase;
import com.jayshah.newsapp.newsData.NewsRepository;
import com.jayshah.newsapp.newsData.NewsRxApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetModule {

    String mBaseUrl;
    Application mApplication;

    // Constructor needs one parameter to instantiate.
    public NetModule(String baseUrl, Application application) {
        this.mBaseUrl = baseUrl;
        this.mApplication = application;
    }

    @Provides
    @Singleton
    Application provideApplicationContext(){
        return this.mApplication;
    }

    @Provides
    @Singleton
    // Application reference must come from AppModule.class
    SharedPreferences providesSharedPreferences(Application application) {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

    @Provides
    @Singleton
    Cache provideOkHttpCache(Application application) {
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(application.getCacheDir(), cacheSize);
        return cache;
    }

    @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.IDENTITY);
        return gsonBuilder.create();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(Cache cache) {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.cache(cache);
        client.addNetworkInterceptor(new StethoInterceptor());
        return client.build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(mBaseUrl)
                .client(okHttpClient)
                .build();
        return retrofit;
    }

    @Provides
    @Singleton
    NewsRxApi getNewsRxApi(Retrofit retrofit){
        return retrofit.create(NewsRxApi.class);
    }

    @Provides
    @Singleton
    NewsDatabase getNewsDatabase(Application application){
        NewsDatabase db = Room.databaseBuilder(application.getApplicationContext(),
                NewsDatabase.class, "news-database")
                .fallbackToDestructiveMigration()
                .build();
        return db;
    }

    @Provides
    @Singleton
    NewsRepository getNewsRepository(Application application, NewsDatabase newsDatabase, NewsRxApi newsRxApi) {
        NewsRepository newsRepository = new NewsRepository(application, newsDatabase, newsRxApi);
        return newsRepository;
    }
}
