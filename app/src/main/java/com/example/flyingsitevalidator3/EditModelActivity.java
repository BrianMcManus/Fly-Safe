package com.example.flyingsitevalidator3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Brian on 05/03/2017.
 */

public class EditModelActivity extends AppCompatActivity {
    private FirebaseAuth mFirebaseAuth;
    private EditText mTitleText;
    private EditText mLength;
    private EditText mWidth;
    private Spinner modTypeSpinner;
    private Spinner fuelTypeSpinner;
    private EditText mRegistrationNum;
    private Model mod;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_model);
        mFirebaseAuth = FirebaseAuth.getInstance();

        //Get the information from the intent sent to populate the model object
        Intent intent = this.getIntent();
        Bundle b = intent.getExtras();
        mod = (Model) b.getSerializable("model");

        //Get views for all fields on form
        mTitleText = (EditText) findViewById(R.id.title);
        modTypeSpinner = (Spinner) findViewById(R.id.spinner);
        mLength = (EditText) findViewById(R.id.Length);
        mWidth = (EditText) findViewById(R.id.Width);
        fuelTypeSpinner = (Spinner) findViewById(R.id.spinner1);
        mRegistrationNum = (EditText) findViewById(R.id.Registration);

        //Set the name of the model
        mTitleText.setText(mod.getName());

        //Set the correct spinner selection based on the models type
        if (mod.getModelType().equals("Airplane")) {
            modTypeSpinner.setSelection(0);

        } else if (mod.getModelType().equals("Helicopter")) {
            modTypeSpinner.setSelection(1);

        } else if (mod.getModelType().equals("Jet")) {
            modTypeSpinner.setSelection(2);

        } else if (mod.getModelType().equals("Glider")) {
            modTypeSpinner.setSelection(3);

        } else if (mod.getModelType().equals("Blimp")) {
            modTypeSpinner.setSelection(4);

        } else if (mod.getModelType().equals("Waterplane")) {
            modTypeSpinner.setSelection(5);

        } else if (mod.getModelType().equals("Multicopters")) {
            modTypeSpinner.setSelection(6);

        } else if (mod.getModelType().equals("Autogyro")) {
            modTypeSpinner.setSelection(7);

        } else if (mod.getModelType().equals("Ornithopters")) {
            modTypeSpinner.setSelection(8);

        } else if (mod.getModelType().equals("Other")) {
            modTypeSpinner.setSelection(9);

        } else {
            modTypeSpinner.setSelection(9);

        }

        //Set the model length and width in their respective text fields
        mLength.setText(mod.getLength() + "");
        mWidth.setText(mod.getWidth() + "");

        //Check the models fuel type and set the correct selection on the spinner
        if (mod.getFuelType().equals("Electric")) {
            fuelTypeSpinner.setSelection(0);
        } else if (mod.getFuelType().equals("Nitro")) {
            fuelTypeSpinner.setSelection(1);
        } else if (mod.getFuelType().equals("Jet A and Kerosene")) {
            fuelTypeSpinner.setSelection(2);
        } else if (mod.getFuelType().equals("Diesel")) {
            fuelTypeSpinner.setSelection(3);
        } else if (mod.getFuelType().equals("None")) {
            fuelTypeSpinner.setSelection(4);
        } else if (mod.getFuelType().equals("Other")) {
            fuelTypeSpinner.setSelection(5);
        }

        //Check if the model is registered and if it is display the registration number
        if (mod.getRegistrationId() != 0 && mod.getRegistrationId() != -1) {
            mRegistrationNum.setText(mod.getRegistrationId() + "");
        } else {
            //If not registered set it as empty
            mRegistrationNum.setText("");
        }

        //Set listener for the cancel button and if pressed send the models info back to populate list again
        final Button cancelButton = (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("AddModCancel", "Entered cancelButton.OnClickListener.onClick()");

                // Package Model data into an Intent
                Intent data = new Intent();
                Model.packageIntent(data, mod.getName(), mod.getModelType(), mod.getWidth(), mod.getLength(), mod.getFuelType(), mod.getRegistrationId());

                setResult(RESULT_OK, data);
                finish();

            }
        });

        // Set up OnClickListener for the Reset Button and set all fields to empty when clicked
        final Button resetButton = (Button) findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("AddModReset", "Entered resetButton.OnClickListener.onClick()");

                // TODO - Reset data to default values
                mTitleText.setText("");
                modTypeSpinner.setSelection(0);
                mLength.setText("");
                mWidth.setText("");
                fuelTypeSpinner.setSelection(0);
                mRegistrationNum.setText("");

            }
        });

        // Set up OnClickListener for the Submit Button and when clicked send new information back
        final Button submitButton = (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("AddModSubmit", "Entered submitButton.OnClickListener.onClick()");

                String mTitle = mTitleText.getText().toString();
                String modType = modTypeSpinner.getSelectedItem().toString();
                double mLen = Double.parseDouble(mLength.getText().toString());
                double mWid = Double.parseDouble(mWidth.getText().toString());
                String fuelType = fuelTypeSpinner.getSelectedItem().toString();
                int regNum = 0;
                if (mRegistrationNum.getText().toString() != null && (!mRegistrationNum.getText().toString().equals(""))) {
                    regNum = Integer.parseInt(mRegistrationNum.getText().toString());
                }

                // Package Model data into an Intent
                Intent data = new Intent();
                Model.packageIntent(data, mTitle, modType, mWid, mLen, fuelType, regNum);

                setResult(RESULT_OK, data);
                finish();


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
}
