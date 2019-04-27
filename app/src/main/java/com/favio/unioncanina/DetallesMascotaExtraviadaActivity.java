package com.favio.unioncanina;

import android.content.Intent;
import android.icu.text.LocaleDisplayNames;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.favio.unioncanina.modelos.Mascota;
import com.favio.unioncanina.modelos.Usuario;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DetallesMascotaExtraviadaActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView ic_retroceso, iv_fotoMascota;
    Button btn_contactarDueno;
    TextView tv_codigoMascota, tv_nombreMascota, tv_razaMascota, tv_sexoMascota, tv_colorMascota, tv_edadMascota, tv_rasgosMascota,
            tv_lugarExtravioMascota, tv_infoExtraMascota;
    Mascota mascota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_mascota_extraviada);

        getControlesXML();

        ic_retroceso.setOnClickListener(this);
        btn_contactarDueno.setOnClickListener(this);

        Bundle bundle=this.getIntent().getExtras();
        String mascotaBundle=bundle.getString("Mascota");

        Gson gson=new Gson();
        mascota=gson.fromJson(mascotaBundle, Mascota.class);

        Calendar fechaNac=extraerElementosFecha(mascota.getF_nac());
        String edad=calcularEdad(fechaNac);

        tv_nombreMascota.setText(mascota.getNombre());
        Picasso.with(this).load("http://unioncanina.mipantano.com/api/petspp/" + mascota.getFoto()).fit().centerCrop().into(iv_fotoMascota);
        tv_codigoMascota.setText(mascota.getCodigo().getCodigo());
        tv_razaMascota.setText(mascota.getRaza().getNombre());
        tv_sexoMascota.setText(mascota.getSexo());
        tv_colorMascota.setText(mascota.getColor());
        tv_edadMascota.setText(edad);
        tv_rasgosMascota.setText(mascota.getRasgos());
        tv_lugarExtravioMascota.setText(mascota.getExtravio().get(mascota.getExtravio().size()-1).getColonia() + ", " +
                mascota.getCiudad().getNombre() + ", " +
                mascota.getCiudad().getEstado().getNombre() + " el " +
                mascota.getExtravio().get(mascota.getExtravio().size()-1).getF_extrav());
    }

    public void getControlesXML(){

        ic_retroceso=findViewById(R.id.ic_retroceso);
        iv_fotoMascota=findViewById(R.id.iv_fotoMascota);
        tv_codigoMascota=findViewById(R.id.tv_codigoMascota);
        tv_nombreMascota=findViewById(R.id.tv_nombreMascota);
        tv_razaMascota=findViewById(R.id.tv_razaMascota);
        tv_sexoMascota=findViewById(R.id.tv_sexoMascota);
        tv_colorMascota=findViewById(R.id.tv_colorMascota);
        tv_edadMascota=findViewById(R.id.tv_edadMascota);
        tv_rasgosMascota=findViewById(R.id.tv_rasgosMascota);
        tv_lugarExtravioMascota=findViewById(R.id.tv_lugarExtravioMascota);
        btn_contactarDueno=findViewById(R.id.btn_contactarDueno);
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

    private Calendar extraerElementosFecha(String fNac){

        Integer year=Integer.valueOf(fNac.substring(0,4));
        Integer month=Integer.valueOf(fNac.substring(5,7));
        Integer day=Integer.valueOf(fNac.substring(8,10));

        Calendar fecha=Calendar.getInstance();
        fecha.set(year, month, day);

        return fecha;
    }

    private String calcularEdad(Calendar fechaNac) {

        Calendar today = Calendar.getInstance();

        String diff_tot="";
        int diff_year = today.get(Calendar.YEAR) -  fechaNac.get(Calendar.YEAR);
        int diff_month = (today.get(Calendar.MONTH) + 1) - fechaNac.get(Calendar.MONTH);
        int diff_day = today.get(Calendar.DAY_OF_MONTH) - fechaNac.get(Calendar.DAY_OF_MONTH);

        Log.d("dif", "" + diff_year + " " + diff_month + " " + diff_day );

        if (diff_year>0){
            diff_tot=diff_year + " años " + diff_month + " meses";
        }
        if (diff_year==0 && diff_month>0){
            diff_tot=diff_month + " meses";
        }
        if (diff_year==0 && diff_month==0){
            diff_tot=diff_day + " días";
        }
        return diff_tot;
    }
}
