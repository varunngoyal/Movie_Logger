package com.example.movielogger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class animelist extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animelist);
    }

    public void addanime(View view) {

        //name of anime, no. of episodes, date of watching, rating
        EditText anime_name = (EditText) findViewById(R.id.anime_name);
        String anime_name_str = anime_name.getText().toString();

        EditText n_episodes = (EditText) findViewById(R.id.episodes);
        int n_eps_int = Integer.parseInt(n_episodes.getText().toString());

        EditText year_watch = (EditText) findViewById(R.id.year_view);
        int year_watch_int = Integer.parseInt(year_watch.getText().toString());

        RatingBar simpleRatingBar = (RatingBar) findViewById(R.id.ratingBar); // initiate a rating bar
        Float rating = simpleRatingBar.getRating(); // get rating number from a rating bar



        Anime ani = new Anime(anime_name_str, n_eps_int, year_watch_int, rating);

        DatabaseReference reff = FirebaseDatabase.getInstance().getReference("Anime");
        final String key = reff.push().getKey();

        reff.child(key).setValue(ani);

        /*reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot keynode: dataSnapshot.getChildren())
                {
                    Anime ani2 = keynode.getValue(Anime.class);
                    Toast toast = Toast.makeText(getApplicationContext(), ani2.getRating()+" "+ani2.getEpisodes()+" "+ani2.getYear_view()+" "+ani2.getAnime_name(),Toast.LENGTH_SHORT);
                    toast.show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

    }
}
