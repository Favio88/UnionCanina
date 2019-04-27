package com.favio.unioncanina;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.favio.unioncanina.extras.JsonArrayRequestCustom;
import com.favio.unioncanina.singleton.VolleyS;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BuscarMascotaIdActivity extends AppCompatActivity implements View.OnClickListener{

    Button btn_buscarMascota;
    EditText et_codigoMascota;
    ImageView ic_retroceso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_mascota_id);

        ic_retroceso = findViewById(R.id.ic_retroceso);
        et_codigoMascota = findViewById(R.id.et_codigoMascota);
        btn_buscarMascota = findViewById(R.id.btn_buscarMascota);

        ic_retroceso.setOnClickListener(this);
        btn_buscarMascota.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.ic_retroceso:
                Intent itt_inicioActivity=new Intent(BuscarMascotaIdActivity.this, InicioActivity.class);
                startActivity(itt_inicioActivity);
                finish();
                break;
            case R.id.btn_buscarMascota:
                String urla = "http://unioncanina.mipantano.com/api/filtrarid";
                JSONObject obj = new JSONObject();
                try {
                    obj.put("codigo",et_codigoMascota.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("objeto de filtros", obj.toString());
                //Peticion personalizada

                JsonArrayRequestCustom jsonArrayRequestCustom=new JsonArrayRequestCustom(
                        Request.Method.POST,
                        urla,
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
                        Toast.makeText(BuscarMascotaIdActivity.this, "Error response MotherFucker", Toast.LENGTH_SHORT).show();
                    }
                }
                );
                VolleyS.getInstance(getApplicationContext()).getRequestQueue().add(jsonArrayRequestCustom);
                break;
        }
    }
}
