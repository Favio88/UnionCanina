package com.favio.unioncanina;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.favio.unioncanina.modelos.Filtro;
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

public class RegistrarMascotaActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView ic_retroceso;
    EditText et_nombreRegistrarMascota, et_colorRegistrarMascota, et_fnacRegistrarMascota, et_rasgosRegistrarMascota, et_enfermedadesRegistrarMascota;
    Spinner sp_razaRegistrarMascota, sp_sexoRegistrarMascota, sp_esterilizadoRegistrarMascota;
    LinearLayout ll_subirFotoRegistrarMascota;
    Button btn_guardarRegistrarMascota;
    Integer dia, mes, anio;
    List<String> listaSpinnerEsterilizado=new ArrayList<>(), listaSpinnerSexo=new ArrayList<>(), listaSpinnerRaza=new ArrayList<>();
    List<Raza> listaRazas=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_mascota);

        ic_retroceso=findViewById(R.id.ic_retroceso);
        et_nombreRegistrarMascota=findViewById(R.id.et_nombreRegistrarMascota);
        sp_razaRegistrarMascota=findViewById(R.id.sp_razaRegistrarMascota);
        sp_sexoRegistrarMascota=findViewById(R.id.sp_sexoRegistrarMascota);
        et_colorRegistrarMascota=findViewById(R.id.et_colorRegistrarMascota);
        et_fnacRegistrarMascota=findViewById(R.id.et_fnacRegistrarMascota);
        sp_esterilizadoRegistrarMascota=findViewById(R.id.sp_esterilizadoRegistrarMascota);
        et_enfermedadesRegistrarMascota=findViewById(R.id.et_enfermedadesRegistrarMascota);
        et_rasgosRegistrarMascota=findViewById(R.id.et_rasgosRegistrarMascota);
        ll_subirFotoRegistrarMascota=findViewById(R.id.ll_subirFotoRegistrarMascota);
        btn_guardarRegistrarMascota=findViewById(R.id.btn_guardarRegistrarMascota);

        ic_retroceso.setOnClickListener(this);
        et_fnacRegistrarMascota.setOnClickListener(this);
        ll_subirFotoRegistrarMascota.setOnClickListener(this);
        btn_guardarRegistrarMascota.setOnClickListener(this);

        cargarSpinnerRaza();
        cargarSpinnerSexo();
        cargarSpinnerEsterilizado();

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.ic_retroceso:
                Intent itt_detallesMiMascotaActivity=new Intent(RegistrarMascotaActivity.this, DetallesMiMascotaActivity.class);
                startActivity(itt_detallesMiMascotaActivity);
                finish();
                break;
            case R.id.et_fnacRegistrarMascota:
                obtenerFecha();
                break;
            case R.id.ll_subirFotoRegistrarMascota:
                Intent itt_seleccionarImagenActivity=new Intent(RegistrarMascotaActivity.this, SeleccionarImagenActivity.class);
                startActivity(itt_seleccionarImagenActivity);
                break;
            case R.id.btn_guardarRegistrarMascota:
                registrarMascota();
                //Intent itt_inicioActivity=new Intent(RegistrarMascotaActivity.this, InicioActivity.class);
                //startActivity(itt_inicioActivity);
                break;
        }
    }

    public void obtenerFecha(){
        final Calendar fecha=Calendar.getInstance();
        dia=fecha.get(Calendar.DAY_OF_MONTH);
        mes=fecha.get(Calendar.MONTH);
        anio=fecha.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                et_fnacRegistrarMascota.setText(year + "-" + (month+1) + "-" + day);
            }
        }, dia, mes, anio);
        datePickerDialog.show();

    }

    public void cargarSpinnerSexo(){

        listaSpinnerSexo.add(0, "Sexo");
        listaSpinnerSexo.add("Macho");
        listaSpinnerSexo.add("Hembra");

        ArrayAdapter<String> adapterSpinnerSexo;
        adapterSpinnerSexo=new ArrayAdapter<String>(getApplicationContext(), R.layout.item_filtro, listaSpinnerSexo);

        sp_sexoRegistrarMascota.setAdapter(adapterSpinnerSexo);
        sp_sexoRegistrarMascota.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                if (adapterView.getItemAtPosition(position).equals("Sexo")){

                }
                else{
                    String item=adapterView.getItemAtPosition(position).toString();

                    Toast.makeText(RegistrarMascotaActivity.this, item, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void cargarSpinnerEsterilizado(){

        listaSpinnerEsterilizado.add(0, "¿Está esterilizado?");
        listaSpinnerEsterilizado.add("Sí");
        listaSpinnerEsterilizado.add("No");

        ArrayAdapter<String> adapterSpinnerEsterilizado;
        adapterSpinnerEsterilizado=new ArrayAdapter<String>(getApplicationContext(), R.layout.item_filtro, listaSpinnerEsterilizado);

        sp_esterilizadoRegistrarMascota.setAdapter(adapterSpinnerEsterilizado);
        sp_esterilizadoRegistrarMascota.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                if (adapterView.getItemAtPosition(position).equals("¿Está esterilizado?")){

                }
                else{
                    String item=adapterView.getItemAtPosition(position).toString();

                    Toast.makeText(RegistrarMascotaActivity.this, item, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void cargarSpinnerRaza(){

        JsonObjectRequest peticion=new JsonObjectRequest(
                Request.Method.GET,
                "http://unioncanina.mipantano.com/api/infofiltros",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Gson gson=new Gson();

                        Filtro objFiltro=gson.fromJson(response.toString(), Filtro.class);

                        listaSpinnerRaza.add("Raza");

                        for (Raza r:objFiltro.getRazas()){

                            listaRazas.add(r);
                            listaSpinnerRaza.add(r.getNombre());
                        }

                        ArrayAdapter<String> adapterSpinnerRaza;
                        adapterSpinnerRaza=new ArrayAdapter<String>(getApplicationContext(), R.layout.item_filtro, listaSpinnerRaza);

                        sp_razaRegistrarMascota.setAdapter(adapterSpinnerRaza);
                        sp_razaRegistrarMascota.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                                if (adapterView.getItemAtPosition(position).equals("Raza")){

                                }
                                else{
                                    Integer idRaza=listaRazas.get(position-1).getId();

                                    Toast.makeText(RegistrarMascotaActivity.this, idRaza.toString(), Toast.LENGTH_SHORT).show();
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

                        Toast.makeText(getApplicationContext(), "Error en la petición", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        VolleyS.getInstance(getApplicationContext()).getRequestQueue().add(peticion);

    }

    public void registrarMascota(){

        JSONObject obj=new JSONObject();

        try {
            obj.put("nombre", "Firulais");
            obj.put("id_raza", 1 );
            obj.put("sexo", "Macho");
            obj.put("color", "Café");
            obj.put("esterilizado", "si");
            obj.put("enfermedad", "Ninguna");
            obj.put("f_nac", "2009-10-03");
            obj.put("estatus", "en casa");
            obj.put("id_usuario", 3);
            obj.put("foto", "juguetes-perros.jpg");
            obj.put("id_ciudad", 1);
            obj.put("rasgos", "Juguetón");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest peticion=new JsonObjectRequest(
                Request.Method.POST,
                "http://unioncanina.mipantano.com/api/registrarMascota",
                obj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        VolleyS.getInstance(getApplicationContext()).getRequestQueue().add(peticion);

    }

}
