package com.example.movielogger;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.movielogger.R.id.imgIcon;

public class AnimelistAdapter extends RecyclerView.Adapter<AnimelistAdapter.AnimelistViewHolder> {

    private String[] data;
    public AnimelistAdapter(String[] data)
    {
        this.data = data;
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
        String title = data[position];
        holder.txtTitle.setText(title);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class AnimelistViewHolder extends RecyclerView.ViewHolder{
        ImageView imgIcon;
        TextView txtTitle;
        public AnimelistViewHolder(@NonNull View itemView) {
            super(itemView);
            imgIcon = itemView.findViewById(R.id.imgIcon);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
        }
    }
}
