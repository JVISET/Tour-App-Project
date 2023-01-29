package com.example.csc8019_teamprojectnotemplate;

/**
 * @Screen BookingSummaryActivity
 * @description BookingSummaryActivity is to display the all the booking details that the user has selected.
 * And it has pay option which allows user to confirm the details and proceed for tickets booking
 * @author Raj Kiran Pasumarthi
 */

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.csc8019_teamprojectnotemplate.util.FireDatabase;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class BookingSummaryActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_summary);
        /**
         * declaring the state variables
         */
         Button back;
         Button pay;
         TextView castleName, passengerNumber,totalCastlePrice,calculateCastlePrice,totalPrice;
         TextView departObStation,arrivalObStation,outboundDate,outboundTime,outboundBusInfo,outboundDuration,totalOutboundPrice,calculateOutboundPrice;
         TextView departIbStation,arrivalIbStation,inboundDate,inboundTime,inboundBusInfo,inboundDuration,totalInboundPrice,calculateInboundPrice;
         TextView note1, changestation1,note2,changestation2;
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


        //------------------------------------- Get data and display --------------------------------------
        //Get the data from previous screen using intent
        String date = getIntent().getStringExtra("date");
        String castle = getIntent().getStringExtra("castle");
        String station = getIntent().getStringExtra("selectedStation");
        Intent mIntent = getIntent();
        int tickets = mIntent.getIntExtra("passengerNo",0);
        Float busprice = mIntent.getFloatExtra("busprice",0);
        System.out.println("checking prices.....");
        String ObBusname = getIntent().getStringExtra("ObBusname");
        String IbBusname = getIntent().getStringExtra("IbBusname");
        String ObBustime = getIntent().getStringExtra("ObBustime");
        String IbBustime = getIntent().getStringExtra("Inboundtime");
        String[] buses = mIntent.getStringArrayExtra("buses");

        /**
         * getting castleprice, busOperator and journey_time details from the firebase
         */
        Float castlePrice = Float.parseFloat(FireDatabase.getOneTimeData("castle",castle,"castle_price").toString());
        String obj = FireDatabase.getOneTimeData("bus",castle,"bus_operator").toString();
        String journeytime = FireDatabase.getOneTimeData("bus",castle,"total_time").toString();

        //to remove all the spaces in between a string
        obj = obj.replaceAll("\\s+", "");

        // Creating a StringBuilder object
        StringBuilder sb = new StringBuilder(obj);

        // Removing the last character of a string
        //this is being done because we are getting the data from firebase as "[.....]"
        sb.deleteCharAt(obj.length() - 1);

        // Removing the first character of a string which is "]"
        sb.deleteCharAt(0);

       //storing busOperator details in an array
        String[] busOperatorArray= sb.toString().split(",");


        /**
         * initializing the state variables
         */
        //------------------------------------------------------------------------------------------------------------------------------------
        castleName = findViewById(R.id.chosenCastleName);

        departObStation = findViewById(R.id.departureObStation);
        departIbStation = findViewById(R.id.departureRtStation);

        arrivalObStation = findViewById(R.id.arrivalObStation);
        arrivalIbStation = findViewById(R.id.arrivalRtStation);

        outboundDate = findViewById(R.id.outboundDate);
        inboundDate = findViewById(R.id.returnDate);

        outboundTime = findViewById(R.id.outboundTime);
        inboundTime = findViewById(R.id.returnTime);

        outboundBusInfo = findViewById(R.id.outboundBusInfo);
        inboundBusInfo = findViewById(R.id.returnBusInfo);

        outboundDuration = findViewById(R.id.outboundDuration);
        inboundDuration = findViewById(R.id.returnDuration);

        totalCastlePrice = findViewById(R.id.totalCastlePrice);
        totalOutboundPrice = findViewById(R.id.totalOutboundPrice);
        totalInboundPrice = findViewById(R.id.totalReturnPrice);
        totalPrice = findViewById(R.id.totalPrice);

        calculateCastlePrice = findViewById(R.id.calculateCastlePrice);
        calculateOutboundPrice = findViewById(R.id.calculateOutboundPrice);
        calculateInboundPrice = findViewById(R.id.calculateReturnPrice);

        passengerNumber = findViewById(R.id.passengerNumber);

        note1 = findViewById(R.id.textNote);
        note2 = findViewById(R.id.textNote2);

        changestation1 = findViewById(R.id.changeStationNote);
        changestation2 = findViewById(R.id.changeStationNote2);
        //-----------------------------------end of initializing the state variable-----------------------------------

        /**
         *Note variables are used when there are more than one bus involved in a journey
         * setting up the note view as invisible initially
         */
        note1.setVisibility(View.INVISIBLE);
        note2.setVisibility(View.INVISIBLE);
        changestation1.setVisibility(View.INVISIBLE);
        changestation2.setVisibility(View.INVISIBLE);

        //Set values to display
        //------------------------------------------------------------------------------------------------------------------------
        castleName.setText(castle);

        departObStation.setText(station);
        departIbStation.setText(castle);

        arrivalObStation.setText(castle);
        arrivalIbStation.setText(station);

        outboundDate.setText(date);
        inboundDate.setText(date);

        outboundTime.setText(ObBustime);
        inboundTime.setText(IbBustime);

        outboundBusInfo.setText("Bus Number: " + ObBusname + " | " + busOperatorArray[0]);
        inboundBusInfo.setText("Bus Number: " + IbBusname + " | " + busOperatorArray[busOperatorArray.length-1]);

        outboundDuration.setText(journeytime);
        inboundDuration.setText(journeytime);

        //Set 2 decimal places
        String newCastlePrice = String.format("%.02f", castlePrice * tickets);
        String newBusPrice = String.format("%.02f", busprice * tickets);
        String newTotalPrice = String.format("%.02f", (castlePrice * tickets) + ((busprice * tickets)*2));
        String newCastleTicket = String.format("%.02f", castlePrice);
        String newBusTicket = String.format("%.02f", busprice);

        totalCastlePrice.setText("£" + newCastlePrice);
        totalOutboundPrice.setText("£" +  newBusPrice);
        totalInboundPrice.setText("£" + newBusPrice);
        totalPrice.setText("£" + newTotalPrice);

        calculateCastlePrice.setText("(£ " + newCastleTicket + " x " + tickets + ")");
        calculateOutboundPrice.setText("(£ " + newBusTicket + " x " + tickets + ")");
        calculateInboundPrice.setText("(£ " + newBusTicket + " x " + tickets + ")");

        passengerNumber.setText("" + tickets); //Convert int to String
        //---------------------------------------end-------------------------------------------------------------------------------

        /**
         * getting the transit station details if there are more than one bus has to be used in a journey
         * And making transit details visible as a note in the summary
         */
        if(buses.length>1) {
            String change = FireDatabase.getOneTimeData("bus",castle,"transit_station").toString();

            changestation1.setText("\tChange the bus at " + change + " to bus No." + buses[buses.length-1] + "\nto reach your destination. Above duration and\nprice are for complete outbound journey.");
            changestation2.setText("\tChange the bus at " + change + " to bus No."+ buses[0] + "\nto reach your destination. Above duration and\nprice are for complete return journey.");

            note1.setVisibility(View.VISIBLE);
            note2.setVisibility(View.VISIBLE);
            changestation1.setVisibility(View.VISIBLE);
            changestation2.setVisibility(View.VISIBLE);
        }
        //-------------------------------------------------------------------------------------------------


        //------------------------------------------ Back button ------------------------------------------
        /**
         * Create back functionality
         */
        back = findViewById(R.id.buttonBack);
        back.setOnClickListener(view -> {
            onBackPressed();
            /* Transition animation between screens */
           overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });
        //-------------------------------------------------------------------------------------------------


        //------------------------------------------- Pay button ------------------------------------------
        /**
         * Create pay functionality
         * It also creates an intent and transfers data to the next activity
         */
        pay = findViewById(R.id.buttonPay);
        pay.setOnClickListener(view -> {
            Intent act = new Intent(BookingSummaryActivity.this, BookingEmailActivity.class);
            act.putExtra("date",date);
            act.putExtra("castle",castle);
            act.putExtra("selectedStation", station);
            act.putExtra("passengerNo", tickets);
            act.putExtra("duration", journeytime);
            act.putExtra("IbBustime", IbBustime);
            act.putExtra("ObBustime", ObBustime);
            act.putExtra("busses", buses);
            act.putExtra("busoperator", busOperatorArray);

            startActivity(act);
            /* Transition animation between screens */
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
        //-------------------------------------------------------------------------------------------------

    }
}
