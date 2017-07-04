package com.example.flyingsitevalidator3;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

/**
 * Created by brian on 22/06/2017.
 */

public class WelcomeScreen  extends Activity {

    SharedPreferences mPrefs;
    final String screenShown = "welcomeScreenShown";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        // second argument is the default to use if the preference can't be found
        Boolean welcomeScreenShown = mPrefs.getBoolean(screenShown, false);

        //welcomeScreenShown = true;
        if (!welcomeScreenShown) {
            // here you can launch another activity if you like
            // the code below will display a popup

            SharedPreferences.Editor editor = mPrefs.edit();
            editor.putBoolean(screenShown, true);
            editor.commit(); // Save the preference
            Intent myIntent = new Intent(WelcomeScreen.this, MainMenuActivity.class);
            boolean welcomeShown = false;
            myIntent.putExtra("welcomeShown", welcomeShown);
            startActivity(myIntent);
            finish();
        }
        else
        {
            Intent myIntent = new Intent(WelcomeScreen.this, MainMenuActivity.class);
            startActivity(myIntent);
            finish();
        }

    }
}
