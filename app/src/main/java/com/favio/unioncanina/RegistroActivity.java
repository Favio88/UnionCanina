package com.favio.unioncanina;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegistroActivity extends AppCompatActivity implements View.OnClickListener{
    Button Btregistrar;
    EditText Ednombre,Edapat,Edamat,Edcorreo,Edpwd,Edpwdconfirmar;
    TextView Txtiniciar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        Ednombre=findViewById(R.id.et_nombre);
        Edapat=findViewById(R.id.et_apat);
        Edamat=findViewById(R.id.et_amat);
        Edcorreo=findViewById(R.id.et_correo);
        Edpwd=findViewById(R.id.et_pwd);
        Edpwdconfirmar=findViewById(R.id.et_pwd_confirmar);

        Txtiniciar=findViewById(R.id.tv_iniciarSesion);

        Btregistrar=findViewById(R.id.btn_registrar);

        Btregistrar.setOnClickListener(this);
        Txtiniciar.setOnClickListener(this);


    }

    private void Registrar(){
        //Con JSONOBJECT
        JSONObject obj = new JSONObject();

        try {
            obj.put("nombre", Ednombre.getText().toString());
            obj.put("apat", Edapat.getText().toString());
            obj.put("amat", Edamat.getText().toString());
            obj.put("correo", Edcorreo.getText().toString());
            obj.put("pwd", Edpwd.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("objeto", obj.toString());


        String url = "http://unioncanina.mipantano.com/api/register";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                obj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                       // Toast.makeText(getApplicationContext(), "Response motherfucker", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(),InicioSesionActivity.class));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error response", Toast.LENGTH_LONG).show();
            }
        });
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.btn_registrar:

                    Registrar();


                break;
            case R.id.tv_iniciarSesion:
                startActivity(new Intent(getApplicationContext(),InicioSesionActivity.class));
                finish();
                break;
        }
    }
}
