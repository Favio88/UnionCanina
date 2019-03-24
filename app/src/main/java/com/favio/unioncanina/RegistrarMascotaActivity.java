package com.favio.unioncanina;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class RegistrarMascotaActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView ic_retroceso;
    EditText et_nombreRegistrarMascota, et_razaRegistrarMascota, et_colorRegistrarMascota, et_sexoRegistrarMascota, et_rasgosRegistrarMascota;
    LinearLayout ll_subirForoMascota;
    Button btn_guardarMascota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_mascota);

        ic_retroceso=findViewById(R.id.ic_retroceso);
        et_nombreRegistrarMascota=findViewById(R.id.et_nombreRegistrarMascota);
        et_razaRegistrarMascota=findViewById(R.id.et_razaRegistrarMascota);
        et_colorRegistrarMascota=findViewById(R.id.et_colorRegistrarMascota);
        et_sexoRegistrarMascota=findViewById(R.id.et_sexoRegistrarMascota);
        et_rasgosRegistrarMascota=findViewById(R.id.et_rasgosRegistrarMascota);
        ll_subirForoMascota=findViewById(R.id.ll_subirFotoMascota);
        btn_guardarMascota=findViewById(R.id.btn_guardarMascota);

        ic_retroceso.setOnClickListener(this);
        ll_subirForoMascota.setOnClickListener(this);
        btn_guardarMascota.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.ic_retroceso:
                Intent itt_detallesMiMascotaActivity=new Intent(RegistrarMascotaActivity.this, DetallesMiMascotaActivity.class);
                startActivity(itt_detallesMiMascotaActivity);
                finish();
                break;
            case R.id.ll_subirFotoMascota:
                Intent itt_seleccionarImagenActivity=new Intent(RegistrarMascotaActivity.this, SeleccionarImagenActivity.class);
                startActivity(itt_seleccionarImagenActivity);
                break;
            case R.id.btn_guardarMascota:
                Intent itt_inicioActivity=new Intent(RegistrarMascotaActivity.this, InicioActivity.class);
                startActivity(itt_inicioActivity);
                break;
        }
    }
}
