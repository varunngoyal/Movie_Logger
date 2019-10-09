package com.example.movielogger;

public class Anime {

    private String anime_name;
    private int episodes;
    private int year_view;
    private double rating;

    public Anime(String anime_name, int episodes, int year_view, double rating) {
        this.anime_name = anime_name;
        this.episodes = episodes;
        this.year_view = year_view;
        this.rating = rating;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public Anime() {

    }

    public String getAnime_name() {
        return anime_name;
    }

    public void setAnime_name(String anime_name) {
        this.anime_name = anime_name;
    }

    public int getEpisodes() {
        return episodes;
    }

    public void setEpisodes(int episodes) {
        this.episodes = episodes;
    }

    public int getYear_view() {
        return year_view;
    }

    public void setYear_view(int year_view) {
        this.year_view = year_view;
    }
}
