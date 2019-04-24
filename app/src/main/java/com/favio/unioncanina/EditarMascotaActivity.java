package com.favio.unioncanina;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.favio.unioncanina.modelos.Ciudad;
import com.favio.unioncanina.modelos.Estado;
import com.favio.unioncanina.modelos.Mascota;
import com.favio.unioncanina.modelos.Raza;
import com.favio.unioncanina.singleton.VolleyS;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EditarMascotaActivity extends AppCompatActivity implements View.OnClickListener{

    //Variables para los controles XML
    ImageView ic_retroceso, iv_fotoEditarMiMascota;
    EditText et_nombreEditarMiMascota, et_colorEditarMiMascota, et_rasgosEditarMiMascota;
    TextView tv_fnacEditarMiMascota;
    Spinner  sp_razaEditarMiMascota, sp_sexoEditarMiMascota, sp_estadoEditarMiMascota, sp_ciudadEditarMiMascota;
    LinearLayout ll_subirFotoEditarMiMascota;
    Button btn_guardarEditarMiMascota;
    //Variables para cargar los Spinners
    List<String> listaSpinnerSexo=new ArrayList<>();
    List<Raza> listaRazas=new ArrayList<>();
    List<Estado> listaEstados=new ArrayList<>();
    ArrayList<Ciudad> listaCiudades=new ArrayList<>();
    //Variables para formar el objeto Mascota
    String sexoMascota;
    Integer idRazaMascota, idEstadoMascota, idCiudadMascota;
    Bundle bundle;
    Mascota mascota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_mascota);

        getControlesXML();

        ic_retroceso.setOnClickListener(this);
        tv_fnacEditarMiMascota.setOnClickListener(this);
        ll_subirFotoEditarMiMascota.setOnClickListener(this);
        btn_guardarEditarMiMascota.setOnClickListener(this);

        bundle=getIntent().getExtras();

        if(bundle!=null){
            String mascotaBundle=bundle.getString("Mascota");

            Gson gson=new Gson();
            mascota=gson.fromJson(mascotaBundle, Mascota.class);

            llenarCamposMiMascota();
        }
    }

    private void getControlesXML(){
        ic_retroceso=findViewById(R.id.ic_retroceso);
        iv_fotoEditarMiMascota=findViewById(R.id.iv_fotoEditarMiMascota);
        et_nombreEditarMiMascota=findViewById(R.id.et_nombreEditarMiMascota);
        sp_razaEditarMiMascota=findViewById(R.id.sp_razaEditarMiMascota);
        sp_sexoEditarMiMascota=findViewById(R.id.sp_sexoEditarMiMascota);
        et_colorEditarMiMascota=findViewById(R.id.et_colorEditarMiMascota);
        tv_fnacEditarMiMascota=findViewById(R.id.tv_fnacEditarMiMascota);
        sp_estadoEditarMiMascota=findViewById(R.id.sp_estadoEditarMiMascota);
        sp_ciudadEditarMiMascota=findViewById(R.id.sp_ciudadEditarMiMascota);
        et_rasgosEditarMiMascota=findViewById(R.id.et_rasgosEditarMiMascota);
        ll_subirFotoEditarMiMascota=findViewById(R.id.ll_subirFotoEditarMiMascota);
        btn_guardarEditarMiMascota=findViewById(R.id.btn_guardarEditarMiMascota);
    }

    @Override
    public void onClick(View view) {

        switch(view.getId()){

            case R.id.ic_retroceso:
                finish();
                break;
            case R.id.btn_guardarEditarMiMascota:
                Intent itt_inicioActivity=new Intent(EditarMascotaActivity.this, InicioActivity.class);
                startActivity(itt_inicioActivity);
                finish();
                break;
        }
    }

    private void llenarCamposMiMascota(){

        cargarSpinnerRaza();
        cargarSpinnerSexo();
        cargarSpinnerEstado();
        cargarSpinnerCiudad();

        et_nombreEditarMiMascota.setText(mascota.getNombre());
        et_colorEditarMiMascota.setText(mascota.getColor());
        tv_fnacEditarMiMascota.setText(mascota.getF_nac());
        et_rasgosEditarMiMascota.setText(mascota.getRasgos());
        Picasso.with(this).load("http://unioncanina.mipantano.com/api/petspp/" + mascota.getFoto())
                .fit().centerCrop().into(iv_fotoEditarMiMascota);
    }

    private void cargarSpinnerRaza(){

        JsonArrayRequest peticion=new JsonArrayRequest(
                Request.Method.GET,
                "http://unioncanina.mipantano.com/api/razas",
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        Gson gson=new Gson();
                        Type listType=new TypeToken<List<Raza>>(){}.getType();
                        listaRazas=gson.fromJson(response.toString(), listType);
                        listaRazas.add(0, new Raza(0,"Raza"));

                        ArrayAdapter<Raza> adapterSpinnerRaza;
                        adapterSpinnerRaza=new ArrayAdapter<Raza>(getApplicationContext(), R.layout.item_filtro, listaRazas);

                        sp_razaEditarMiMascota.setAdapter(adapterSpinnerRaza);
                        sp_razaEditarMiMascota.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                                if (listaRazas.get(position).getId()!=0){

                                    idRazaMascota=listaRazas.get(position).getId();
                                    Toast.makeText(EditarMascotaActivity.this, idRazaMascota.toString(), Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(EditarMascotaActivity.this, "Error en la petición", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        VolleyS.getInstance(getApplicationContext()).getRequestQueue().add(peticion);
    }

    private void cargarSpinnerSexo(){

        listaSpinnerSexo.add(0, "Sexo");
        listaSpinnerSexo.add("Macho");
        listaSpinnerSexo.add("Hembra");

        ArrayAdapter<String> adapterSpinnerSexo;
        adapterSpinnerSexo=new ArrayAdapter<String>(getApplicationContext(), R.layout.item_filtro, listaSpinnerSexo);

        sp_sexoEditarMiMascota.setAdapter(adapterSpinnerSexo);
        sp_sexoEditarMiMascota.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                if (adapterView.getItemAtPosition(position).equals("Sexo")){

                }
                else{
                    sexoMascota=adapterView.getItemAtPosition(position).toString();

                    Toast.makeText(EditarMascotaActivity.this, sexoMascota, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
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

                        sp_estadoEditarMiMascota.setAdapter(adapterSpinnerEstado);
                        sp_estadoEditarMiMascota.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                                if (listaEstados.get(position).getId()!=0){

                                    idEstadoMascota=listaEstados.get(position).getId();
                                    Toast.makeText(EditarMascotaActivity.this, idEstadoMascota.toString(), Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(EditarMascotaActivity.this, "Error en la petición", Toast.LENGTH_SHORT).show();
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

                        sp_ciudadEditarMiMascota.setAdapter(adapterSpinnerCiudad);
                        sp_ciudadEditarMiMascota.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                                if (listaCiudades.get(position).getId()!=0){

                                    idCiudadMascota=listaCiudades.get(position).getId();
                                    Toast.makeText(EditarMascotaActivity.this, idCiudadMascota.toString(), Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(EditarMascotaActivity.this, "Error en la petición", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        VolleyS.getInstance(getApplicationContext()).getRequestQueue().add(peticion);
    }

}
