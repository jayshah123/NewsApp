package com.jayshah.newsapp.newsData;

import com.jayshah.newsapp.models.TopHeadline;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsRxApi {

    static final String baseURL = "https://newsapi.org/";

    @GET("v2/top-headlines")
    Flowable<BaseResponse<TopHeadline>> getTopHeadlines(@Query("country") String country,
                                                        @Query("apiKey") String apiKey);
}
