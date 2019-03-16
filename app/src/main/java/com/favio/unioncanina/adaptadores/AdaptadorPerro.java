package com.favio.unioncanina.adaptadores;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.favio.unioncanina.R;
import com.favio.unioncanina.modelos.Perro;

import java.util.List;

public class AdaptadorPerro extends RecyclerView.Adapter<AdaptadorPerro.ViewHolder> {

    List<Perro> listaPerrosExtraviados;

    public AdaptadorPerro(List<Perro> listaPerrosExtraviados){
        this.listaPerrosExtraviados=listaPerrosExtraviados;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_perro_extraviado,parent,false);

        ViewHolder vh=new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        //holder.iv_fotoPerro.setImageDrawable(listaPerrosExtraviados.get(position).getFoto());
        holder.tv_nombreDueno.setText(listaPerrosExtraviados.get(position).getDueno());
        holder.tv_estadoDueno.setText(listaPerrosExtraviados.get(position).getEstado());
        holder.tv_coloniaDueno.setText(listaPerrosExtraviados.get(position).getColonia());
        holder.tv_nombrePerro.setText(listaPerrosExtraviados.get(position).getNombre());
        holder.tv_lugarExtravioPerro.setText(listaPerrosExtraviados.get(position).getColoniaExtravio());
        holder.tv_fechaExtravioPerro.setText(listaPerrosExtraviados.get(position).getFechaExtravio());
    }

    @Override
    public int getItemCount() {
        return listaPerrosExtraviados.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_fotoDueno, iv_fotoPerro;
        TextView tv_nombreDueno, tv_estadoDueno, tv_coloniaDueno, tv_nombrePerro, tv_lugarExtravioPerro, tv_fechaExtravioPerro;

        public ViewHolder(View itemView) {
            super(itemView);

            iv_fotoDueno=itemView.findViewById(R.id.iv_fotoDueno);
            tv_nombreDueno=itemView.findViewById(R.id.tv_nombreDueno);
            tv_estadoDueno=itemView.findViewById(R.id.tv_estadoDueno);
            tv_coloniaDueno=itemView.findViewById(R.id.tv_coloniaDueno);
            iv_fotoPerro=itemView.findViewById(R.id.iv_fotoPerro);
            tv_nombrePerro=itemView.findViewById(R.id.tv_nombrePerro);
            tv_lugarExtravioPerro=itemView.findViewById(R.id.tv_lugarExtravioPerro);
            tv_fechaExtravioPerro=itemView.findViewById(R.id.tv_fechaExtravioPerro);
        }
    }
}
