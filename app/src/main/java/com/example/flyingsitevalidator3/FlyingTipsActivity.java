package com.example.flyingsitevalidator3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Menu;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Brian on 30/03/2017.
 */

public class FlyingTipsActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flying_tips);

        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        //Check if the user is logged in
        if (mFirebaseUser == null) {
            // Not logged in, launch the Log In activity
            loadLogInView();
        } else {


            //Set the title textview and populate it with text
            TextView title = (TextView) findViewById(R.id.titlelabel);
            title.setText("Flying Tips");

            //Set the into textView and populate it with text
            TextView intro = (TextView) findViewById(R.id.intro);
            intro.setText("Here is a list of guidelines on how to get started in the wonderful world of rc aircraft\n\n");

            //Set the first title textView and populate it with text
            TextView title1 = (TextView) findViewById(R.id.title1);
            title1.setText("Learning To Fly");

            //Set the first content textView and populate it with text
            TextView content1 = (TextView) findViewById(R.id.content1);
            content1.setText("The most important point, one which cannot be overstressed: GET AN INSTRUCTOR!\n Also make sure you have your instructor check your plane thoroughly before the first flight---as someone said, \"it is much better to go home with no flights and one airplane than go home with one half a flight and many little pieces.\" This is really, REALLY important. \n\n");

            //Set the second title textView and populate it with text
            TextView title2 = (TextView) findViewById(R.id.title2);
            title2.setText("Pre-flight Checklist");

            //Set the second content textView and populate it with text using HTML to be able to highlight parts of the text.
            TextView content2 = (TextView) findViewById(R.id.content2);
            content2.setText(Html.fromHtml("When your model is ready to fly, make sure it is thouroghly checked over by someone who has done alot of building and flying. When I say thouroughly, I don't mean just picking it up and checking the balance and thumping the tires a few times. Every detail of setup and connection should be gone over in detail. If your instructor doesn't want to spend this much time checking your plane, find a new instructor.\n" +
                    "The importance of this pre-flight check cannot be overemphasized! Many planes are lost due to a simple oversight that could have been caught by a pre-flight!<br><br>" +
                    "Here's a checklist:<br>" +
                    "Before the first flight:<br>" +
                    "<br>" +
                    "<b>Weight</b><br>" +
                    "&nbsp&nbsp Is the model too heavy?<br>" +
                    "<br><b>Balance</b><br>" +
                    "&nbsp&nbsp Is the center of gravity (fore and aft) within the range shown on &nbsp&nbsp the plans?<br>" +
                    "&nbsp&nbsp Is the model balanced side to side? (right and left wings of <br>&nbsp&nbsp equal weight)<br>" +
                    "<br><b>Alignment</b><br>" +
                    "&nbsp&nbsp Are all flying surfaces at the proper angle relative to each other?<br>" +
                    "&nbsp&nbsp Are there any twists in the wings? (other than designed-in <br>&nbsp&nbsp washin or washout)<br>" +
                    "<br><b>Control surfaces</b><br>" +
                    "&nbsp&nbsp Are they all securely attached? (i.e. hinges glued, not just <br>&nbsp&nbsp pushed in)<br>" +
                    "&nbsp&nbsp Are the control throws in the proper direction and amount? <br>&nbsp&nbsp (usually indicated in the plans)<br>" +
                    "<br><b>Control linkage</b><br>" +
                    "&nbsp&nbsp Have all linkages been checked to make sure they are secure?<br>" +
                    "&nbsp&nbsp Are all snap-links closed?<br>" +
                    "&nbsp&nbsp Have snap-links been used on the servo end? (They are more <br>&nbsp&nbsp likely to come loose when used on the servo)<br>" +
                    "&nbsp&nbsp Have all screws been attached to servo horns?<br>" +
                    "<br><b>Engine and fuel (if applicable)</b><br>" +
                    "&nbsp&nbsp Has the engine been thoroughly tested?<br>" +
                    "&nbsp&nbsp Are all engine screws tight?<br>" +
                    "&nbsp&nbsp Has the engine been run up at full throttle with the plane's nose <br>&nbsp&nbsp straight up in the air? (To make sure it won't stall when full <br>&nbsp&nbsp power is applied on climbout)<br>" +
                    "&nbsp&nbsp Is the fuel tank level with the flying attitude of the plane?<br>" +
                    "&nbsp&nbsp Is the carburetor at the same height (not above) as the fuel <br>&nbsp&nbsp tank?<br>" +
                    "&nbsp&nbsp Is the fuel tank klunk in the proper position and moving freely?<br>" +
                    "<br><b>Radio</b><br>" +
                    "&nbsp&nbsp Has a full range check been performed?<br>" +
                    "&nbsp&nbsp Has the flight pack charge been checked with a voltmeter?<br>" +
                    "&nbsp&nbsp Have the receiver and battery been protected from vibration and <br>&nbsp&nbsp shock?<br>" +
                    "&nbsp&nbsp Is the receiver's antenna fully extended and not placed within a <br>&nbsp&nbsp fuselage with any sort of metallic covering?<br>" +
                    "<br><b>After repair:</b><br>" +
                    "&nbsp&nbsp The checklist should be gone through again, with particular <br>&nbsp&nbsp attention to the areas that were worked on or repaired.<br>" +
                    "<br>&nbsp&nbsp Before EVERY flight:<br>" +
                    "&nbsp&nbsp Start the engine (if applicable) and test the entire throttle range. <br>&nbsp&nbsp Run it at full throttle with its nose in the air for 15 seconds or so.<br>" +
                    "&nbsp&nbsp Check the receiver flight pack with a voltmeter to ensure enough <br>&nbsp&nbsp charge.<br>" +
                    "&nbsp&nbsp Check the control throw direction for all surfaces. It's very easy <br>&nbsp&nbsp to do a repair or radio adjustment and forget to switch these. "));


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
