package com.favio.unioncanina;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent itt_InicioSesion=new Intent(SplashActivity.this, InicioSesionActivity.class);
                startActivity(itt_InicioSesion);
                finish();
            }
        },5000);
    }
}
