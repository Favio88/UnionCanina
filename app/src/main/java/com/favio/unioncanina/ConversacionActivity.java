package com.favio.unioncanina;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class ConversacionActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView ic_retroceso, ic_infoConversacion, ic_enviar;
    EditText et_escribirMensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversacion);

        ic_retroceso=findViewById(R.id.ic_retroceso);
        ic_infoConversacion=findViewById(R.id.ic_infoConversacion);
        ic_enviar=findViewById(R.id.ic_enviar);
        et_escribirMensaje=findViewById(R.id.et_escribirMensaje);

        ic_retroceso.setOnClickListener(this);
        ic_infoConversacion.setOnClickListener(this);
        ic_enviar.setOnClickListener(this);
        et_escribirMensaje.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.ic_retroceso:
                Intent itt_inicioActivity=new Intent(ConversacionActivity.this, InicioActivity.class);
                startActivity(itt_inicioActivity);
                finish();
                break;
            case R.id.ic_infoConversacion:
                Intent itt_informacionConversacionActivity=new Intent(ConversacionActivity.this, InformacionConversacionActivity.class);
                startActivity(itt_informacionConversacionActivity);
                break;
            case R.id.ic_enviar:

                break;
            case R.id.et_escribirMensaje:

                break;
        }
    }
}
