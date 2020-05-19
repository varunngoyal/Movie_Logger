package com.example.movielogger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.movielogger.models.Anime;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AnimeMainActivity extends AppCompatActivity {

    private ArrayList<Anime> animeArrayList;
    private ArrayList<Anime> selectionList = new ArrayList<>();
    private int counter = 0;
    RecyclerView animeList;
    public boolean isInActionMode = false;
    public int position = -1;
    public boolean select_all = false;
    public boolean selectEnabled;

    private Toolbar toolbar;
    private TextView textToolbarTitle;
    private ImageButton backBtn;
    private AnimelistAdapter adapter;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_main);

        final ScrollView scrollview = findViewById(R.id.scrollview);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textToolbarTitle = findViewById(R.id.txtToolbarTitle);
        backBtn = findViewById(R.id.btnBack);

        textToolbarTitle.setVisibility(View.GONE);
        backBtn.setVisibility(View.GONE);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearActionMode();
            }
        });

        animeArrayList = new ArrayList<>();

        database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference().child("Anime");

        scrollview.smoothScrollTo(0,0);
        animeList = findViewById(R.id.list_anime);
        animeList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        reference.addValueEventListener(getValueListenerForAnimeList());
        reference.addListenerForSingleValueEvent(getValueListenerForAnimeList());

    }

    private void clearActionMode() {
        isInActionMode = false;
        textToolbarTitle.setText("List of Watched Anime");
        backBtn.setVisibility(View.GONE);
        counter = 0;
        selectionList.clear();
        toolbar.getMenu().clear();

        adapter.notifyDataSetChanged();
        select_all = true;
    }

    //############################################## ADD ANIME ON CLICK LISTENER ##########################################################

    //On Click method for add anime button
    public void addAnime(View view) {
        Intent intent = new Intent(this, AddAnimeActivity.class);
        startActivity(intent);
    }

    //##################################### Firebase value event listener ###############################################################

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
                adapter = new AnimelistAdapter(AnimeMainActivity.this, animeArrayList);
                animeList.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
    }

    //######################################### recycler view delete functions #######################################################3333

    public void startSelection(int index) {

        if(!isInActionMode) {
            isInActionMode = true;
            selectionList.add(animeArrayList.get(index));
            counter++;
            updateToolBarText(counter);
            textToolbarTitle.setVisibility(View.VISIBLE);
            backBtn.setVisibility(View.VISIBLE);
            toolbar.inflateMenu(R.menu.anime_main_menu);
            position = index;
            adapter.notifyDataSetChanged();

        }
    }

    private void updateToolBarText(int counter) {
        textToolbarTitle.setText(counter+" selected");
    }

    public void check(View v, int position) {

        if (((CheckBox) v).isChecked()) {
            selectionList.add(animeArrayList.get(position));
            counter++;
            updateToolBarText(counter);

        } else {
            selectionList.remove(animeArrayList.get(position));
            counter--;
            updateToolBarText(counter);

        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()) {

            case R.id.option_2_delete:

                if(selectionList.size() > 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("Delete " + selectionList.size() + " items?");
                    builder.setTitle("Confirm");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //actually delete the item
                            for (Anime anime : selectionList) {
                                DatabaseReference animeRef = FirebaseDatabase.getInstance().getReference().child("Anime/" + anime.getAnimeName());
                                animeRef.removeValue();
                            }
                            //adapter.notifyDataSetChanged();
                            clearActionMode();
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                } else
                    Toast.makeText(this, "No item selected!", Toast.LENGTH_SHORT).show();

                return true;

            case R.id.option_1_selectall:

                selectEnabled = true;
                select_all = !select_all;

                adapter.notifyDataSetChanged();


                //selectEnabled = false;

                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    //################################# ACTIVITY control FUNCTIONS #############################################################

    @Override
    public void onBackPressed() {
        if(isInActionMode) {
            clearActionMode();
        } else {
            super.onBackPressed();
        }
    }
}
