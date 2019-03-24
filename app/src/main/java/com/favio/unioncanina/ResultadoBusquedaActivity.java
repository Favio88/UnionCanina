package com.favio.unioncanina;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ResultadoBusquedaActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView ic_retroceso;
    LinearLayout ll_contactarDueno2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado_busqueda);

        ic_retroceso=findViewById(R.id.ic_retroceso);
        ll_contactarDueno2=findViewById(R.id.ll_contactarDueno2);

        ic_retroceso.setOnClickListener(this);
        ll_contactarDueno2.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.ic_retroceso:
                Intent itt_buscarMascotaIdActivity=new Intent(ResultadoBusquedaActivity.this, BuscarMascotaIdActivity.class);
                startActivity(itt_buscarMascotaIdActivity);
                finish();
                break;
            case R.id.ll_contactarDueno2:
                Intent itt_detallesMascotaExtraviadaActivity=new Intent(ResultadoBusquedaActivity.this, DetallesMiMascotaActivity.class);
                startActivity(itt_detallesMascotaExtraviadaActivity);
                break;
        }
    }
}
