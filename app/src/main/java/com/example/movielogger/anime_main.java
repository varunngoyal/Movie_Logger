package com.example.movielogger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class anime_main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_main);
    }

    public void anime_add_new(View view) {
        Intent intent = new Intent(this, animelist.class);
        startActivity(intent);
    }
}
