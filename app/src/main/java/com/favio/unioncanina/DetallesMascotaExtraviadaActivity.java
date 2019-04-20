package com.favio.unioncanina;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.favio.unioncanina.modelos.Mascota;
import com.favio.unioncanina.modelos.Usuario;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

public class DetallesMascotaExtraviadaActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView ic_retroceso, iv_fotoMascota;
    Button btn_contactarDueno;
    TextView tv_razaMascota, tv_nombreMascota, tv_edadMascota, tv_rasgosMascota, tv_lugarExtravioMascota;
    Mascota mascota;

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

        Bundle bundle=this.getIntent().getExtras();
        String mascotaBundle=bundle.getString("Mascota");

        Gson gson=new Gson();
        mascota=gson.fromJson(mascotaBundle, Mascota.class);

        tv_nombreMascota.setText(mascota.getNombre());
        Picasso.with(this).load("http://unioncanina.mipantano.com/api/petspp/" + mascota.getFoto()).fit().centerCrop().into(iv_fotoMascota);
        tv_razaMascota.setText(mascota.getRaza().getNombre());
        tv_edadMascota.setText(mascota.getF_nac());
        tv_rasgosMascota.setText(mascota.getRasgos());
        tv_lugarExtravioMascota.setText(mascota.getExtravio().get(0).getColonia());

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
