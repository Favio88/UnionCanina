package com.favio.unioncanina;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class BuscarMascotaIdActivity extends AppCompatActivity implements View.OnClickListener{

    Button btn_buscarMascota;
    EditText et_cdigoMascota;
    ImageView ic_retroceso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_mascota_id);

        ic_retroceso = findViewById(R.id.ic_retroceso);
        et_cdigoMascota = findViewById(R.id.et_idMascota);
        btn_buscarMascota = findViewById(R.id.btn_buscarMascota);

        ic_retroceso.setOnClickListener(this);
        btn_buscarMascota.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.ic_retroceso:
                //Intent itt_inicioActivity=new Intent(BuscarMascotaIdActivity.this, InicioActivity.class);
                //startActivity(itt_inicioActivity);
                finish();
                break;
            case R.id.btn_buscarMascota:
                //Intent itt_resultadoBusquedaActivity=new Intent(BuscarMascotaIdActivity.this, ResultadoBusquedaActivity.class);
                //startActivity(itt_resultadoBusquedaActivity);
                break;
        }
    }
}
