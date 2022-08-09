package com.example.mynewsapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.mynewsapplication.Models.NewsApiResponse;
import com.example.mynewsapplication.Models.NewsHeadlines;

import java.util.List;

public class home extends AppCompatActivity {
    RecyclerView recyclerView;
    CustomAdapter adapter;
    ProgressDialog dialog;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();

        searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                dialog.setTitle("Getting News Articles of" + query);
                dialog.show();
                RequestManager manager = new RequestManager(home.this);
                manager.getNewsHeadlines(listener,"general", query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        dialog = new ProgressDialog(this);
        dialog.setTitle("Getting News Articles!");
        dialog.show();
        RequestManager manager = new RequestManager(this);
        manager.getNewsHeadlines(listener,"general", null);

    }

    private final OnFetchDataListener<NewsApiResponse> listener = new OnFetchDataListener<NewsApiResponse>() {
        @Override
        void onFetchData(List<NewsHeadlines> list, String mesaage) {
            if (list.isEmpty()){
                Toast.makeText(home.this, "No data found!", Toast.LENGTH_SHORT).show();
            }
            else {
                showNews(list);
                dialog.dismiss();
            }

        }

        @Override
        void onError(String message) {
            Toast.makeText(home.this, "Error initiated!!!", Toast.LENGTH_SHORT).show();
        }
    };

    private void showNews(List<NewsHeadlines> list) {
        recyclerView = findViewById(R.id.recycler_name);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        adapter = new CustomAdapter(this, list);
        recyclerView.setAdapter(adapter);
    }

}