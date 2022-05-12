package com.m1p9.kidz;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

import com.m1p9.kidz.ui.login.LoginActivity;

public class SplashScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                startActivity(new Intent(SplashScreen.this, LoginActivity.class));
                finish();
            }
        },4000);
    }
}
