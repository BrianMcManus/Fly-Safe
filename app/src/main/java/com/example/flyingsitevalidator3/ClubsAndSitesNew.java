package com.example.flyingsitevalidator3;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by brian on 30/06/2017.
 */

public class ClubsAndSitesNew extends AppCompatActivity{

    private ArrayList<Club> clubs = new ArrayList<Club>();
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mDatabase;
    private String mUserId;
    private EditText editsearch;
    ClubListAdapter adapter;
    Context mContext = this;

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

            Intent intent = getIntent();
            Bundle b = intent.getExtras();
            clubs = (ArrayList<Club>)  b.getSerializable("Clubs_dataProvider");


            // Set up ListView
            final ListView listView = (ListView) findViewById(R.id.listView);
            adapter = new ClubListAdapter(mContext, clubs);
            listView.setAdapter(adapter);
            //adapter.add("All Sites");

            // Locate the EditText in listview_main.xml
            editsearch = (EditText) findViewById(R.id.search);

            // Capture Text in EditText
            editsearch.addTextChangedListener(new TextWatcher() {

                @Override
                public void afterTextChanged(Editable arg0) {
                    // TODO Auto-generated method stub
                    String text = editsearch.getText().toString().toLowerCase(Locale.getDefault());
                    adapter.filter(text);

                }

                @Override
                public void beforeTextChanged(CharSequence arg0, int arg1,
                                              int arg2, int arg3) {
                    // TODO Auto-generated method stub
                }

                @Override
                public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                    // TODO Auto-generated method stub

                    /*String text = editsearch.getText().toString().toLowerCase(Locale.getDefault());
                    adapter.filter(text);*/
                }
            });

            /*//Set the click listener for the club list
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
                                String county = clubs.get(i).getCounty();
                                String url = clubs.get(i).getUrl();
                                Bundle args = new Bundle();
                                args.putParcelable("longLat_dataProvider", fromPosition);
                                Intent intent = new Intent(ClubsAndSites.this, MapsActivity.class);
                                intent.putExtra("name_dataProvider", name);
                                intent.putExtra(("address_dataProvider"), county);
                                intent.putExtra("url_dataProvider", url);
                                intent.putExtras(args);
                                startActivity(intent);

                            }
                        }
                    }
                }
            });
*/


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
