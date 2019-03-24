package com.favio.unioncanina;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

public class InformacionConversacionActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView ic_retroceso, iv_fotoInfoConversacion;
    TextView tv_nombreInfoConversacion, tv_eliminarInfoConversacion, tv_bloquearInfoConversacion;
    Switch sw_silenciarInfoConversacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion_conversacion);

        ic_retroceso=findViewById(R.id.ic_retroceso);
        iv_fotoInfoConversacion=findViewById(R.id.iv_fotoInfoConversacion);
        tv_nombreInfoConversacion=findViewById(R.id.tv_nombreInfoConversacion);
        sw_silenciarInfoConversacion=findViewById(R.id.sw_silenciarInfoConversacion);
        tv_eliminarInfoConversacion=findViewById(R.id.tv_eliminarInfoConversacion);
        tv_bloquearInfoConversacion=findViewById(R.id.tv_bloquearInfoConversacion);

        ic_retroceso.setOnClickListener(this);
        sw_silenciarInfoConversacion.setOnClickListener(this);
        tv_eliminarInfoConversacion.setOnClickListener(this);
        tv_bloquearInfoConversacion.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.ic_retroceso:
                Intent itt_conversacionActivity=new Intent(InformacionConversacionActivity.this, ConversacionActivity.class);
                startActivity(itt_conversacionActivity);
                finish();
                break;
            case R.id.sw_silenciarInfoConversacion:

                break;
            case R.id.tv_eliminarInfoConversacion:
                irInicioActivity();
                break;
            case R.id.tv_bloquearInfoConversacion:
                irInicioActivity();
                break;
        }
    }

    public  void irInicioActivity(){

        Intent itt_inicioActivity=new Intent(InformacionConversacionActivity.this, InicioActivity.class);
        startActivity(itt_inicioActivity);
        finish();
    }
}
