package com.favio.unioncanina.adaptadores;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.favio.unioncanina.R;
import com.favio.unioncanina.modelos.Mascota;

import java.util.List;

public class AdaptadorMascota extends RecyclerView.Adapter<AdaptadorMascota.ViewHolder> {

    List<Mascota> listaMascotasExtraviadas;

    public AdaptadorMascota(List<Mascota> listaMascotasExtraviadas){
        this.listaMascotasExtraviadas=listaMascotasExtraviadas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mascota_extraviada,parent,false);

        ViewHolder vh=new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        //holder.iv_fotoPerro.setImageDrawable(listaPerrosExtraviados.get(position).getFoto());
        holder.tv_nombreDueno.setText(listaMascotasExtraviadas.get(position).getDueno());
        holder.tv_estadoDueno.setText(listaMascotasExtraviadas.get(position).getEstado());
        holder.tv_coloniaDueno.setText(listaMascotasExtraviadas.get(position).getColonia());
        holder.tv_nombreMascota.setText(listaMascotasExtraviadas.get(position).getNombre());
        holder.tv_lugarExtravioMascota.setText(listaMascotasExtraviadas.get(position).getColoniaExtravio());
        holder.tv_fechaExtravioMascota.setText(listaMascotasExtraviadas.get(position).getFechaExtravio());
    }

    @Override
    public int getItemCount() {
        return listaMascotasExtraviadas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_fotoDueno, iv_fotoMascota;
        TextView tv_nombreDueno, tv_estadoDueno, tv_coloniaDueno, tv_nombreMascota, tv_lugarExtravioMascota, tv_fechaExtravioMascota;

        public ViewHolder(View itemView) {
            super(itemView);

            iv_fotoDueno=itemView.findViewById(R.id.iv_fotoDueno);
            tv_nombreDueno=itemView.findViewById(R.id.tv_nombreDueno);
            tv_estadoDueno=itemView.findViewById(R.id.tv_estadoDueno);
            tv_coloniaDueno=itemView.findViewById(R.id.tv_coloniaDueno);
            iv_fotoMascota=itemView.findViewById(R.id.iv_fotoMascota);
            tv_nombreMascota=itemView.findViewById(R.id.tv_nombreMascota);
            tv_lugarExtravioMascota=itemView.findViewById(R.id.tv_lugarExtravioMascota);
            tv_fechaExtravioMascota=itemView.findViewById(R.id.tv_fechaExtravioMascota);
        }
    }
}
