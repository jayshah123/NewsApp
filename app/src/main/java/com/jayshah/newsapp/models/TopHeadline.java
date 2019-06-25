package com.jayshah.newsapp.models;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "TopHeadlines")
public class TopHeadline {
    @PrimaryKey
    @NonNull
    public String url;

    public String title;
    public String author;
    public String description;
    public String urlToImage;
    public Date publishedAt;
    @Embedded public NewsSource source;

    @Override
    public String toString() {
        return "TopHeadline{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", urlToImage='" + urlToImage + '\'' +
                ", publishedAt=" + publishedAt +
                ", source=" + source +
                '}';
    }
}
