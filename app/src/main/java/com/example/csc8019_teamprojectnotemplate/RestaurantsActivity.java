package com.example.csc8019_teamprojectnotemplate;

/**
 * @Screen RestaurantsActivity
 * @description RestaurantsActivity is to display the nearby restaurants regarding the selected castle on the map.
 * @author Jirayut Visetsillapanont
 */

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class RestaurantsActivity extends FragmentActivity implements OnMapReadyCallback {

    //Initialize variables
    private GoogleMap mMap;

    //Latitude, Longtitude variables(Castles)
    private final LatLng ALNWICK = new LatLng(55.415631499636405, -1.7059150371564835);
    private final LatLng AUCKLAND = new LatLng(54.666840948756715, -1.6701753503125378);
    private final LatLng BAMBURGH = new LatLng(55.608977150296255, -1.7098951887078466);
    private final LatLng BARNARD = new LatLng(54.543640388496925, -1.9261694317608957);
    private final LatLng DEFAULT = new LatLng(52.829069885330654, -3.725293800670304);

    //Latitude, Longtitude variables(Restaurants)
    //Alnwick Castle
    private final LatLng RESTAURANT1 = new LatLng(55.41506358457273, -1.7077700725593485); //Restaurant at the Castle
    private final LatLng RESTAURANT2 = new LatLng(55.41489835035168, -1.708614250092873); //The Dirty Bottles
    private final LatLng RESTAURANT3 = new LatLng(55.414592665220724, -1.7082212708962323); //Mumbai of Alnwick
    private final LatLng RESTAURANT4 = new LatLng(55.41629455776524, -1.708978119719392); //The Bailiffgate Bistro
    private final LatLng RESTAURANT5 = new LatLng(55.413518617626444, -1.706576580184366); //Melvyn's Cafe
    private final LatLng RESTAURANT6 = new LatLng(55.413189256680866, -1.7045592263618845); //Hardys Bistro
    private final LatLng RESTAURANT7 = new LatLng(55.41308449701301, -1.704039136194533); //Yan's Restaurant
    private final LatLng RESTAURANT8 = new LatLng(55.412540464669576, -1.7033732782509519); //Caffe Tirreno
    private final LatLng RESTAURANT9 = new LatLng(55.41279983008509, -1.7082686500311728); //Adam and Eve
    private final LatLng RESTAURANT10 = new LatLng(55.412479760169326, -1.709157434281643); //Bay Leaf Lounge

    //Auckland Castle
    private final LatLng RESTAURANT11 = new LatLng(54.666691977902964, -1.6705532901754367); //Bishop's Kitchen
    private final LatLng RESTAURANT12 = new LatLng(54.665433704702366, -1.6728140317326645); //Chang Thai
    private final LatLng RESTAURANT13 = new LatLng(54.66542068556685, -1.6765073329411844); //Spice Lounge
    private final LatLng RESTAURANT14 = new LatLng(54.66447309974526, -1.6767719898823148); //The Hut
    private final LatLng RESTAURANT15 = new LatLng(54.66525167010799, -1.678045115376484); //Knead A Slice

    //Bamburgh Castle
    private final LatLng RESTAURANT16 = new LatLng(55.607604307774075, -1.714464311624519); //Wyndenwell
    private final LatLng RESTAURANT17 = new LatLng(55.6074923556875, -1.7148607016031057); //The Copper Kettle Tea Rooms
    private final LatLng RESTAURANT18 = new LatLng(55.607356413438474, -1.715780892624825); //The Pantry
    private final LatLng RESTAURANT19 = new LatLng(55.60638869384894, -1.717103088551793); //The Potted Lobster
    private final LatLng RESTAURANT20 = new LatLng(55.61194311362359, -1.7169187813874596); //The Hut at Bamburgh

    //Barnard Castle
    private final LatLng RESTAURANT21 = new LatLng(54.545675523401556, -1.9235299598828655); //Fish & Chips at 149 (Barnard Castle)
    private final LatLng RESTAURANT22 = new LatLng(54.545593406365924, -1.9237069196654903); //The Golden Gate
    private final LatLng RESTAURANT23 = new LatLng(54.54479686254351, -1.9241599367090099); //Valentine's Restaurant
    private final LatLng RESTAURANT24 = new LatLng(54.54404546930833, -1.9238909578309809); //Barnard Castle Fisheries
    private final LatLng RESTAURANT25 = new LatLng(54.542337332666655, -1.9240042120971743); //Penny's

    //Markers variables
    private Marker markerDefault; //United Kingdom


    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);

        //----------------------------------------- Navigate Bar ------------------------------------------
        /**
         * Implements the bottom navigation bar to enable switching between activities
         * @param item An array of resource ids
         * @author: Christoffer
         */
        // Initialize and assign variable
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
        // Set Restaurants selected
        bottomNavigationView.setSelectedItemId(R.id.restaurants);
        // Perform item selected listener
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch(item.getItemId())
            {
                case R.id.home:
                    startActivity(new Intent(getApplicationContext(), CastleSelectionActivity.class));
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                    return true;
                case R.id.route:
                    startActivity(new Intent(getApplicationContext(),RouteActivity.class));
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                    return true;
                case R.id.restaurants:
                    return true;
                case R.id.attractions:
                    startActivity(new Intent(getApplicationContext(),AttractionsActivity.class));
                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                    return true;
            }
            return false;
        });
        //-------------------------------------------------------------------------------------------------


        //------------------------------------------ Back button ------------------------------------------
        /**
         * Implements the back button to enable switching back to the previous screen.
         * @author Jirayut Visetsillapanont
         */
        //Connect front-end to back-end
        Button back;
        back = findViewById(R.id.buttonBack2);
        back.setOnClickListener(view -> {
            onBackPressed();
            /* Transition animation between screens */
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });
        //-------------------------------------------------------------------------------------------------


        //--------------------------------------- Set adapter value ---------------------------------------
        //Implement dropdown list
        Spinner spinner = findViewById(R.id.selectCastle);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.planets_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            /**
             * Mechanism to display the nearby restaurants regarding the selected castle from the dropdown(adapter).
             * @param parent AdapterView where selection occurs.
             * @param view The view inside the AdapterView is being clicked
             * @param position Location of the view in the adapter.
             * @param l Selected item row code.
             */
            //Set Origin and Destination values
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                String castleName = parent.getItemAtPosition(position).toString();

                if(castleName.equalsIgnoreCase("Alnwick Castle"))
                {
                    showMap("Alnwick Castle", ALNWICK);
                }
                else if(castleName.equalsIgnoreCase("Auckland Castle"))
                {
                    showMap("Auckland Castle", AUCKLAND);
                }
                else if(castleName.equalsIgnoreCase("Bamburgh Castle"))
                {
                    showMap("Bamburgh Castle", BAMBURGH);
                }
                else if(castleName.equalsIgnoreCase("Barnard Castle"))
                {
                    showMap("Barnard Castle", BARNARD);
                }
                else if(castleName.equalsIgnoreCase("Select Castle"))
                {
                    showMap("United Kingdom", DEFAULT);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        //-------------------------------------------------------------------------------------------------

        //-------------------------------------------- Map View -------------------------------------------
        //Build the map
        //Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.googleMap);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        //-------------------------------------------------------------------------------------------------
    }

    //------------------------------------------- Locations -------------------------------------------

    /**
     * Display all nearby restaurants around the selected castle.
     * @param castleName String to transfer the castle's name to the if condition.
     * @param location GPS coordinates, latitude and longitude of the restaurants.
     * @auther Jirayut Visetsillapanont
     */
    //Method to display specific location from selected item(dropdown)
    private void showMap(String castleName, LatLng location) {
        mMap.clear();
        if(!castleName.equalsIgnoreCase("United Kingdom")) {
            //Display marker of selected location
            mMap.addMarker(new MarkerOptions().position(location).title(castleName).icon(BitmapFromVector(getApplicationContext(), R.drawable.pin_castle_svg)));
            //Move the map's camera to the selected location.
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 16.0f));
            if (castleName.equalsIgnoreCase("Alnwick Castle")) {
                mMap.addMarker(new MarkerOptions().position(RESTAURANT1).title("Restaurant at the Castle").icon(BitmapFromVector(getApplicationContext(), R.drawable.pin_restaurant_svg)));
                mMap.addMarker(new MarkerOptions().position(RESTAURANT2).title("The Dirty Bottles").icon(BitmapFromVector(getApplicationContext(), R.drawable.pin_restaurant_svg)));
                mMap.addMarker(new MarkerOptions().position(RESTAURANT3).title("Mumbai of Alnwick").icon(BitmapFromVector(getApplicationContext(), R.drawable.pin_restaurant_svg)));
                mMap.addMarker(new MarkerOptions().position(RESTAURANT4).title("The Bailiffgate Bistro").icon(BitmapFromVector(getApplicationContext(), R.drawable.pin_restaurant_svg)));
                mMap.addMarker(new MarkerOptions().position(RESTAURANT5).title("Melvyn's Cafe").icon(BitmapFromVector(getApplicationContext(), R.drawable.pin_restaurant_svg)));
                mMap.addMarker(new MarkerOptions().position(RESTAURANT6).title("Hardys Bistro").icon(BitmapFromVector(getApplicationContext(), R.drawable.pin_restaurant_svg)));
                mMap.addMarker(new MarkerOptions().position(RESTAURANT7).title("Yan's Restaurant").icon(BitmapFromVector(getApplicationContext(), R.drawable.pin_restaurant_svg)));
                mMap.addMarker(new MarkerOptions().position(RESTAURANT8).title("Caffe Tirreno").icon(BitmapFromVector(getApplicationContext(), R.drawable.pin_restaurant_svg)));
                mMap.addMarker(new MarkerOptions().position(RESTAURANT9).title("Adam and Eve").icon(BitmapFromVector(getApplicationContext(), R.drawable.pin_restaurant_svg)));
                mMap.addMarker(new MarkerOptions().position(RESTAURANT10).title("Bay Leaf Lounge").icon(BitmapFromVector(getApplicationContext(), R.drawable.pin_restaurant_svg)));
            } else if (castleName.equalsIgnoreCase("Auckland Castle")) {
                mMap.addMarker(new MarkerOptions().position(RESTAURANT11).title("Bishop's Kitchen").icon(BitmapFromVector(getApplicationContext(), R.drawable.pin_restaurant_svg)));
                mMap.addMarker(new MarkerOptions().position(RESTAURANT12).title("Chang Thai").icon(BitmapFromVector(getApplicationContext(), R.drawable.pin_restaurant_svg)));
                mMap.addMarker(new MarkerOptions().position(RESTAURANT13).title("Spice Lounge").icon(BitmapFromVector(getApplicationContext(), R.drawable.pin_restaurant_svg)));
                mMap.addMarker(new MarkerOptions().position(RESTAURANT14).title("The Hut").icon(BitmapFromVector(getApplicationContext(), R.drawable.pin_restaurant_svg)));
                mMap.addMarker(new MarkerOptions().position(RESTAURANT15).title("Knead A Slice").icon(BitmapFromVector(getApplicationContext(), R.drawable.pin_restaurant_svg)));
            } else if (castleName.equalsIgnoreCase("Bamburgh Castle")) {
                mMap.addMarker(new MarkerOptions().position(RESTAURANT16).title("Wyndenwell").icon(BitmapFromVector(getApplicationContext(), R.drawable.pin_restaurant_svg)));
                mMap.addMarker(new MarkerOptions().position(RESTAURANT17).title("The Copper Kettle Tea Rooms").icon(BitmapFromVector(getApplicationContext(), R.drawable.pin_restaurant_svg)));
                mMap.addMarker(new MarkerOptions().position(RESTAURANT18).title("The Pantry").icon(BitmapFromVector(getApplicationContext(), R.drawable.pin_restaurant_svg)));
                mMap.addMarker(new MarkerOptions().position(RESTAURANT19).title("The Potted Lobster").icon(BitmapFromVector(getApplicationContext(), R.drawable.pin_restaurant_svg)));
                mMap.addMarker(new MarkerOptions().position(RESTAURANT20).title("The Hut at Bamburgh").icon(BitmapFromVector(getApplicationContext(), R.drawable.pin_restaurant_svg)));
            } else if (castleName.equalsIgnoreCase("Barnard Castle")) {
                mMap.addMarker(new MarkerOptions().position(RESTAURANT21).title("Fish & Chips at 149").icon(BitmapFromVector(getApplicationContext(), R.drawable.pin_restaurant_svg)));
                mMap.addMarker(new MarkerOptions().position(RESTAURANT22).title("The Golden Gate").icon(BitmapFromVector(getApplicationContext(), R.drawable.pin_restaurant_svg)));
                mMap.addMarker(new MarkerOptions().position(RESTAURANT23).title("Valentine's Restaurant").icon(BitmapFromVector(getApplicationContext(), R.drawable.pin_restaurant_svg)));
                mMap.addMarker(new MarkerOptions().position(RESTAURANT24).title("Barnard Castle Fisheries").icon(BitmapFromVector(getApplicationContext(), R.drawable.pin_restaurant_svg)));
                mMap.addMarker(new MarkerOptions().position(RESTAURANT25).title("Penny's").icon(BitmapFromVector(getApplicationContext(), R.drawable.pin_restaurant_svg)));
            }
        } else {
            //Mark location to default location(UK).
            markerDefault = mMap.addMarker(new MarkerOptions().position(location).title(castleName));
            //Move the map's camera to the default location(UK).
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 5.5f));
            //Hide marker
            markerDefault.setVisible(false);
        }
    }

    /**
     * Display the marker at the specific location determined below(default location).
     * @param googleMap Maps API resource from Google.
     * @author Jirayut Visetsillapanont
     */
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.clear();

        //Default location
        markerDefault = mMap.addMarker(new MarkerOptions().position(DEFAULT).title("Default Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(DEFAULT, 5.5f));
        markerDefault.setVisible(false);
    }
    //-------------------------------------------------------------------------------------------------


    //---------------------------------------- Edit pin style -----------------------------------------
    /**
     * Creating bitmap file by converting from vector file.
     * @param context The running environment used.
     * @param vectorResId Id of vector resource.
     * @return null
     * @reference https://www.geeksforgeeks.org/how-to-add-custom-marker-to-google-maps-in-android/
     */
    private BitmapDescriptor BitmapFromVector(Context context, int vectorResId) {
        //Below line is use to generate a drawable.
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        //Below line is use to set bounds to our vector drawable.
        if (vectorDrawable != null) {
            vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        }
        //Below line is used to create a bitmap for our
        //Drawable which we have added.
        Bitmap bitmap = null;
        if (vectorDrawable != null) {
            bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }
        //Below line is used to add bitmap in our canvas.
        Canvas canvas = new Canvas(bitmap);
        //Below line is use to draw our
        //Vector drawable in canvas.
        if (vectorDrawable != null) {
            vectorDrawable.draw(canvas);
        }
        //After generating our bitmap we are returning our bitmap.
        if (bitmap != null) {
            return BitmapDescriptorFactory.fromBitmap(bitmap);
        }
        return null;
    }
    //-------------------------------------------------------------------------------------------------
}