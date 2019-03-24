package com.favio.unioncanina;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ContactarDuenoActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView ic_retroceso, ic_enviar;
    EditText et_escribirMensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactar_dueno);

        ic_retroceso=findViewById(R.id.ic_retroceso);
        et_escribirMensaje=findViewById(R.id.et_escribirMensaje);
        ic_enviar=findViewById(R.id.ic_enviar);

        ic_retroceso.setOnClickListener(this);
        et_escribirMensaje.setOnClickListener(this);
        ic_enviar.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.ic_retroceso:
                Intent itt_detallesMascotaExtraviadaActivity=new Intent(ContactarDuenoActivity.this, DetallesMascotaExtraviadaActivity.class);
                startActivity(itt_detallesMascotaExtraviadaActivity);
                finish();
                break;
            case R.id.et_escribirMensaje:

                break;
            case R.id.ic_enviar:

                break;
        }
    }
}
