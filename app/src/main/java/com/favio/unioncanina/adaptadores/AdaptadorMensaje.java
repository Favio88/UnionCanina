package com.favio.unioncanina.adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.favio.unioncanina.modelos.Mensaje;

import java.util.ArrayList;

public class AdaptadorMensaje extends RecyclerView.Adapter<AdaptadorMensaje.ViewHolder>
    implements View.OnClickListener{

    ArrayList<Mensaje> mensajes;
    Context context;
    Integer itemLayout;

    private View.OnClickListener listener;

    public AdaptadorMensaje(ArrayList<Mensaje> mensajes, Context context, Integer itemLayout) {
        this.mensajes = mensajes;
        this.context = context;
        this.itemLayout = itemLayout;
    }


    @NonNull
    @Override
    public AdaptadorMensaje.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);

        AdaptadorMensaje.ViewHolder vh = new AdaptadorMensaje.ViewHolder(v);

        v.setOnClickListener(this);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorMensaje.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public void onClick(View view) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
