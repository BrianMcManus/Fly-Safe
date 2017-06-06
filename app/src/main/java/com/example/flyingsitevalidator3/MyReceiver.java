package com.example.flyingsitevalidator3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.widget.Toast;

//This class is used to check the status of the users GPS
public class MyReceiver extends BroadcastReceiver {

    //Default constructor
    public MyReceiver() {
    }

    //This method checks to see if the users GPS is turned on or off when the method registers a change in its status
    @Override
    public void onReceive(Context context, Intent intent) {

        //Initalize the location manager object with reference to the systems gps system.
        final LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        //If its enabled do nothing
        if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

        }
        //Otherwise alert the user that the system works better with GPS turned
        else {
            Toast.makeText(context, "Sorry GPS must be switched on in order for this " +
                            "application to work correctly, Please turn back on imediately.",
                    Toast.LENGTH_LONG).show();
        }

    }
}
