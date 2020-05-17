package com.example.movielogger;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movielogger.models.Anime;

import java.util.ArrayList;

import static com.example.movielogger.R.id.imgIcon;

public class AnimelistAdapter extends RecyclerView.Adapter<AnimelistAdapter.AnimelistViewHolder> {

    public ArrayList<Anime> animeArrayList;

    public AnimelistAdapter(ArrayList<Anime> animeArrayList)
    {
        this.animeArrayList = animeArrayList;
    }
    @NonNull
    @Override
    public AnimelistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_general_movie, parent, false);
        return new AnimelistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimelistViewHolder holder, int position) {
        String title = animeArrayList.get(position).getAnimeName();
        String subtitle = new Integer(animeArrayList.get(position).getYearView()).toString();

        holder.txtTitle.setText(title);
        holder.txtSubTitle.setText(subtitle);
    }

    @Override
    public int getItemCount() {
        return animeArrayList.size();
    }

    public class AnimelistViewHolder extends RecyclerView.ViewHolder{
        ImageView imgIcon;
        TextView txtTitle, txtSubTitle;
        public AnimelistViewHolder(@NonNull View itemView) {
            super(itemView);
            imgIcon = itemView.findViewById(R.id.imgIcon);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtSubTitle = itemView.findViewById(R.id.txtSubTitle);
        }
    }
}
