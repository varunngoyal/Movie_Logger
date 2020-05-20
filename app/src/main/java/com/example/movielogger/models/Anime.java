package com.example.movielogger.models;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Anime implements Serializable {

    private String animeName;
    private int episodes;
    private double rating;
    private int yearView;

    public Anime() {

    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getAnimeName() {
        return animeName;
    }

    public void setAnimeName(String animeName) {
        this.animeName = animeName;
    }

    public int getEpisodes() {
        return episodes;
    }

    public void setEpisodes(int episodes) {
        this.episodes = episodes;
    }

    public int getYearView() {
        return yearView;
    }

    public void setYearView(int yearView) {
        this.yearView = yearView;
    }

    @NonNull
    @Override
    public String toString() {
        //return super.toString();
        return "["+animeName+","+episodes+","+rating+","+yearView+"]";
    }
}
