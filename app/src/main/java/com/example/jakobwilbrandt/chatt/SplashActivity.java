package com.example.jakobwilbrandt.chatt;

import android.content.Intent;
import android.os.Bundle;

import com.example.jakobwilbrandt.chatt.ServerHandling.ServerFactory.IUserHandling;
import com.example.jakobwilbrandt.chatt.ServerHandling.ServerFactory.FirebaseLoginAcitivity;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


            BaseActivity baseActivity = serverFactory.CreateLoginActivity();

            Intent intent = new Intent(this, baseActivity.getClass());
            startActivity(intent);
            finish();
        }



    }
