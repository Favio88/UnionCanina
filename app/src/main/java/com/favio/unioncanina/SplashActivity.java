package com.favio.unioncanina;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;

import com.google.gson.JsonObject;

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
    private void cargarfiltros(JsonObject response){

        SharedPreferences preferences = getSharedPreferences("Info", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("Info", response.toString());
        editor.apply();
    }
}
