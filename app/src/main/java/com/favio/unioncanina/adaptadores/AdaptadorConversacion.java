package com.favio.unioncanina.adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.favio.unioncanina.R;
import com.favio.unioncanina.extras.CircleTransform;
import com.favio.unioncanina.modelos.Conversacion;
import com.favio.unioncanina.modelos.Mensaje;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdaptadorConversacion extends RecyclerView.Adapter<AdaptadorConversacion.ViewHolder>
        implements View.OnClickListener{

    ArrayList<Conversacion> conversaciones;
    Context context;
    Integer itemLayout;

    public AdaptadorConversacion(ArrayList<Conversacion> conversaciones, Context context, Integer itemLayout) {
        this.conversaciones = conversaciones;
        this.context = context;
        this.itemLayout = itemLayout;
    }

    @NonNull
    @Override
    public AdaptadorConversacion.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);

        AdaptadorConversacion.ViewHolder vh = new AdaptadorConversacion.ViewHolder(v);

        v.setOnClickListener(this);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorConversacion.ViewHolder holder, int position) {
        Picasso.with(context).load("http://unioncanina.mipantano.com/api/profilePicture/220px-Walter_White2.jpg")
                .transform(new CircleTransform()).fit()
                .centerCrop().into(holder.iv_fotoPersonaMensaje);
       /* holder.tv_ultimoMensaje.setText(conversaciones.get(position).getMensaje().getMensaje());
        holder.tv_horaMensaje.setText(conversaciones.get(position).getMensaje().getHora());*/
    }

    @Override
    public int getItemCount() {
        return conversaciones.size();
    }

    @Override
    public void onClick(View view) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_fotoPersonaMensaje;
        TextView tv_ultimoMensaje, tv_horaMensaje;

        public ViewHolder(View itemView) {
            super(itemView);

            iv_fotoPersonaMensaje=itemView.findViewById(R.id.iv_fotoPersonaMensaje);
            tv_ultimoMensaje=itemView.findViewById(R.id.tv_ultimoMensaje);
            tv_horaMensaje=itemView.findViewById(R.id.tv_horaMensaje);
        }
    }
}
