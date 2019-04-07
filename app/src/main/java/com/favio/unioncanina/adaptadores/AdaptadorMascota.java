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
import com.favio.unioncanina.modelos.Mascota;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdaptadorMascota extends RecyclerView.Adapter<AdaptadorMascota.ViewHolder> {

    List<Mascota> listaMascotas;
    Context context;
    Integer itemLayout;

    public AdaptadorMascota(List<Mascota> listaMascotas, Context context, int itemLayout) {
        this.listaMascotas = listaMascotas;
        this.context=context;
        this.itemLayout=itemLayout;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);

        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        switch(itemLayout){

            case R.layout.item_mascota_extraviada:
                Picasso.with(context).load("http://unioncanina.mipantano.com/api/profilePicture/" +
                        listaMascotas.get(position).getUsuario().getFoto()).transform(new CircleTransform()).fit()
                        .centerCrop().into(holder.iv_fotoDueno);
                holder.tv_nombreDueno.setText(listaMascotas.get(position).getUsuario().getNombre());
                holder.tv_estadoDueno.setText(listaMascotas.get(position).getCiudad().getEstado().getNombre() + ",");
                holder.tv_coloniaDueno.setText(listaMascotas.get(position).getCiudad().getNombre());
                Picasso.with(context).load("http://unioncanina.mipantano.com/api/petspp/" +
                        listaMascotas.get(position).getFoto()).fit().centerCrop().into(holder.iv_fotoMascota);
                holder.tv_nombreMascota.setText(listaMascotas.get(position).getNombre());
                //holder.tv_lugarExtravioMascota.setText(listaMascotas.get(position).getExtravio().getColonia());
                //holder.tv_fechaExtravioMascota.setText(listaMascotas.get(position).getExtravio().getF_extrav());
                break;
            case R.layout.item_mi_mascota:
                Picasso.with(context).load("http://unioncanina.mipantano.com/api/misMascotas/1" +
                        listaMascotas.get(position).getFoto()).into(holder.iv_fotoMiMascota);
                holder.tv_nombreMiMascota.setText(listaMascotas.get(position).getNombre());
                //holder.tv_razaMiMascota.setText(listaMascotas.get(position).getRaza().getNombre());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return listaMascotas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_fotoDueno, iv_fotoMascota,
                iv_fotoMiMascota;
        TextView tv_nombreDueno, tv_estadoDueno, tv_coloniaDueno, tv_nombreMascota, tv_lugarExtravioMascota, tv_fechaExtravioMascota,
                tv_nombreMiMascota, tv_razaMiMascota;

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

            iv_fotoMiMascota=itemView.findViewById(R.id.iv_fotoMiMascota);
            tv_nombreMiMascota=itemView.findViewById(R.id.tv_nombreMiMascota);
            tv_razaMiMascota=itemView.findViewById(R.id.tv_razaMiMascota);

        }
    }
}