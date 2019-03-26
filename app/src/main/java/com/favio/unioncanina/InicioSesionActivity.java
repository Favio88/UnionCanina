package com.favio.unioncanina;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

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



    public void login(){
      StringRequest st=new StringRequest(Request.Method.GET,
              "http://unioncanina.mipantano.com/loginandroid",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.contains("1")){
                            startActivity(new Intent(getApplicationContext(),InicioActivity.class));
                        }
                        Toast.makeText(getApplicationContext(),"Esto es una respuesta"+response,Toast.LENGTH_LONG).show();
                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Ahora haber problema",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError{
                Map<String,String> params=new HashMap<>();
                params.put("usuario",et_correo.getText().toString());
                params.put("contraseña",et_pwd.getText().toString());
                return params;
            }

        };
        Volley.newRequestQueue(this).add(st);
    }
    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.btn_iniciarSesion:
                login();
            case R.id.tv_registrame:
                Intent itt_registroActivity=new Intent(InicioSesionActivity.this, RegistroActivity.class);
                startActivity(itt_registroActivity);
                finish();
        }

    }
}
