package com.favio.unioncanina;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.btn_iniciarSesion:
                Intent itt_inicioActivity=new Intent(InicioSesionActivity.this, InicioActivity.class);
                startActivity(itt_inicioActivity);
                finish();
            /*case R.id.tv_registrame:
                Intent itt_registroActivity=new Intent(InicioSesionActivity.this, RegistroActivity.class);
                startActivity(itt_registroActivity);
                finish();*/
        }

    }
}
