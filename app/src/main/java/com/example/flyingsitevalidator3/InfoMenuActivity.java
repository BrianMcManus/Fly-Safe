package com.example.flyingsitevalidator3;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Brian on 28/03/2017.
 */

public class InfoMenuActivity extends AppCompatActivity {
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_menu);

        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        //Check if the user is logged in
        if (mFirebaseUser == null) {
            // Not logged in, launch the Log In activity
            loadLogInView();
        } else {


            //Set up the rules and regulations textview
            TextView tvRaR = (TextView) findViewById(R.id.Rules_and_Regulations);
            //Set the background color of the text field
            tvRaR.setBackgroundColor(Color.parseColor("#7ECFE3"));
            //Set the color of the text
            tvRaR.setTextColor(Color.WHITE);
            //Populate the text field with text
            tvRaR.setText("Rules and Regulations");
            //Set the click listener to send the user to the relevant activity
            tvRaR.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(InfoMenuActivity.this, RulesAndRegulationsActivity.class);
                    startActivity(intent);
                }
            });


            //Set up the FAQ's textview
            TextView tvFAQ = (TextView) findViewById(R.id.FAQ);
            //Populate the text field with the relevant text
            tvFAQ.setText("FAQ's");
            //Set the click listener to send the user to the relevant activity
            tvFAQ.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(InfoMenuActivity.this, FAQActivityNew.class);
                    startActivity(intent);
                }
            });


            //Set up the Flying Tips textview
            TextView tvFT = (TextView) findViewById(R.id.Flying_Tips);
            //Populate the text field with text
            tvFT.setText("Flying Tips");
            //Set the background color of the text field
            tvFT.setBackgroundColor(Color.parseColor("#7ECFE3"));
            //Set the color of the text
            tvFT.setTextColor(Color.WHITE);
            //Set the click listener to send the user to the relevant activity
            tvFT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(InfoMenuActivity.this, FlyingTipsActivityNew.class);
                    startActivity(intent);
                }
            });


            //Set up the Useful Links textview
            TextView tvUL = (TextView) findViewById(R.id.Useful_Links);
            //Populate the text field with text
            tvUL.setText("Useful Links");
            //Set the click listener to send the user to the relevant activity
            tvUL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(InfoMenuActivity.this, UsefulLinksActivity.class);
                    startActivity(intent);
                }
            });


            //Set up the Contact Us textview
            TextView tvCU = (TextView) findViewById(R.id.Contact_Us);
            //Populate the text field with text
            tvCU.setText("Contact Us");
            //Set the background color of the text field
            tvCU.setBackgroundColor(Color.parseColor("#7ECFE3"));
            //Set the color of the text
            tvCU.setTextColor(Color.WHITE);
            //Set the click listener to send the user to the relevant activity
            tvCU.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(InfoMenuActivity.this, ContactUsActivity.class);
                    startActivity(intent);
                }
            });


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
