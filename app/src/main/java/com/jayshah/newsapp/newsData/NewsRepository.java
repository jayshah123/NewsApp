package com.jayshah.newsapp.newsData;

import android.app.Application;

import com.jayshah.newsapp.Constants;
import com.jayshah.newsapp.models.TopHeadline;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

public class NewsRepository {

    NewsRxApi newsRxApi;
    NewsDatabase newsDatabase;
    Application application;

    @Inject
    public NewsRepository(Application application, NewsDatabase newsDatabase, NewsRxApi newsRxApi) {
        this.application = application;
        this.newsDatabase = newsDatabase;
        this.newsRxApi = newsRxApi;
    }

    public Flowable<List<TopHeadline>> getHeadlinesForUI(){
        return Flowable.mergeDelayError(
                getAllHeadlinesFromNetwork().doOnNext(headlines -> {
                    saveHeadlinesToDatabase(headlines);
                }).subscribeOn(Schedulers.io()),
                getAllHeadlinesFromDatabase().subscribeOn(Schedulers.io())
        );
    }

    Flowable<List<TopHeadline>> getAllHeadlinesFromNetwork(){
        return newsRxApi.getTopHeadlines("in", Constants.API_KEY)
                .map(resp -> {
                    return Arrays.asList(resp.articles);
                });
    }

    Flowable<List<TopHeadline>> getAllHeadlinesFromDatabase(){
        return newsDatabase.topHeadlineDAO().getAllTopHeadlines();
    }

    void saveHeadlinesToDatabase(List<TopHeadline> headlines){
        newsDatabase.topHeadlineDAO().insertAll(headlines);
    }
}
