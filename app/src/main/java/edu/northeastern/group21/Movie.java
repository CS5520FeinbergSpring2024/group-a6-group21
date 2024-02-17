package edu.northeastern.group21;


public class Movie {
    private String name;
    private String genres;
    private String releaseYear;

    private Double rating;
    private String posterUrl;

    // Constructor
    public Movie(String name, String genres, String releaseYear, Double rating, String posterUrl) {
        this.name = name;
        this.genres = genres;
        this.releaseYear = releaseYear;
        this.rating = rating;
        this.posterUrl = posterUrl;
    }

    // Getters
    public String getName() { return name; }
    public String getGenres() { return genres; }
    public String getReleaseDate() { return releaseYear; }
    public Double getRate() { return rating; }
    public String getPosterUrl() { return posterUrl; }
}

