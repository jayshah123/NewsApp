package com.jayshah.newsapp.newsData;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.jayshah.newsapp.models.TopHeadline;

@Database(entities = {TopHeadline.class}, version = 2)
@TypeConverters(DateConverter.class)
public abstract class NewsDatabase extends RoomDatabase {
    public abstract TopHeadlineDAO topHeadlineDAO();
}
