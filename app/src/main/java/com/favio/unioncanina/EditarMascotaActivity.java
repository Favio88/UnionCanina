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
import android.support.v7.widget.RecyclerView;
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
import com.favio.unioncanina.modelos.Ciudad;
import com.favio.unioncanina.modelos.Estado;
import com.favio.unioncanina.modelos.Mascota;
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

public class EditarMascotaActivity extends AppCompatActivity implements View.OnClickListener{

    //Variables para los controles XML
    ImageView ic_retroceso, iv_fotoEditarMiMascota;
    EditText et_nombreEditarMiMascota, et_colorEditarMiMascota, et_rasgosEditarMiMascota;
    TextView tv_fnacEditarMiMascota;
    Spinner  sp_razaEditarMiMascota, sp_sexoEditarMiMascota, sp_estadoEditarMiMascota, sp_ciudadEditarMiMascota;
    LinearLayout ll_subirFotoEditarMiMascota;
    Button btn_guardarEditarMiMascota;

    //Variables para recuperar la fecha de nacimiento
    Integer dia, mes, anio;

    //Variables para cargar los Spinners
    List<String> listaSpinnerSexo=new ArrayList<>();
    List<Raza> listaRazas=new ArrayList<>();
    List<Estado> listaEstados=new ArrayList<>();
    List<Ciudad> listaCiudades=new ArrayList<>();

    //Variables para formar el objeto Mascota
    String nombreMascota, sexoMascota, colorMascota, fnacMascota, rasgosMascota;
    Integer idMascota, idRazaMascota, idEstadoMascota, idCiudadMascota, idUsuarioMascota;

    Bundle bundle;
    Mascota mascota;
    JSONObject jsonMascota;

    //Variable para recuperar el Usuario loggeado (SharedPreferences)
    Usuario usuario;

    Bitmap bitmapFotoMascota;
    String fotoMascotaString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_mascota);

        getControlesXML();
        getUsuarioSharedPreferences();

        ic_retroceso.setOnClickListener(this);
        tv_fnacEditarMiMascota.setOnClickListener(this);
        ll_subirFotoEditarMiMascota.setOnClickListener(this);
        btn_guardarEditarMiMascota.setOnClickListener(this);

        bundle=getIntent().getExtras();

        if(bundle!=null){
            String mascotaBundle=bundle.getString("Mascota");

            Gson gson=new Gson();
            mascota=gson.fromJson(mascotaBundle, Mascota.class);

            llenarCamposMiMascota();
        }
    }

    private void getControlesXML(){
        ic_retroceso=findViewById(R.id.ic_retroceso);
        iv_fotoEditarMiMascota=findViewById(R.id.iv_fotoEditarMiMascota);
        et_nombreEditarMiMascota=findViewById(R.id.et_nombreEditarMiMascota);
        sp_razaEditarMiMascota=findViewById(R.id.sp_razaEditarMiMascota);
        sp_sexoEditarMiMascota=findViewById(R.id.sp_sexoEditarMiMascota);
        et_colorEditarMiMascota=findViewById(R.id.et_colorEditarMiMascota);
        tv_fnacEditarMiMascota=findViewById(R.id.tv_fnacEditarMiMascota);
        sp_estadoEditarMiMascota=findViewById(R.id.sp_estadoEditarMiMascota);
        sp_ciudadEditarMiMascota=findViewById(R.id.sp_ciudadEditarMiMascota);
        et_rasgosEditarMiMascota=findViewById(R.id.et_rasgosEditarMiMascota);
        ll_subirFotoEditarMiMascota=findViewById(R.id.ll_subirFotoEditarMiMascota);
        btn_guardarEditarMiMascota=findViewById(R.id.btn_guardarEditarMiMascota);
    }

    @Override
    public void onClick(View view) {

        switch(view.getId()){

            case R.id.ic_retroceso:
                finish();
                break;
            case R.id.tv_fnacEditarMiMascota:
                obtenerFecha();
                break;
            case R.id.ll_subirFotoEditarMiMascota:
                cargarImagen();
                break;
            case R.id.btn_guardarEditarMiMascota:
                formarJSONMascota();
                editarMascota();
                break;
        }
    }

    private void llenarCamposMiMascota(){

        cargarSpinnerRaza();
        cargarSpinnerSexo();
        cargarSpinnerEstado();
        cargarSpinnerCiudad();

        et_nombreEditarMiMascota.setText(mascota.getNombre());
        et_colorEditarMiMascota.setText(mascota.getColor());
        tv_fnacEditarMiMascota.setText(mascota.getF_nac());
        et_rasgosEditarMiMascota.setText(mascota.getRasgos());
        Picasso.with(this).load("http://unioncanina.mipantano.com/api/petspp/" + mascota.getFoto())
                .fit().centerCrop().into(iv_fotoEditarMiMascota);
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

                        sp_razaEditarMiMascota.setAdapter(adapterSpinnerRaza);
                        sp_razaEditarMiMascota.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                                if (listaRazas.get(position).getId()!=0){

                                    idRazaMascota=listaRazas.get(position).getId();
                                    Toast.makeText(EditarMascotaActivity.this, idRazaMascota.toString(), Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(EditarMascotaActivity.this, "Error en la petición", Toast.LENGTH_SHORT).show();
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

        sp_sexoEditarMiMascota.setAdapter(adapterSpinnerSexo);
        sp_sexoEditarMiMascota.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                if (adapterView.getItemAtPosition(position).equals("Sexo")){

                }
                else{
                    sexoMascota=adapterView.getItemAtPosition(position).toString();

                    Toast.makeText(EditarMascotaActivity.this, sexoMascota, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
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
                    tv_fnacEditarMiMascota.setText(year + "-0" + (month+1) + "-0" + day);
                }else if((month+1)<10 && day>=10){
                    tv_fnacEditarMiMascota.setText(year + "-0" + (month+1) + "-" + day);
                }else if ((month+1)>=10 && day<10){
                    tv_fnacEditarMiMascota.setText(year + "-" + (month+1) + "-0" + day);
                }else {
                    tv_fnacEditarMiMascota.setText(year + "-" + (month+1) + "-" + day);
                }
            }
        }, dia, mes, anio);
        datePickerDialog.show();

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

                        sp_estadoEditarMiMascota.setAdapter(adapterSpinnerEstado);
                        sp_estadoEditarMiMascota.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                                if (listaEstados.get(position).getId()!=0){

                                    idEstadoMascota=listaEstados.get(position).getId();
                                    Toast.makeText(EditarMascotaActivity.this, idEstadoMascota.toString(), Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(EditarMascotaActivity.this, "Error en la petición", Toast.LENGTH_SHORT).show();
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

                        sp_ciudadEditarMiMascota.setAdapter(adapterSpinnerCiudad);
                        sp_ciudadEditarMiMascota.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                                if (listaCiudades.get(position).getId()!=0){

                                    idCiudadMascota=listaCiudades.get(position).getId();
                                    Toast.makeText(EditarMascotaActivity.this, idCiudadMascota.toString(), Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(EditarMascotaActivity.this, "Error en la petición", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        VolleyS.getInstance(getApplicationContext()).getRequestQueue().add(peticion);
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
            Picasso.with(getApplicationContext()).load(path).fit().centerCrop().into(iv_fotoEditarMiMascota);

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

        idMascota=mascota.getId();
        nombreMascota=et_nombreEditarMiMascota.getText().toString();
        colorMascota=et_colorEditarMiMascota.getText().toString();
        fnacMascota=tv_fnacEditarMiMascota.getText().toString();
        rasgosMascota=et_rasgosEditarMiMascota.getText().toString();
        idUsuarioMascota=usuario.getId();

        jsonMascota=new JSONObject();

        try {
            jsonMascota.put("id_mascota", idMascota);
            jsonMascota.put("nombre", nombreMascota);
            jsonMascota.put("sexo", sexoMascota);
            jsonMascota.put("color", colorMascota);
            jsonMascota.put("f_nac", fnacMascota);
            jsonMascota.put("id_ciudad", idCiudadMascota);
            jsonMascota.put("id_usuario", idUsuarioMascota);
            jsonMascota.put("id_raza", idRazaMascota);
            //jsonMascota.put("foto", fotoMascotaString);
            jsonMascota.put("foto", "juguetes-perros.jpg");
            jsonMascota.put("rasgos", rasgosMascota);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("mascotaEditar", jsonMascota.toString());
    }

    private void editarMascota(){

        JsonObjectRequest peticion=new JsonObjectRequest(
                Request.Method.POST,
                "http://unioncanina.mipantano.com/api/editarMascota",
                jsonMascota,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("mascotaRegistrada", response.toString());
                        irActivityInicio();
                        Toast.makeText(EditarMascotaActivity.this, "Actualizada", Toast.LENGTH_SHORT).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(EditarMascotaActivity.this, "Error en la petición", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        VolleyS.getInstance(getApplicationContext()).getRequestQueue().add(peticion);
    }

    private void irActivityInicio(){
        Intent itt_inicioActivity=new Intent(EditarMascotaActivity.this, InicioActivity.class);
        startActivity(itt_inicioActivity);
        finish();
    }
}
