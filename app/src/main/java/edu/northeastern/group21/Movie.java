package edu.northeastern.group21;

public class Movie {
    private final String title;
    private final String type;
    private final int releaseYear;

    public Movie(String title, String type, int releaseYear) {
        this.title = title;
        this.type = type;
        this.releaseYear = releaseYear;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public int getReleaseYear() {
        return releaseYear;
    }
}
