package com.example.flyingsitevalidator3;

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

/**
 * Created by Brian on 04/03/2017.
 */

public class AddModelActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private EditText mTitleText;
    private EditText mLength;
    private EditText mWidth;
    private Spinner modTypeSpinner;
    private Spinner fuelTypeSpinner;
    private EditText mRegistrationNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_model);

        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();

        //Get views for form items
        mTitleText = (EditText) findViewById(R.id.title);
        modTypeSpinner = (Spinner) findViewById(R.id.spinner);
        mLength = (EditText) findViewById(R.id.Length);
        mWidth = (EditText) findViewById(R.id.Width);
        fuelTypeSpinner = (Spinner) findViewById(R.id.spinner1);
        mRegistrationNum = (EditText) findViewById(R.id.Registration);

        //Create cancel button and gets its view and set the clickListener to cancel the form when clicked
        final Button cancelButton = (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("AddModCancel", "Entered cancelButton.OnClickListener.onClick()");

                // Indicate result and finish
                setResult(RESULT_CANCELED);
                finish();

            }
        });

        //Create reset button and gets its view and set the clickListener to emtpy all fields on form
        final Button resetButton = (Button) findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("AddModReset", "Entered resetButton.OnClickListener.onClick()");

                mTitleText.setText("");
                modTypeSpinner.setSelection(0);
                mLength.setText("");
                mWidth.setText("");
                fuelTypeSpinner.setSelection(0);
                mRegistrationNum.setText("");

            }
        });

        //Create Submit button and gets its view and set the clickListener to take in information from all fields on form
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

                // Indicate result and finish
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

        //Log the user out when log out item is selected
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
