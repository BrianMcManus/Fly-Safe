package com.example.flyingsitevalidator3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ClubsAndSites extends AppCompatActivity {

    private ArrayList<Club> clubs = new ArrayList<Club>();
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mDatabase;
    private String mUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clubs_and_sites);

        // Initialize Firebase Auth and get the user details
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        //Get reference to the database
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //Check if the user is logged in
        if (mFirebaseUser == null) {
            // Not logged in, launch the Log In activity
            loadLogInView();
        } else {
            //Get the user id from the firebase user reference
            mUserId = mFirebaseUser.getUid();

            // Set up ListView
            final ListView listView = (ListView) findViewById(R.id.listView);
            final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1);
            listView.setAdapter(adapter);
            adapter.add("All Sites");

            // Use Firebase to populate the list of clubs and sites.
            mDatabase.child("FlyingSites").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    if (dataSnapshot.child("name").getValue() != null) {

                        //Add the name of the club to the list
                        adapter.add((String) dataSnapshot.child("name").getValue());

                        //Create a Club object and populate its fields
                        Club club = new Club();
                        club.setName((String) dataSnapshot.child("name").getValue());
                        club.setAddress((String) dataSnapshot.child("address").getValue());

                        try {
                            double nLat = (double) dataSnapshot.child("lat").getValue();
                            club.setLat(nLat);
                        } catch (NumberFormatException e) {
                            System.out.println((String) dataSnapshot.child("lat").getValue());
                        }

                        String nLon = (String) dataSnapshot.child("long").getValue();
                        double dLon = Double.parseDouble(nLon);
                        club.setLon(dLon);

                        String url = (String) dataSnapshot.child("url").getValue();
                        club.setUrl(url);

                        clubs.add(club);
                    }
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child("name").getValue() != null) {
                        adapter.remove((String) dataSnapshot.child("name").getValue());
                    }
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


            //Set the click listener for the club list
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    //Get the name of the club from the list
                    String selectedFromList = (String) parent.getItemAtPosition(position);

                    //check if the user chose the "all sites" option and if so send all the clubs info in the intent
                    if (selectedFromList.equals("All Sites")) {

                        //Start the map activity and send the all club information in the intent
                        Intent intent = new Intent(ClubsAndSites.this, MapsActivity.class);
                        intent.putExtra("AllSites_dataProvider", clubs);
                        startActivity(intent);
                    } else {
                        //Check the users chosen club against the list of clubs and send the relevant information in the intent
                        for (int i = 0; i < clubs.size(); i++) {

                            if (clubs.get(i).getName().equals(selectedFromList)) {

                                LatLng fromPosition = new LatLng(clubs.get(i).getLat(), clubs.get(i).getLon());
                                String name = clubs.get(i).getName();
                                String address = clubs.get(i).getAddress();
                                String url = clubs.get(i).getUrl();
                                Bundle args = new Bundle();
                                args.putParcelable("longLat_dataProvider", fromPosition);
                                Intent intent = new Intent(ClubsAndSites.this, MapsActivity.class);
                                intent.putExtra("name_dataProvider", name);
                                intent.putExtra(("address_dataProvider"), address);
                                intent.putExtra("url_dataProvider", url);
                                intent.putExtras(args);
                                startActivity(intent);

                            }
                        }
                    }
                }
            });


        }
    }

    //Create options menu for logout function
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds clubs to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //Created when the option menu item is selected
    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        // Handle action bar item clicks here.
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            mFirebaseAuth.signOut();
            loadLogInView();
        }

        return super.onOptionsItemSelected(item);
    }

    //Load the login view if the user is not logged in
    private void loadLogInView() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
