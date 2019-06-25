package com.jayshah.newsapp.newsData;

import com.google.gson.annotations.SerializedName;

class BaseResponse<T> {
    @SerializedName("status")
    String status;

    @SerializedName("totalResults")
    Integer totalResults;

    @SerializedName("articles")
    T[] articles;
}
