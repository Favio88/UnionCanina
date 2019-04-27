package com.favio.unioncanina;

import android.app.VoiceInteractor;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.favio.unioncanina.extras.JsonArrayRequestCustom;
import com.favio.unioncanina.modelos.Ciudad;
import com.favio.unioncanina.modelos.Raza;
import com.favio.unioncanina.singleton.VolleyS;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FiltrarMascotasActivity extends AppCompatActivity implements View.OnClickListener {
    ArrayList<Raza>listaRazas= new ArrayList<Raza>();
    ArrayList<Ciudad>listaCiudades=new ArrayList<Ciudad>();
    ImageView ic_retroceso;
    Spinner sp_raza, sp_sexo, sp_ciudad;
    EditText et_rasgo;
    Button btn_aplicarFiltros;
    int raza=0,ciudad=0;
    String rasgo="",sexo="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtrar_mascotas);

        ic_retroceso=findViewById(R.id.ic_retroceso);
        sp_raza=findViewById(R.id.sp_raza);
        sp_sexo=findViewById(R.id.sp_sexo);
        sp_ciudad=findViewById(R.id.sp_ciudad);
        et_rasgo=findViewById(R.id.et_rasgo);
        btn_aplicarFiltros=findViewById(R.id.btn_aplicarFiltros);
        btn_aplicarFiltros.setOnClickListener(this);

        LoadSexo();
        BajarRazas();
        BajarCiudades();

    }
    private void LoadSexo(){
        ArrayAdapter<CharSequence>adapter=ArrayAdapter.createFromResource(
                this,R.array.combo_Sexo,R.layout.item_filtro );
        sp_sexo.setAdapter(adapter);
        sp_sexo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               if(position == 0){
                   sexo=null;
               }else{
                   sexo=parent.getItemAtPosition(position).toString();
               }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    private void BajarCiudades(){

        String url = "http://unioncanina.mipantano.com/api/ciudades";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,url,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Gson gson=new Gson();
                Type listType=new TypeToken<List<Ciudad>>(){}.getType();
                listaCiudades=gson.fromJson(response.toString(),listType);
                listaCiudades.add(0,new Ciudad(0,"Ciudad"));
                ArrayAdapter<Ciudad> ciudadArrayAdapter;
                ciudadArrayAdapter=new ArrayAdapter<Ciudad>(getApplicationContext(),R.layout.item_filtro,listaCiudades);
                sp_ciudad.setAdapter(ciudadArrayAdapter);
                sp_ciudad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if(listaCiudades.get(position).getId()!=0){
                            ciudad=listaCiudades.get(position).getId();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        VolleyS.getInstance(getApplicationContext()).getRequestQueue().add(jsonArrayRequest);
        MostrarCiudades();
    }
    private void BajarRazas(){
        //Con JSONOBJECT
        String url = "http://unioncanina.mipantano.com/api/razas";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,url,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Gson gson=new Gson();
                Type listType=new TypeToken<List<Raza>>(){}.getType();
                listaRazas=gson.fromJson(response.toString(),listType);
                listaRazas.add(0,new Raza(0,"Raza"));
                ArrayAdapter<Raza> razaArrayAdapter;
                razaArrayAdapter=new ArrayAdapter<Raza>(getApplicationContext(),R.layout.item_filtro,listaRazas);
                sp_raza.setAdapter(razaArrayAdapter);
                sp_raza.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if(listaRazas.get(position).getId()!=0){
                            raza=listaRazas.get(position).getId();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        VolleyS.getInstance(getApplicationContext()).getRequestQueue().add(jsonArrayRequest);


    }

    private void MostrarCiudades(){
        ArrayAdapter<Ciudad>adapter=new ArrayAdapter<Ciudad>(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,listaCiudades);
        sp_ciudad.setAdapter(adapter);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_aplicarFiltros:{
                String url = "http://unioncanina.mipantano.com/api/filtrar";
                JSONObject obj = new JSONObject();
                try {
                    obj.put("raza",raza);
                    obj.put("sexo", sexo);
                    obj.put("ciudad", ciudad);
                    obj.put("rasgo",et_rasgo.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("objeto de filtros", obj.toString());


                //Peticion personalizada

                JsonArrayRequestCustom jsonArrayRequestCustom=new JsonArrayRequestCustom(
                        Request.Method.POST,
                        url,
                        obj,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                Log.e("extravios del response",response.toString());
                                Bundle bundle=new Bundle();
                                bundle.putString("extravios",response.toString());
                                Intent i=new Intent(getApplicationContext(),InicioActivity.class);
                                i.putExtras(bundle);
                                startActivity(i);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(FiltrarMascotasActivity.this, "Error response MotherFucker", Toast.LENGTH_SHORT).show();
                    }
                }
                );
                VolleyS.getInstance(getApplicationContext()).getRequestQueue().add(jsonArrayRequestCustom);

                //peticion personalizada
            }break;
        }

    }
}
