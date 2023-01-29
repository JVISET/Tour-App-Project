package com.example.csc8019_teamprojectnotemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * Splash screen: to show the logo of application as the first screen when the application is opened while it's being loaded.
 * @author Jirayut Visetsillapanont
 */
@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //----------------------------------------- Display splash screen ------------------------------------------
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, CastleSelectionActivity.class));
            finish();
        }, 3000); //Display duration(millisecond)
        //----------------------------------------------------------------------------------------------------------
    }
}