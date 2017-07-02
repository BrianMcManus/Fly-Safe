package com.example.flyingsitevalidator3;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

/**
 * Created by brian on 01/07/2017.
 */

public class ClubsAndSitesMap extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;
    LatLng ll;
    private ArrayList<Club> clubs = new ArrayList<Club>();
    private ArrayList<Club> items1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model_eligibility_map);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map1);
        mapFragment.getMapAsync(this);

    }

    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        //Create a new map and set the camera over ireland at the specified height
        mMap = googleMap;
        float zoomLevel1 = (float) 6.5;
        moveCamera(53.2734, -7.778320310000026, zoomLevel1);

        //Get the information sent through the intent and place in a new Intent object
        Intent intent = getIntent();



        //Check if the intent is for individual flying site if it is then enter
        if (intent.getSerializableExtra("AllSites_dataProvider") == null && intent.getParcelableExtra("longLat_dataProvider") != null) {

            //Populate the club list with the club list sent in the intent
            clubs = (ArrayList<Club>) intent.getSerializableExtra("ClubDataProvider");

            //Alert the user that they should check in with club officials before flying at any of the sites
            Toast.makeText(this, "Be sure to check-in with club officals before attending", Toast.LENGTH_LONG);

            //Create a lat/lng object from the club position taken from the intent
            ll = intent.getParcelableExtra("longLat_dataProvider");
            //Take the club name, address and website address from the intent and place them in variables
            final String iName = intent.getExtras().getString("name_dataProvider");
            final String iAddress = intent.getExtras().getString("address_dataProvider");
            final String iUrl = intent.getExtras().getString(("url_dataProvider"));

            createMarker(ll.latitude, ll.longitude, iName, iAddress, iUrl);

            //Check if the intent is for all available flying site if it is then enter
        } else if (intent.getSerializableExtra("AllSites_dataProvider") != null && intent.getParcelableExtra("longLat_dataProvider") == null) {

            //Alert the user that they should check in with club officials before flying at any of the sites
            Toast.makeText(this, "Be sure to check-in with club officals before attending", Toast.LENGTH_LONG);
            //Populate the list with the list of clubs taken from the intent
            clubs = (ArrayList<Club>) intent.getSerializableExtra("AllSites_dataProvider");

            for (int i = 0; i < clubs.size(); i++) {
                double lat = clubs.get(i).getLat();
                double lon = clubs.get(i).getLon();
                String name = clubs.get(i).getName();
                String address = clubs.get(i).getCounty();
                createMarker(lat, lon, name, address, clubs.get(i).getUrl());
            }
        }
    }

    /*When an info window is clicked we extract the title of the club and cycle through the club list to find a match
       * when one is found we check if it has a url attached, if it does we send the user to the website, if not we alert
       * the user that the club does not own a website through a toast*/
    @Override
    public void onInfoWindowClick(com.google.android.gms.maps.model.Marker marker) {

        if (clubs != null) {
            if (clubs.size() > 0) {
                for (int i = 0; i < clubs.size(); i++) {
                    if (marker.getTitle().equals(clubs.get(i).getName())) {
                        if (clubs.get(i).getUrl() != null && !clubs.get(i).getUrl().equals("")) {
                            String url = "http://" + clubs.get(i).getUrl();
                            Uri uri = Uri.parse(url);
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);
                        } else {
                            Toast.makeText(this, "Sorry there is no website available for this club", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        }
    }

    /*This method is used to move the camera position to the coordinates taken in by the method,
     * it sets the zoom level to the zoom level taken in */
    public void moveCamera(final double lat, final double lon, final float zoom) {

        LatLng latLng = new LatLng(lat, lon);
        float zoomLevel = (float) zoom; //This goes up to 21
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel));

    }


    /*This method creates a standard custom club marker, it takes in the latitude and longitude coordinates to place the marker in
    * the correct position on the map, it also takes in the name of the club, as well as that the clubs address is contained in the snippet variable
    * and if there is one present the url variable will contain the clubs website address*/
    protected void createMarker(final double latitude, final double longitude, final String title, final String snippet, final String url) {


        //Set the size of the marker
        int height = 100;
        int width = 100;

        //Get the marker image and create the custom marker with the standard club marker image
        BitmapDrawable bitmapdraw = (BitmapDrawable) ContextCompat.getDrawable(getApplicationContext(), R.mipmap.ic_launcher2);
        Bitmap b = bitmapdraw.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);

        //If the url is not null(The club has a website)
        if (url != null) {
            //Create a new marker with all the clubs information displayed including the website address
            com.google.android.gms.maps.model.Marker m = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(latitude, longitude))
                    .anchor(0.5f, 0.5f)
                    .title(title)
                    .snippet(snippet + " " +  url)
                    .icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));

            //Set the listener to listen for a click on the info window and if it is open the browser and navigate to the website
            mMap.setOnInfoWindowClickListener(this);

        } else {
            //Create a new marker with all the clubs information displayed
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(latitude, longitude))
                    .anchor(0.5f, 0.5f)
                    .title(title)
                    .snippet(snippet)
                    .icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
        }

    }

}