package com.example.myapplication;

public class MovieData {
    private String id;
    private String media_type;
    private String url;
    private String title;

    public MovieData(String id, String media_type, String url, String title) {
        this.id = id;
        this.media_type = media_type;
        this.url = url;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMedia_type() {
        return media_type;
    }

    public void setMedia_type(String media_type) {
        this.media_type = media_type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
