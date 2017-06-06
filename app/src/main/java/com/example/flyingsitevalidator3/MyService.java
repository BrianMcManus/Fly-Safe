package com.example.flyingsitevalidator3;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.IBinder;
import android.widget.Toast;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //Starts the service and kills it when the application is destroyed
    public int onStartCommand(Intent intent, int flag, int startId) {
        return START_NOT_STICKY;
    }


}
