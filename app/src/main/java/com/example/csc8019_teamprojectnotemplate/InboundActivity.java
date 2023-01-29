package com.example.csc8019_teamprojectnotemplate;
/**
 * @Screen InboundActivity
 * @description InboundActivity is used to display and choose the bus timings available from the time selected by the user in previous activity
 * It displays the timebuttons dynamically with maximum of 5 buttons
 * @author Raj Kiran Pasumarthi
 */


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.csc8019_teamprojectnotemplate.util.FireDatabase;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class InboundActivity extends AppCompatActivity {

  //declaring the state variables
    EditText castleName,editdate,no_of_tickets,stationName;
    Button timebutton1,timebutton2,timebutton3,timebutton4,timebutton5;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbound);

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


        //------------------------------------------ Back button ------------------------------------------
        /**
         * Create back functionality
         */
        Button back;
        back = findViewById(R.id.buttonBack3);

        back.setOnClickListener(view -> {
            onBackPressed();
            /* Transition animation between screens */
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });
        //-------------------------------------------------------------------------------------------------
        //-------------------Time_Buttons----------------------------------------------------------------------
        //initializing timebuttons and adding them to arraylist for the further use
        timebutton1 = findViewById(R.id.timeButton1);
        timebutton2 = findViewById(R.id.timeButton2);
        timebutton3 = findViewById(R.id.timeButton3);
        timebutton4 = findViewById(R.id.timeButton4);
        timebutton5 = findViewById(R.id.timeButton5);

        ArrayList<Button> buttons = new ArrayList<>();
        buttons.add(timebutton1);
        buttons.add(timebutton2);
        buttons.add(timebutton3);
        buttons.add(timebutton4);
        buttons.add(timebutton5);
       //----------------------------------------------------End----------------------------------------

        //Setting all the buttons to invisible initially
        for (int k = 0; k < buttons.size(); k++)
            buttons.get(k).setVisibility(View.INVISIBLE);

        //------------------------------------- Get data and display --------------------------------------
        //Get the data from previous screen using intent
        String time = getIntent().getStringExtra("time");
        String date = getIntent().getStringExtra("date");
        String castle = getIntent().getStringExtra("castle");
        String station = getIntent().getStringExtra("selectedStation");
        Intent mIntent = getIntent();
        int tickets = mIntent.getIntExtra("tickets", 0);
        String[] buses = mIntent.getStringArrayExtra("busses");
        Float outboundprice = mIntent.getFloatExtra("outboundprice", 0);
        Float busprice = mIntent.getFloatExtra("busprice", 0);
        String busname = getIntent().getStringExtra("selectedbus");
        String outboundtime = getIntent().getStringExtra("outboundTime");
        String IbBusname = buses[buses.length - 1];
        String ObBusname = buses[0];



        //Initializing the state variables
        castleName = findViewById(R.id.castle_name);
        stationName = findViewById(R.id.inboundBusStation);
        editdate = findViewById(R.id.date);
        no_of_tickets = findViewById(R.id.disclaimer1);

        //Set value to display
        castleName.setText(station);
        stationName.setText("From " + castle);
        editdate.setText(date);
        no_of_tickets.setText("*Price calculated for " + tickets + " passenger(s).");


        //getting journey time details from the firebase
        String journeytime = FireDatabase.getOneTimeData("bus", castle, "total_time").toString();

        //Splitting time by ":" inorder to get hours and minutes
        String[] temp1 = outboundtime.split(":");
        String[] temp2 = journeytime.split(":");

        /**
         * adding two hours after the outbound departure and journey time , which allows application to fetch inbound bus timings
         * calculating the minutes and incrementing the hour by 1 if minutes are more than 60
         */
        int hour = Integer.parseInt(temp1[0]) + Integer.parseInt(temp2[0]) + 2;
        int min = Integer.parseInt(temp1[1]) + Integer.parseInt(temp2[1]);
        if (min % 60 != 0) {
            hour = hour + (min / 60);
            min = min % 60;
        }

        /**
         * setting up the time in an appropriate order which is used to fetch bus timing details from firebase
         * adding 0 before hr/min value if the hour or minute is less than 10
         */

        if (hour < 10 && min < 10)
            time = "0" + hour + ":" + "0" + min;
        else if (hour > 10 && min > 10)
            time = "" + hour + ":" + min;
        else if (hour < 10 && min >= 10)
            time = "0" + hour + ":" + min;
        else if (hour >= 10 && min < 10)
            time = hour + ":" + "0" + min;


      //getting inbound bus times from fire base and making it the list as arraylist for the further use
        Object b2 = FireDatabase.getOneTimeData("bus_time", busname, "return_time");
        ArrayList bustime = (ArrayList) b2;

        /**
         * if condition throws a warning when there are no bused available
         * else condition displays the bustimes in time_buttons , maximum of 5 buttons
         */
        if (bustime.get(bustime.size()-1).toString().compareTo(time)<0) {
            Toast toast = Toast.makeText(getApplicationContext(), "Sorry, no return buses are available as per selected Outbound time", Toast.LENGTH_SHORT);
            toast.setMargin(100, 100);
            toast.show();

        }
        else {
            int j = 0;
            for (int i = 0; i < bustime.size(); i++) {
                if (bustime.get(i).toString().compareTo(time) == 0 || bustime.get(i).toString().compareTo(time) > 0) {
                    buttons.get(j).setText(bustime.get(i).toString() + "       |       £" + outboundprice);
                    buttons.get(j).setVisibility(View.VISIBLE);
                    j++;
                }
                if (j > 4) break;
            }

            //-------------------------------------------------------------------------------------------------





// forwarding data by creating intent-------------------------------------------------------------------------------------------
            Intent act = new Intent(InboundActivity.this, BookingSummaryActivity.class);
            act.putExtra("date", date);
            act.putExtra("castle", castle);
            act.putExtra("selectedStation", station);
            act.putExtra("passengerNo", tickets);
            act.putExtra("IbBusname", IbBusname);
            act.putExtra("ObBusname", ObBusname);
            act.putExtra("ObBustime", outboundtime);
            act.putExtra("ObBusprice", outboundprice);
            act.putExtra("busprice", busprice);
            act.putExtra("buses", buses);

//----------------------------------------------------end--------------------------------------------------

            /**
             * Create next functionality to summary page when choose inbound time
             */

 //setting up onclick listener and starting the intent-------------------------------------------------------------------------------
            timebutton1.setOnClickListener(view -> {
                String[] temp = timebutton1.getText().toString().split(" ");
                act.putExtra("Inboundtime", temp[0]);
                startActivity(act);
                /* Transition animation between screens */
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            });
            timebutton2.setOnClickListener(view -> {
                String[] temp = timebutton2.getText().toString().split(" ");
                act.putExtra("Inboundtime", temp[0]);
                startActivity(act);
                /* Transition animation between screens */
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            });

            timebutton3.setOnClickListener(view -> {

                String[] temp = timebutton3.getText().toString().split(" ");
                act.putExtra("Inboundtime", temp[0]);
                startActivity(act);
                /* Transition animation between screens */
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            });
            timebutton4.setOnClickListener(view -> {
                String[] temp = timebutton4.getText().toString().split(" ");
                act.putExtra("Inboundtime", temp[0]);
                startActivity(act);
                /* Transition animation between screens */
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            });
            timebutton5.setOnClickListener(view -> {
                String[] temp = timebutton5.getText().toString().split(" ");
                act.putExtra("Inboundtime", temp[0]);
                startActivity(act);
                /* Transition animation between screens */
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            });
//-------------------------------------------------------------------------------------------------------------------

        }
    }


}
