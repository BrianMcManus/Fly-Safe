package com.example.flyingsitevalidator3;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, GoogleMap.OnInfoWindowClickListener, LocationListener {

    private GoogleMap mMap;
    private ArrayList<AirportMarker> items = new ArrayList<AirportMarker>();
    private GoogleApiClient mGoogleApiClient;
    private double userLat = 0;
    private double userLon = 0;
    private ArrayList<Club> items1;
    private Location mLastLocation;
    boolean schoolOk = true;
    private LocationRequest mLocationRequest;
    private String temp;
    List<HashMap<String, String>> googlePlaces = null;
    Place_JSON placeJson = new Place_JSON();
    JSONObject jObject;
    Location loc;
    LatLng ll;
    com.google.android.gms.maps.model.Marker userposition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        // Create an instance of GoogleAPIClient adding location and Place API's.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(Places.GEO_DATA_API)
                    .addApi((Places.PLACE_DETECTION_API))
                    .enableAutoManage(this, this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
            createLocationRequest();
        }

    }

    protected void onStart() {
        //Connect the client
        mGoogleApiClient.connect();
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
        //Check if the client is still connected and if not connect them
        if (!mGoogleApiClient.isConnected()) {
            mGoogleApiClient.connect();
        }
    }

    protected void onStop() {
        //Disconnect the client from the api
        mGoogleApiClient.disconnect();
        super.onStop();

    }

    @Override
    protected void onPause() {
        super.onPause();
        //Check if the client is still connected and if they are stop updating location and disconnect client
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {


        //Create a new map
        mMap = googleMap;

        //Create an intent from the information sent from previous activity
        Intent intent = getIntent();

        //Populate the list with all the airport marker object information
        items = (ArrayList<AirportMarker>) intent.getSerializableExtra("AllAirports_dataProvider");

        //Cycle through the list placing no fly areas around the coordinates given and place them on the map using google map circles
        if (items != null && items.size() > 0) {

            for (int i = 0; i < items.size(); i++) {
                double lat = items.get(i).getLat();
                double lon = items.get(i).getLon();
                String name = items.get(i).getName();
                createNoFlyZone(lat, lon);
            }
        }

        //Default camera position over ireland at the specified zoom level in the case that the users location is unavailable
        float zoomLevel1 = (float) 6.5;
        moveCamera(53.2734, -7.778320310000026, zoomLevel1);

        //Used for testing that markers are being placed correctly
        //createAirportMarker(53.988447, -6.381086, "Test Marker");

        //Set the listener to get the coordinates from the postion that the user clicked
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(final LatLng position) {
                //Clear the map of all markers
                mMap.clear();
                //Create a new blank location object and set its lat/lon coordinates
                loc = new Location("");
                loc.setLatitude(position.latitude);
                loc.setLongitude(position.longitude);

                //Return the schoolOk variable back to its default value so it can be used
                schoolOk = true;




                    //Use the sbMethod to build the request url to send to the API
                    StringBuilder sbValue = new StringBuilder(sbMethod(loc));
                    //Send the url to the placesTask method
                    plasesTask1(sbValue.toString());

                //Check the distance between the user and any of the airport positions in the list
                if (items != null && items.size() > 0) {
                    validDistance(loc, items);
                }

                //Place a marker on the map in the spot where the user clicked
                createMarker(position.latitude, position.longitude, "Your Chosen Site", "Please be sure to check rules before flying here", "");
               /* mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(position.latitude, position.longitude))
                        .anchor(0.5f, 0.5f)
                        .title("Your chosen site"));*/
            }
        });


        //Check if the intent is for individual flying site if it is then enter
        /*if (intent.getSerializableExtra("AllSites_dataProvider") == null && intent.getParcelableExtra("longLat_dataProvider") != null) {

            //Alert the user that they should check in with club officials before flying at any of the sites
            Toast.makeText(this, "Be sure to check-in with club officals before attending", Toast.LENGTH_LONG);

            //Create a lat/lng object from the club position taken from the intent
            ll = intent.getParcelableExtra("longLat_dataProvider");
            //Take the club name, address and website address from the intent and place them in variables
            final String iName = intent.getExtras().getString("name_dataProvider");
            final String iAddress = intent.getExtras().getString("address_dataProvider");
            final String iUrl = intent.getExtras().getString(("url_dataProvider"));

            //Overwrite the previous listener to use the location information taken in from the intent instead of the default
            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

                @Override
                public void onMapClick(final LatLng position) {
                    mMap.clear();
                    loc = new Location("");
                    loc.setLatitude(position.latitude);
                    loc.setLongitude(position.longitude);
                    schoolOk = true;


                    if (mLastLocation != null) {

                        StringBuilder sbValue = new StringBuilder(sbMethod(loc));
                        plasesTask1(sbValue.toString());
                    }


                    if (items != null && items.size() > 0) {
                        validDistance(loc, items);
                    }

                    mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(position.latitude, position.longitude))
                            .anchor(0.5f, 0.5f)
                            .title("Your chosen site"));
                    createMarker(ll.latitude, ll.longitude, iName, iAddress, iUrl);
                }
            });

            createMarker(ll.latitude, ll.longitude, iName, iAddress, iUrl);

            //Check if the intent is for all available flying site if it is then enter
        } else if (intent.getSerializableExtra("AllSites_dataProvider") != null && intent.getParcelableExtra("longLat_dataProvider") == null) {

            //Alert the user that they should check in with club officials before flying at any of the sites
            Toast.makeText(this, "Be sure to check-in with club officals before attending", Toast.LENGTH_LONG);
            //Populate the list with the list of clubs taken from the intent
            items1 = (ArrayList<Club>) intent.getSerializableExtra("AllSites_dataProvider");

            //Overwrite the previous listener to use the club location information taken in from the intent instead of the default and set all their markers on the map
            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

                @Override
                public void onMapClick(final LatLng position) {
                    mMap.clear();
                    loc = new Location("");
                    loc.setLatitude(position.latitude);
                    loc.setLongitude(position.longitude);
                    boolean valid = true;
                    schoolOk = true;

                    if (mLastLocation != null) {
                        StringBuilder sbValue = new StringBuilder(sbMethod(loc));
                        plasesTask1(sbValue.toString());
                    }


                    if (items != null && items.size() > 0) {
                        validDistance(loc, items);
                    }

                    mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(position.latitude, position.longitude))
                            .anchor(0.5f, 0.5f)
                            .title("Your chosen site"));

                    for (int i = 0; i < items1.size(); i++) {
                        double lat = items1.get(i).getLat();
                        double lon = items1.get(i).getLon();
                        String name = items1.get(i).getName();
                        String address = items1.get(i).getCounty();
                        createMarker(lat, lon, name, address, items1.get(i).getUrl());
                    }
                }
            });

            for (int i = 0; i < items1.size(); i++) {
                double lat = items1.get(i).getLat();
                double lon = items1.get(i).getLon();
                String name = items1.get(i).getName();
                String address = items1.get(i).getCounty();
                createMarker(lat, lon, name, address, items1.get(i).getUrl());
            }


        }*/


    }

    /*When an info window is clicked we extract the title of the club and cycle through the club list to find a match
    * when one is found we check if it has a url attached, if it does we send the user to the website, if not we alert
    * the user that the club does not own a website through a toast*/
    @Override
    public void onInfoWindowClick(com.google.android.gms.maps.model.Marker marker) {

        if (items1 != null) {
            if (items1.size() > 0) {
                for (int i = 0; i < items1.size(); i++) {
                    if (marker.getTitle().equals(items1.get(i).getName())) {
                        if (items1.get(i).getUrl() != null && !items1.get(i).getUrl().equals("")) {
                            String url = "http://" + items1.get(i).getUrl();
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


    /*This method creates a standard custom club marker, it takes in the latitude and longitude coordinates to place the marker in
    * the correct position on the map, it also takes in the name of the club, as well as that the clubs address is contained in the snippet variable
    * and if there is one present the url variable will contain the clubs website address*/
    protected void createMarker(final double latitude, final double longitude, final String title, final String snippet, final String url) {


        int height = 100;
        int width = 100;
        BitmapDrawable bitmapdraw = (BitmapDrawable) ContextCompat.getDrawable(getApplicationContext(), R.mipmap.ic_launcher2);
        Bitmap b = bitmapdraw.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);

        if (url != null) {
            com.google.android.gms.maps.model.Marker m = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(latitude, longitude))
                    .anchor(0.5f, 0.5f)
                    .title(title)
                    .snippet(snippet + " " + url)
                    .icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));

            mMap.setOnInfoWindowClickListener(this);

        } else {
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(latitude, longitude))
                    .anchor(0.5f, 0.5f)
                    .title(title)
                    .snippet(snippet)
                    .icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
        }

    }





    //Used in the early stages of the project to locate airports more easily by placing markers on the map
    protected void createAirportMarker(double latitude, double longitude, String title) {
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .anchor(0.5f, 0.5f)
                .title(title)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))

        );
    }

    //Creates circles on the map that users cannot enter without being alerted to, these circles are invisible to the user
    protected void createNoFlyZone(double latitude, double longitude) {
        CircleOptions circleOptions = new CircleOptions()
                .center(new LatLng(latitude, longitude))
                .radius(5000)
                .visible(false);
        //.fillColor(Color.RED);
        Circle circle = mMap.addCircle(circleOptions);
    }


    protected void createPositionMarker(final Location location) {
        // Add users location to the map
        userposition = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(location.getLatitude(), location.getLongitude()))
                .anchor(0.5f, 0.5f)
                .title("Your Position")
        );

    }

    //When the user is connected to googles location services API we try and get the users coordinates
    @Override
    public void onConnected(Bundle bundle) {

        //Check if the proper permissions where granted to the application
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        //Get user location
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);



        //If the location if not empty then retrieve regular updates on the users location
        if (mLastLocation != null) {
            Log.wtf("Fly-Safe Location", mLastLocation.getLatitude() + " " + mLastLocation.getLongitude());
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

        }


        //Create a new intent from the data passed to this activity through an intent
        Intent intent = getIntent();

        //If the intent was not for locating clubs and just for validating the users position or a particular site of te users choosing
        if (intent.getParcelableExtra("longLat_dataProvider") == null && intent.getSerializableExtra("AllSites_dataProvider") == null) {

            if (mLastLocation != null) {
                StringBuilder sbValue = new StringBuilder(sbMethod(mLastLocation));
                plasesTask(sbValue.toString());
            }


            //Check if we have the users location
            if (mLastLocation != null) {

                // Add users location to the map
                createPositionMarker(mLastLocation);

                //Re-validate that the user is in a safe flying zone
                if (items != null) {
                    validDistance(mLastLocation, items);
                }
            }
        }

       /* //If the users location was gotten successfully move the camera to their postion on the map otherwise set it to the default position
        if (userLat != 0.0 && userLon != 0.0) {
            moveCamera(userLat, userLon, (float) 13.5);
        } else {
            float zoomLevel = (float) 6.5;
            moveCamera(53.2734, -7.778320310000026, zoomLevel);
        }*/

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    //If the users location changes then clear the map of markers and set the users new position
    @Override
    public void onLocationChanged(Location location) {
        //mMap.clear();
        userposition.remove();
        createPositionMarker(location);

        //Check if the user has entered a dangerous area after location update
       /* boolean isValid = validateAirportDistance(location, items);
        if(!isValid)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("You have just entered a dangerous flying area, please don't fly at this location")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }*/

    }

    //This method requests updates of the users position every 1-10 seconds
    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();

        mLocationRequest.setInterval(10 * 1000);
        mLocationRequest.setFastestInterval(1 * 1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    /*This method is used to move the camera position to the coordinates taken in by the method,
     * it sets the zoom level to the zoom level taken in */
    public void moveCamera(final double lat, final double lon, final float zoom) {

        LatLng latLng = new LatLng(lat, lon);
        float zoomLevel = (float) zoom; //This goes up to 21
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel));

    }

    public boolean validateAirportDistance(final Location location, final ArrayList<AirportMarker> locations) {

        //Check the location postion against all airport entries in the list
        boolean valid = true;
        for (int i = 0; i < locations.size(); i++) {

            Location b = new Location("");
            b.setLatitude(locations.get(i).getLat());
            b.setLongitude(locations.get(i).getLon());

            float distance = location.distanceTo(b);


            if (distance <= 5000) {
                valid = false;
                break;
            }
        }

        return valid;
    }

    public boolean validateDangersDistance(final Location location)
    {
        boolean valid = true;

        return valid;
    }

    //This method is used to validate the distance between the location coordinates taken in and airports in the area or other structures which are deemed unsafe to fly around
    public boolean validDistance(final Location location, final ArrayList<AirportMarker> locations) {


        //Check the location postion against all airport entries in the list
        boolean valid = true;
        for (int i = 0; i < locations.size(); i++) {

            Location b = new Location("");
            b.setLatitude(locations.get(i).getLat());
            b.setLongitude(locations.get(i).getLon());

            float distance = location.distanceTo(b);


            if (distance <= 5000) {
                valid = false;
                break;
            }
        }

        //If the location that was passed to the method is not near any airports or other areas which are unsafe to fly then enter
        if (valid && schoolOk) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("This site seems fine but make sure to check rules and regulations checklist to properly assess this site!")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();

            mMap.clear();
            if (mLastLocation != null) {
                createPositionMarker(mLastLocation);
            }
            //getPolygon();

        } else
        //If either fails then we can alert the user to this fact and suggest other areas to try
        {
            schoolOk = true;

            //Alert the user that the area is unsafe for flying
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Here is some suggestions to try but always make sure you have the permission of the land owners first and assess this site against the rules!")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();

            //Alert the user that we have placed suggested areas to try on the map
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage("Sorry this is not a safe flying zone, please try another location!")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
            AlertDialog alert1 = builder1.create();
            alert1.show();

            //Populate the list with all the locations that are within the geofence and a safe distance from any airports
            ArrayList<Location> loc = FindValidLocations(location, locations);
            //Shuffle the list to make the entries random
            Collections.shuffle(loc);
            //Clear the map of any markers already present
            mMap.clear();

            //If the users location is not null then place a marker in their position and zoom out from their location so that the suggested flying sites are visible at a glance
            if (mLastLocation != null) {
                moveCamera(mLastLocation.getLatitude(), mLastLocation.getLongitude(), (float) 11.5);
                createPositionMarker(mLastLocation);

            }
            else
            {
                moveCamera(location.getLatitude(), location.getLongitude(), (float) 11.5);
            }


            //Take the first 6 entries of the list of available sites and create markers for each and place them on the map
            for (int i = 0; i < 6; i++) {
                createMarker(loc.get(i).getLatitude(), loc.get(i).getLongitude(), "Potential Flying Site", "Please be sure to check rules before flying here", "");
            }
        }

        //Return true or false depending if the site is safe or not
        return valid;
    }

    //Used to only look to validate distance from airports only and returns the result
    public boolean validDistance1(final Location location, final ArrayList<AirportMarker> locations) {


        boolean valid = true;
        for (int i = 0; i < locations.size(); i++) {

            Location b = new Location("");
            b.setLatitude(locations.get(i).getLat());
            b.setLongitude(locations.get(i).getLon());

            float distance = location.distanceTo(b);
            valid = true;


            if (distance <= 5000) {
                valid = false;
                break;
            }

        }

        return valid;
    }

    //This method is used to find all preliminary valid positions which can be concidered safe flying areas and returns them in a list of locations
    public ArrayList<Location> FindValidLocations(Location location, ArrayList<AirportMarker> locations) {

        //Arraylist to store the preliminary safe flying positions
        ArrayList<Location> tempLocations = new ArrayList<Location>();
        //ArrayList to store the final safe flying locations
        ArrayList<Location> validLocations = new ArrayList<Location>();
        //Get all points around a user, 360 points around user, 1 for every degree
        ArrayList<Location> boundry = getBoundryPoints(location);


        //Cycle through all the boundry points and check if they are near a airport, if not add them to the list
        for (int j = 0; j < boundry.size(); j++) {
            Location l = new Location("");
            l.setLatitude(boundry.get(j).getLatitude());
            l.setLongitude(boundry.get(j).getLongitude());
            boolean valid = validDistance1(l, locations);
            if (valid) {
                tempLocations.add(l);
            }
        }

        //Cycle through the preliminary results and only add those sites which are not near prohibited structures
        //Make i< 30 to reduce results and speed up processing time
        for(int i = 0; i< 30; i++)
        {
            final Location location1 = new Location("");
            location1.setLatitude(tempLocations.get(i).getLatitude());
            location1.setLongitude(tempLocations.get(i).getLongitude());
            Runnable run = new Runnable() {
                @Override
                public void run() {
                    StringBuilder sbValue = new StringBuilder(sbMethod(location1));
                    plasesTask(sbValue.toString());
                }
            };
            Thread t = new Thread(run);
            t.start();

            synchronized (t) {
                try {
                    t.wait(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if(schoolOk)
            {
                validLocations.add(tempLocations.get(i));
            }
        }

        //Return a list of all the valid locations
        return validLocations;


        //Return a list of all the preliminary valid locations
        //return tempLocations;
    }

    private final double EARTH_RADIUS_NM = 3437.670013352;

    //This method is used to make a circle of markers around the user, 1 for each degree of the circle around the user
    public ArrayList<Location> getBoundryPoints(Location location) {

        ArrayList<Location> boundry = new ArrayList<Location>();
        //Get the locations lat/lon coordinates
        double lat = location.getLatitude();
        double lon = location.getLongitude();

        //Used to get the lat/lon coordinates of the points on the circle while taking into account the curvature of the earth
        double d2r = Math.PI / 180;
        double circleLat = (3.106856 / 3963.189) / d2r;
        double circleLng = circleLat / Math.cos(lat * d2r);

        //Cycle through the 360 degrees of the circle creating a location object from the new lat/lon coordinates and adding it to the list
        for (int x = 0; x <= 360; x++) {
            double theta = x * d2r;
            double nLat = lat + (circleLat * Math.sin(theta));
            double nLon = lon + (circleLng * Math.cos(theta));

            Location l = new Location("");
            l.setLatitude(nLat);
            l.setLongitude(nLon);

            boundry.add(l);
        }

        ArrayList<Location> nBoundry = new ArrayList<Location>();

        //Get all the points in the geofence that is placed around Ireland's coastline
        ArrayList<LatLng> pPoint = (ArrayList) getPolyPoints();

        //For each position in the boundry list we check if it is within the geofence we add it to the new list of valid sites
        for (int i = 0; i < boundry.size(); i++) {
            LatLng ll = new LatLng(boundry.get(i).getLatitude(), boundry.get(i).getLongitude());
            if (isPointInPolygon(ll, pPoint)) {
                nBoundry.add(boundry.get(i));
            }
        }

        return nBoundry;
    }


    //Converts an angle measured in degrees into one measured in radians(double)
    public double ConvertToRadians(double angle) {
        return (angle * Math.PI) / 180;
    }

    //Places all points in the geofence on the map, this was used in early stages of development to view the geofence to see its coverage
    public void getPolygon() {

        //Get the list of geofence coordinates
        List<LatLng> newPolygon = getPolyPoints();

        Polygon p = mMap.addPolygon(new PolygonOptions()
                .addAll(newPolygon)
                .strokeColor(Color.RED)
                .fillColor(Color.BLUE));
    }

    //Check if the given position is within the geofence, if it is then the return true or false dependent on if the algorithm result is odd or even.
    private boolean isPointInPolygon(LatLng tap, ArrayList<LatLng> vertices) {
        int intersectCount = 0;
        for (int j = 0; j < vertices.size() - 1; j++) {
            if (rayCastIntersect(tap, vertices.get(j), vertices.get(j + 1))) {
                intersectCount++;
            }
        }

        return ((intersectCount % 2) == 1); // odd = inside, even = outside;
    }

    //Check if the position is within the polygon intersection and return the result
    private boolean rayCastIntersect(LatLng tap, LatLng vertA, LatLng vertB) {

        double aY = vertA.latitude;
        double bY = vertB.latitude;
        double aX = vertA.longitude;
        double bX = vertB.longitude;
        double pY = tap.latitude;
        double pX = tap.longitude;

        if ((aY > pY && bY > pY) || (aY < pY && bY < pY)
                || (aX < pX && bX < pX)) {
            return false; // a and b can't both be above or below pt.y, and a or
            // b must be east of pt.x
        }

        double m = (aY - bY) / (aX - bX); // Rise over run
        double bee = (-aX) * m + aY; // y = mx + b
        double x = (pY - bee) / m;

        return x > pX;
    }

    //List of points around irelands coastline to stop markers from being placed in the sea
    public List<LatLng> getPolyPoints() {
        List<LatLng> newPolygon = new ArrayList<>();
        //Rough outline of ireland
        /*newPolygon.add(new LatLng(51.464887, -9.730879));
        newPolygon.add(new LatLng(52.221877, -6.367972));
        newPolygon.add(new LatLng(54.018836, -6.371809));
        newPolygon.add(new LatLng(54.390726, -5.498396));
        newPolygon.add(new LatLng(55.213917, -6.146589));
        newPolygon.add(new LatLng(55.276546, -7.673689));
        newPolygon.add(new LatLng(54.671223, -8.783308));
        newPolygon.add(new LatLng(54.632364, -8.117503));
        newPolygon.add(new LatLng(54.284240, -8.912397));
        newPolygon.add(new LatLng(54.341921, -9.805037));
        newPolygon.add(new LatLng(53.318336, -9.881941));
        newPolygon.add(new LatLng(53.273180, -8.910733));
        newPolygon.add(new LatLng(51.898894, -10.396918));
        newPolygon.add(new LatLng(51.438062, -9.500841));*/

        //More accurate outline points for Irelands Coastline
        //0-20
        newPolygon.add(new LatLng(54.029324, -6.366316));
        newPolygon.add(new LatLng(53.997853, -6.102644));
        newPolygon.add(new LatLng(54.089777, -6.259199));
        newPolygon.add(new LatLng(54.025290, -6.055952));
        newPolygon.add(new LatLng(54.121983, -5.891157));
        newPolygon.add(new LatLng(54.202388, -5.888411));
        newPolygon.add(new LatLng(54.240927, -5.830733));
        newPolygon.add(new LatLng(54.250556, -5.613753));
        newPolygon.add(new LatLng(54.485768, -5.437971));
        newPolygon.add(new LatLng(54.675193, -5.583540));
        newPolygon.add(new LatLng(54.613210, -5.902144));
        newPolygon.add(new LatLng(54.764822, -5.694777));
        newPolygon.add(new LatLng(54.978965, -5.984541));
        newPolygon.add(new LatLng(55.056746, -5.977953));
        newPolygon.add(new LatLng(55.059271, -6.061445));
        newPolygon.add(new LatLng(55.166096, -6.039473));
        newPolygon.add(new LatLng(55.198300, -6.060361));
        newPolygon.add(new LatLng(55.224940, -6.140012));
        newPolygon.add(new LatLng(55.204962, -6.234083));
        newPolygon.add(new LatLng(55.230423, -6.295194));

        //21-40
        newPolygon.add(new LatLng(55.229639, -6.310300));
        newPolygon.add(new LatLng(55.239036, -6.332960));
        newPolygon.add(new LatLng(55.248235, -6.471662));
        newPolygon.add(new LatLng(55.186923, -6.715300));
        newPolygon.add(new LatLng(55.190059, -6.962493));
        newPolygon.add(new LatLng(55.048697, -7.066863));
        newPolygon.add(new LatLng(55.056564, -7.250884));
        newPolygon.add(new LatLng(55.150842, -7.154753));
        newPolygon.add(new LatLng(55.150842, -7.154753));
        newPolygon.add(new LatLng(55.237068, -6.924040));
        newPolygon.add(new LatLng(55.381664, -7.374480));
        newPolygon.add(new LatLng(55.312949, -7.363494));
        newPolygon.add(new LatLng(55.275418, -7.517302));
        newPolygon.add(new LatLng(55.197701, -7.547859));
        newPolygon.add(new LatLng(55.122385, -7.457222));
        newPolygon.add(new LatLng(54.949261, -7.692140));
        newPolygon.add(new LatLng(55.035529, -7.558930));
        newPolygon.add(new LatLng(55.054019, -7.578843));
        newPolygon.add(new LatLng(55.038677, -7.648194));
        newPolygon.add(new LatLng(55.132212, -7.521165));

        //41-60
        newPolygon.add(new LatLng(55.202415, -7.625535));
        newPolygon.add(new LatLng(55.231010, -7.604249));
        newPolygon.add(new LatLng(55.280318, -7.644761));
        newPolygon.add(new LatLng(55.208293, -8.007310));
        newPolygon.add(new LatLng(55.151834, -8.281960));
        newPolygon.add(new LatLng(54.988282, -8.457749));
        newPolygon.add(new LatLng(54.868340, -8.314927));
        newPolygon.add(new LatLng(54.824060, -8.545640));
        newPolygon.add(new LatLng(54.754380, -8.457749));
        newPolygon.add(new LatLng(54.754380, -8.688462));
        newPolygon.add(new LatLng(54.665522, -8.776353));
        newPolygon.add(new LatLng(54.640099, -8.128160));
        newPolygon.add(new LatLng(54.352985, -8.666490));
        newPolygon.add(new LatLng(54.288908, -9.029039));
        newPolygon.add(new LatLng(54.177905, -9.146544));
        newPolygon.add(new LatLng(54.319122, -9.336058));
        newPolygon.add(new LatLng(54.315918, -9.857909));
        newPolygon.add(new LatLng(54.068450, -9.962279));
        newPolygon.add(new LatLng(53.978094, -10.242430));
        newPolygon.add(new LatLng(53.881067, -9.577757));

        //61-80
        newPolygon.add(new LatLng(53.783813, -9.572264));
        newPolygon.add(new LatLng(53.757841, -9.896361));
        newPolygon.add(new LatLng(53.608188, -9.874388));
        newPolygon.add(new LatLng(53.549483, -10.171019));
        newPolygon.add(new LatLng(53.444917, -10.061156));
        newPolygon.add(new LatLng(53.274448, -8.951537));
        newPolygon.add(new LatLng(53.149442, -8.973509));
        newPolygon.add(new LatLng(53.149442, -9.259154));
        newPolygon.add(new LatLng(52.732337, -9.511840));
        newPolygon.add(new LatLng(52.559024, -9.918334));
        newPolygon.add(new LatLng(52.645766, -9.522826));
        newPolygon.add(new LatLng(52.665759, -9.116332));
        newPolygon.add(new LatLng(52.765587, -8.995482));
        newPolygon.add(new LatLng(52.675752, -8.704344));
        newPolygon.add(new LatLng(52.559024, -9.660155));
        newPolygon.add(new LatLng(52.485493, -9.682128));
        newPolygon.add(new LatLng(52.411839, -9.934813));
        newPolygon.add(new LatLng(52.385025, -9.830443));
        newPolygon.add(new LatLng(52.277606, -9.863402));
        newPolygon.add(new LatLng(52.183400, -10.462157));

        //81-100
        newPolygon.add(new LatLng(52.099119, -10.451171));
        newPolygon.add(new LatLng(52.159817, -9.781005));
        newPolygon.add(new LatLng(51.882632, -10.423705));
        newPolygon.add(new LatLng(51.794385, -10.341307));
        newPolygon.add(new LatLng(51.845318, -10.212218));
        newPolygon.add(new LatLng(51.790987, -10.176512));
        newPolygon.add(new LatLng(51.779094, -10.220458));
        newPolygon.add(new LatLng(51.756998, -10.081755));
        newPolygon.add(new LatLng(51.885175, -9.551665));
        newPolygon.add(new LatLng(51.581545, -10.232817));
        newPolygon.add(new LatLng(51.742544, -9.550292));
        newPolygon.add(new LatLng(51.686387, -9.448668));
        newPolygon.add(new LatLng(51.541419, -9.848296));
        newPolygon.add(new LatLng(51.615666, -9.531066));
        newPolygon.add(new LatLng(51.479881, -9.829070));
        newPolygon.add(new LatLng(51.548251, -9.408843));
        newPolygon.add(new LatLng(51.469616, -9.411589));
        newPolygon.add(new LatLng(51.567035, -9.007842));
        newPolygon.add(new LatLng(51.531168, -8.950164));
        newPolygon.add(new LatLng(51.604580, -8.865019));

        //101-120
        newPolygon.add(new LatLng(51.577278, -8.708464));
        newPolygon.add(new LatLng(51.654023, -8.670012));
        newPolygon.add(new LatLng(51.647311, -8.586119));
        newPolygon.add(new LatLng(51.742649, -8.300475));
        newPolygon.add(new LatLng(51.852209, -8.279875));
        newPolygon.add(new LatLng(51.860691, -8.171385));
        newPolygon.add(new LatLng(51.815718, -8.262709));
        newPolygon.add(new LatLng(51.785145, -8.174132));
        newPolygon.add(new LatLng(51.826329, -8.000411));
        newPolygon.add(new LatLng(51.856450, -8.004531));
        newPolygon.add(new LatLng(51.883160, -7.865142));
        newPolygon.add(new LatLng(51.903500, -7.922820));
        newPolygon.add(new LatLng(51.947115, -7.841109));
        newPolygon.add(new LatLng(51.979693, -7.858275));
        newPolygon.add(new LatLng(51.953251, -7.722319));
        newPolygon.add(new LatLng(51.975886, -7.693824));
        newPolygon.add(new LatLng(51.994493, -7.585334));
        newPolygon.add(new LatLng(52.039709, -7.568854));
        newPolygon.add(new LatLng(52.054067, -7.537268));
        newPolygon.add(new LatLng(52.064199, -7.638892));

        //121-140
        newPolygon.add(new LatLng(52.099644, -7.579154));
        newPolygon.add(new LatLng(52.103862, -7.498130));
        newPolygon.add(new LatLng(52.130847, -7.428092));
        newPolygon.add(new LatLng(52.138433, -7.171286));
        newPolygon.add(new LatLng(52.170029, -7.130774));
        newPolygon.add(new LatLng(52.159921, -7.073096));
        newPolygon.add(new LatLng(52.127896, -7.108458));
        newPolygon.add(new LatLng(52.137801, -7.050093));
        newPolygon.add(new LatLng(52.146018, -6.989669));
        newPolygon.add(new LatLng(52.155498, -6.993102));
        newPolygon.add(new LatLng(52.241775, -6.957740));
        newPolygon.add(new LatLng(52.197184, -6.902808));
        newPolygon.add(new LatLng(52.152549, -6.891822));
        newPolygon.add(new LatLng(52.260271, -6.766852));
        newPolygon.add(new LatLng(52.247620, -6.753788));
        newPolygon.add(new LatLng(52.169357, -6.594486));
        newPolygon.add(new LatLng(52.238370, -6.315708));
        newPolygon.add(new LatLng(52.329942, -6.450290));
        newPolygon.add(new LatLng(52.363498, -6.418705));
        newPolygon.add(new LatLng(52.359305, -6.391239));

        //141-160
        newPolygon.add(new LatLng(52.564304, -6.192112));
        newPolygon.add(new LatLng(52.630203, -6.229190));
        newPolygon.add(new LatLng(52.966481, -5.998478));
        newPolygon.add(new LatLng(52.988807, -6.046543));
        newPolygon.add(new LatLng(53.069746, -6.032810));
        newPolygon.add(new LatLng(53.129114, -6.046543));
        newPolygon.add(new LatLng(53.263216, -6.108341));
        newPolygon.add(new LatLng(53.270608, -6.089115));
        newPolygon.add(new LatLng(53.287030, -6.109714));
        newPolygon.add(new LatLng(53.337079, -6.214084));
        newPolygon.add(new LatLng(53.387070, -6.126194));
        newPolygon.add(new LatLng(53.561168, -6.083622));
        newPolygon.add(new LatLng(53.657310, -6.227817));
        newPolygon.add(new LatLng(53.729678, -6.251163));
        newPolygon.add(new LatLng(53.809222, -6.262149));
        newPolygon.add(new LatLng(53.862706, -6.251163));
        newPolygon.add(new LatLng(53.891042, -6.361026));
        newPolygon.add(new LatLng(53.956545, -6.378879));
        newPolygon.add(new LatLng(53.957353, -6.366520));
        newPolygon.add(new LatLng(53.979971, -6.367893));

        //161-168
        newPolygon.add(new LatLng(53.997853, -6.345717));
        newPolygon.add(new LatLng(54.002293, -6.348493));
        newPolygon.add(new LatLng(54.004714, -6.345030));
        newPolygon.add(new LatLng(54.008750, -6.349150));
        newPolygon.add(new LatLng(54.009153, -6.391035));
        newPolygon.add(new LatLng(54.013995, -6.389319));
        newPolygon.add(new LatLng(54.015811, -6.360480));
        newPolygon.add(new LatLng(54.029585, -6.371233));

        return newPolygon;

    }

    //Moving into server

    //This method takes in a location and uses it to build a StringBuilder objects which represents a request to Google Places API
    public StringBuilder sbMethod(Location currentLocation) {
        //current location
        double mLatitude = currentLocation.getLatitude();
        double mLongitude = currentLocation.getLongitude();

        StringBuilder sb = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        sb.append("location=" + mLatitude + "," + mLongitude);
        sb.append("&radius=500");
        sb.append("&types=" + "school");
        sb.append("&sensor=true");

        sb.append("&key=AIzaSyCRLa4LQZWNQBcjCYcIVYA45i9i8zfClqc");

        Log.d("Map", "<><>api: " + sb.toString());

        return sb;
    }
//End going to server

    /*Used specifically for when the users location is available, it requests data from Google Places API to find any
     * structures around the the users location coordinates, the place type is specified in the parameters,
     * it then passes the data that is returned to the parserTask method to parse the JSON response from the API */
    private void plasesTask(final String... url) {
        Runnable run = new Runnable() {
            @Override
            public void run() {
                try {
                    //Get the data from Googles Places API
                    String data = downloadUrl(url[0]);
                    temp = data;
                } catch (Exception e) {
                    Log.d("Background Task", e.toString());
                }
            }
        };
        Thread t = new Thread(run);
        t.start();

        //Wait half a second for the response to be returned
        synchronized (t) {
            try {
                t.wait(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //Parse the response
        parserTask(temp);
    }


    /*Used specifically for when the users location is unavailable, it requests data from Google Places API to find any
    * structures around the the given location coordinates, the place type is specified in the parameters,
    * it then passes the data that is returned to the parserTask method to parse the JSON response from the API */
    private void plasesTask1(final String... url) {
        Runnable run = new Runnable() {
            @Override
            public void run() {
                try {
                    //Get the data from Googles Places API
                    String data = downloadUrl(url[0]);
                    temp = data;
                } catch (Exception e) {
                    Log.d("Background Task", e.toString());
                }
            }
        };
        Thread t = new Thread(run);
        t.start();

        //Wait half a second for the response to be returned
        synchronized (t) {
            try {
                t.wait(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //Parse the response
        parserTask1(temp);
    }

    //Request information from Googles Places API using the passed Request String
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            //Read the incoming stream into the bufferedReader
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            //While the bufferedReader is not empty keep adding lines to the StringBuffer
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            //Cast the StringBuffer data into String
            data = sb.toString();

            //Close the reader
            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }

        //Return the data
        return data;
    }

    /* Used specifically when the users location is available, this method takes the JSON response from Googles Places API and places it in
    * JSON objects which are then placed in a hashmap, it then uses these locations to check their distance from the user, if any structure is too close
    * the method sets the schoolOk variable as false which alerts the user that the area is unsafe*/
    private void parserTask(final String... jsonData) {


        Runnable run = new Runnable() {
            @Override
            public void run() {
                try {
                    //Place JSON response in an object
                    jObject = new JSONObject(jsonData[0]);

                    //Place object in the hashmap
                    googlePlaces = placeJson.parse(jObject);


                } catch (Exception e) {
                    Log.d("Exception", e.toString());
                }
            }
        };
        Thread t = new Thread(run);
        t.start();
        //Wait half a second for the response
        synchronized (t) {
            try {
                t.wait(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        //if the response was not null then cycle through the list checking that it is a valid distance from the user
        if (googlePlaces != null) {
            for (int i = 0; i < googlePlaces.size(); i++) {
                double d1 = Double.parseDouble(googlePlaces.get(i).get("lat"));
                double d2 = Double.parseDouble(googlePlaces.get(i).get("lng"));
                Location loc = new Location("");
                loc.setLatitude(d1);
                loc.setLongitude(d2);
                float distance = loc.distanceTo(mLastLocation);


                //If not the respective variable is changed and user is alerted
                if (distance <= 500) {
                    schoolOk = false;
                    break;
                }

            }
        }
    }

    /* Used specifically when the users location is unavailable, this method takes the JSON response from Googles Places API and places it in
   * JSON objects which are then placed in a hashmap, it then uses these locations to check their distance from the location passed to the method, if any structure is too close
   * the method sets the schoolOk variable as false which alerts the user that the area is unsafe*/
    private void parserTask1(final String... jsonData) {


        Runnable run = new Runnable() {
            @Override
            public void run() {
                try {
                    //Place JSON response in an object
                    jObject = new JSONObject(jsonData[0]);

                    //Place object in the hashmap
                    googlePlaces = placeJson.parse(jObject);


                } catch (Exception e) {
                    Log.d("Exception", e.toString());
                }
            }
        };
        Thread t = new Thread(run);
        t.start();

        //Wait half a second for the response
        synchronized (t) {
            try {
                t.wait(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //If the response was not null
        if(googlePlaces != null) {
            //if the response was not null then cycle through the list checking that it is a valid distance from the user
            for (int i = 0; i < googlePlaces.size(); i++) {
                double d1 = Double.parseDouble(googlePlaces.get(i).get("lat"));
                double d2 = Double.parseDouble(googlePlaces.get(i).get("lng"));
                Location location = new Location("");
                location.setLatitude(d1);
                location.setLongitude(d2);
                float distance = location.distanceTo(this.loc);

                //If not the respective variable is changed and user is alerted
                if (distance <= 500) {
                    schoolOk = false;
                    break;
                }
            }
        }
    }


    public class Place_JSON {

        /**
         * Receives a JSONObject and returns a list
         */
        public List<HashMap<String, String>> parse(JSONObject jObject) {

            JSONArray jPlaces = null;
            try {
                /** Retrieves all the elements in the 'places' array */
                jPlaces = jObject.getJSONArray("results");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            /** Invoking getPlaces with the array of json object
             * where each json object represent a place
             */
            return getPlaces(jPlaces);
        }

        private List<HashMap<String, String>> getPlaces(JSONArray jPlaces) {
            int placesCount = jPlaces.length();
            List<HashMap<String, String>> placesList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> place = null;

            /** Taking each place, parses and adds to list object */
            for (int i = 0; i < placesCount; i++) {
                try {
                    /** Call getPlace with place JSON object to parse the place */
                    place = getPlace((JSONObject) jPlaces.get(i));
                    placesList.add(place);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return placesList;
        }

        /**
         * Parsing the Place JSON object
         */
        private HashMap<String, String> getPlace(JSONObject jPlace) {

            HashMap<String, String> place = new HashMap<String, String>();
            String placeName = "-NA-";
            String vicinity = "-NA-";
            String latitude = "";
            String longitude = "";
            String reference = "";

            try {
                // Extracting Place name, if available
                if (!jPlace.isNull("name")) {
                    placeName = jPlace.getString("name");
                }

                // Extracting Place Vicinity, if available
                if (!jPlace.isNull("vicinity")) {
                    vicinity = jPlace.getString("vicinity");
                }

                latitude = jPlace.getJSONObject("geometry").getJSONObject("location").getString("lat");
                longitude = jPlace.getJSONObject("geometry").getJSONObject("location").getString("lng");
                reference = jPlace.getString("reference");

                place.put("place_name", placeName);
                place.put("vicinity", vicinity);
                place.put("lat", latitude);
                place.put("lng", longitude);
                place.put("reference", reference);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return place;
        }
    }


}

