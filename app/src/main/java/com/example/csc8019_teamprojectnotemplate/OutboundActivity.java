package com.example.csc8019_teamprojectnotemplate;
/**
 * @Screen InboundActivity
 * @description InboundActivity is used to display and choose the return bus timings available from the outbound time selected by the user in previous activity
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

public class OutboundActivity extends AppCompatActivity {

   //declaring state variables
    EditText castleName,editdate,no_of_tickets, stationName;
    Button timebutton1,timebutton2,timebutton3,timebutton4,timebutton5;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outbound);

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
         * @param item An array of resource ids
         */
        Button back;
        back = findViewById(R.id.buttonBack3);
        back.setOnClickListener(view -> {
            onBackPressed();
            /* Transition animation between screens */
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });
        //-------------------------------------------------------------------------------------------------


        //------------------------------------- Get data and display --------------------------------------
        //Get the data from previous screen
        String time = getIntent().getStringExtra("time");
        String date = getIntent().getStringExtra("date");
        Intent mIntent = getIntent();
        int tickets = mIntent.getIntExtra("tickets", 0);
        String castle = getIntent().getStringExtra("castle");
        String station = getIntent().getStringExtra("selectedStation");


        //initializing state variable
        castleName = findViewById(R.id.castle_name);
        stationName = findViewById(R.id.outboundBusStation);
        editdate = findViewById(R.id.date);
        no_of_tickets = findViewById(R.id.disclaimer1);
        //Set value to display
        castleName.setText(castle);
        stationName.setText("From " + station);
        editdate.setText(date);
        no_of_tickets.setText("*Price calculated for " + tickets + " passenger(s).");


        //------------------------------------------------------------------------------------------------------------------

        //-------------------Time_Buttons----------------------------------------------------------------------
        //initializing timebuttons and adding them to arraylist for the further use

        timebutton1 = findViewById(R.id.timeButton10);
        timebutton2 = findViewById(R.id.timeButton11);
        timebutton3 = findViewById(R.id.timeButton12);
        timebutton4 = findViewById(R.id.timeButton13);
        timebutton5 = findViewById(R.id.timeButton14);

        ArrayList<Button> buttons = new ArrayList<>();
        buttons.add(timebutton1);
        buttons.add(timebutton2);
        buttons.add(timebutton3);
        buttons.add(timebutton4);
        buttons.add(timebutton5);

        //-----------------------------------------end----------------------------------------------------------


        //Setting all the buttons to invisible initially
        for (int k = 0; k < buttons.size(); k++)
            buttons.get(k).setVisibility(View.INVISIBLE);

        //getting required values from firebase
        String obj = FireDatabase.getOneTimeData("bus", castle, "bus_name").toString();

        //to remove all the spaces in between a string
        obj = obj.replaceAll("\\s+", "");

        // Creating a StringBuilder object
        StringBuilder sb = new StringBuilder(obj);

        // Removing the last character of a string
        //this is being dne because we are getting the data from firebase as "[.....]"
        sb.deleteCharAt(obj.length() - 1);

        // Removing the first character of a string which is "]"
        sb.deleteCharAt(0);

       //Splitting the string returned from firebase by "," inorder to store a single bustime in an index of array.
        String[] busses = sb.toString().split(",");

        //getting the busprice from firebase and calculating by the no.of passengers
        Float busprice = Float.parseFloat("0");//converting string into float
        for (int i = 0; i < busses.length; i++) {
            busprice = busprice + Float.parseFloat(FireDatabase.getOneTimeData("bus_time", busses[i], "bus_price").toString());
        }

        //calculatig the final castle price depending on the passengers
        Float finalprice = tickets * busprice;

        //getting the bustimes from firebase
        Object b2 = FireDatabase.getOneTimeData("bus_time", busses[0], "start_time");

        //storing the above retrieved bustimes in an objective arraylist
        ArrayList bustime = (ArrayList) b2;


        /**
         * if condition throws a warning when there are no bused available
         * else condition displays the bustimes in time_buttons , maximum of 5 buttons
         */
        if(bustime.get(bustime.size() - 1).toString().compareTo(time) < 0) {
            Toast toast = Toast.makeText(getApplicationContext(),"Sorry, no buses are available on selected time", Toast.LENGTH_SHORT);
            toast.setMargin(50,50);
            toast.show();
        }
        else {
            //Setting up of timebuttons dynamically using loop
            int j = 0;
            for (int i = 0; i < bustime.size(); i++) {
                if (bustime.get(i).toString().compareTo(time) == 0 || bustime.get(i).toString().compareTo(time) > 0) {
                    buttons.get(j).setText(bustime.get(i).toString() + "      |       £" + finalprice );
                    buttons.get(j).setVisibility(View.VISIBLE);
                    j++;
                }
                if (j > 4) break;
            }
//       ------------------------------------------------------------------------------------------------

            //Creating Intent and forwarding the data---------------------------------------------------------------------------
            Intent act = new Intent(OutboundActivity.this, InboundActivity.class);
            act.putExtra("date", date);
            act.putExtra("castle", castle);
            act.putExtra("selectedStation", station);
            act.putExtra("tickets", tickets);
            act.putExtra("outboundprice", finalprice);
            act.putExtra("selectedbus", busses[0]);
            act.putExtra("ObBusname", busses[0]);
            act.putExtra("busses", busses);
            act.putExtra("busprice", busprice);
            //---------------------------------------------end--------------------------------------------------

            /**
             * Create next functionality to summary page when choose inbound time
             */
            //setting up onclick listener and starting the intent-------------------------------------------------------------------------------
            timebutton1.setOnClickListener(view -> {
                String[] temp = timebutton1.getText().toString().split(" ");
                act.putExtra("outboundTime", temp[0]);
                startActivity(act);
                /* Transition animation between screens */
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            });

            timebutton2.setOnClickListener(view -> {
                String[] temp = timebutton2.getText().toString().split(" ");
                act.putExtra("outboundTime", temp[0]);
                startActivity(act);
                /* Transition animation between screens */
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            });

            timebutton3.setOnClickListener(view -> {
                String[] temp = timebutton3.getText().toString().split(" ");
                act.putExtra("outboundTime", temp[0]);
                startActivity(act);
                /* Transition animation between screens */
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            });

            timebutton4.setOnClickListener(view -> {
                String[] temp = timebutton4.getText().toString().split(" ");
                act.putExtra("outboundTime", temp[0]);
                startActivity(act);
                /* Transition animation between screens */
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            });

            timebutton5.setOnClickListener(view -> {
                String[] temp = timebutton5.getText().toString().split(" ");
                act.putExtra("outboundTime", temp[0]);
                startActivity(act);
                /* Transition animation between screens */
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            });
            //--------------------------------------end------------------------------------------------------------

        }
    }
}
