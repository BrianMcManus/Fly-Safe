package com.example.flyingsitevalidator3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<AirportMarker> aMkr = new ArrayList<AirportMarker>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1);
            Intent lastIntent = getIntent();
        aMkr = (ArrayList<AirportMarker>) lastIntent.getSerializableExtra("AllAirports_DataProvider");

                        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                        intent.putExtra("AllAirports_dataProvider", aMkr);
                        startActivity(intent);



    }
}
