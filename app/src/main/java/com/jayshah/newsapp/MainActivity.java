package com.jayshah.newsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import com.jayshah.newsapp.HeadlinesUI.NewsHeadlinesFragment;
import com.jayshah.newsapp.models.TopHeadline;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    Flowable<List<TopHeadline>> topHeadlines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, new NewsHeadlinesFragment())
                .commit();

        MainApplication application = (MainApplication) getApplication();
        application.getAppComponent().inject(this);
    }

}
