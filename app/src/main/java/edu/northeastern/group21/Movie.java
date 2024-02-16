package edu.northeastern.group21;


public class Movie {
    private String name;
    private String genres;
    private String releaseDate;
    private String rate;
    private String posterUrl;

    // Constructor
    public Movie(String name, String genres, String releaseDate, String rate, String posterUrl) {
        this.name = name;
        this.genres = genres;
        this.releaseDate = releaseDate;
        this.rate = rate;
        this.posterUrl = posterUrl;
    }

    // Getters
    public String getName() { return name; }
    public String getGenres() { return genres; }
    public String getReleaseDate() { return releaseDate; }
    public String getRate() { return rate; }
    public String getPosterUrl() { return posterUrl; }
}

