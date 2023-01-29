package com.example.csc8019_teamprojectnotemplate;
/**
 * @Screen BookingActivity
 * @description BookingActivity is to select the booking options such as date, time and no.of passengers.
 * It also displays the castle price on update of no.of passengers
 * Displays two images of castle selected in previous activity(done by Jirayut Visetsillapanont)
 * @author Raj Kiran Pasumarthi
 */


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import com.example.csc8019_teamprojectnotemplate.util.FireDatabase;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;


public class BookingActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private TimePicker t;
    private DatePicker d;
    private Spinner s;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        ImageView im1;
        ImageView im2;
        TextView item;
        Button back,next;
        /**
         * Create back functionality
         */
        back = findViewById(R.id.buttonBack3);
        back.setOnClickListener(view -> {
            onBackPressed();

            /* Transition animation between screens */
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });

        /**
         * Create numbers in dropdown menu of number of passengers
         * Inspiration: https://stackoverflow.com/questions/13377361/how-to-create-a-drop-down-list
         */
        //Instantiate spinner
        s = findViewById(R.id.spinner_passengers);

        //Use ArrayAdapter to render items in the noOfPassengers string array when the dropdown menu is accessed
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.noOfPassengers, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        //Attach the adapter to our dropdown menu (spinner)
        s.setAdapter(adapter);
        // Apply the adapter to the spinner
        s.setOnItemSelectedListener(this);

        /**
         * Implements the bottom navigation bar to enable switching between activities
         * @param item An array of resource ids
         * @author: Christoffer
         */
        // Initialize and assign variable
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
        //Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.home);
        // Perform item selected listener
        bottomNavigationView.setOnItemSelectedListener(item1 -> {

            switch(item1.getItemId())
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
        });


        /**
         * To move to next activity and transfer the selected data as well
         */
        next = findViewById(R.id.button1);
        t = (TimePicker) findViewById(R.id.timePicker1);
        d = (DatePicker) findViewById(R.id.datePicker1);

        //to set the minimum date as current date
        d.setMinDate(System.currentTimeMillis());

        im1 = findViewById(R.id.imageView1);
        im2 = findViewById(R.id.imageView2);
        item = findViewById(R.id.castleName);

        /**
         * getting the values of previous activity using intent
         */


        String cas = getIntent().getStringExtra("selectedCastle");
        String sta = getIntent().getStringExtra("selectedStation");
        item.setText(cas);

        //Display 2 images of selected castle
        if (cas.equals("Alnwick Castle")) {
            im1.setImageResource(R.drawable.a1_alnwick1);
            im2.setImageResource(R.drawable.a1_alnwick2);
        }else if (cas.equals("Auckland Castle")) {
            im1.setImageResource(R.drawable.a2_auckland1);
            im2.setImageResource(R.drawable.a2_auckland2);
        }else if (cas.equals("Bamburgh Castle")) {
            im1.setImageResource(R.drawable.a3_bamburgh1);
            im2.setImageResource(R.drawable.a3_bamburgh2);
        }else if (cas.equals("Barnard Castle")) {
            im1.setImageResource(R.drawable.a4_banard1);
            im2.setImageResource(R.drawable.a4_banard2);
        }

        //Set timepicker to be 24h instead of AM/PM to make database lookup easier
        t.setIs24HourView(true);

        /**
         * Next button is for after selecting all thr values and proceeding for the next activity
         * This method is activated when the button is pressed
         * This method sets time and date given by the user and coverts it into proper format
         * Initializes the Intent and sends the required data to next activity via intent
         */
        next.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {

                int hour = t.getCurrentHour();
                int min = t.getCurrentMinute();
                String time = "";

                //setting up the time in correct format; this is because when we are getting hours
                // and minutes from timepicker. we are not getting the "O" if the hours and minutes are less
                // than 10 ... e.g, 9 instead of 09
                if(hour < 10 && min < 10)
                    time = "0" + hour + ":" + "0" + min;
                else if(hour > 10 && min > 10)
                    time = "" + hour + ":" + min;
                else if(hour < 10 && min > 10)
                    time = "0" + hour + ":" + min;
                else if(hour > 10 && min < 10)
                    time = hour + ":" + "0" + min;

               //Getting date from the timepicker, where 'd' is the variable for date spinner
                int day = d.getDayOfMonth();
                int month = d.getMonth() + 1;
                int year = d.getYear();

                /**
                 * changing the format of date and arranging in an oder which could able to parse into LocalDate
                 */
                String d = "" + year + "-" + month + "-" + day;
                if(day < 10 && month < 10) d = "" + year + "-0" + month + "-0" + day;
                else if(day < 10 && month >= 10) d = "" + year + "-" + month + "-0" + day;
                else if(day >= 10 && month < 10) d = "" + year + "-0" + month + "-" + day;

                /**
                 * getting the day name and month name  from the date selected by the user
                 */
                LocalDate currentDate = LocalDate.parse(d);
                Month monthName = currentDate.getMonth();
                DayOfWeek dayName = currentDate.getDayOfWeek();
                int day1 = currentDate.getDayOfMonth();
                String date = "" + dayName + ", " + day1 + " " + monthName + " " + year;

                /**
                 *getting the no.of passengers and converting it into integer
                 */
                int price1 =  Integer.parseInt(s.getSelectedItem().toString());


                /**
                 * Initializing an intent and transferring all the required data for next page
                 */
                Intent act=new Intent(BookingActivity.this, OutboundActivity.class);
                act.putExtra("castle",cas);
                act.putExtra("time",time);
                act.putExtra("date",date);
                act.putExtra("tickets",price1);
                act.putExtra("hour",hour);
                act.putExtra("min",min);
                act.putExtra("selectedStation", sta);

                startActivity(act);

                /* Transition animation between screens */
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        //Initial display castle price
        getTotalCastlePrice(1);
    }


    /**
     * To get the price of castle from database and calculate it according to the selected no.of passengers
     * @param parent The AdapterView where the selection happened
     * @param view The view within the AdapterView that was clicked
     * @param position The position of the view in the adapter
     * @param id The row id of the item that is selected
     * @author: Ying Xie(Y.Xie28@newcastle.ac.uk)
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int numberOfPassengers = Integer.parseInt(s.getSelectedItem().toString());
        getTotalCastlePrice(numberOfPassengers);
    }

    /**
     * get the final price the user will have to pay for the castle
     * @param numberOfPassengers the number of passengers
     * @author: Ying Xie(Y.Xie28@newcastle.ac.uk)
     */
    @SuppressLint("SetTextI18n")
    public void getTotalCastlePrice(int numberOfPassengers){
        String castleName = getIntent().getStringExtra("selectedCastle");
        Object castlePrice = FireDatabase.getOneTimeData("castle",castleName,"castle_price");
        BigDecimal unitCastlePrice = BigDecimal.valueOf(Double.valueOf(castlePrice.toString()));
        BigDecimal totalPrice = unitCastlePrice.multiply(BigDecimal.valueOf(numberOfPassengers));
        TextView totalPriceCastle = findViewById(R.id.totalPriceCastle);
        totalPriceCastle.setText("£" + totalPrice);
    }

    /**
     *
     * @param parent The AdapterView where the selection happened
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }


}