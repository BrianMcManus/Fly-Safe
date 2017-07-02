package com.example.flyingsitevalidator3;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by Brian on 04/03/2017.
 */

public class ModelInfo extends AppCompatActivity {

    private ArrayList<Club> clubs = new ArrayList<Club>();
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mDatabase;
    private String mUserId;
    private Context context = this;


    @Override
    public void onCreate(Bundle savedInstaneState) {

        super.onCreate(savedInstaneState);
        this.setContentView(R.layout.model_info_view);

        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        //Get reference to the database
        mDatabase = FirebaseDatabase.getInstance().getReference();


        //Check if the user is logged in
        if (mFirebaseUser == null) {
            // Not logged in, launch the Log In activity
            loadLogInView();
        } else {
            //Get the id of the user
            mUserId = mFirebaseUser.getUid();

            //Get the data that was sent in the intent and place it in a new intent then use it to populate Model variable
            Intent intent = getIntent();
            final Model m = (Model) intent.getSerializableExtra("Model");

            //Get the title label view and set the text
            TextView nameTitleTv = (TextView) findViewById(R.id.name_title_label);
            nameTitleTv.setText("Model Name");
            //Set the name text field view and populate it with the models name
            TextView nameTv = (TextView) findViewById(R.id.name_label);
            nameTv.setText(m.getName());

            //Get the title label view and set the text
            TextView typeTitleTv = (TextView) findViewById(R.id.type_title_label);
            typeTitleTv.setText("Model Type");
            //Set the model type text field view and populate it with the models type
            TextView typeTv = (TextView) findViewById(R.id.type_label);
            typeTv.setText(m.getModelType());

            //Get the title label view and set the text
            TextView fuelTitleTv = (TextView) findViewById(R.id.fuel_title_label);
            fuelTitleTv.setText("Model Fuel Type");
            //Set the model fuel type text field view and populate it with the models fuel type
            TextView fuelTv = (TextView) findViewById(R.id.fuel_label);
            fuelTv.setText(m.getFuelType());

            //Get the title label view and set the text
            TextView lengthTitleTv = (TextView) findViewById(R.id.length_title_label);
            lengthTitleTv.setText("Model Length");
            //Set the model length text field view and populate it with the models length
            TextView lengthTv = (TextView) findViewById(R.id.length_label);
            lengthTv.setText(m.getLength() + " cm");

            //Get the title label view and set the text
            TextView widthTitleTv = (TextView) findViewById(R.id.width_title_label);
            widthTitleTv.setText("Model Width");
            //Set the model width text field view and populate it with the models width
            TextView widthTv = (TextView) findViewById(R.id.width_label);
            widthTv.setText(m.getWidth() + " cm");

            //Get the title label view and set the text
            TextView registrationTitleTv = (TextView) findViewById(R.id.registration_title_label);
            registrationTitleTv.setText("Model Registration Number");
            //Set the model registrationId text field view and if they are registered populate it with the models registration number
            TextView registrationTv = (TextView) findViewById(R.id.registration_label);
            if (m.getRegistrationId() != -1 && m.getRegistrationId() != 0) {
                registrationTv.setText(m.getRegistrationId() + "");
            } else {
                //If its not registered set the id as not registered
                registrationTv.setText("Aircraft is not registered!!!");
            }


            // Use Firebase to populate the list.
            mDatabase.child("FlyingSites").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    if (dataSnapshot.child("name").getValue() != null) {

                        //Create a new Club object and populate it with data from the database and add it to the list
                        Club club = new Club();
                        club.setShortName((String) dataSnapshot.getKey());
                        club.setClubId((String) dataSnapshot.child("club_id").getValue());
                        club.setName((String) dataSnapshot.child("name").getValue());
                        club.setContact((String) dataSnapshot.child("contact").getValue());
                        club.setCounty((String) dataSnapshot.child("county").getValue());

                        try {
                            double nLat = (double) dataSnapshot.child("lat").getValue();
                            club.setLat(nLat);
                        } catch (NumberFormatException e) {
                            System.out.println((String) dataSnapshot.child("lat").getValue());
                        }

                        try {
                        double nLon = (Double) dataSnapshot.child("lng").getValue();
                        club.setLon(nLon);
                        } catch (NumberFormatException e) {
                            System.out.println((String) dataSnapshot.child("lng").getValue());
                        }

                        String url = (String) dataSnapshot.child("url").getValue();
                        club.setUrl(url);

                        String restriction = (String) dataSnapshot.child("restriction").getValue();
                        club.setRestriction(restriction);

                        clubs.add(club);
                    }
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            //Set the onClickListener to send the model information to the next class through the intent
            Button validateModel = (Button) findViewById(R.id.validateButton);
            validateModel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ModelEligibilityMap.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("ModelDataProvider", m);
                    intent.putExtra("ModelBundle", bundle);
                    intent.putExtra("ClubDataProvider", clubs);
                    startActivity(intent);
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
