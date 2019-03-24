package com.favio.unioncanina;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class EditarMascotaActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView ic_retroceso;
    EditText et_nombreEditarMiMascota, et_razaEditarMiMascota, et_colorEditarMiMascota, et_sexoEditarMiMascota, et_rasgosEditarMiMascota;
    RecyclerView rv_fotosMiMascota;
    Button btn_guardarMiMascota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_mascota);

        ic_retroceso=findViewById(R.id.ic_retroceso);
        et_nombreEditarMiMascota=findViewById(R.id.et_nombreEditarMiMascota);
        et_razaEditarMiMascota=findViewById(R.id.et_razaEditarMiMascota);
        et_colorEditarMiMascota=findViewById(R.id.et_colorEditarMiMascota);
        et_sexoEditarMiMascota=findViewById(R.id.et_sexoEditarMiMascota);
        et_rasgosEditarMiMascota=findViewById(R.id.et_razaEditarMiMascota);
        rv_fotosMiMascota=findViewById(R.id.rv_fotosMiMascota);
        btn_guardarMiMascota=findViewById(R.id.btn_guardarMiMascota);

        ic_retroceso.setOnClickListener(this);
        btn_guardarMiMascota.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch(view.getId()){

            case R.id.ic_retroceso:
                Intent itt_detallesMisMascotasActivity=new Intent(EditarMascotaActivity.this,DetallesMiMascotaActivity.class);
                startActivity(itt_detallesMisMascotasActivity);
                finish();
                break;
            case R.id.btn_guardarMiMascota:
                Intent itt_inicioActivity=new Intent(EditarMascotaActivity.this, InicioActivity.class);
                startActivity(itt_inicioActivity);
                finish();
                break;
        }
    }
}
