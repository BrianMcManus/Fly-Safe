package com.example.flyingsitevalidator3;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Brian on 30/03/2017.
 */

public class UsefulLinksActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_useful_links);

        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        //Check if the user is logged in
        if (mFirebaseUser == null) {
            // Not logged in, launch the Log In activity
            loadLogInView();
        } else {

            //Set the textview and populate it with html text so the text will stand out as its a section header
            TextView sectionTitle1 = (TextView) findViewById(R.id.sectionTitle1);
            sectionTitle1.setText(Html.fromHtml("<b>Aviation Authorities</b>"));

            //Set the textview for the link title and populate it with text
            TextView linktitle1 = (TextView) findViewById(R.id.title1);
            linktitle1.setText("IAA (Irish Aviation Authority)");

            //Set the textview for the link and populate it with text
            final TextView link1 = (TextView) findViewById(R.id.link1);
            link1.setText("https://www.iaa.ie/general-aviation/drones");
            //Set the clicklistener, once clicked the intent opens the browser and navigates to the link url
            link1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link1.getText().toString()));
                    startActivity(intent);
                }
            });


            //Set the textview for the link title and populate it with text
            TextView linktitle2 = (TextView) findViewById(R.id.title2);
            linktitle2.setText("NIAA (Northern Ireland Association of Areomodellers)");

            //Set the textview for the link title and populate it with text
            final TextView link2 = (TextView) findViewById(R.id.link2);
            link2.setText("http://www.niaeromodellers.org.uk/");
            //Set the clicklistener, once clicked the intent opens the browser and navigates to the link url
            link2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link2.getText().toString()));
                    startActivity(intent);
                }
            });


            //Set the textview and populate it with html text so the text will stand out as its a section header
            TextView sectionTitle2 = (TextView) findViewById(R.id.sectionTitle2);
            sectionTitle2.setText(Html.fromHtml("<b>Where to buy model aircraft at the best price</b>"));

            //Set the textview for the link title and populate it with text
            TextView linktitle3 = (TextView) findViewById(R.id.title3);
            linktitle3.setText("HobbyKing");

            //Set the textview for the link and populate it with text
            final TextView link3 = (TextView) findViewById(R.id.link3);
            link3.setText("https://hobbyking.com/");
            //Set the clicklistener, once clicked the intent opens the browser and navigates to the link url
            link3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link3.getText().toString()));
                    startActivity(intent);
                }
            });


            //Set the textview for the link title and populate it with text
            TextView linktitle3_1 = (TextView) findViewById(R.id.title3_1);
            linktitle3_1.setText("ebay");

            //Set the textview for the link and populate it with text
            final TextView link3_1 = (TextView) findViewById(R.id.link3_1);
            link3_1.setText("http://www.ebay.ie");
            //Set the clicklistener, once clicked the intent opens the browser and navigates to the link url
            link3_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link3_1.getText().toString()));
                    startActivity(intent);
                }
            });


            //Set the textview for the link title and populate it with text
            TextView linktitle3_2 = (TextView) findViewById(R.id.title3_2);
            linktitle3_2.setText("Gumtree");

            //Set the textview for the link and populate it with text
            final TextView link3_2 = (TextView) findViewById(R.id.link3_2);
            link3_2.setText("https://www.gumtree.com/");
            //Set the clicklistener, once clicked the intent opens the browser and navigates to the link url
            link3_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link3_2.getText().toString()));
                    startActivity(intent);
                }
            });

            //Set the textview for the link title and populate it with text
            TextView linktitle3_3 = (TextView) findViewById(R.id.title3_3);
            linktitle3_3.setText("Banggood");

            //Set the textview for the link and populate it with text
            final TextView link3_3 = (TextView) findViewById(R.id.link3_3);
            link3_3.setText("http://www.banggood.com/Wholesale-RC-Airplane-c-1855.html");
            //Set the clicklistener, once clicked the intent opens the browser and navigates to the link url
            link3_3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link3_3.getText().toString()));
                    startActivity(intent);
                }
            });


            //Set the textview and populate it with html text so the text will stand out as its a section header
            TextView sectionTitle3 = (TextView) findViewById(R.id.sectionTitle3);
            sectionTitle3.setText(Html.fromHtml("<b>Best places to find model building advice and plans</b>"));

            //Set the textview for the link title and populate it with text
            TextView linktitle4 = (TextView) findViewById(R.id.title4);
            linktitle4.setText("RC advisor.com for free building plans for aircraft");

            //Set the textview for the link and populate it with text
            final TextView link4 = (TextView) findViewById(R.id.link4);
            link4.setText("http://rcadvisor.com/best-free-model-airplane-plans-websites");
            //Set the clicklistener, once clicked the intent opens the browser and navigates to the link url
            link4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link4.getText().toString()));
                    startActivity(intent);
                }
            });


            //Set the textview and populate it with html text so the text will stand out as its a section header
            TextView sectionTitle4 = (TextView) findViewById(R.id.sectionTitle4);
            sectionTitle4.setText(Html.fromHtml("<b>Everything beginners need to get off the ground safely</b>"));

            //Set the textview for the link title and populate it with text
            TextView linktitle5 = (TextView) findViewById(R.id.title5);
            linktitle5.setText("Wattflyer for all the information you'll need to get off the ground");

            //Set the textview for the link and populate it with text
            final TextView link5 = (TextView) findViewById(R.id.link5);
            link5.setText("http://www.wattflyer.com/forums/showthread.php?t=28083");
            //Set the clicklistener, once clicked the intent opens the browser and navigates to the link url
            link5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link5.getText().toString()));
                    startActivity(intent);
                }
            });

            //Set the textview for the link title and populate it with text
            TextView linktitle5_1 = (TextView) findViewById(R.id.title5_1);
            linktitle5_1.setText("RC Airplane World");

            //Set the textview for the link and populate it with text
            final TextView link5_1 = (TextView) findViewById(R.id.link5_1);
            link5_1.setText("http://www.rc-airplane-world.com/flying-model-airplanes.html");
            //Set the clicklistener, once clicked the intent opens the browser and navigates to the link url
            link5_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link5_1.getText().toString()));
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
