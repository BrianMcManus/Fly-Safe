package com.example.flyingsitevalidator3;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;

/**
 * Created by Brian on 04/03/2017.
 */

public class Models extends AppCompatActivity {
    //Initalize the Firebase Auth
    private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    //Get reference to the database
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    ArrayList<Model> mods = new ArrayList<Model>();
    Context context = this;
    ListView listView;
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_models);

        //Create a new intent from the intent passed from the previous activity
        Intent intent = this.getIntent();
        Bundle b = intent.getExtras();
        //Use the bundle in the intent to populate the list with the users models
        mods = (ArrayList<Model>) b.getSerializable("AllModels_dataProvider");


        // Set up ListView and ArrayAdapter
        listView = (ListView) findViewById(R.id.listView);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1);

       /* // Put divider between Models and FooterView
        listView.setFooterDividersEnabled(true);

        //Set the footer view to the end of the list
        TextView footerView = null;
        footerView = (TextView) getLayoutInflater().inflate(R.layout.footer_view, null);

        //add listener to the footer view, when clicked start the add model activity and get a result back
        listView.addFooterView(footerView);
        footerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, AddModelActivity.class);
                startActivityForResult(intent, 0);
            }
        });*/

        FloatingActionButton myFab = (FloatingActionButton) findViewById(R.id.fab);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(context, AddModelActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        //Set the custom adapter to the list view
        listView.setAdapter(adapter);
        //Set the long-click listener to the list view
        registerForContextMenu(listView);

        //Add each models name in the list to the adapter
        for (int i = 0; i < mods.size(); i++) {
            adapter.add(mods.get(i).getName());
        }


        /*Set the click listener for the list view, when an item is clicked it gets the name of the model and cycles through the list of models to
        * get that models details which is then placed in a model object, this model is then sent to the ModelInfo class through an intent*/
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Get the name of the model from the list that the user clicked
                String selectedFromList = (String) parent.getItemAtPosition(position);


                //Cycle through the list od users models and when the relevant model is found create a model object from it
                for (int i = 0; i < mods.size(); i++) {

                    if (mods.get(i).getName().equals(selectedFromList)) {

                        Model m = new Model();
                        m.setModelId(mods.get(i).getModelId());
                        m.setName(mods.get(i).getName());
                        m.setWidth(mods.get(i).getWidth());
                        m.setLength(mods.get(i).getLength());
                        m.setFuelType(mods.get(i).getFuelType());
                        m.setModelType(mods.get(i).getModelType());
                        m.setRegistrationId(mods.get(i).getRegistrationId());


                        //Place the model in a bundle
                        Bundle args = new Bundle();
                        args.putSerializable("Model", m);

                        //Create intent and place bundle with model info into it, then start the activity
                        Intent intent = new Intent(Models.this, ModelInfo.class);
                        intent.putExtras(args);
                        startActivity(intent);
                    }
                }
            }
        });
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

    //Creates a context menu when the user long presses an item on the model list
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        //Creates the context menu and gives it the custom view menu_items which is then inflated
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_items, menu);
    }


    //Method is used to get the users choice from the context menu options and use the choice to either edit the models information or delete the model from the system
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        //Get the menu adapter
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        //Check which option the user chose if it was edit then enter statement
        if (item.getItemId() == R.id.edit) {
            //Create a new intent for the EditModelActivity class
            Intent intent = new Intent(context, EditModelActivity.class);

            //Get the position of the chosen item
            int listPos = info.position;

            //Get the model that coresponse with the position
            Model selectedFromList = mods.get(listPos);

            //Create a new bundle and place the model inside then place bundle in the intent and start the activity and get the result
            Bundle b = new Bundle();
            b.putSerializable("model", selectedFromList);
            intent.putExtras(b);
            startActivityForResult(intent, 5);
            Model m = new Model();

            //Cycle through the list and remove the model from the list and the database
            if (mods != null && mods.size() > 0) {
                for (int i = 0; i < mods.size(); i++) {

                    if (mods.get(i).getName().equals(selectedFromList.getName())) {


                        m.setModelId(mods.get(i).getModelId());
                        m.setName(mods.get(i).getName());
                        m.setWidth(mods.get(i).getWidth());
                        m.setLength(mods.get(i).getLength());
                        m.setFuelType(mods.get(i).getFuelType());
                        m.setModelType(mods.get(i).getModelType());
                        m.setRegistrationId(mods.get(i).getRegistrationId());
                        mods.remove(i);
                        adapter.remove(m.getName());
                        adapter.notifyDataSetChanged();
                        mDatabase.child("users").child(mFirebaseAuth.getCurrentUser().getUid()).child("models").child(m.getName()).removeValue();

                    }
                }
            }


        }
        //If the user chose to delete the model then enter the statement
        else if (item.getItemId() == R.id.delete) {
            //Create a new Model object
            final Model m = new Model();


            //Create a new alert dialog to allow the user to make sure they really want to delete the model
            new AlertDialog.Builder(context)
                    .setTitle("Delete entry")
                    .setMessage("Are you sure you want to delete this entry?")
                    //If the answer is that they wish to proceed
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            //Get the position of the selected model and get its name which is placed in a String variable
                            int listPos = info.position;
                            String selectedFromList = mods.get(listPos).getName();

                            //If the model list is not empty proceed
                            if (mods != null && mods.size() > 0) {

                                /*Cycle through the llist of models and find the model that corresponds with the selected model name,
                                * Once found create a model object from the model and use this information to remove the model from
                                * the list and the database*/
                                for (int i = 0; i < mods.size(); i++) {

                                    if (mods.get(i).getName().equals(selectedFromList)) {


                                        m.setModelId(mods.get(i).getModelId());
                                        m.setName(mods.get(i).getName());
                                        m.setWidth(mods.get(i).getWidth());
                                        m.setLength(mods.get(i).getLength());
                                        m.setFuelType(mods.get(i).getFuelType());
                                        m.setModelType(mods.get(i).getModelType());
                                        m.setRegistrationId(mods.get(i).getRegistrationId());
                                        mods.remove(i);
                                        adapter.remove(m.getName());
                                        adapter.notifyDataSetChanged();

                                    }
                                }
                                mDatabase.child("users").child(mFirebaseAuth.getCurrentUser().getUid()).child("models").child(m.getName()).removeValue();
                            }
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

            return true;
        }

        return true;
    }

    //When starting an activity for a result we must handle the response, this method process the response differently for each individual activity response
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //If the response is from adding a model
        if (requestCode == 0) {
            //If retrieval of the model information was a success we add the model to the list, database and the adapter
            if (resultCode == RESULT_OK) {
                Model model = new Model(data);
                adapter.add(model.getName());
                mods.add(model);
                mDatabase.child("users").child(mFirebaseAuth.getCurrentUser().getUid()).child("models").child(model.getName()).setValue(model);
            }
        }
        //If the response was from editing a model
        else if (requestCode == 5) {
            //If retrieval of the edited models data was successful then we remove the old entry and add the new information instead
            if (resultCode == RESULT_OK) {
                Model model = new Model(data);
                mDatabase.child("users").child(mFirebaseAuth.getCurrentUser().getUid()).child("models").child(model.getName()).removeValue();
                adapter.remove(model.getName());
                mods.remove(model);
                adapter.add(model.getName());
                mods.add(model);
            }
        }
        //If it is any other response we simply add the model back onto the list, adapter and the database in case of a fault
        else {
            Model model = new Model(data);
            adapter.add(model.getName());
            mods.add(model);
            mDatabase.child("users").child(mFirebaseAuth.getCurrentUser().getUid()).child("models").child(model.getName()).setValue(model);
        }
    }
}
