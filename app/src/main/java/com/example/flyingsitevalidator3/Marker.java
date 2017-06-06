package com.example.flyingsitevalidator3;

/**
 * Created by Brian on 09/11/2016.
 */

//This class is used to represent a custom club marker
public class Marker {

    private String title;
    private String description;
    private double longitude;
    private double latitude;
    private String url;

    public Marker(String title, String snippet, double longitude, double latitude, String url) {
        this. title = title;
        this.longitude = longitude;
        this.latitude = latitude;
        description = snippet;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
