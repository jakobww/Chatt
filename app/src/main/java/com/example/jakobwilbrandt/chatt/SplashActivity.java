package com.example.jakobwilbrandt.chatt;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuItem item = menu.findItem(R.id.action_loginout);
        item.setVisible(false);
        return true;

    }




    }



