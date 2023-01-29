package com.example.csc8019_teamprojectnotemplate;

/**
 * @Screen RouteActivity
 * @description RouteActivity is to show the possible shortest walking route from the bus station to the selected castle.
 * @author Jirayut Visetsillapanont
 */

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class RouteActivity extends AppCompatActivity {

    //Initialize variables
    private TextView destination;
    private ImageView image;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        //--------------------------------------- Set adapter value ---------------------------------------
        //Connect front-end to back-end
        destination = findViewById(R.id.textDestination);
        image = findViewById(R.id.imageDefault);
        Spinner spinner = findViewById(R.id.busStation);

        //Implement dropdown list
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.source2, android.R.layout.simple_spinner_item);
        //Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            /**
             * Mechanism to display the data(walking route) regarding the selected value from the dropdown(adapter).
             * @param parent AdapterView where selection occurs.
             * @param view The view inside the AdapterView is being clicked
             * @param position Location of the view in the adapter.
             * @param l Selected item row code.
             */
            //Set Origin and Destination values
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                String stationName = parent.getItemAtPosition(position).toString();

                if(stationName.equalsIgnoreCase("Alnwick Bus station"))
                {
                    destination.setText("Alnwick Castle");
                    image.setImageResource(R.drawable.instructiona_png);
                }
                else if(stationName.equalsIgnoreCase("Auckland Bus station"))
                {
                    destination.setText("Auckland Castle");
                    image.setImageResource(R.drawable.instructionb_png);
                }
                else if(stationName.equalsIgnoreCase("Bamburgh Bus station"))
                {
                    destination.setText("Bamburgh Castle");
                    image.setImageResource(R.drawable.instructionc_png);
                }
                else if(stationName.equalsIgnoreCase("Barnard Bus station"))
                {
                    destination.setText("Barnard Castle");
                    image.setImageResource(R.drawable.instructiond_png);
                }
                else if(stationName.equalsIgnoreCase("Select Bus station"))
                {
                    destination.setText("Please select bus station");
                    image.setImageResource(R.drawable.instruction_png);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        //-------------------------------------------------------------------------------------------------


        //------------------------------------------ Back button ------------------------------------------
        /**
         * Implements the back button to enable switching back to the previous screen.
         * @author Jirayut Visetsillapanont
         */
        //Connect front-end to back-end
        Button back;
        back = findViewById(R.id.buttonBack);
        back.setOnClickListener(view -> {
            onBackPressed();
            /* Transition animation between screens */
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });
        //-------------------------------------------------------------------------------------------------


        //----------------------------------------- Navigate bar ------------------------------------------
        /**
         * Implements the bottom navigation bar to enable switching between activities
         * @param item An array of resource ids
         * @author: Christoffer
         */
        // Initialize and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        // Set Route selected
        bottomNavigationView.setSelectedItemId(R.id.route);
        // Perform item selected listener
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    startActivity(new Intent(getApplicationContext(), CastleSelectionActivity.class));
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    return true;
                case R.id.route:
                    return true;
                case R.id.restaurants:
                    startActivity(new Intent(getApplicationContext(), RestaurantsActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    return true;
                case R.id.attractions:
                    startActivity(new Intent(getApplicationContext(), AttractionsActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    return true;
            }
            return false;
        });
    }
    //-------------------------------------------------------------------------------------------------
}