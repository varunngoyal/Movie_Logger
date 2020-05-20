package com.example.movielogger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.movielogger.models.Anime;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class AddAnimeActivity extends AppCompatActivity {

    private EditText txtAnimeName, txtEpisodes;
    private RatingBar ratingBar;
    private Button addButton;
    private NumberPicker yearPicker;
    private String mode = "added";
    private int currentYear =Calendar.getInstance().get(Calendar.YEAR);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_anime);

        txtAnimeName = findViewById(R.id.anime_name);
        txtEpisodes = findViewById(R.id.episodes);
        //txtYear = findViewById(R.id.year_view);
        ratingBar = findViewById(R.id.ratingBar);
        addButton = findViewById(R.id.btnAdd);
        yearPicker = findViewById(R.id.year);

        yearPicker.setMinValue(1950);
        yearPicker.setMaxValue(currentYear);
        yearPicker.setValue(currentYear);

        Anime anime = (Anime) getIntent().getSerializableExtra("edit_anime");
        if(anime != null) {

            //Toast.makeText(this, anime.toString(), Toast.LENGTH_SHORT).show();

            txtAnimeName.setText(anime.getAnimeName());
            txtEpisodes.setText(""+anime.getEpisodes());
            yearPicker.setValue(anime.getYearView());
            ratingBar.setRating((float) (anime.getRating()/2));
            txtAnimeName.setEnabled(false);
            addButton.setText(R.string.update_anime);
            mode = "updated";
            addButton.setEnabled(true);
        }


        yearPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

            }
        });

        txtAnimeName.addTextChangedListener(textWatcher);
        txtEpisodes.addTextChangedListener(textWatcher);
        //txtYear.addTextChangedListener(textWatcher);

    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String animeName = txtAnimeName.getText().toString().trim();
            String episodes = txtEpisodes.getText().toString().trim();
            //String year = txtYear.getText().toString().trim();

            if(!animeName.isEmpty() && !episodes.isEmpty()) {
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

        //        int anime_year_watched = Integer.parseInt(txtYear.getText().toString());
        int anime_year_watched = yearPicker.getValue();

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
        Toast.makeText(this, "Anime "+mode+" successfully!",Toast.LENGTH_SHORT).show();

        startActivity(new Intent(this, AnimeMainActivity.class));
    }
}
