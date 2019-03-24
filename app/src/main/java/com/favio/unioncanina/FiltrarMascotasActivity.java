package com.favio.unioncanina;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

public class FiltrarMascotasActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView ic_retroceso;
    Spinner sp_razaMascotaFiltro, sp_sexoMascotaFiltro, sp_ciudadMascotaFiltro, sp_coloniaMascotaFiltro;
    EditText et_rasgosMascotaFiltro;
    Button btn_aplicarFiltros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtrar_mascotas);

        ic_retroceso=findViewById(R.id.ic_retroceso);
        sp_razaMascotaFiltro=findViewById(R.id.sp_razaMascotaFiltro);
        sp_sexoMascotaFiltro=findViewById(R.id.sp_sexoMascotaFiltro);
        sp_ciudadMascotaFiltro=findViewById(R.id.sp_ciudadMascotaFiltro);
        sp_coloniaMascotaFiltro=findViewById(R.id.sp_coloniaMascotaFiltro);
        et_rasgosMascotaFiltro=findViewById(R.id.et_rasgosMascotaFiltro);
        btn_aplicarFiltros=findViewById(R.id.btn_aplicarFiltros);

        ic_retroceso.setOnClickListener(this);
        btn_aplicarFiltros.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.ic_retroceso:
                irInicioActivity();
                break;
            case R.id.btn_aplicarFiltros:
                irInicioActivity();
                break;
        }
    }

    public void irInicioActivity(){

        Intent itt_inicioActivity=new Intent(FiltrarMascotasActivity.this, InicioActivity.class);
        startActivity(itt_inicioActivity);
        finish();
    }
}
