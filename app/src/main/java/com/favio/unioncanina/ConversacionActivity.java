package com.favio.unioncanina;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

public class ConversacionActivity extends AppCompatActivity {

    ImageView ic_retroceso, ic_infoConversacion;
    EditText et_escribirMensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversacion);

    }
}
