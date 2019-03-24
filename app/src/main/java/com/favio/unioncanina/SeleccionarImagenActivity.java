package com.favio.unioncanina;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

public class SeleccionarImagenActivity extends AppCompatActivity {

    ImageView ic_retroceso;
    RecyclerView rv_seleccionarImagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_imagen);

        ic_retroceso=findViewById(R.id.ic_retroceso);
        rv_seleccionarImagen=findViewById(R.id.rv_seleccionarImagen);

        ic_retroceso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
    }
}
