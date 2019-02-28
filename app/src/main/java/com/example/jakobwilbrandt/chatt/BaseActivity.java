package com.example.jakobwilbrandt.chatt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.jakobwilbrandt.chatt.UserHandling.FirebaseLoginHandler;
import com.example.jakobwilbrandt.chatt.UserHandling.ILoginHandler;

public class BaseActivity extends AppCompatActivity {

    String TAG = "BaseActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ILoginHandler loginHandler = new FirebaseLoginHandler();
        boolean isLoggedIn = loginHandler.CheckLogin();
        if(isLoggedIn){



        }
        else{
            Toast.makeText(this,"User is not logged in", Toast.LENGTH_LONG).show();
        }

    }


}
