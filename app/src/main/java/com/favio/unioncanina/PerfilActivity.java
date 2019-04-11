package com.favio.unioncanina;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.favio.unioncanina.extras.CircleTransform;
import com.favio.unioncanina.modelos.Usuario;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

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
    private void EliminarPreferencias(){
        getApplicationContext().getSharedPreferences("Usuario", 0).edit().clear().apply();
        //context.getSharedPreferences("YOUR_PREFS", 0).edit().clear().commit();
    }
    private void llenarcampos(){
        SharedPreferences preferences = getSharedPreferences("Usuario", Context.MODE_PRIVATE);
        Gson gson= new Gson();
        Usuario usuario=gson.fromJson(preferences.getString("Usuario", ""), Usuario.class );
        String url;
        url="http://unioncanina.mipantano.com/api/profilePicture/"+usuario.getFoto();

        Picasso.with(getApplicationContext()).load(url).transform(new CircleTransform().).fit().centerCrop().into(ic_fotoPerfil);


       // et_nombrePerfil.setText(, );


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.ic_cerrar:
                finish();
                break;
            case R.id.ic_cerrarSesion:
                EliminarPreferencias();
                startActivity(new Intent(PerfilActivity.this,InicioSesionActivity.class));
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
