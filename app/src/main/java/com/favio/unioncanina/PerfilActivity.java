package com.favio.unioncanina;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.favio.unioncanina.extras.CircleTransform;
import com.favio.unioncanina.modelos.Usuario;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class PerfilActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView ic_retroceso, ic_cerrarSesion, iv_fotoPerfil;
    TextView tv_cambiarFotoPerfil;
    EditText et_nombre, et_apat, et_amat, et_correo, et_pwdactual, et_pwdnueva;
    Button btn_guardarCambiosPerfil;
    Bitmap bitmapFotoPerfil;
    String fotoPerfilString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        getControlesXML();

        ic_retroceso.setOnClickListener(this);
        ic_cerrarSesion.setOnClickListener(this);
        iv_fotoPerfil.setOnClickListener(this);
        tv_cambiarFotoPerfil.setOnClickListener(this);
        btn_guardarCambiosPerfil.setOnClickListener(this);

        llenarcampos();
    }

    public void getControlesXML(){

        ic_retroceso=findViewById(R.id.ic_retroceso);
        ic_cerrarSesion=findViewById(R.id.ic_cerrarSesion);
        iv_fotoPerfil=findViewById(R.id.iv_fotoPerfil);
        tv_cambiarFotoPerfil=findViewById(R.id.tv_cambiarFotoPerfil);
        et_nombre=findViewById(R.id.et_nombrePerfil);
        et_apat=findViewById(R.id.et_apat);
        et_amat=findViewById(R.id.et_amat);
        et_correo=findViewById(R.id.et_correo);
        et_pwdactual=findViewById(R.id.et_pwdActual);
        et_pwdnueva=findViewById(R.id.et_pwdnueva);

        btn_guardarCambiosPerfil=findViewById(R.id.btn_guardarCambiosPerfil);
    }

    private void eliminarPreferencias(){
        getApplicationContext().getSharedPreferences("Usuario", 0).edit().clear().apply();
        //context.getSharedPreferences("YOUR_PREFS", 0).edit().clear().commit();
    }

    private void llenarcampos(){
        SharedPreferences preferences = getSharedPreferences("Usuario", Context.MODE_PRIVATE);
        Gson gson= new Gson();
        Usuario usuario=gson.fromJson(preferences.getString("Usuario", ""), Usuario.class );
        String url;
        url="http://unioncanina.mipantano.com/api/profilePicture/"+usuario.getFoto();
        Picasso.with(getApplicationContext()).load(url).transform(new CircleTransform()).fit().centerCrop().into(iv_fotoPerfil);
        et_nombre.setText(usuario.getNombre());
        et_apat.setText(usuario.getApat() );
        et_amat.setText(usuario.getAmat());
        et_correo.setText(usuario.getCorreo());
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.ic_retroceso:
                finish();
                break;
            case R.id.ic_cerrarSesion:
                eliminarPreferencias();
                abrirDialogoCerrarSesion();
                break;
            case R.id.iv_fotoPerfil:
                cargarImagen();
                break;
            case R.id.tv_cambiarFotoPerfil:
                cargarImagen();
                break;
            case R.id.btn_guardarCambiosPerfil:
                //Toast.makeText(getApplicationContext(), "Click", Toast.LENGTH_SHORT).show();
                guardarCambios();
                break;
        }
    }

    private void cargarImagen(){

        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent, "Seleccione la aplicación"),10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK){
            Uri path=data.getData();
            Picasso.with(getApplicationContext()).load(path).fit().centerCrop().transform(new CircleTransform()).into(iv_fotoPerfil);

            try {
                bitmapFotoPerfil=MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), path);

            } catch (IOException e) {
                e.printStackTrace();
            }

            fotoPerfilString=convertirImgString(bitmapFotoPerfil);
            Log.d("imagen", fotoPerfilString);
        }
    }

    private String convertirImgString(Bitmap bitmap) {

        ByteArrayOutputStream array=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100, array);
        byte[] imagenByte=array.toByteArray();
        String imagenString= Base64.encodeToString(imagenByte, Base64.DEFAULT);

        return imagenString;
    }

    private void guardarCambios() {
        SharedPreferences preferences = getSharedPreferences("Usuario", Context.MODE_PRIVATE);
        Gson gson= new Gson();
        Usuario usuario=gson.fromJson(preferences.getString("Usuario", ""), Usuario.class );
        if(et_pwdactual.getText().toString().equals(usuario.getPwd()))
        {
            //Con JSONOBJECT

            JSONObject obj = new JSONObject();

            try {
                obj.put("id",usuario.getId());
                obj.put("nombre", et_nombre.getText().toString());
                obj.put("apat", et_apat.getText().toString());
                obj.put("amat", et_amat.getText().toString());
                obj.put("correo", et_correo.getText().toString());
                obj.put("pwd", et_pwdnueva.getText().toString());
                obj.put("foto", fotoPerfilString);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.e("objeto", obj.toString());


            String url = "http://unioncanina.mipantano.com/api/update";

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    obj,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Toast.makeText(getApplicationContext(), "Cambios aplicados", Toast.LENGTH_LONG).show();
                            finish();
                            startActivity(new Intent(getApplicationContext(),PerfilActivity.class));
                            guardarCredenciales(response);

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Error response", Toast.LENGTH_LONG).show();
                }
            });
            Volley.newRequestQueue(this).add(jsonObjectRequest);
        }
    }

    private void guardarCredenciales(JSONObject response) {

     /*   int id=response.optInt("id");
        String nombre= response.optString("nombre");
        String apat=response.optString("apat");
        String amat=response.optString("amat");
        String correo=response.optString("correo");
        String pwd=response.optString("pwd");
        String habilitado=response.optString("habilitado");
        String admin=response.optString("admin");
        String foto=response.optString("foto");*/


        SharedPreferences preferences = getSharedPreferences("Usuario", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        /*editor.putInt("id",id);
        editor.putString("nombre",nombre);
        editor.putString("apat",apat);
        editor.putString("amat",amat);
        editor.putString("correo",correo);
        editor.putString("pwd",pwd);
        editor.putString("habilitado",habilitado);
        editor.putString("admin",admin);
        editor.putString("foto",foto);*/

        editor.putString("Usuario", response.toString());
        editor.apply();
        Log.e("valor",preferences.getString("Usuario",""));

    }

    public void abrirDialogoCerrarSesion(){

        AlertDialog.Builder builder=new AlertDialog.Builder(PerfilActivity.this);
        builder.setTitle("Cerrar sesión")
                .setMessage("¿Deseas cerrar sesion?")
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(PerfilActivity.this,InicioSesionActivity.class));
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();
    }
}
