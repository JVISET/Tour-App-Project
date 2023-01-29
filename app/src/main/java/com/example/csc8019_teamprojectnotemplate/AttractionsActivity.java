package com.example.csc8019_teamprojectnotemplate;

/**
 * @Screen AttractionsActivity
 * @description AttractionsActivity is to display the nearby points of interest regarding the selected castle on the map.
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

public class AttractionsActivity extends FragmentActivity implements OnMapReadyCallback {

    //Initialize variables
    private GoogleMap mMap;

    //Latitude, Longtitude variables
    private final LatLng ALNWICK = new LatLng(55.415631499636405, -1.7059150371564835);
    private final LatLng AUCKLAND = new LatLng(54.666840948756715, -1.6701753503125378);
    private final LatLng BAMBURGH = new LatLng(55.608977150296255, -1.7098951887078466);
    private final LatLng BARNARD = new LatLng(54.543640388496925, -1.9261694317608957);
    private final LatLng DEFAULT = new LatLng(52.829069885330654, -3.725293800670304);

    //Latitude, Longtitude variables(Restaurants)
    //Alnwick Castle
    private final LatLng ATTRACTION1 = new LatLng(55.41629522917435, -1.7074213677254542); //Fusiliers Museum of Northumberland
    private final LatLng ATTRACTION2 = new LatLng(55.41621608594267, -1.7091817013595036); //Bailiffgate Museum
    private final LatLng ATTRACTION3 = new LatLng(55.41479156154474, -1.7089076145768924); //Hotspur Statue
    private final LatLng ATTRACTION4 = new LatLng(55.413656390191434, -1.6995737492663947); //The Alnwick Garden Poison Garden
    private final LatLng ATTRACTION5 = new LatLng(55.41737537770951, -1.7117960631243356); //St Michael's Parish Hall

    //Auckland Castle
    private final LatLng ATTRACTION6 = new LatLng(54.671370618454816, -1.6576119627996417); //High Park
    private final LatLng ATTRACTION7 = new LatLng(54.66532246926012, -1.673619048037031); //Spanish Gallery
    private final LatLng ATTRACTION8 = new LatLng(54.66585173428603, -1.6730666705109631); //Mining Art Gallery
    private final LatLng ATTRACTION9 = new LatLng(54.66590895171419, -1.6737262257585794); //The McGuinness Gallery
    private final LatLng ATTRACTION10 = new LatLng(54.66622962538296, -1.6681228794368732); //Auckland Park

    //Bamburgh Castle
    private final LatLng ATTRACTION11 = new LatLng(55.607339167449275, -1.7185530067409425); //RNLI Grace Darling Museum
    private final LatLng ATTRACTION12 = new LatLng(55.6080785175989, -1.7192181945472271); //Grace Darling Memorial
    private final LatLng ATTRACTION13 = new LatLng(55.61144656996934, -1.7053384678332861); //Bamburgh Beach
    private final LatLng ATTRACTION14 = new LatLng(55.61689205240941, -1.7242212894328968); //Bamburgh Lighthouse
    private final LatLng ATTRACTION15 = new LatLng(55.61772759845565, -1.7333874789364272); //Bamburgh Coast and Hills

    //Barnard Castle
    private final LatLng ATTRACTION16 = new LatLng(54.542581689731364, -1.9157774275920634); //The Bowes Museum
    private final LatLng ATTRACTION17 = new LatLng(54.544726749512904, -1.9243495858312534); //The Sandra Parker Studio
    private final LatLng ATTRACTION18 = new LatLng(54.54678649826598, -1.9311810848616495); //The Band Stand
    private final LatLng ATTRACTION19 = new LatLng(54.54370310563271, -1.9235136477349826); //The Witham
    private final LatLng ATTRACTION20 = new LatLng(54.54578242167278, -1.9269249022241801); //Ann Whitfield

    //Markers variables
    private Marker markerDefault; //United Kingdom


    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attractions);

        //----------------------------------------- Navigate bar ------------------------------------------
        /**
         * Implements the bottom navigation bar to enable switching between activities
         * @param item An array of resource ids
         * @author: Christoffer
         */
        // Initialize and assign variable
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
        // Set Attractions selected
        bottomNavigationView.setSelectedItemId(R.id.attractions);
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
                    startActivity(new Intent(getApplicationContext(),RestaurantsActivity.class));
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                    return true;
                case R.id.attractions:
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
        back = findViewById(R.id.buttonBack4);
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
             * Mechanism to display the nearby attractions regarding the selected castle from the dropdown(adapter).
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
     * Display all nearby attractions around the selected castle.
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
                mMap.addMarker(new MarkerOptions().position(ATTRACTION1).title("Fusiliers Museum of Northumberland").icon(BitmapFromVector(getApplicationContext(), R.drawable.pin_attraction_svg)));
                mMap.addMarker(new MarkerOptions().position(ATTRACTION2).title("Bailiffgate Museum").icon(BitmapFromVector(getApplicationContext(), R.drawable.pin_attraction_svg)));
                mMap.addMarker(new MarkerOptions().position(ATTRACTION3).title("Hotspur Statue").icon(BitmapFromVector(getApplicationContext(), R.drawable.pin_attraction_svg)));
                mMap.addMarker(new MarkerOptions().position(ATTRACTION4).title("The Alnwick Garden Poison Garden").icon(BitmapFromVector(getApplicationContext(), R.drawable.pin_attraction_svg)));
                mMap.addMarker(new MarkerOptions().position(ATTRACTION5).title("St. Michael's Parish Hall").icon(BitmapFromVector(getApplicationContext(), R.drawable.pin_attraction_svg)));
            } else if (castleName.equalsIgnoreCase("Auckland Castle")) {
                mMap.addMarker(new MarkerOptions().position(ATTRACTION6).title("High Park").icon(BitmapFromVector(getApplicationContext(), R.drawable.pin_attraction_svg)));
                mMap.addMarker(new MarkerOptions().position(ATTRACTION7).title("Spanish Gallery").icon(BitmapFromVector(getApplicationContext(), R.drawable.pin_attraction_svg)));
                mMap.addMarker(new MarkerOptions().position(ATTRACTION8).title("Mining Art Gallery").icon(BitmapFromVector(getApplicationContext(), R.drawable.pin_attraction_svg)));
                mMap.addMarker(new MarkerOptions().position(ATTRACTION9).title("The McGuinness Gallery").icon(BitmapFromVector(getApplicationContext(), R.drawable.pin_attraction_svg)));
                mMap.addMarker(new MarkerOptions().position(ATTRACTION10).title("Auckland Park").icon(BitmapFromVector(getApplicationContext(), R.drawable.pin_attraction_svg)));
            } else if (castleName.equalsIgnoreCase("Bamburgh Castle")) {
                mMap.addMarker(new MarkerOptions().position(ATTRACTION11).title("RNLI Grace Darling Museum").icon(BitmapFromVector(getApplicationContext(), R.drawable.pin_attraction_svg)));
                mMap.addMarker(new MarkerOptions().position(ATTRACTION12).title("Grace Darling Memorial").icon(BitmapFromVector(getApplicationContext(), R.drawable.pin_attraction_svg)));
                mMap.addMarker(new MarkerOptions().position(ATTRACTION13).title("Bamburgh Beach").icon(BitmapFromVector(getApplicationContext(), R.drawable.pin_attraction_svg)));
                mMap.addMarker(new MarkerOptions().position(ATTRACTION14).title("Bamburgh Lighthouse").icon(BitmapFromVector(getApplicationContext(), R.drawable.pin_attraction_svg)));
                mMap.addMarker(new MarkerOptions().position(ATTRACTION15).title("Bamburgh Coast and Hills").icon(BitmapFromVector(getApplicationContext(), R.drawable.pin_attraction_svg)));
            } else if (castleName.equalsIgnoreCase("Barnard Castle")) {
                mMap.addMarker(new MarkerOptions().position(ATTRACTION16).title("The Bowes Museum").icon(BitmapFromVector(getApplicationContext(), R.drawable.pin_attraction_svg)));
                mMap.addMarker(new MarkerOptions().position(ATTRACTION17).title("The Sandra Parker Studio").icon(BitmapFromVector(getApplicationContext(), R.drawable.pin_attraction_svg)));
                mMap.addMarker(new MarkerOptions().position(ATTRACTION18).title("The Band Stand").icon(BitmapFromVector(getApplicationContext(), R.drawable.pin_attraction_svg)));
                mMap.addMarker(new MarkerOptions().position(ATTRACTION19).title("The Witham").icon(BitmapFromVector(getApplicationContext(), R.drawable.pin_attraction_svg)));
                mMap.addMarker(new MarkerOptions().position(ATTRACTION20).title("Ann Whitfield").icon(BitmapFromVector(getApplicationContext(), R.drawable.pin_attraction_svg)));
            }
        } else {
            //Mark location to default location(UK).
            markerDefault = mMap.addMarker(new MarkerOptions().position(location).title(castleName).icon(BitmapFromVector(getApplicationContext(), R.drawable.pin_svg)));
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