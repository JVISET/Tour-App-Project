package com.example.csc8019_teamprojectnotemplate;
/**
 * @Screen BookingSuccessActivity
 * @description BookingSuccessActivity is to display the confirmation of booking and the booking details.
 * @author Raj Kiran Pasumarthi
 */

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


public class BookingSuccessActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_success);

        Button done;
        TextView castleName, journeyDate, passenger;
        TextView outboundStation1, outboundStation2, outboundTime, outboundDuration, outboundBusNo, outboundBusOperator;
        TextView inboundStation1, inboundStation2, inboundTime, inboundDuration, inboundBusNo, inboundBusOperator;
        //----------------------------------------- Navigate bar ------------------------------------------
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
                        startActivity(new Intent(getApplicationContext(), CastleSelectionActivity.class));
                        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
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


        //------------------------------------------ Done button ------------------------------------------
        /**
         * Create done functionality, back to main page
         */
        done = findViewById(R.id.buttonDone);
        done.setOnClickListener(view -> {
            Intent act = new Intent(BookingSuccessActivity.this,CastleSelectionActivity.class);
            startActivity(act);
            /* Transition animation between screens */
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });
        //-------------------------------------------------------------------------------------------------


        //------------------------------------- Get data and display --------------------------------------
        //Get the data from previous screen
        String date = getIntent().getStringExtra("date");
        String castle = getIntent().getStringExtra("castle");
        String station = getIntent().getStringExtra("selectedStation");
        Intent mIntent = getIntent();
        int tickets = mIntent.getIntExtra("passengerNo",0);
        String journeyTime = getIntent().getStringExtra("duration");
        String IbBustime = getIntent().getStringExtra("IbBustime");
        String ObBustime = getIntent().getStringExtra("ObBustime");
        String[] busses = mIntent.getStringArrayExtra("busses");
        String[] BusOperators = mIntent.getStringArrayExtra("busOperator");

        castleName = findViewById(R.id.chosenCastleName2);
        journeyDate = findViewById(R.id.journeyDate);
        passenger = findViewById(R.id.passengerNumber2);

        outboundStation1 = findViewById(R.id.departureObStation2);
        outboundStation2 = findViewById(R.id.arrivalObStation2);
        outboundTime = findViewById(R.id.outboundTime2);
        outboundDuration = findViewById(R.id.outboundDuration2);
        outboundBusNo = findViewById(R.id.outboundBus);
        outboundBusOperator = findViewById(R.id.outboundOperator);

        inboundStation1 = findViewById(R.id.departureIbStation2);
        inboundStation2 = findViewById(R.id.arrivalIbStation2);
        inboundTime = findViewById(R.id.inboundTime2);
        inboundDuration = findViewById(R.id.inboundDuration2);
        inboundBusNo = findViewById(R.id.inboundBus);
        inboundBusOperator = findViewById(R.id.inboundOperator);

        castleName.setText(castle);
        journeyDate.setText(date);
        passenger.setText(tickets + "");

        outboundStation1.setText(station);
        outboundStation2.setText(castle);
        outboundTime.setText(ObBustime);
        outboundDuration.setText(journeyTime);
        outboundBusNo.setText(busses[0]);
        outboundBusOperator.setText(BusOperators[0]);

        inboundStation1.setText(castle);
        inboundStation2.setText(station);
        inboundTime.setText(IbBustime);
        inboundDuration.setText(journeyTime);
        inboundBusNo.setText(busses[busses.length-1]);
        inboundBusOperator.setText(BusOperators[BusOperators.length-1]);

        //-------------------------------------------------------------------------------------------------
    }
}
