package com.example.movielogger;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movielogger.models.Anime;

import java.util.ArrayList;

public class AnimelistAdapter extends RecyclerView.Adapter<AnimelistAdapter.AnimelistViewHolder> {

    public ArrayList<Anime> animeArrayList;
    private Context context;
    private AnimeMainActivity animeMainActivity;

    public AnimelistAdapter(Context context, ArrayList<Anime> animeArrayList)
    {
        this.animeArrayList = animeArrayList;
        this.context = context;
        this.animeMainActivity = (AnimeMainActivity) context;
    }
    @NonNull
    @Override
    public AnimelistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_general_movie, parent, false);
        return new AnimelistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimelistViewHolder holder, final int position) {
        String title = animeArrayList.get(position).getAnimeName();
        String year = animeArrayList.get(position).getYearView()+"";
        String episodes = animeArrayList.get(position).getEpisodes()+" Episodes";
        String rating = ""+animeArrayList.get(position).getRating();

        holder.txtTitle.setText(title);
        holder.txtYear.setText(year);
        holder.txtEpisodes.setText(episodes);
        holder.txtRating.setText(rating);

        if(animeMainActivity.position == position) {
            holder.checkBox.setChecked(true);
            animeMainActivity.position = -1;
        }

        if(animeMainActivity.selectEnabled) {
            if(animeMainActivity.select_all) {
                if(!holder.checkBox.isChecked()) {
                    holder.checkBox.performClick();
                }
            } else {
                if(holder.checkBox.isChecked()) {
                    holder.checkBox.performClick();
                }
            }

            if(position == getItemCount() - 1) {
                animeMainActivity.selectEnabled = false;
            }
        }


        if(animeMainActivity.isInActionMode) {
            CheckBoxAnimation checkBoxAnimation = new CheckBoxAnimation(100, holder.checkBoxHolder);
            checkBoxAnimation.setDuration(300);
            holder.checkBoxHolder.setAnimation(checkBoxAnimation);

        } else {
            CheckBoxAnimation checkBoxAnimation = new CheckBoxAnimation(0, holder.checkBoxHolder);
            checkBoxAnimation.setDuration(300);
            holder.checkBoxHolder.setAnimation(checkBoxAnimation);
            holder.checkBox.setChecked(false);

        }

        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //return false;
                animeMainActivity.startSelection(position);
                return true;
            }
        });

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animeMainActivity.check(v, position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return animeArrayList.size();
    }

    public class AnimelistViewHolder extends RecyclerView.ViewHolder{

        TextView txtTitle, txtYear, txtEpisodes, txtRating;
        CheckBox checkBox;
        LinearLayout checkBoxHolder;
        CardView cardView;

        public AnimelistViewHolder(@NonNull View itemView) {
            super(itemView);


            txtRating = itemView.findViewById(R.id.txtRating);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtYear = itemView.findViewById(R.id.txtYear);
            txtEpisodes = itemView.findViewById(R.id.txtEpisodes);
            checkBoxHolder = itemView.findViewById(R.id.checkboxHolder);
            checkBox = itemView.findViewById(R.id.checkbox);
            cardView = itemView.findViewById(R.id.cardViewAnime);
        }

    }

    class CheckBoxAnimation extends Animation {
        private int width, startWidth;
        private View view;

        public CheckBoxAnimation(int width, View view) {
            this.width = width;
            this.view = view;
            this.startWidth = view.getWidth();
        }


        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {

            int newWidth = startWidth + (int) ((width - startWidth)*interpolatedTime);
            view.getLayoutParams().width = newWidth;
            view.requestLayout();

            super.applyTransformation(interpolatedTime, t);
        }

        @Override
        public boolean willChangeBounds() {
            return true;
        }
    }
}
