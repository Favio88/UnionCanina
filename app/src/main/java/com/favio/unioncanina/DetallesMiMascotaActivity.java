package com.favio.unioncanina;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.favio.unioncanina.modelos.Mascota;
import com.favio.unioncanina.singleton.VolleyS;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.Calendar;

public class DetallesMiMascotaActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView ic_retroceso, iv_fotoMiMascota;
    TextView tv_codigoMiMascota, tv_nombreMiMascota, tv_razaMiMascota, tv_sexoMiMascota, tv_colorMiMascota, tv_edadMiMascota,
            tv_estadoMiMascota, tv_ciudadMiMascota, tv_rasgosMiMascota;
    FrameLayout fl_editarMiMascota, fl_reportarMiMascota, fl_eliminarMiMascota;
    Mascota mascota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_mi_mascota);

        getControlesXML();

        ic_retroceso.setOnClickListener(this);
        fl_editarMiMascota.setOnClickListener(this);
        fl_reportarMiMascota.setOnClickListener(this);
        fl_eliminarMiMascota.setOnClickListener(this);

        Bundle bundle=this.getIntent().getExtras();
        String mascotaBundle=bundle.getString("Mascota");

        Gson gson=new Gson();
        mascota=gson.fromJson(mascotaBundle, Mascota.class);

        Calendar fechaNac=extraerElementosFecha(mascota.getF_nac());
        String edad=calcularEdad(fechaNac);

        tv_nombreMiMascota.setText(mascota.getNombre());
        Picasso.with(this).load("http://unioncanina.mipantano.com/api/petspp/" +
                mascota.getFoto()).fit().centerCrop().into(iv_fotoMiMascota);
        tv_codigoMiMascota.setText(mascota.getCodigo().getCodigo());
        tv_razaMiMascota.setText(mascota.getRaza().getNombre());
        tv_sexoMiMascota.setText(mascota.getSexo());
        tv_colorMiMascota.setText(edad);
        tv_edadMiMascota.setText(mascota.getF_nac());
        tv_estadoMiMascota.setText(mascota.getCiudad().getEstado().getNombre());
        tv_ciudadMiMascota.setText(mascota.getCiudad().getNombre());
        tv_rasgosMiMascota.setText(mascota.getRasgos());
    }

    public void getControlesXML(){

        ic_retroceso=findViewById(R.id.ic_retroceso);
        tv_codigoMiMascota=findViewById(R.id.tv_codigoMiMascota);
        tv_nombreMiMascota=findViewById(R.id.tv_nombreMiMascota);
        tv_razaMiMascota=findViewById(R.id.tv_razaMiMascota);
        tv_sexoMiMascota=findViewById(R.id.tv_sexoMiMascota);
        tv_colorMiMascota=findViewById(R.id.tv_colorMiMascota);
        tv_edadMiMascota=findViewById(R.id.tv_edadMiMascota);
        tv_estadoMiMascota=findViewById(R.id.tv_edadMiMascota);
        tv_ciudadMiMascota=findViewById(R.id.tv_ciudadMiMascota);
        tv_rasgosMiMascota=findViewById(R.id.tv_rasgosMiMascota);
        fl_editarMiMascota=findViewById(R.id.fl_editarMiMascota);
        fl_reportarMiMascota=findViewById(R.id.fl_reportarMiMascota);
        fl_eliminarMiMascota=findViewById(R.id.fl_eliminarMiMascota);
        iv_fotoMiMascota=findViewById(R.id.iv_fotoMiMascota);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.ic_retroceso:
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
                abrirDialogoEliminar();
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

    public void abrirDialogoEliminar(){

        AlertDialog.Builder builder=new AlertDialog.Builder(DetallesMiMascotaActivity.this);
        builder.setTitle(mascota.getNombre())
                .setMessage("¿Deseas eliminar esta mascota")
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        eliminarMascota();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();
    }

    private void eliminarMascota(){

        Integer idMascota=mascota.getId();

        JsonObjectRequest peticion=new JsonObjectRequest(
                Request.Method.GET,
                "http://unioncanina.mipantano.com/api/deshabilitarMascota/" + idMascota,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(DetallesMiMascotaActivity.this, "Mascota eliminada", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DetallesMiMascotaActivity.this, "Error en la peticion", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        VolleyS.getInstance(this).getRequestQueue().add(peticion);

        irActivityInicio();
    }

    public void irActivityInicio(){
        Intent itt_eliminarMascotaActivity=new Intent(DetallesMiMascotaActivity.this, InicioActivity.class);
        startActivity(itt_eliminarMascotaActivity);
        finish();
    }
}
