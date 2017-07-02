package com.example.flyingsitevalidator3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by brian on 28/06/2017.
 */

public class FlyingTipsActivityNew extends AppCompatActivity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<ListItem>> listDataChild;
    List<ListItem> items = new ArrayList<ListItem>();
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mDatabase;
    private String mUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flying_tips_new);

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

            //Get the user id from the firebase user reference
            mUserId = mFirebaseUser.getUid();

            // get the listview
            expListView = (ExpandableListView) findViewById(R.id.lvExp);

            // preparing list data
            populateList();

            listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

            // setting list adapter
            expListView.setAdapter(listAdapter);

            // Listview Group click listener
            expListView.setOnGroupClickListener(new OnGroupClickListener() {

                @Override
                public boolean onGroupClick(ExpandableListView parent, View v,
                                            int groupPosition, long id) {

                    /*Toast.makeText(getApplicationContext(),
                    "Group Clicked " + listDataHeader.get(groupPosition),
                    Toast.LENGTH_SHORT).show();*/

                    return false;
                }
            });

            // Listview Group expanded listener
            expListView.setOnGroupExpandListener(new OnGroupExpandListener() {

                @Override
                public void onGroupExpand(int groupPosition) {
                    /*Toast.makeText(getApplicationContext(),
                            listDataHeader.get(groupPosition) + " Expanded",
                            Toast.LENGTH_SHORT).show();*/
                }
            });

            // Listview Group collasped listener
            expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

                @Override
                public void onGroupCollapse(int groupPosition) {
                    /*Toast.makeText(getApplicationContext(),
                            listDataHeader.get(groupPosition) + " Collapsed",
                            Toast.LENGTH_SHORT).show();*/

                }
            });

            // Listview on child click listener
            expListView.setOnChildClickListener(new OnChildClickListener() {

                @Override
                public boolean onChildClick(ExpandableListView parent, View v,
                                            int groupPosition, int childPosition, long id) {

                    /*Toast.makeText(
                            getApplicationContext(),
                            listDataHeader.get(groupPosition)
                                    + " : "
                                    + listDataChild.get(
                                    listDataHeader.get(groupPosition)).get(
                                    childPosition), Toast.LENGTH_SHORT)
                            .show();*/

                    return false;
                }
            });
        }
    }


    /*
     * Preparing the list data
     */
    private void populateList() {

        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<ListItem>>();

        // Adding child data
        listDataHeader.add("Learning To Fly");
        listDataHeader.add("Pre-flight Checklist");
        listDataHeader.add("After repair");
        listDataHeader.add("Before EVERY flight");


        // Use Firebase to populate the learning to fly list
        mDatabase.child("Flying Tips").child("Learning To Fly").addChildEventListener(new ChildEventListener() {

            ArrayList<ListItem> items1 = new ArrayList<ListItem>();
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if (dataSnapshot.child("Title") != null) {

                    ListItem item = new ListItem(dataSnapshot.child("Title").getValue().toString(),dataSnapshot.child("Content").getValue().toString());
                    items1.add(item);

                    Log.wtf("Brian'sERROR", items1.toString());

                }

                listDataChild.put("Learning To Fly", items1);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("Title") != null) {
                    items1.remove((String) dataSnapshot.child("Title").getValue());
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });


        // Use Firebase to populate the Pre-flight Checklist list
        mDatabase.child("Flying Tips").child("Pre-flight Checklist").addChildEventListener(new ChildEventListener() {
            ArrayList<ListItem> items2 = new ArrayList<ListItem>();
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if (dataSnapshot.child("Title") != null) {

                    ListItem item = new ListItem(dataSnapshot.child("Title").getValue().toString(),dataSnapshot.child("Content").getValue().toString());
                    items2.add(item);

                    Log.wtf("Brian'sERROR", items2.toString());

                }

                listDataChild.put("Pre-flight Checklist", items2);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("Title") != null) {
                    items2.remove((String) dataSnapshot.child("Title").getValue());
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        // Use Firebase to populate the After repair list
        mDatabase.child("Flying Tips").child("After repair").addChildEventListener(new ChildEventListener() {
            ArrayList<ListItem> items3 = new ArrayList<ListItem>();
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if (dataSnapshot.child("Title") != null) {

                    ListItem item = new ListItem(dataSnapshot.child("Title").getValue().toString(),dataSnapshot.child("Content").getValue().toString());
                    items3.add(item);

                    Log.wtf("Brian'sERROR", items3.toString());

                }

                listDataChild.put("After repair", items3);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("Title") != null) {
                    items3.remove((String) dataSnapshot.child("Title").getValue());
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        // Use Firebase to populate the Before EVERY flight list
        mDatabase.child("Flying Tips").child("Before EVERY flight").addChildEventListener(new ChildEventListener() {
            ArrayList<ListItem> items = new ArrayList<ListItem>();
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if (dataSnapshot.child("Title") != null) {

                    ListItem item = new ListItem(dataSnapshot.child("Title").getValue().toString(),dataSnapshot.child("Content").getValue().toString());
                    items.add(item);

                    Log.wtf("Brian'sERROR", items.toString());

                }

                listDataChild.put("Before EVERY flight", items);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("Title") != null) {
                    items.remove((String) dataSnapshot.child("name").getValue());
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    //Create options menu for logout function
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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
