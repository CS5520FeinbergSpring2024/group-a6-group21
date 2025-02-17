package edu.northeastern.group21;

import com.google.gson.annotations.SerializedName;

public class MovieJson {

    @SerializedName("_id")
    private String id;

    private TitleText titleText;

    private ReleaseDate releaseDate;

    private TitleType titleType;

    private PrimaryImage primaryImage;

    public PrimaryImage getPrimaryImage() {
        return primaryImage;
    }

    public void setPrimaryImage(PrimaryImage primaryImage) {
        this.primaryImage = primaryImage;
    }

    // Constructor, getters, and setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TitleText getTitleText() {
        return titleText;
    }

    public void setTitleText(TitleText titleText) {
        this.titleText = titleText;
    }

    public ReleaseDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(ReleaseDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public TitleType getTitleType() {
        return titleType;
    }

    public void setTitleType(TitleType titleType) {
        this.titleType = titleType;
    }
}

class TitleText {
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

class ReleaseDate {
    private int day;
    private int month;
    private int year;

    // Constructor, getters, and setters

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}

class TitleType {
    private String id;
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    // Constructor, getters, and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

class PrimaryImage {
    private String id;

    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
