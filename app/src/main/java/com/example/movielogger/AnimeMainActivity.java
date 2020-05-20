package com.example.movielogger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.movielogger.models.Anime;
import com.example.movielogger.utilities.ConnectivityReceiver;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;

import static com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_INDEFINITE;
import static com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_SHORT;

public class AnimeMainActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

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
    private DatabaseReference reference;
    private CoordinatorLayout coordinatorLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_main);

        final ScrollView scrollview = findViewById(R.id.scrollview);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textToolbarTitle = findViewById(R.id.txtToolbarTitle);
        backBtn = findViewById(R.id.btnBack);
        coordinatorLayout = findViewById(R.id.layout1);

        //textToolbarTitle.setVisibility(View.GONE);
        textToolbarTitle.setText(R.string.list_of_watched_anime);
        backBtn.setVisibility(View.GONE);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearActionMode();
            }
        });

        animeArrayList = new ArrayList<>();

        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("Anime");

        scrollview.smoothScrollTo(0,0);
        animeList = findViewById(R.id.list_anime);
        animeList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        reference.addValueEventListener(getValueListenerForAnimeList());
        reference.addListenerForSingleValueEvent(getValueListenerForAnimeList());

        //checkConnection();

    }

    public void checkConnection() {

        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnackBar(isConnected);

    }

    private void showSnackBar(boolean isConnected) {

        String message;
        int color, length;

        if(isConnected) {

            message = "You are Online!";
            color = Color.WHITE;
            length = LENGTH_SHORT;

        } else {
            message = "No internet connection!";
            color = Color.RED;
            length = LENGTH_INDEFINITE;
        }

        Snackbar snackbar =  Snackbar.make(coordinatorLayout, message, length);
                snackbar.setAction("CLOSE", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

        View view = snackbar.getView();
        TextView textView = view.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(color);

        snackbar.show();
    }

    private void clearActionMode() {
        isInActionMode = false;
        textToolbarTitle.setText(R.string.list_of_watched_anime);
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

                //checkConnection();
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
                //checkConnection();
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

            updateEditStatus(counter);
            position = index;
            adapter.notifyDataSetChanged();

        }
    }

    private void updateToolBarText(int counter) {
        textToolbarTitle.setText(counter+" selected");
    }

    private void updateEditStatus(int counter) {

        MenuItem edit = toolbar.getMenu().findItem(R.id.option_edit);
        if(counter == 1) {
            //editable
            //invalidateOptionsMenu();
            edit.setVisible(true);

        }
        else {
            //not editable
            //invalidateOptionsMenu();
            edit.setVisible(false);
        }
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
        updateEditStatus(counter);

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

                return true;

            case R.id.option_edit:
                Anime edit_anime = selectionList.get(0);
                Intent intent = new Intent(AnimeMainActivity.this, AddAnimeActivity.class);
                intent.putExtra("edit_anime", edit_anime);
                startActivity(intent);
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

    @Override
    protected void onResume() {
        super.onResume();

        //register intent filter
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);

        ConnectivityReceiver connectivityReceiver = new ConnectivityReceiver();
        registerReceiver(connectivityReceiver, intentFilter);

        //register connection status listener
        MyApp.getInstance().setConnectivityListener(this);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnackBar(isConnected);
    }
}
