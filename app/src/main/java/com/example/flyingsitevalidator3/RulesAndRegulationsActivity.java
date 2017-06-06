package com.example.flyingsitevalidator3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Brian on 30/03/2017.
 */

public class RulesAndRegulationsActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules_and_regulations);

        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        //Check of the user is logged in
        if (mFirebaseUser == null) {
            // Not logged in, launch the Log In activity
            loadLogInView();
        } else {


            //Set the title textview and populate it with text
            TextView title = (TextView) findViewById(R.id.titleLabel);
            title.setText("Rules and Regulations Overview");

            //Set the imageview and set a picture to it
            ImageView image = (ImageView) findViewById(R.id.guidelinesPic);
            image.setImageResource(R.drawable.drone_guidance);

            //Set the title textview and populate it with text
            TextView contenttitle = (TextView) findViewById(R.id.titleLabel2);
            contenttitle.setText("Done Registration");

            //Set the content textview and populate it with text
            TextView content = (TextView) findViewById(R.id.contentLabel);
            content.setText("From the 21st December, drone registration is mandatory in accordance with the Small Unmanned Aircraft (Drones) and Rockets Order S.I. 563 of 2015.\n All drones over 1kg must be registered.");


        }
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
