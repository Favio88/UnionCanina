package com.favio.unioncanina;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ContactarDuenoActivity extends AppCompatActivity {

    ImageView ic_retroceso;
    EditText et_escribirMensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactar_dueno);

        ic_retroceso=findViewById(R.id.ic_retroceso);
        et_escribirMensaje=findViewById(R.id.et_escribirMensaje);

        ic_retroceso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent itt_detallesMascotaExtraviadaActivity=new Intent(ContactarDuenoActivity.this, DetallesMascotaExtraviadaActivity.class);
                startActivity(itt_detallesMascotaExtraviadaActivity);
                finish();
            }
        });
    }

}
