package com.favio.unioncanina;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class DetallesMiMascotaActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView ic_retroceso, iv_fotoMiMascota;
    TextView tv_nombreMiMascota, tv_razaMiMascota, tv_edadMiMascota, tv_rasgosMiMascota;
    FrameLayout fl_editarMiMascota, fl_reportarMiMascota, fl_eliminarMiMascota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_mi_mascota);

        ic_retroceso=findViewById(R.id.ic_retroceso);
        tv_nombreMiMascota=findViewById(R.id.tv_nombreMiMascota);
        tv_razaMiMascota=findViewById(R.id.tv_razaMiMascota);
        tv_edadMiMascota=findViewById(R.id.tv_edadMiMascota);
        tv_rasgosMiMascota=findViewById(R.id.tv_rasgosMiMascota);
        fl_editarMiMascota=findViewById(R.id.fl_editarMiMascota);
        fl_reportarMiMascota=findViewById(R.id.fl_reportarMiMascota);
        fl_eliminarMiMascota=findViewById(R.id.fl_eliminarMiMascota);
        iv_fotoMiMascota=findViewById(R.id.iv_fotoMiMascota);

        ic_retroceso.setOnClickListener(this);
        fl_editarMiMascota.setOnClickListener(this);
        fl_reportarMiMascota.setOnClickListener(this);
        fl_eliminarMiMascota.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.ic_retroceso:
                Intent itt_misMascotasActivity=new Intent(DetallesMiMascotaActivity.this, InicioActivity.class);
                startActivity(itt_misMascotasActivity);
                finish();
                break;
            case  R.id.fl_editarMiMascota:
                Intent itt_editarMascotaActivity=new Intent(DetallesMiMascotaActivity.this, EditarMascotaActivity.class);
                startActivity(itt_editarMascotaActivity);
                break;
            case R.id.fl_reportarMiMascota:
                Intent itt_reporteExtravioActivity=new Intent(DetallesMiMascotaActivity.this, ReporteExtravioActivity.class);
                startActivity(itt_reporteExtravioActivity);
                break;
            case R.id.fl_eliminarMiMascota:
                Intent itt_eliminarMascotaActivity=new Intent(DetallesMiMascotaActivity.this, InicioActivity.class);
                startActivity(itt_eliminarMascotaActivity);
                finish();
                break;
        }
    }
}
