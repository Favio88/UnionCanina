package com.favio.unioncanina;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DetallesMascotaExtraviadaActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView ic_retroceso, iv_fotoMascota;
    Button btn_contactarDueno;
    TextView tv_razaMascota, tv_nombreMascota, tv_edadMascota, tv_rasgosMascota, tv_lugarExtravioMascota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_mascota_extraviada);

        ic_retroceso=findViewById(R.id.ic_retroceso);
        iv_fotoMascota=findViewById(R.id.iv_fotoMascota);
        btn_contactarDueno=findViewById(R.id.btn_contactarDueno);
        tv_razaMascota=findViewById(R.id.tv_razaMascota);
        tv_nombreMascota=findViewById(R.id.tv_nombreMascota);
        tv_edadMascota=findViewById(R.id.tv_edadMascota);
        tv_rasgosMascota=findViewById(R.id.tv_rasgosMascota);
        tv_lugarExtravioMascota=findViewById(R.id.tv_lugarExtravioMascota);

        ic_retroceso.setOnClickListener(this);
        btn_contactarDueno.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.ic_retroceso:
                Intent itt_inicioActivity=new Intent(DetallesMascotaExtraviadaActivity.this, InicioActivity.class);
                startActivity(itt_inicioActivity);
                finish();
                break;
            case R.id.btn_contactarDueno:
                Intent itt_contactarDuenoActivity=new Intent(DetallesMascotaExtraviadaActivity.this, ContactarDuenoActivity.class);
                startActivity(itt_contactarDuenoActivity);
                break;
        }
    }
}
