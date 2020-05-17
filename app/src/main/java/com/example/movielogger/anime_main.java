package com.example.movielogger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;

import com.example.movielogger.models.Anime;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class anime_main extends AppCompatActivity {

    public ArrayList<Anime> animeArrayList;
    RecyclerView animeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_main);

        final ScrollView scrollview = findViewById(R.id.scrollview);

        animeArrayList = new ArrayList<Anime>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Anime");

        scrollview.smoothScrollTo(0,0);
        animeList = findViewById(R.id.list_anime);
        animeList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        reference.addValueEventListener(getValueListenerForAnimeList());
        reference.addListenerForSingleValueEvent(getValueListenerForAnimeList());

    }

    //On Click method for add anime button
    public void addAnime(View view) {
        Intent intent = new Intent(this, AddAnimeActivity.class);
        startActivity(intent);
    }

    public ValueEventListener getValueListenerForAnimeList() {
        return new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                animeArrayList.clear();
                for(DataSnapshot keynode: dataSnapshot.getChildren())
                {
                    Anime anime = keynode.getValue(Anime.class);
                    animeArrayList.add(anime);
                }
                animeList.setAdapter(new AnimelistAdapter(animeArrayList));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
    }
}
