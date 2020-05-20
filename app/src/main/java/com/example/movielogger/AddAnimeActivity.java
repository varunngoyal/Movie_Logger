package com.example.movielogger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.movielogger.models.Anime;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddAnimeActivity extends AppCompatActivity {

    private EditText txtAnimeName, txtEpisodes, txtYear;
    private RatingBar ratingBar;
    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_anime);

        txtAnimeName = findViewById(R.id.anime_name);
        txtEpisodes = findViewById(R.id.episodes);
        txtYear = findViewById(R.id.year_view);
        ratingBar = findViewById(R.id.ratingBar);
        addButton = findViewById(R.id.btnAdd);

        txtAnimeName.addTextChangedListener(textWatcher);
        txtEpisodes.addTextChangedListener(textWatcher);
        txtYear.addTextChangedListener(textWatcher);

    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String animeName = txtAnimeName.getText().toString().trim();
            String episodes = txtEpisodes.getText().toString().trim();
            String year = txtYear.getText().toString().trim();

            if(!animeName.isEmpty() && !episodes.isEmpty() && !year.isEmpty()) {
                addButton.setEnabled(true);
            } else {
                addButton.setEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    public void addAnime(View view) {

        String anime_name = txtAnimeName.getText().toString();

        int anime_episodes = Integer.parseInt(txtEpisodes.getText().toString());

        int anime_year_watched = Integer.parseInt(txtYear.getText().toString());

        double anime_rating = ratingBar.getRating() * 2; // get rating number from a rating bar

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

        startActivity(new Intent(this, AnimeMainActivity.class));
    }
}
