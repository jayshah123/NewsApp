package com.jayshah.newsapp;

import com.jayshah.newsapp.HeadlinesUI.NewsHeadlinesFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules={NetModule.class})
public interface AppComponent {
    void inject(MainActivity mainActivity);
    void inject(NewsHeadlinesFragment newsHeadlinesFragment);
}
