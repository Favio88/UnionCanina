package com.favio.unioncanina;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegistroActivity extends AppCompatActivity implements View.OnClickListener{

    EditText et_usuarioRegistro, et_correoRegistro, et_pwdRegistro, et_pwd2Registro, et_estadoRegistro, et_ciudadRegistro;
    Button btn_registrarme;
    TextView tv_iniciarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        et_usuarioRegistro=findViewById(R.id.et_usuarioRegistro);
        et_correoRegistro=findViewById(R.id.et_correoRegistro);
        et_pwdRegistro=findViewById(R.id.et_pwdRegistro);
        et_pwd2Registro=findViewById(R.id.et_pwd2Registro);
        et_estadoRegistro=findViewById(R.id.et_estadoRegistro);
        et_ciudadRegistro=findViewById(R.id.et_ciudadRegistro);
        btn_registrarme=findViewById(R.id.btn_registrarme);
        tv_iniciarSesion=findViewById(R.id.btn_iniciarSesion);

        btn_registrarme.setOnClickListener(this);
        tv_iniciarSesion.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.btn_registrarme:
                Intent itt_inicioActivity=new Intent(RegistroActivity.this, InicioActivity.class);
                startActivity(itt_inicioActivity);
                finish();
                break;
            case R.id.tv_iniciarSesion:
                Intent itt_inicioSesionActivity=new Intent(RegistroActivity.this, InicioSesionActivity.class);
                startActivity(itt_inicioSesionActivity);
                finish();
                break;
        }
    }
}
