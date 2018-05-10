package com.example.nstorflores.musicalizza.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;


import com.example.nstorflores.musicalizza.Activities.SignInActivity;
import com.example.nstorflores.musicalizza.MainActivity;
import com.example.nstorflores.musicalizza.R;
import com.tumblr.remember.Remember;
//import com.tumblr.remember.Remember;

public class SplashScreenActivity extends AppCompatActivity{

    /*public void validarSesion (){
        if (Remember.getString("access_token", "").isEmpty()){
            Intent intent = new Intent(this, SignInActivity.class);
            startActivity(intent);

        }
        else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        }, 3000);

    }
}
