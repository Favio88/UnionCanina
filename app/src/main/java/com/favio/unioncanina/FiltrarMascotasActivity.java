package com.favio.unioncanina;

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
import com.favio.unioncanina.modelos.Ciudad;
import com.favio.unioncanina.modelos.Raza;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FiltrarMascotasActivity extends AppCompatActivity  {
    ArrayList<Raza>listaRazas= new ArrayList<Raza>();
    ArrayList<Ciudad>listaCiudades=new ArrayList<Ciudad>();
    ImageView ic_retroceso;
    Spinner sp_raza, sp_sexo, sp_ciudad;
    EditText et_rasgo;
    Button btn_aplicarFiltros;
    int raza,ciudad;
    String rasgo,sexo;

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

        LoadSexo();
        BajarRazas();
        BajarCiudades();

    }
    private void LoadSexo(){
        ArrayAdapter<CharSequence>adapter=ArrayAdapter.createFromResource(
                this,R.array.combo_Sexo,android.R.layout.simple_list_item_1 );
        sp_sexo.setAdapter(adapter);

    }
    private void BajarCiudades(){
        //Con JSONOBJECT
        String url = "http://unioncanina.mipantano.com/api/ciudades";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i=0;i<response.length();i++){
                    Ciudad ciudad=new Ciudad();
                    try {
                        ciudad.setId(response.getJSONObject(i).getInt("id"));
                        ciudad.setNombre(response.getJSONObject(i).getString("nombre"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    listaCiudades.add(ciudad);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(this).add(jsonArrayRequest);
        MostrarCiudades();
    }
    private void BajarRazas(){
        //Con JSONOBJECT
        String url = "http://unioncanina.mipantano.com/api/razas";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
            for(int i=0;i<response.length();i++){
                Raza raza=new Raza();
                try {
                    raza.setId(response.getJSONObject(i).getInt("id"));
                    raza.setNombre(response.getJSONObject(i).getString("nombre"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                listaRazas.add(raza);
            }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(this).add(jsonArrayRequest);
        MostrarRazas();

    }
    private void MostrarRazas(){
        ArrayAdapter<Raza> adapter=new ArrayAdapter<Raza>(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,listaRazas );
        sp_raza.setAdapter(adapter);
    }
    private void MostrarCiudades(){
        ArrayAdapter<Ciudad>adapter=new ArrayAdapter<Ciudad>(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,listaCiudades);
        sp_ciudad.setAdapter(adapter);
    }

    public void filtrar(View view) {
    }
/*
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_aplicarFiltros:{
                AplicarFiltros();
            }break;
        }
    } */
}
