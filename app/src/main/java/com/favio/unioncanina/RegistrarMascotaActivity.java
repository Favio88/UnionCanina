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
import android.widget.TextView;
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
    ImageView ic_retroceso, iv_fotoRegistrarMiMascota;
    TextView tv_fnacRegistrarMiMascota;
    EditText et_nombreRegistrarMiMascota, et_colorRegistrarMiMascota, et_rasgosRegistrarMiMascota;
    Spinner sp_razaRegistrarMiMascota, sp_sexoRegistrarMiMascota, sp_estadoRegistrarMiMascota, sp_ciudadRegistrarMiMascota;
    LinearLayout ll_subirFotoRegistrarMiMascota;
    Button btn_guardarRegistrarMiMascota;
    //Variables para recuperar la fecha de nacimiento
    Integer dia, mes, anio;
    //Variables para cargar los Spinners
    List<String> listaSpinnerSexo=new ArrayList<>();
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
        cargarSpinners();
        getUsuarioSharedPreferences();

        ic_retroceso.setOnClickListener(this);
        tv_fnacRegistrarMiMascota.setOnClickListener(this);
        ll_subirFotoRegistrarMiMascota.setOnClickListener(this);
        btn_guardarRegistrarMiMascota.setOnClickListener(this);
    }

    private void getControlesXML(){

        ic_retroceso=findViewById(R.id.ic_retroceso);
        et_nombreRegistrarMiMascota=findViewById(R.id.et_nombreRegistrarMiMascota);
        sp_razaRegistrarMiMascota=findViewById(R.id.sp_razaRegistrarMiMascota);
        sp_sexoRegistrarMiMascota=findViewById(R.id.sp_sexoRegistrarMiMascota);
        et_colorRegistrarMiMascota=findViewById(R.id.et_colorRegistrarMiMascota);
        tv_fnacRegistrarMiMascota=findViewById(R.id.tv_fnacRegistrarMiMascota);
        sp_estadoRegistrarMiMascota=findViewById(R.id.sp_estadoRegistrarMiMascota);
        sp_ciudadRegistrarMiMascota=findViewById(R.id.sp_ciudadRegistrarMiMascota);
        et_rasgosRegistrarMiMascota=findViewById(R.id.et_rasgosRegistrarMiMascota);
        iv_fotoRegistrarMiMascota=findViewById(R.id.iv_fotoRegistrarMiMascota);
        ll_subirFotoRegistrarMiMascota=findViewById(R.id.ll_subirFotoRegistrarMiMascota);
        btn_guardarRegistrarMiMascota=findViewById(R.id.btn_guardarRegistrarMiMascota);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.ic_retroceso:
                finish();
                break;
            case R.id.tv_fnacRegistrarMiMascota:
                obtenerFecha();
                break;
            case R.id.ll_subirFotoRegistrarMiMascota:
                cargarImagen();
                break;
            case R.id.btn_guardarRegistrarMiMascota:
                formarJSONMascota();
                //registrarMascota();
                //irActivityInicio();
                break;
        }
    }

    private void cargarSpinnerRaza(){

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

                        sp_razaRegistrarMiMascota.setAdapter(adapterSpinnerRaza);
                        sp_razaRegistrarMiMascota.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

    private void cargarSpinnerSexo(){

        listaSpinnerSexo.add(0, "Sexo");
        listaSpinnerSexo.add("Macho");
        listaSpinnerSexo.add("Hembra");

        ArrayAdapter<String> adapterSpinnerSexo;
        adapterSpinnerSexo=new ArrayAdapter<String>(getApplicationContext(), R.layout.item_filtro, listaSpinnerSexo);

        sp_sexoRegistrarMiMascota.setAdapter(adapterSpinnerSexo);
        sp_sexoRegistrarMiMascota.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

    private void cargarSpinnerEstado(){

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

                        sp_estadoRegistrarMiMascota.setAdapter(adapterSpinnerEstado);
                        sp_estadoRegistrarMiMascota.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

    private void cargarSpinnerCiudad(){

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

                        sp_ciudadRegistrarMiMascota.setAdapter(adapterSpinnerCiudad);
                        sp_ciudadRegistrarMiMascota.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

    private void obtenerFecha(){

        final Calendar fecha=Calendar.getInstance();

        dia=fecha.get(Calendar.DAY_OF_MONTH);
        mes=fecha.get(Calendar.MONTH);
        anio=fecha.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                if((month+1)<10 && day<10){
                    tv_fnacRegistrarMiMascota.setText(year + "-0" + (month+1) + "-0" + day);
                }else if((month+1)<10 && day>=10){
                    tv_fnacRegistrarMiMascota.setText(year + "-0" + (month+1) + "-" + day);
                }else if ((month+1)>=10 && day<10){
                    tv_fnacRegistrarMiMascota.setText(year + "-" + (month+1) + "-0" + day);
                }else {
                    tv_fnacRegistrarMiMascota.setText(year + "-" + (month+1) + "-" + day);
                }
            }
        }, dia, mes, anio);
        datePickerDialog.show();

    }

    private void cargarSpinnerEsterilizado(){

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
            Picasso.with(getApplicationContext()).load(path).fit().centerCrop().into(iv_fotoRegistrarMiMascota);

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


    private void formarJSONMascota(){

        nombreMascota=et_nombreRegistrarMiMascota.getText().toString();
        colorMascota=et_colorRegistrarMiMascota.getText().toString();
        fnacMascota=tv_fnacRegistrarMiMascota.getText().toString();
        estatusMascota="en casa";
        rasgosMascota=et_rasgosRegistrarMiMascota.getText().toString();
        idUsuarioMascota=usuario.getId();

        jsonMascota=new JSONObject();

        try {
            jsonMascota.put("nombre", nombreMascota);
            jsonMascota.put("id_raza", idRazaMascota);
            jsonMascota.put("sexo", sexoMascota);
            jsonMascota.put("color", colorMascota);
            jsonMascota.put("esterilizado", "No");
            jsonMascota.put("enfermedad", "Ninguna");
            jsonMascota.put("f_nac", fnacMascota);
            jsonMascota.put("estatus", estatusMascota);
            jsonMascota.put("id_usuario", idUsuarioMascota);
            //jsonMascota.put("foto", fotoMascotaString);
            jsonMascota.put("foto", "juguetes-perros.jpg");
            jsonMascota.put("id_ciudad", idCiudadMascota);
            jsonMascota.put("rasgos", rasgosMascota);
            jsonMascota.put("habilitada", "Si");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("mascotaRegistrar", jsonMascota.toString());
    }

    private void registrarMascota(){

        JsonObjectRequest peticion=new JsonObjectRequest(
                Request.Method.POST,
                "http://unioncanina.mipantano.com/api/registrarMascota",
                jsonMascota,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("mascotaRegistrada", response.toString());
                        Toast.makeText(RegistrarMascotaActivity.this, "Registrada", Toast.LENGTH_SHORT).show();
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

    private void irActivityInicio(){
        Intent itt_inicioActivity=new Intent(RegistrarMascotaActivity.this, InicioActivity.class);
        startActivity(itt_inicioActivity);
    }

    private void cargarSpinners(){
        cargarSpinnerRaza();
        cargarSpinnerSexo();
        cargarSpinnerEstado();
        cargarSpinnerCiudad();
    }

}
