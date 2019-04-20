package com.favio.unioncanina;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.favio.unioncanina.extras.CircleTransform;
import com.favio.unioncanina.modelos.Ciudad;
import com.favio.unioncanina.modelos.Estado;
import com.favio.unioncanina.modelos.Filtro;
import com.favio.unioncanina.modelos.Raza;
import com.favio.unioncanina.modelos.Usuario;
import com.favio.unioncanina.singleton.VolleyS;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RegistrarMascotaActivity extends AppCompatActivity implements View.OnClickListener{

    //Variables para los controles XML
    ImageView ic_retroceso, iv_fotoMascota;
    EditText et_nombreRegistrarMascota, et_colorRegistrarMascota, et_fnacRegistrarMascota, et_rasgosRegistrarMascota;
    Spinner sp_razaRegistrarMascota, sp_sexoRegistrarMascota, sp_estadoRegistrarMascota, sp_ciudadRegistrarMascota;
    LinearLayout ll_subirFotoRegistrarMascota;
    Button btn_guardarRegistrarMascota;
    //Variables para recuperar la fecha de nacimiento
    Integer dia, mes, anio;
    //Variables para cargar los Spinners
    List<String> listaSpinnerEsterilizado=new ArrayList<>(), listaSpinnerSexo=new ArrayList<>(), listaSpinnerRaza=new ArrayList<>(),
            listaSpinnerEstado=new ArrayList<>(), listaSpinnerCiudad=new ArrayList<>();
    List<Raza> listaRazas=new ArrayList<>();
    List<Estado> listaEstados=new ArrayList<>();
    ArrayList<Ciudad> listaCiudades=new ArrayList<>();
    //Variable para recuperar el Usuario loggeado (SharedPreferences)
    Usuario usuario;
    //Variables para formar el objeto Mascota
    String nombreMascota, sexoMascota, colorMascota, fnacMascota, rasgosMascota, estatusMascota;
    Integer idRazaMascota, idUsuarioMascota, idEstadoMascota, idCiudadMascota;
    JSONObject jsonMascota;
    Bitmap bitmapFotoMascota;
    String fotoMascotaString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_mascota);

        getControlesXML();

        cargarSpinnerRaza();
        cargarSpinnerSexo();
        cargarSpinnerEstado();
        cargarSpinnerCiudad();

        getUsuarioSharedPreferences();

        ic_retroceso.setOnClickListener(this);
        et_fnacRegistrarMascota.setOnClickListener(this);
        ll_subirFotoRegistrarMascota.setOnClickListener(this);
        btn_guardarRegistrarMascota.setOnClickListener(this);
    }

    public void getControlesXML(){

        ic_retroceso=findViewById(R.id.ic_retroceso);
        et_nombreRegistrarMascota=findViewById(R.id.et_nombreRegistrarMascota);
        sp_razaRegistrarMascota=findViewById(R.id.sp_razaRegistrarMascota);
        sp_sexoRegistrarMascota=findViewById(R.id.sp_sexoRegistrarMascota);
        et_colorRegistrarMascota=findViewById(R.id.et_colorRegistrarMascota);
        et_fnacRegistrarMascota=findViewById(R.id.et_fnacRegistrarMascota);
        sp_estadoRegistrarMascota=findViewById(R.id.sp_estadoRegistrarMascota);
        sp_ciudadRegistrarMascota=findViewById(R.id.sp_ciudadRegistrarMascota);
        et_rasgosRegistrarMascota=findViewById(R.id.et_rasgosRegistrarMascota);
        iv_fotoMascota=findViewById(R.id.iv_fotoMascota);
        ll_subirFotoRegistrarMascota=findViewById(R.id.ll_subirFotoRegistrarMascota);
        btn_guardarRegistrarMascota=findViewById(R.id.btn_guardarRegistrarMascota);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.ic_retroceso:
                finish();
                break;
            case R.id.et_fnacRegistrarMascota:
                obtenerFecha();
                break;
            case R.id.ll_subirFotoRegistrarMascota:
                cargarImagen();
                //Intent itt_seleccionarImagenActivity=new Intent(RegistrarMascotaActivity.this, SeleccionarImagenActivity.class);
                //startActivity(itt_seleccionarImagenActivity);
                break;
            case R.id.btn_guardarRegistrarMascota:
                formarJSONMascota();
                //registrarMascota();
                //Intent itt_inicioActivity=new Intent(RegistrarMascotaActivity.this, InicioActivity.class);
                //startActivity(itt_inicioActivity);
                break;
        }
    }

    public void cargarSpinnerRaza(){

        JsonArrayRequest peticion=new JsonArrayRequest(
                Request.Method.GET,
                "http://unioncanina.mipantano.com/api/razas",
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        Gson gson=new Gson();
                        Type listType=new TypeToken<List<Raza>>(){}.getType();
                        listaRazas=gson.fromJson(response.toString(), listType);
                        listaRazas.add(0, new Raza(0,"Raza"));

                        ArrayAdapter<Raza> adapterSpinnerRaza;
                        adapterSpinnerRaza=new ArrayAdapter<Raza>(getApplicationContext(), R.layout.item_filtro, listaRazas);

                        sp_razaRegistrarMascota.setAdapter(adapterSpinnerRaza);
                        sp_razaRegistrarMascota.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                                if (listaRazas.get(position).getId()!=0){

                                    idRazaMascota=listaRazas.get(position).getId();
                                    Toast.makeText(RegistrarMascotaActivity.this, idRazaMascota.toString(), Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    // Hacer nada
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegistrarMascotaActivity.this, "Error en la petición", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        VolleyS.getInstance(getApplicationContext()).getRequestQueue().add(peticion);
    }

    public void cargarSpinnerSexo(){

        listaSpinnerSexo.add(0, "Sexo");
        listaSpinnerSexo.add("Macho");
        listaSpinnerSexo.add("Hembra");

        ArrayAdapter<String> adapterSpinnerSexo;
        adapterSpinnerSexo=new ArrayAdapter<String>(getApplicationContext(), R.layout.item_filtro, listaSpinnerSexo);

        sp_sexoRegistrarMascota.setAdapter(adapterSpinnerSexo);
        sp_sexoRegistrarMascota.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                if (adapterView.getItemAtPosition(position).equals("Sexo")){

                }
                else{
                    sexoMascota=adapterView.getItemAtPosition(position).toString();

                    Toast.makeText(RegistrarMascotaActivity.this, sexoMascota, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void cargarSpinnerEstado(){

        JsonArrayRequest peticion=new JsonArrayRequest(
                Request.Method.GET,
                "http://unioncanina.mipantano.com/api/estados",
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        Gson gson=new Gson();
                        Type listType=new TypeToken<List<Estado>>(){}.getType();
                        listaEstados=gson.fromJson(response.toString(), listType);
                        listaEstados.add(0, new Estado(0,"Estado"));

                        ArrayAdapter<Estado> adapterSpinnerEstado;
                        adapterSpinnerEstado=new ArrayAdapter<Estado>(getApplicationContext(), R.layout.item_filtro, listaEstados);

                        sp_estadoRegistrarMascota.setAdapter(adapterSpinnerEstado);
                        sp_estadoRegistrarMascota.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                                if (listaEstados.get(position).getId()!=0){

                                    idEstadoMascota=listaEstados.get(position).getId();
                                    Toast.makeText(RegistrarMascotaActivity.this, idEstadoMascota.toString(), Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    // Hacer nada
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegistrarMascotaActivity.this, "Error en la petición", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        VolleyS.getInstance(getApplicationContext()).getRequestQueue().add(peticion);

    }

    public void cargarSpinnerCiudad(){

        JsonArrayRequest peticion=new JsonArrayRequest(
                Request.Method.GET,
                "http://unioncanina.mipantano.com/api/ciudades",
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        Gson gson=new Gson();
                        Type listType=new TypeToken<List<Ciudad>>(){}.getType();
                        listaCiudades=gson.fromJson(response.toString(), listType);
                        listaCiudades.add(0, new Ciudad(0,"Ciudad"));

                        ArrayAdapter<Ciudad> adapterSpinnerCiudad;
                        adapterSpinnerCiudad=new ArrayAdapter<Ciudad>(getApplicationContext(), R.layout.item_filtro, listaCiudades);

                        sp_ciudadRegistrarMascota.setAdapter(adapterSpinnerCiudad);
                        sp_ciudadRegistrarMascota.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                                if (listaCiudades.get(position).getId()!=0){

                                    idCiudadMascota=listaCiudades.get(position).getId();
                                    Toast.makeText(RegistrarMascotaActivity.this, idCiudadMascota.toString(), Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    // Hacer nada
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegistrarMascotaActivity.this, "Error en la petición", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        VolleyS.getInstance(getApplicationContext()).getRequestQueue().add(peticion);
    }

    public void obtenerFecha(){
        final Calendar fecha=Calendar.getInstance();
        dia=fecha.get(Calendar.DAY_OF_MONTH);
        mes=fecha.get(Calendar.MONTH);
        anio=fecha.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                if((month+1)<10 && day<10){
                    et_fnacRegistrarMascota.setText(year + "-0" + (month+1) + "-0" + day);
                }else if((month+1)<10 && day>=10){
                    et_fnacRegistrarMascota.setText(year + "-0" + (month+1) + "-" + day);
                }else if ((month+1)>=10 && day<10){
                    et_fnacRegistrarMascota.setText(year + "-" + (month+1) + "-0" + day);
                }else {
                    et_fnacRegistrarMascota.setText(year + "-" + (month+1) + "-" + day);
                }
            }
        }, dia, mes, anio);
        datePickerDialog.show();

    }

    public void cargarSpinnerEsterilizado(){

    /*  listaSpinnerEsterilizado.add(0, "¿Está esterilizado?");
        listaSpinnerEsterilizado.add("Sí");
        listaSpinnerEsterilizado.add("No");

        ArrayAdapter<String> adapterSpinnerEsterilizado;
        adapterSpinnerEsterilizado=new ArrayAdapter<String>(getApplicationContext(), R.layout.item_filtro, listaSpinnerEsterilizado);

        sp_esterilizadoRegistrarMascota.setAdapter(adapterSpinnerEsterilizado);
        sp_esterilizadoRegistrarMascota.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                if (adapterView.getItemAtPosition(position).equals("¿Está esterilizado?")){

                }
                else{
                    esterilizadoMascota=adapterView.getItemAtPosition(position).toString();

                    Toast.makeText(RegistrarMascotaActivity.this, esterilizadoMascota, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
     */
    }

    public void getUsuarioSharedPreferences(){

        SharedPreferences preferences = getSharedPreferences("Usuario", Context.MODE_PRIVATE);
        Gson gson= new Gson();
        usuario=gson.fromJson(preferences.getString("Usuario", ""), Usuario.class );
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
            Picasso.with(getApplicationContext()).load(path).fit().centerCrop().into(iv_fotoMascota);

            try {
                bitmapFotoMascota=MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), path);

            } catch (IOException e) {
                e.printStackTrace();
            }

            fotoMascotaString=convertirImgString(bitmapFotoMascota);
            Log.d("imagen", fotoMascotaString);
        }
    }

    private String convertirImgString(Bitmap bitmap) {

        ByteArrayOutputStream array=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100, array);
        byte[] imagenByte=array.toByteArray();
        String imagenString= Base64.encodeToString(imagenByte, Base64.DEFAULT);

        return imagenString;
    }


    public void formarJSONMascota(){

        nombreMascota=et_nombreRegistrarMascota.getText().toString();
        colorMascota=et_colorRegistrarMascota.getText().toString();
        fnacMascota=et_fnacRegistrarMascota.getText().toString();
        estatusMascota="en casa";
        rasgosMascota=et_rasgosRegistrarMascota.getText().toString();
        idUsuarioMascota=usuario.getId();

        jsonMascota=new JSONObject();

        try {
            jsonMascota.put("nombre", nombreMascota);
            jsonMascota.put("id_raza", idRazaMascota);
            jsonMascota.put("sexo", sexoMascota);
            jsonMascota.put("color", colorMascota);
            jsonMascota.put("esterilizado", "");
            jsonMascota.put("enfermedad", "");
            jsonMascota.put("f_nac", fnacMascota);
            jsonMascota.put("estatus", estatusMascota);
            jsonMascota.put("id_usuario", idUsuarioMascota);
            jsonMascota.put("foto", fotoMascotaString);
            jsonMascota.put("id_ciudad", idCiudadMascota);
            jsonMascota.put("rasgos", rasgosMascota);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("mascota", jsonMascota.toString());
    }

    public void registrarMascota(){

        JsonObjectRequest peticion=new JsonObjectRequest(
                Request.Method.POST,
                "http://unioncanina.mipantano.com/api/registrarMascota",
                jsonMascota,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        VolleyS.getInstance(getApplicationContext()).getRequestQueue().add(peticion);

    }

}
