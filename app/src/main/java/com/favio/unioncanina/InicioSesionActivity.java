package com.favio.unioncanina;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.favio.unioncanina.modelos.Usuario;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

public class InicioSesionActivity extends AppCompatActivity implements View.OnClickListener{

    EditText et_correo, et_pwd;
    Button btn_iniciarSesion;
    TextView tv_registrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);

        et_correo=findViewById(R.id.et_correo);
        et_pwd=findViewById(R.id.et_pwd);
        btn_iniciarSesion=findViewById(R.id.btn_iniciarSesion);
        tv_registrame=findViewById(R.id.tv_registrame);

        btn_iniciarSesion.setOnClickListener(this);
        tv_registrame.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.btn_iniciarSesion:

                /*Intent itt_inicioActivity=new Intent(InicioSesionActivity.this, InicioActivity.class);
                startActivity(itt_inicioActivity);
                finish();*/

                entrar();
                break;

            case R.id.tv_registrame:

                Intent itt_registroActivity=new Intent(getApplicationContext(), RegistroActivity.class);
                startActivity(itt_registroActivity);
                finish();
                break;
        }

    }

    public void entrar() {

        //Con JSONOBJECT
        JSONObject obj= new JSONObject();

        try {
            obj.put("correo",et_correo.getText().toString());
            obj.put("pwd",et_pwd.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }


        String url="http://unioncanina.mipantano.com/api/login";
        //RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                obj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if(response.isNull("nombre")){
                            Toast.makeText(getApplicationContext(),"Usuario o contraseña incorrectos",Toast.LENGTH_LONG).show();
                        }else{
                            guardarCredenciales(response);
                            startActivity(new Intent(getApplicationContext(),InicioActivity.class));
                        }

                        try {
                            Log.e("nombre",response.getString("nombre")) ;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }}, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Usuario o contraseña incorrectos",Toast.LENGTH_LONG).show();
            }
        });
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }
    private void guardarCredenciales(JSONObject response) {

     /*   int id=response.optInt("id");
        String nombre= response.optString("nombre");
        String apat=response.optString("apat");
        String amat=response.optString("amat");
        String correo=response.optString("correo");
        String pwd=response.optString("pwd");
        String habilitado=response.optString("habilitado");
        String admin=response.optString("admin");
        String foto=response.optString("foto");*/


        SharedPreferences preferences = getSharedPreferences("Usuario", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        /*editor.putInt("id",id);
        editor.putString("nombre",nombre);
        editor.putString("apat",apat);
        editor.putString("amat",amat);
        editor.putString("correo",correo);
        editor.putString("pwd",pwd);
        editor.putString("habilitado",habilitado);
        editor.putString("admin",admin);
        editor.putString("foto",foto);*/

        editor.putString("Usuario", response.toString());
        editor.apply();
        Log.e("valor",preferences.getString("Usuario",""));

    }

    public void registro(View view) {


    }

        /*
        StringRequest st=new StringRequest(Request.Method.POST,
                "http://unioncanina.mipantano.com/loginandroid",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(),"Esto es una respuesta"+response,Toast.LENGTH_LONG).show();
                        if(response.contains("1")){
                            startActivity(new Intent(getApplicationContext(),InicioActivity.class));
                            Toast.makeText(getApplicationContext(),"Esto es una respuesta2"+response,Toast.LENGTH_LONG).show();
                        }
                        if(response.contains("0")){
                           Toast.makeText(getApplicationContext(),"Usuario o contraseña incorrectos"+response,Toast.LENGTH_LONG).show();
                        }

                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error Response",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError{
                Map<String,String> params=new HashMap<>();
                params.put("correo",et_correo.getText().toString());
                params.put("pwd",et_pwd.getText().toString());
                return params;
            }

        };
        Volley.newRequestQueue(this).add(st);
    }*/

}
