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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class FiltrarMascotasActivity extends AppCompatActivity {

    ImageView ic_retroceso;
    Spinner sp_raza, sp_sexo, sp_ciudad;
    EditText et_rasgo;
    Button btn_aplicarFiltros;

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
    }
    private void LoadSexo(){
        ArrayAdapter<CharSequence>adapter=ArrayAdapter.createFromResource(
                this,R.array.combo_Sexo ,android.R.layout.simple_list_item_1 );
        sp_sexo.setAdapter(adapter);
    }


    private void AplicarFiltros(View view){

    }
}
