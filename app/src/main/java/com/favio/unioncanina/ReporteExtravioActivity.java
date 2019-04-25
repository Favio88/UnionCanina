package com.favio.unioncanina;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.favio.unioncanina.modelos.Ciudad;
import com.favio.unioncanina.modelos.Estado;
import com.favio.unioncanina.modelos.Mascota;
import com.favio.unioncanina.modelos.Raza;
import com.favio.unioncanina.singleton.VolleyS;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ReporteExtravioActivity extends AppCompatActivity implements View.OnClickListener{

    //Variables para los controles XML
    ImageView ic_retroceso;
    TextView tv_nombreReportarMiMascota;
    Spinner sp_estadoReportarMiMascota, sp_ciudadReportarMiMascota;
    EditText et_coloniaReportarMiMascota, et_infoExtraReportarMiMascota;
    LinearLayout ll_reportarMiMascota;

    //Variables para cargar los Spinners
    List<Estado> listaEstados=new ArrayList<>();
    List<Ciudad> listaCiudades=new ArrayList<>();

    Bundle bundle;
    Mascota mascota;
    JSONObject jsonExtravio;

    //Variables para formar JSONExtravío
    Integer idMascotaExtravio, idEstadoExtravio, idCiudadExtravio;
    String coloniaExtravio, infoExtraExtravio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_extravio);

        getControlesXML();

        ic_retroceso.setOnClickListener(this);
        ll_reportarMiMascota.setOnClickListener(this);

        bundle=getIntent().getExtras();

        if(bundle!=null){
            String mascotaBundle=bundle.getString("Mascota");

            Gson gson=new Gson();
            mascota=gson.fromJson(mascotaBundle, Mascota.class);

            cargarNombreMascota();
            cargarSpinners();
        }
    }

    private void getControlesXML(){
        ic_retroceso=findViewById(R.id.ic_retroceso);
        tv_nombreReportarMiMascota=findViewById(R.id.tv_nombreReportarMiMascota);
        sp_estadoReportarMiMascota=findViewById(R.id.sp_estadoReportarMiMascota);
        sp_ciudadReportarMiMascota=findViewById(R.id.sp_ciudadReportarMiMascota);
        et_coloniaReportarMiMascota=findViewById(R.id.et_coloniaReportarMiMascota);
        et_infoExtraReportarMiMascota=findViewById(R.id.et_infoExtraReportarMiMascota);
        ll_reportarMiMascota=findViewById(R.id.ll_reportarMiMascota);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.ic_retroceso:
                finish();
                break;
            case R.id.ll_reportarMiMascota:
                formarJSONExtravio();
                abrirDialogoReportar();
                break;
        }
    }

    private void cargarNombreMascota(){
        tv_nombreReportarMiMascota.setText(mascota.getNombre());
    }

    private void cargarSpinners(){

        cargarSpinnerEstado();
        cargarSpinnerCiudad();
    }

    private void cargarSpinnerEstado(){

        JsonArrayRequest peticion=new JsonArrayRequest(
                Request.Method.GET,
                "http://unioncanina.mipantano.com/api/estados",
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        Gson gson=new Gson();
                        Type listType=new TypeToken<List<Estado>>(){}.getType();
                        listaEstados=gson.fromJson(response.toString(), listType);
                        listaEstados.add(0, new Estado(0,"Estado"));

                        ArrayAdapter<Estado> adapterSpinnerEstado;
                        adapterSpinnerEstado=new ArrayAdapter<Estado>(getApplicationContext(), R.layout.item_filtro, listaEstados);

                        sp_estadoReportarMiMascota.setAdapter(adapterSpinnerEstado);
                        sp_estadoReportarMiMascota.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                                if (listaEstados.get(position).getId()!=0){

                                    idEstadoExtravio=listaEstados.get(position).getId();
                                    Toast.makeText(ReporteExtravioActivity.this, idEstadoExtravio.toString(), Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    // Hacer nada
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ReporteExtravioActivity.this, "Error en la petición", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        VolleyS.getInstance(getApplicationContext()).getRequestQueue().add(peticion);
    }

    private void cargarSpinnerCiudad(){

        JsonArrayRequest peticion=new JsonArrayRequest(
                Request.Method.GET,
                "http://unioncanina.mipantano.com/api/ciudades",
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        Gson gson=new Gson();
                        Type listType=new TypeToken<List<Ciudad>>(){}.getType();
                        listaCiudades=gson.fromJson(response.toString(), listType);
                        listaCiudades.add(0, new Ciudad(0,"Ciudad"));

                        ArrayAdapter<Ciudad> adapterSpinnerCiudad;
                        adapterSpinnerCiudad=new ArrayAdapter<Ciudad>(getApplicationContext(), R.layout.item_filtro, listaCiudades);

                        sp_ciudadReportarMiMascota.setAdapter(adapterSpinnerCiudad);
                        sp_ciudadReportarMiMascota.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                                if (listaCiudades.get(position).getId()!=0){

                                    idCiudadExtravio=listaCiudades.get(position).getId();
                                    Toast.makeText(ReporteExtravioActivity.this, idCiudadExtravio.toString(), Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    // Hacer nada
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ReporteExtravioActivity.this, "Error en la petición", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        VolleyS.getInstance(getApplicationContext()).getRequestQueue().add(peticion);
    }

    public void abrirDialogoReportar(){

        AlertDialog.Builder builder=new AlertDialog.Builder(ReporteExtravioActivity.this);
        builder.setTitle(mascota.getNombre())
                .setMessage("Podrás ver su reporte en la pantalla de Inicio.")
                .setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        reportarExtravio();
                    }
                }).show();
    }

    private void formarJSONExtravio(){

        String fechaHoy=obtenerFechaHoy();

        idMascotaExtravio=mascota.getId();
        coloniaExtravio=et_coloniaReportarMiMascota.getText().toString();
        infoExtraExtravio=et_infoExtraReportarMiMascota.getText().toString();

        jsonExtravio=new JSONObject();

        try {
            jsonExtravio.put("id_mascota", idMascotaExtravio);
            jsonExtravio.put("colonia", coloniaExtravio);
            jsonExtravio.put("id_ciudad", idCiudadExtravio);
            jsonExtravio.put("f_extrav", fechaHoy);
            jsonExtravio.put("info_extra", infoExtraExtravio);
            jsonExtravio.put("estado", "extraviado");
            jsonExtravio.put("estatus", "extraviado");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("extravio", jsonExtravio.toString());
    }

    private void reportarExtravio(){

        JsonObjectRequest peticion=new JsonObjectRequest(
                Request.Method.POST,
                "http://unioncanina.mipantano.com/api/reportarExtravio",
                jsonExtravio,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("reporte",response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ReporteExtravioActivity.this, "Error en la petición", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        VolleyS.getInstance(this).getRequestQueue().add(peticion);

        irActivityInicio();
    }

    private void irActivityInicio(){
        Intent itt_misMascotasActivity=new Intent(ReporteExtravioActivity.this, InicioActivity.class);
        startActivity(itt_misMascotasActivity);
        finish();

    }

    private String obtenerFechaHoy(){

        Integer day, month, year;

        Calendar fecha=Calendar.getInstance();
        year=fecha.get(Calendar.YEAR);
        month=fecha.get(Calendar.MONTH);
        day=fecha.get(Calendar.DAY_OF_MONTH);

        String fechaHoy;

        if((month+1)<10 && day<10){
            fechaHoy=year + "-0" + (month+1) + "-0" + day;
        }else if((month+1)<10 && day>=10){
            fechaHoy=year + "-0" + (month+1) + "-" + day;
        }else if ((month+1)>=10 && day<10){
            fechaHoy=year + "-" + (month+1) + "-0" + day;
        }else {
            fechaHoy=year + "-" + (month+1) + "-" + day;
        }

        return fechaHoy;
    }

}
