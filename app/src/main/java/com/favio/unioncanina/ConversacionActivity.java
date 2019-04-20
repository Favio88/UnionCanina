package com.favio.unioncanina;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.favio.unioncanina.adaptadores.AdaptadorMensaje;
import com.favio.unioncanina.modelos.Conversacion;
import com.favio.unioncanina.modelos.FBMensaje;
import com.favio.unioncanina.modelos.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConversacionActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView ic_retroceso, ic_infoConversacion, ic_enviar;
    TextView participante;
    EditText et_escribirMensaje;
    DatabaseReference database;
    Bundle parameters;
    Conversacion conv;
    Usuario usuario;
    int id_conversation;

    AdaptadorMensaje adaptadorMensaje;
    List<FBMensaje> mensajes;
    RecyclerView recyclerViewMensajes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversacion);

        ic_retroceso=findViewById(R.id.ic_retroceso);
        ic_infoConversacion=findViewById(R.id.ic_infoConversacion);
        ic_enviar=findViewById(R.id.ic_enviar);
        et_escribirMensaje=findViewById(R.id.et_escribirMensaje);
        participante = findViewById(R.id.participante);
        recyclerViewMensajes = findViewById(R.id.rvMensajes);
        recyclerViewMensajes.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerViewMensajes.setLayoutManager(linearLayoutManager);

        ic_retroceso.setOnClickListener(this);
        ic_infoConversacion.setOnClickListener(this);
        ic_enviar.setOnClickListener(this);
        et_escribirMensaje.setOnClickListener(this);

        parameters = this.getIntent().getExtras();
        if(parameters !=null){

            SharedPreferences preferences = this.getSharedPreferences("Usuario", Context.MODE_PRIVATE);
            Gson gson = new Gson();
            usuario = gson.fromJson(preferences.getString("Usuario", "nani"), Usuario.class);

            database = FirebaseDatabase.getInstance().getReference();
            id_conversation = parameters.getInt("conversation");
            conv = (Conversacion) parameters.getSerializable("conversation");
            participante.setText(conv.getParticipante().getNombre());

            readMessages(usuario.getId(), conv.getParticipante().getId());
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.ic_retroceso:
                Intent itt_inicioActivity=new Intent(ConversacionActivity.this, InicioActivity.class);
                startActivity(itt_inicioActivity);
                finish();
                break;
            case R.id.ic_infoConversacion:
                Intent itt_informacionConversacionActivity=new Intent(ConversacionActivity.this, InformacionConversacionActivity.class);
                startActivity(itt_informacionConversacionActivity);
                break;
            case R.id.ic_enviar:

                sendMessage(
                        usuario.getId(),
                        conv.getParticipante().getId(),
                        String.valueOf(et_escribirMensaje.getText())
                );

                break;
            case R.id.et_escribirMensaje:

                break;
        }
    }

    public void sendMessage(int sender, int receiver, String message)
    {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("destinatario", receiver);
        hashMap.put("remitente", sender);
        hashMap.put("mensaje", message);
        hashMap.put("leido", 0);
        hashMap.put("conversacion", conv.getId());

        database.push().setValue(hashMap);
        et_escribirMensaje.setText("");
    }

    private void readMessages(final int myid, final int userid)
    {
        mensajes = new ArrayList<>();
        database = FirebaseDatabase.getInstance().getReference(String.valueOf(conv.getId()));
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mensajes.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    FBMensaje mensaje = snapshot.getValue(FBMensaje.class);

                    if(     mensaje.getDestinatario() == myid &&
                            mensaje.getRemitente() == userid ||
                            mensaje.getDestinatario() == userid &&
                            mensaje.getRemitente() == myid){
                        mensajes.add(mensaje);
                    }

                    adaptadorMensaje = new AdaptadorMensaje((ArrayList<FBMensaje>) mensajes,
                            ConversacionActivity.this, conv.getParticipante().getId());
                    recyclerViewMensajes.setAdapter(adaptadorMensaje);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
