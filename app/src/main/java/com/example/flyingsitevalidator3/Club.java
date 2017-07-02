package com.example.flyingsitevalidator3;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by Brian on 10/11/2016.
 */
// Class to represent a club and its elements, its made serializable in order to transfer it to another class by means of an intent
public class Club implements Serializable, Comparable<Club>{

    private String shortName;
    private String clubId;
    private String name;
    private String contact;
    private String county;
    private double lat;
    private double lon;
    private String url;
    private String restriction;

    public Club() {
    }

    public Club(String shortName, String clubId, String name, String contact, String county, double lat, double lon, String url, String restriction) {
        this.shortName = shortName;
        this.clubId = clubId;
        this.name = name;
        this.contact = contact;
        this.county = county;
        this.lat = lat;
        this.lon = lon;
        this.url = url;
        this.restriction = restriction;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getClubId() {
        return clubId;
    }

    public void setClubId(String clubId) {
        this.clubId = clubId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
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
        if (shortName != null ? !shortName.equals(club.shortName) : club.shortName != null)
            return false;
        if (clubId != null ? !clubId.equals(club.clubId) : club.clubId != null) return false;
        if (name != null ? !name.equals(club.name) : club.name != null) return false;
        if (contact != null ? !contact.equals(club.contact) : club.contact != null) return false;
        if (county != null ? !county.equals(club.county) : club.county != null) return false;
        if (url != null ? !url.equals(club.url) : club.url != null) return false;
        return restriction != null ? restriction.equals(club.restriction) : club.restriction == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = shortName != null ? shortName.hashCode() : 0;
        result = 31 * result + (clubId != null ? clubId.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (contact != null ? contact.hashCode() : 0);
        result = 31 * result + (county != null ? county.hashCode() : 0);
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
                "shortName='" + shortName + '\'' +
                ", clubId='" + clubId + '\'' +
                ", name='" + name + '\'' +
                ", contact='" + contact + '\'' +
                ", county='" + county + '\'' +
                ", lat=" + lat +
                ", lon=" + lon +
                ", url='" + url + '\'' +
                ", restriction='" + restriction + '\'' +
                '}';
    }
    // Overriding the compareTo method
    public int compareTo(Club c) {
        return (this.name).compareTo(c.name);
    }

    static class NameComparator implements  Comparator<Club>
    {
        public int compare(Club c1, Club c2) {
            return c1.name.compareTo(c2.name);
        }
    }

}
