package com.example.csc8019_teamprojectnotemplate;
/**
 * @Screen CastleSelectionActivity
 * @description CastleSelectionActivity is to select the source and destination where the uer wants to visit.
 * It also displays the castle image once a castle is chosen
 * Navigation bar at the bottom has the options of home, instructions, restaurant, attractions(done by Christopher)
 * @author Raj Kiran Pasumarthi
 */

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

//Inspiration: https://www.geeksforgeeks.org/how-to-implement-bottom-navigation-with-activities-in-android/

public class CastleSelectionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    //Initialize variables
    private TextView info1, info2;
    private ImageView image;
    private Button button;

    /**
     *
     * @param savedInstanceState activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //----------------------------------------- Navigate bar ------------------------------------------
        /**
         * Implements the bottom navigation bar to enable switching between activities
         * @param item An array of resource ids
         * @author: Christoffer
         */
        //Initialize and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        //Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.home);
        //Perform item selected listener
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            /**
             *
             * @param item An array of resource ids
             */
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.home:
                        return true;
                    case R.id.route:
                        startActivity(new Intent(getApplicationContext(),RouteActivity.class));
                        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                        return true;
                    case R.id.restaurants:
                        startActivity(new Intent(getApplicationContext(),RestaurantsActivity.class));
                        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                        return true;
                    case R.id.attractions:
                        startActivity(new Intent(getApplicationContext(),AttractionsActivity.class));
                        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                        return true;
                }
                return false;
            }
        });
        //-------------------------------------------------------------------------------------------------


        //----------------------------------------- Set adapter value -----------------------------------------
        //Connect front-end to back-end
        Spinner spinner1 = findViewById(R.id.selectCastle);
        Spinner spinner2 = findViewById(R.id.departStation);
        info1 = findViewById(R.id.castleInfo1);
        info2 = findViewById(R.id.castleInfo2);
        image = findViewById(R.id.castleImage);

        //Implement dropdown list
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.planets_array, android.R.layout.simple_spinner_item);
        //Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Apply the adapter to the spinner
        spinner1.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(this);

        //to set source values
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,R.array.source, android.R.layout.simple_spinner_item);
        //Specify the layout to use when the list of choices appears
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Apply the adapter to the spinner
        spinner2.setAdapter(adapter1);
        spinner2.setOnItemSelectedListener(this);
        //-------------------------------------------------------------------------------------------------


        //--------------------------------------- Book Ticket button --------------------------------------
        button = findViewById(R.id.button);
        button.setOnClickListener(view -> {
            Intent act = new Intent(CastleSelectionActivity.this, BookingActivity.class);

            //Get value from dropdown station
            Object depSta = spinner2.getSelectedItem();

            String castle = info1.getText().toString();
            String busStation = depSta.toString();
            //Send text value to next screen
            act.putExtra("selectedCastle", castle);
            act.putExtra("selectedStation", busStation);
            startActivity(act);

            /* Transition animation between screens */
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
        //-------------------------------------------------------------------------------------------------
    }

    //-------------------------------------- Display castle image+info --------------------------------------
    /**
     *
     * @param parent The AdapterView where the selection happened
     * @param view The view within the AdapterView that was clicked
     * @param position The position of the view in the adapter
     * @param id The row id of the item that is selected
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String castleName = parent.getItemAtPosition(position).toString();

        if(castleName.equalsIgnoreCase("Alnwick Castle"))

        {
            info1.setText(castleName);
            image.setImageResource(R.drawable.a1_alnwick1);
            info2.setText(R.string.alnwick_description);
            info1.setVisibility(View.VISIBLE);
            info2.setVisibility(View.VISIBLE);
            image.setVisibility(View.VISIBLE);
            button.setEnabled(true);
        }
        else if(castleName.equalsIgnoreCase("Auckland Castle"))
        {
            info1.setText(castleName);
            image.setImageResource(R.drawable.a2_auckland1);
            info2.setText(R.string.auckland_description);
            info1.setVisibility(View.VISIBLE);
            info2.setVisibility(View.VISIBLE);
            image.setVisibility(View.VISIBLE);
            button.setEnabled(true);
        }
        else if(castleName.equalsIgnoreCase("Bamburgh Castle"))
        {
            info1.setText(castleName);
            image.setImageResource(R.drawable.a3_bamburgh1);
            info2.setText(R.string.bamburgh_description);
            info1.setVisibility(View.VISIBLE);
            info2.setVisibility(View.VISIBLE);
            image.setVisibility(View.VISIBLE);
            button.setEnabled(true);
        }
        else if(castleName.equalsIgnoreCase("Barnard Castle"))
        {
            info1.setText(castleName);
            image.setImageResource(R.drawable.a4_banard1);
            info2.setText(R.string.barnard_description);
            info1.setVisibility(View.VISIBLE);
            info2.setVisibility(View.VISIBLE);
            image.setVisibility(View.VISIBLE);
            button.setEnabled(true);
        }
        else if(castleName.equalsIgnoreCase("Select Castle"))
        {
            image.setImageResource(R.drawable.logo2_png);
            info2.setText("Please select the caslte and bus station.");
            info1.setVisibility(View.INVISIBLE);
            info2.setVisibility(View.VISIBLE);
            image.setVisibility(View.VISIBLE);
            button.setEnabled(false);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
    //------------------------------------------------------------------------------------------------------
}