package com.example.movielogger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;

public class anime_main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_main);
        final ScrollView scrollview = ((ScrollView) findViewById(R.id.scrollview));
        //scrollview.fullScroll(ScrollView.FOCUS_UP);

        scrollview.smoothScrollTo(0,0);
        RecyclerView list_anime = (RecyclerView) findViewById(R.id.list_anime);
        list_anime.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        String[] languages = {"Java", "JavaScript", "C#", "php", "C", "C++", "Python", "C", "C", "C++", "Python", "C"};
        list_anime.setAdapter(new AnimelistAdapter(languages));



    }

    public void anime_add_new(View view) {
        Intent intent = new Intent(this, animelist.class);
        startActivity(intent);
    }
}
