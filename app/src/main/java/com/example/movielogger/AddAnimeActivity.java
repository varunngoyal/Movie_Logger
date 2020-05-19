package com.example.movielogger;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.movielogger.models.Anime;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddAnimeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_anime);

    }

    public void addAnime(View view) {

        EditText txt_name = findViewById(R.id.anime_name);
        String anime_name = txt_name.getText().toString();

        EditText txt_episodes = findViewById(R.id.episodes);
        int anime_episodes = Integer.parseInt(txt_episodes.getText().toString());

        EditText txt_year =findViewById(R.id.year_view);
        int anime_year_watched = Integer.parseInt(txt_year.getText().toString());

        RatingBar anime_rating_bar = findViewById(R.id.ratingBar); // initiate a rating bar
        double anime_rating = anime_rating_bar.getRating() * 2; // get rating number from a rating bar

        Anime anime = new Anime();
        anime.setAnimeName(anime_name);
        anime.setEpisodes(anime_episodes);
        anime.setRating(anime_rating);
        anime.setYearView(anime_year_watched);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Anime");
        //final String key = reference.push().getKey();
        final String key = anime.getAnimeName();
        reference.child(key).setValue(anime);
        Toast.makeText(this, "Anime added successfully!",Toast.LENGTH_SHORT).show();

    }
}
