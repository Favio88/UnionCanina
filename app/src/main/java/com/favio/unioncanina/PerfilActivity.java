package com.favio.unioncanina;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class PerfilActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView ic_cerrar, ic_cerrarSesion, ic_fotoPerfil;
    TextView tv_cambiarFotoPerfil;
    EditText et_nombrePerfil, et_correoPerfil, et_estadoPerfil, et_ciudadPerfil, et_pwdNuevoPerfil, et_pwdActualPerfil;
    Button btn_guardarCambiosPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        ic_cerrar=findViewById(R.id.ic_cerrar);
        ic_cerrarSesion=findViewById(R.id.ic_cerrarSesion);
        ic_fotoPerfil=findViewById(R.id.ic_fotoPerfil);
        tv_cambiarFotoPerfil=findViewById(R.id.tv_cambiarFotoPerfil);
        et_nombrePerfil=findViewById(R.id.et_nombrePerfil);
        et_correoPerfil=findViewById(R.id.et_correoPerfil);
        et_estadoPerfil=findViewById(R.id.et_estadoPerfil);
        et_ciudadPerfil=findViewById(R.id.et_ciudadPerfil);
        et_pwdNuevoPerfil=findViewById(R.id.et_pwdNuevoPerfil);
        et_pwdActualPerfil=findViewById(R.id.et_pwdActualPerfil);
        btn_guardarCambiosPerfil=findViewById(R.id.btn_guardarCambiosPerfil);

        ic_cerrar.setOnClickListener(this);
        ic_cerrarSesion.setOnClickListener(this);
        ic_fotoPerfil.setOnClickListener(this);
        tv_cambiarFotoPerfil.setOnClickListener(this);
        btn_guardarCambiosPerfil.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.ic_cerrar:
                finish();
                break;
            case R.id.ic_cerrarSesion:
                Intent itt_inicioSesionActivity=new Intent(PerfilActivity.this, InicioSesionActivity.class);
                startActivity(itt_inicioSesionActivity);
                finish();
                break;
            case R.id.ic_fotoPerfil:

                break;
            case R.id.tv_cambiarFotoPerfil:

                break;
            case R.id.btn_guardarCambiosPerfil:

                break;
        }
    }
}
