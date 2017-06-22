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

            new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("How to use Fly-Safe").setMessage("Welcome to Fly-Safe, We are here to help you find your perfect flying location which is safe and fun. While using Fly-Safe please pay particular attention to the rules and regulations of flying, this app gives a good indication of how safe a site is but it is up to you to ensure the site does not break any rules, Please see IAA.ie or the information section of this app to see rules and regulations.").setPositiveButton(
                    "Press to continue...", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                        }
                    }).show();
            SharedPreferences.Editor editor = mPrefs.edit();
            editor.putBoolean(screenShown, false);
            editor.commit(); // Save the preference
            Intent myIntent = new Intent(WelcomeScreen.this, MainMenuActivity.class);
            startActivity(myIntent);
        }
        else
        {
            Intent myIntent = new Intent(WelcomeScreen.this, MainMenuActivity.class);
            startActivity(myIntent);
        }

    }
}
