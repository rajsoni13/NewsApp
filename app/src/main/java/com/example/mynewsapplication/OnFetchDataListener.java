package com.example.mynewsapplication;

import com.example.mynewsapplication.Models.NewsHeadlines;

import java.util.List;

public abstract class OnFetchDataListener<NewsApiResponse> {
    abstract void onFetchData(List<NewsHeadlines> list, String mesaage);
    abstract void onError(String message);
}
