package com.example.flyingsitevalidator3;

import java.io.Serializable;

/**
 * Created by Brian on 10/11/2016.
 */

public class Item implements Serializable{

    private String name;
    private String address;
    private double lat;
    private double lon;
    private String url;

    public Item() {
    }

    public Item(String name, String address, double lat, double lon, String url) {
        this.name = name;
        this.address = address;
        this.lat = lat;
        this.lon = lon;
        this.url = url;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        if (Double.compare(item.lat, lat) != 0) return false;
        if (Double.compare(item.lon, lon) != 0) return false;
        if (!name.equals(item.name)) return false;
        if (!address.equals(item.address)) return false;
        return url != null ? url.equals(item.url) : item.url == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name.hashCode();
        result = 31 * result + address.hashCode();
        temp = Double.doubleToLongBits(lat);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(lon);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", lat=" + lat +
                ", lon=" + lon +
                ", url='" + url + '\'' +
                '}';
    }
}
