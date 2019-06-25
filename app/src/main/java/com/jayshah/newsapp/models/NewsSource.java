package com.jayshah.newsapp.models;

public class NewsSource {
    public String id;
    public String name;

    @Override
    public String toString() {
        return "NewsSource{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
