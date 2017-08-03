package com.example.flyingsitevalidator3;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by brian on 02/08/2017.
 */

public class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
    private ArrayList<Club> clubs = new ArrayList<Club>();
    private ArrayList<Model> models = new ArrayList<Model>();
    private Context context;
    Model m = null;


    public CustomOnItemSelectedListener(Context context, ArrayList<Club> clubs, ArrayList<Model> models)
    {
        this.clubs = clubs;
        this.models = models;
        this.context = context;

    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        m = null;
        for(int i = 0; i<models.size(); i++)
        {
            if(parent.getItemAtPosition(position).toString().equals(models.get(i).getName()))
            {
                m = models.get(i);
            }
        }

        if(m!=null) {
            Intent intent = new Intent(context, ModelEligibilityMap.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("ModelDataProvider", m);
            intent.putExtra("ModelBundle", bundle);
            intent.putExtra("ClubDataProvider", clubs);
            context.startActivity(intent);
            Toast.makeText(parent.getContext(),
                    "OnItemSelectedListener : " + parent.getItemAtPosition(position).toString(),
                    Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
