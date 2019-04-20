package com.favio.unioncanina.adaptadores;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.favio.unioncanina.R;
import com.favio.unioncanina.modelos.FBMensaje;
import com.favio.unioncanina.modelos.Usuario;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdaptadorMensaje extends RecyclerView.Adapter<AdaptadorMensaje.ViewHolder>
    implements View.OnClickListener{

    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;

    ArrayList<FBMensaje> mensajes;
    Context context;
    Integer itemLayout;
    private String imageurl;
    private Usuario usuario;
    private int participante;
    private View.OnClickListener listener;

    public AdaptadorMensaje(ArrayList<FBMensaje> mensajes, Context context, int p) {
        this.mensajes = mensajes;
        this.context = context;
        this.participante = p;

        SharedPreferences preferences = context.getSharedPreferences("Usuario", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        usuario = gson.fromJson(preferences.getString("Usuario", "nani"), Usuario.class);
    }


    @NonNull
    @Override
    public AdaptadorMensaje.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType == MSG_TYPE_RIGHT) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_right, parent, false);
            AdaptadorMensaje.ViewHolder vh = new AdaptadorMensaje.ViewHolder(v);
            v.setOnClickListener(this);
            return vh;
        }
        else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_left, parent, false);
            AdaptadorMensaje.ViewHolder vh = new AdaptadorMensaje.ViewHolder(v);
            v.setOnClickListener(this);
            return vh;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorMensaje.ViewHolder holder, int position) {
        FBMensaje mensaje = mensajes.get(position);
        holder.show_message.setText(mensaje.getMensaje());
        Picasso.with(context).load("http://unioncanina.mipantano.com/api/loadDestinatarioFoto/" +
                participante).into(holder.profile_image);

    }

    @Override
    public int getItemCount() {
        return mensajes.size();
    }

    @Override
    public void onClick(View view) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView show_message;
        public ImageView profile_image;

        public ViewHolder(View itemView) {
            super(itemView);

            show_message = itemView.findViewById(R.id.show_message);
            profile_image = itemView.findViewById(R.id.profile_image);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(mensajes.get(position).getRemitente() == usuario.getId()){
            return MSG_TYPE_RIGHT;
        }else{
            return MSG_TYPE_LEFT;
        }
    }
}
