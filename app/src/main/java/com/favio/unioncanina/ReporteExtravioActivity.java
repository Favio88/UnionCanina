package com.favio.unioncanina;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class ReporteExtravioActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView ic_retroceso;
    TextView tv_nombreMascotaExtravio;
    Spinner sp_estadoExtravio, sp_ciudadExtravio, sp_coloniaExtravio;
    EditText et_infoExtra;
    LinearLayout ll_reportarExtravio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_extravio);

        ic_retroceso=findViewById(R.id.ic_retroceso);
        tv_nombreMascotaExtravio=findViewById(R.id.tv_nombreMascotaExtravio);
        sp_estadoExtravio=findViewById(R.id.sp_estadoExtravio);
        sp_ciudadExtravio=findViewById(R.id.sp_ciudadExtravio);
        sp_coloniaExtravio=findViewById(R.id.sp_coloniaExtravio);
        et_infoExtra=findViewById(R.id.et_infoExtra);
        ll_reportarExtravio=findViewById(R.id.ll_reportarExtravio);

        ic_retroceso.setOnClickListener(this);
        ll_reportarExtravio.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.ic_retroceso:
                finish();
                break;
            case R.id.ll_reportarExtravio:
                Intent itt_misMascotasActivity=new Intent(ReporteExtravioActivity.this, MisMascotasFragment.class);
                startActivity(itt_misMascotasActivity);
                finish();
                break;
        }
    }
}
