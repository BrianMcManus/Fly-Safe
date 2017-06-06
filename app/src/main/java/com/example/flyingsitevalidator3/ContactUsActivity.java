package com.example.flyingsitevalidator3;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Brian on 30/03/2017.
 */

public class ContactUsActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        //Check if the user is logged in
        if (mFirebaseUser == null) {
            // Not logged in, launch the Log In activity
            loadLogInView();
        } else {

            //set the image and set the onClickListener to start the email activity using the systems email services allowing the user to send the developer an email
            ImageView developerImage = (ImageView) findViewById(R.id.developerImage);
            developerImage.setImageResource(R.drawable.dev_pic);

            developerImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent devEmailintent = new Intent(android.content.Intent.ACTION_SEND);
                    devEmailintent.setType("plain/text");
                    devEmailintent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"brianmcm88@gmail.com"});
                    devEmailintent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
                    devEmailintent.putExtra(android.content.Intent.EXTRA_TEXT, "");
                    startActivity(Intent.createChooser(devEmailintent, "Send mail..."));
                }
            });

            //set the text for under the image and set the onClickListener to start the email activity using the systems email services allowing the user to send the developer an email
            TextView developerInfo = (TextView) findViewById(R.id.developerInfo);
            developerInfo.setText("Brian Mc Manus \nBachelor's Degree(Hons) in Commercial Computing");

            developerInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent devEmailintent = new Intent(android.content.Intent.ACTION_SEND);
                    devEmailintent.setType("plain/text");
                    devEmailintent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"brianmcm88@gmail.com"});
                    devEmailintent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
                    devEmailintent.putExtra(android.content.Intent.EXTRA_TEXT, "");
                    startActivity(Intent.createChooser(devEmailintent, "Send mail..."));
                }
            });

            //set the image and set the onClickListener to start the email activity using the systems email services allowing the user to send the institute an email
            ImageView collegeImage = (ImageView) findViewById(R.id.collegeImage);
            collegeImage.setImageResource(R.drawable.dkit_logo);

            collegeImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent devEmailintent = new Intent(android.content.Intent.ACTION_SEND);
                    devEmailintent.setType("plain/text");
                    devEmailintent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"info@dkit.ie"});
                    devEmailintent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
                    devEmailintent.putExtra(android.content.Intent.EXTRA_TEXT, "");
                    startActivity(Intent.createChooser(devEmailintent, "Send mail..."));
                }
            });

            //set the text for under the image and set the onClickListener to start the email activity using the systems email services allowing the user to send the Institute an email
            TextView collegeInfo = (TextView) findViewById(R.id.collegeInfo);
            collegeInfo.setText("Dundalk Institute of Technology");

            collegeInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent devEmailintent = new Intent(android.content.Intent.ACTION_SEND);
                    devEmailintent.setType("plain/text");
                    devEmailintent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"info@dkit.ie"});
                    devEmailintent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
                    devEmailintent.putExtra(android.content.Intent.EXTRA_TEXT, "");
                    startActivity(Intent.createChooser(devEmailintent, "Send mail..."));
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
