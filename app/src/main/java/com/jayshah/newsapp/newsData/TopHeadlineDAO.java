package com.jayshah.newsapp.newsData;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.jayshah.newsapp.models.TopHeadline;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface TopHeadlineDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertAll(List<TopHeadline> topHeadlineList);

    @Query("select * from TopHeadlines")
    Flowable<List<TopHeadline>> getAllTopHeadlines();

}
