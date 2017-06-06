package com.example.flyingsitevalidator3;

import java.io.Serializable;

/**
 * Created by Brian on 10/11/2016.
 */
// Class to represent a club and its elements, its made serializable in order to transfer it to another class by means of an intent
public class Club implements Serializable{

    private String name;
    private String address;
    private double lat;
    private double lon;
    private String url;
    private String restriction;

    public Club() {
    }

    public Club(String name, String address, double lat, double lon, String url, String restriction) {
        this.name = name;
        this.address = address;
        this.lat = lat;
        this.lon = lon;
        this.url = url;
        this.restriction = restriction;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRestriction() {
        return restriction;
    }

    public void setRestriction(String restriction) {
        this.restriction = restriction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Club club = (Club) o;

        if (Double.compare(club.lat, lat) != 0) return false;
        if (Double.compare(club.lon, lon) != 0) return false;
        if (name != null ? !name.equals(club.name) : club.name != null) return false;
        if (address != null ? !address.equals(club.address) : club.address != null) return false;
        if (url != null ? !url.equals(club.url) : club.url != null) return false;
        return restriction != null ? restriction.equals(club.restriction) : club.restriction == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name != null ? name.hashCode() : 0;
        result = 31 * result + (address != null ? address.hashCode() : 0);
        temp = Double.doubleToLongBits(lat);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(lon);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (restriction != null ? restriction.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Club{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", lat=" + lat +
                ", lon=" + lon +
                ", url='" + url + '\'' +
                ", restriction='" + restriction + '\'' +
                '}';
    }
}
