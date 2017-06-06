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
 * Created by Brian on 30/03/2017.
 */

public class FAQActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        //Check if the user is logged in
        if (mFirebaseUser == null) {
            // Not logged in, launch the Log In activity
            loadLogInView();
        } else {

            //Set the introduction view and populate it with text
            TextView intro = (TextView) findViewById(R.id.intro);
            intro.setText("The use of drones is becoming more and more prevalent in Ireland and people who intend to operate drones should be aware of their responsibilities to ensure that they do so safely. \n" +
                    "This Q&A information has been developed by the Irish Aviation Authority (IAA) to provide users with information regarding the use of drones in Ireland.\n");

            //Set the textview for the questions and answers and populate them with the relevant text
            TextView question1 = (TextView) findViewById(R.id.question1);
            question1.setText("1. What is a Drone?");

            TextView answer1 = (TextView) findViewById(R.id.answer1);
            answer1.setText("A drone is an unmanned aircraft/remotely piloted aircraft\n\n");

            TextView question2 = (TextView) findViewById(R.id.question2);
            question2.setText("2. Is there a difference between a drone and a model aircraft?");

            TextView answer2 = (TextView) findViewById(R.id.answer2);
            answer2.setText("Model aircraft and drones are both considered small unmanned aircraft and the same rules apply to their operation. These rules are contained within in the Small Unmanned Aircraft (Drones) and Rockets Order, 2015 (SI 563 of 2015).\n\n");

            TextView question3 = (TextView) findViewById(R.id.question3);
            question3.setText("3. What aviation regulations govern the use of drones?");

            TextView answer3 = (TextView) findViewById(R.id.answer3);
            answer3.setText("The Small Unmanned Aircraft (Drones) and Rockets Order, 2015 (SI 563 of 2015) regulates the use of drones in Ireland. There are also Aeronautical Notices, Directions and Instructions that apply to the use of drones.\n\n");

            TextView question4 = (TextView) findViewById(R.id.question4);
            question4.setText("4. What is prohibited regarding the operation of drones?");

            TextView answer4 = (TextView) findViewById(R.id.answer4);
            answer4.setText("The Small Unmanned Aircraft (Drones) and Rockets Order, 2015 (SI 563 of 2015) contains a number of limitations for the operation of drones. \n" +
                    "These include never operating a drone:\n\n" +
                    "• if it will be a hazard to another aircraft in flight\n" +
                    "• over an assembly of people - 12 persons or more\n" +
                    "• farther than 300m from the person operating the drone\n" +
                    "• within 30m of any person, vessel or structure not under the control of the person operating the drone\n" +
                    "• closer than 5km from an aerodrome\n" +
                    "• in a negligent or reckless manner, so as to endanger life or property of others\n" +
                    "• over 400ft (120m) above ground level\n" +
                    "• over urban areas\n" +
                    "• in civil or military controlled airspace\n" +
                    "• in restricted areas (e.g. military installations, prisons, etc.)\n" +
                    "• unless the person operating the drone has permission from the landowner for take-off and landing.\n\n" +
                    "Please see the Small Unmanned Aircraft (Drones) and Rockets Order, 2015 (SI 563 of 2015) for more detailed information.\n\n");

            TextView question5 = (TextView) findViewById(R.id.question5);
            question5.setText("5. Do I need a permission from the IAA to fly a drone? If so, what do I need?");

            TextView answer5 = (TextView) findViewById(R.id.answer5);
            answer5.setText("No, provided you adhere to the limits prescribed within the Small Unmanned Aircraft (Drones) and Rockets Order, 2015 SI 563 of 2015. \n (Please see question 4 above for the list of limits). \n\n");

            TextView question6 = (TextView) findViewById(R.id.question6);
            question6.setText("6. What if I want to fly my drone outside the limits prescribed in the regulations?");

            TextView answer6 = (TextView) findViewById(R.id.answer6);
            answer6.setText("If you wish to fly your drone outside the limits prescribed in the regulations, you must apply for a Specific Operating Permission (SOP) from the IAA following training with one of the approved IAA Registered Training Facilities (RTF).\n" +
                    "Please see www.iaa.ie/rtf for a list of training facilities.\n\n");

            TextView question7 = (TextView) findViewById(R.id.question7);
            question7.setText("7. How do I apply for a Specific Operating Permission to fly my drone?");

            TextView answer7 = (TextView) findViewById(R.id.answer7);
            answer7.setText("In order to get Specific Operating Permission, you must first attend a drone safety training course and produce an operations manual that is acceptable to the IAA. \n" +
                    "Please see Aeronautical Notice U.02 for more detailed information. To apply, go to www.iaa.ie/dronesapplicationforms\n\n");

            TextView question8 = (TextView) findViewById(R.id.question8);
            question8.setText("8. Do I need insurance to operate my drone?");

            TextView answer8 = (TextView) findViewById(R.id.answer8);
            answer8.setText("The IAA recommends that everybody who operates a drone has insurance.\n\n");

            TextView question9 = (TextView) findViewById(R.id.question9);
            question9.setText("9. Do I need a permission to fly a drone with an inbuilt or mounted camera?");

            TextView answer9 = (TextView) findViewById(R.id.answer9);
            answer9.setText("Irrespective of whether your drone has an inbuilt or mounted camera or not, the same conditions apply. \n See Data Protection Commissioner website www.dataprotection.ie\n\n");

            TextView question10 = (TextView) findViewById(R.id.question10);
            question10.setText("10. Do the same regulations apply if I fly my drone over private property?");

            TextView answer10 = (TextView) findViewById(R.id.answer10);
            answer10.setText("The regulations apply EVERYWHERE in Irish airspace. Be advised that there may be privacy or trespass laws or other legal issues that should be taken into consideration by the person operating the drone. These rules are not within the remit of the IAA.\n\n");

            TextView question11 = (TextView) findViewById(R.id.question11);
            question11.setText("11.Do I need to register my drone with the IAA?");

            TextView answer11 = (TextView) findViewById(R.id.answer11);
            answer11.setText("All drones over 1kg must be registered with the IAA. This includes the weight of the battery and all attached equipment.\nYou can register your drone via the IAA website at www.iaa.ie/drones or with the registration function in this app\n\n");

            TextView question12 = (TextView) findViewById(R.id.question12);
            question12.setText("12. Do I need a qualification to operate a drone?");

            TextView answer12 = (TextView) findViewById(R.id.answer12);
            answer12.setText("If you operate within the limits set in the Small Unmanned Aircraft (Drones) and Rockets Order, 2015 (SI 563 of 2015), no qualification is required. However, to operate under Specific Operating Permission, a Pilot Competency Certificate is required. www.iaa.ie/dronesapplicationforms\n\n");

            TextView question13 = (TextView) findViewById(R.id.question13);
            question13.setText("13. Where can I get more information on the regulations and how to safely operate a drone?");

            TextView answer13 = (TextView) findViewById(R.id.answer13);
            answer13.setText("Please see the Small Unmanned Aircraft (Drones) and Rockets Order, 2015 (SI 563 of 2015) and associated Aeronautical Notices. \n" +
                    "For further information, visit www.iaa.ie/drones \n\n");

            TextView question14 = (TextView) findViewById(R.id.question14);
            question14.setText("14. Do I need to register my model aircraft?");

            TextView answer14 = (TextView) findViewById(R.id.answer14);
            answer14.setText("Yes. Model aircraft are treated in the same way as drones. All model aircraft over 1kg must be registered with the IAA. For more, visit the Model Aeronautics Council of Ireland at www.maci.ie\n\n");

            TextView question15 = (TextView) findViewById(R.id.question15);
            question15.setText("15. How do I register my drone?");

            TextView answer15 = (TextView) findViewById(R.id.answer15);
            answer15.setText("You can register your drone via the IAA website at www.iaa.ie/drones. This is a two-step process. In order to register your drone, you must first register with ASSET, the IAA's online terrain mapping system. Once done, you will be able to register your drone with the IAA via the ASSET system.\n\n");

            TextView question16 = (TextView) findViewById(R.id.question16);
            question16.setText("16. Where can I not fly my drone?");

            TextView answer16 = (TextView) findViewById(R.id.answer16);
            answer16.setText("There are   a number of airspace restrictions, in which you must never fly your drone. \nPlease see question 18.\n\n");

            TextView question17 = (TextView) findViewById(R.id.question17);
            question17.setText("17. How can I tell if an aerodrome is nearby?");

            TextView answer17 = (TextView) findViewById(R.id.answer17);
            answer17.setText("It is the responsibility of the person operating a drone to assess the area in which they intend to fly. While the IAA’s ASSET tool can be used to establish the location of licensed aerodromes within the State, it is essential for the person who will operate the drone to visually inspect the area in which they   intend to operate for aircraft activity.\n\n");

            TextView question18 = (TextView) findViewById(R.id.question18);
            question18.setText("18. How can I tell where controlled or restricted airspace is?");

            TextView answer18 = (TextView) findViewById(R.id.answer18);
            answer18.setText("The IAA’s Asset tool can be used to determine controlled and restricted airspace areas. \n" +
                    "Please note that the boundaries of restricted airspace may change and that Temporary Restricted Airspace areas may be created. It is essential to get up to date information on controlled and restricted airspace via ASSET every time you wish to fly. \n\n");

            TextView question19 = (TextView) findViewById(R.id.question19);
            question19.setText("19. Is there a minimum age for operating a drone?");

            TextView answer19 = (TextView) findViewById(R.id.answer19);
            answer19.setText("While there is no minimum age for operating a drone in aviation regulation, the IAA advises that age guidelines indicated by drone manufacturers are followed. You must be over 16 to register your drone, if it is over 1kg. A parent or guardian may register the drone for those under 16. \n" +
                    "Please note that other laws may apply.\n\n");

            TextView question20 = (TextView) findViewById(R.id.question20);
            question20.setText("20. I want to use my drone for commercial activities. Do I need permission from the IAA?");

            TextView answer20 = (TextView) findViewById(R.id.answer20);
            answer20.setText("You do not need permission from the IAA to use drones commercially. However, if you wish to fly your drone outside the limits prescribed in the regulations you must apply for a Specific Operating Permission from the IAA.\n\n");

            TextView question21 = (TextView) findViewById(R.id.question21);
            question21.setText("21. Up to what distance and height can I operate my drone?");

            TextView answer21 = (TextView) findViewById(R.id.answer21);
            answer21.setText("You must not operate your drone beyond 300m and400ft (120m) above ground level in uncontrolled airspace, and 300m and 50ft (15m) in controlled airspace\n\n");


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
