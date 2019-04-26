package com.favio.unioncanina.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.favio.unioncanina.DetallesMascotaExtraviadaActivity;
import com.favio.unioncanina.R;
import com.favio.unioncanina.extras.CircleTransform;
import com.favio.unioncanina.modelos.Extravio;
import com.favio.unioncanina.modelos.Mascota;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AdaptadorMascota extends RecyclerView.Adapter<AdaptadorMascota.ViewHolder>
        implements View.OnClickListener{

    List<Mascota> listaMascotas;
    Context context;
    Integer itemLayout;
    Drawable iv_extraviadoMiMascota, iv_casaMiMascota, roundedYellow, roundedGreen;

    private View.OnClickListener listener;

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

        v.setOnClickListener(this);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

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
                holder.tv_lugarExtravioMascota.setText("en " + listaMascotas.get(position).getExtravio()
                        .get(listaMascotas.get(position).getExtravio().size()-1).getColonia());

                Calendar fExtrav=extraerFechaExtravioAtras(listaMascotas.get(position).getExtravio()
                        .get(listaMascotas.get(position).getExtravio().size()-1).getF_extrav());
                String tiempoAtras=calcularExtravioTiempoAtras(fExtrav);
                if (tiempoAtras.equals("hoy"))
                {
                    holder.tv_fechaExtravioMascota.setText("Se extravió " + tiempoAtras);
                }else{
                    holder.tv_fechaExtravioMascota.setText("Se extravió hace " + tiempoAtras);
                }
                holder.tv_codigoMascota.setText("ID " + listaMascotas.get(position).getCodigo().getCodigo());
                holder.tv_masInfoMascota.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        /*Intent itt_DetallesMascotaExtraviadaActivity=new Intent(context, DetallesMascotaExtraviadaActivity.class);
                        itt_DetallesMascotaExtraviadaActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        context.startActivity(itt_DetallesMascotaExtraviadaActivity);*/
                        //Toast.makeText(context, "Item: " + position, Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.layout.item_mi_mascota:

                Picasso.with(context).load("http://unioncanina.mipantano.com/api/petspp/" +
                        listaMascotas.get(position).getFoto()).fit().centerCrop().into(holder.iv_fotoMiMascota);
                holder.tv_nombreMiMascota.setText(listaMascotas.get(position).getNombre());
                holder.tv_razaMiMascota.setText(listaMascotas.get(position).getRaza().getNombre());
                holder.tv_codigoMiMascota.setText("ID " + listaMascotas.get(position).getCodigo().getCodigo());
                if(listaMascotas.get(position).getEstatus().equals("extraviado")){
                    holder.ll_estatusMiMascota.setBackground(roundedYellow);
                    holder.iv_imagenEstatusMiMascota.setBackground(iv_extraviadoMiMascota);
                    holder.tv_estatusMiMascota.setText(listaMascotas.get(position).getEstatus());
                }else{
                    holder.ll_estatusMiMascota.setBackground(roundedGreen);
                    holder.iv_imagenEstatusMiMascota.setBackground(iv_casaMiMascota);
                    holder.tv_estatusMiMascota.setText(listaMascotas.get(position).getEstatus());
                    holder.tv_estatusMiMascota.setTextColor(Color.WHITE);
                }
                break;
        }
    }

    @Override
    public int getItemCount() {
        return listaMascotas.size();
    }

    public void setOnclickListener(View.OnClickListener listener){
        this.listener=listener;
    }

    @Override
    public void onClick(View view) {
        if(listener!=null){
            listener.onClick(view);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_fotoDueno, iv_fotoMascota, iv_fotoMiMascota, iv_imagenEstatusMiMascota;
        TextView tv_nombreDueno, tv_estadoDueno, tv_coloniaDueno, tv_nombreMascota, tv_lugarExtravioMascota, tv_fechaExtravioMascota,
                tv_nombreMiMascota, tv_razaMiMascota, tv_masInfoMascota, tv_codigoMiMascota, tv_estatusMiMascota, tv_codigoMascota;
        LinearLayout ll_estatusMiMascota;

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
            tv_masInfoMascota=itemView.findViewById(R.id.tv_masInfoMascota);
            tv_codigoMascota=itemView.findViewById(R.id.tv_codigoMascota);

            iv_fotoMiMascota=itemView.findViewById(R.id.iv_fotoMiMascota);
            tv_nombreMiMascota=itemView.findViewById(R.id.tv_nombreMiMascota);
            tv_razaMiMascota=itemView.findViewById(R.id.tv_razaMiMascota);
            tv_codigoMiMascota=itemView.findViewById(R.id.tv_codigoMiMascota);
            ll_estatusMiMascota=itemView.findViewById(R.id.ll_estatusMiMascota);
            iv_imagenEstatusMiMascota=itemView.findViewById(R.id.iv_imagenEstatusMiMascota);
            tv_estatusMiMascota=itemView.findViewById(R.id.tv_estatusMiMascota);
            iv_extraviadoMiMascota=itemView.getResources().getDrawable(R.drawable.ic_warning_black_16dp);
            iv_casaMiMascota=itemView.getResources().getDrawable(R.drawable.ic_home_white_16dp);
            roundedYellow=itemView.getResources().getDrawable(R.drawable.rounded_yellow);
            roundedGreen=itemView.getResources().getDrawable(R.drawable.rounded_green);
        }
    }

    private Calendar extraerFechaExtravioAtras(String fExtrav){

        Integer year=Integer.valueOf(fExtrav.substring(0,4));
        Integer month=Integer.valueOf(fExtrav.substring(5,7));
        Integer day=Integer.valueOf(fExtrav.substring(8,10));

        Calendar fecha=Calendar.getInstance();
        fecha.set(year, month, day);

        return fecha;
    }

    private String calcularExtravioTiempoAtras(Calendar fExtrav) {

        Calendar today = Calendar.getInstance();

        String diff_tot="";
        int diff_year = today.get(Calendar.YEAR) -  fExtrav.get(Calendar.YEAR);
        int diff_month = (today.get(Calendar.MONTH) + 1) - fExtrav.get(Calendar.MONTH);
        int diff_day = today.get(Calendar.DAY_OF_MONTH) - fExtrav.get(Calendar.DAY_OF_MONTH);

        if (diff_year>1){
            diff_tot=diff_year + " años";
        }
        if (diff_year==1){
            diff_tot=diff_year + " año";
        }
        if (diff_year==0 && diff_month>0){
            diff_tot=diff_month + " meses";
        }
        if (diff_year==0 && diff_month==0){
            diff_tot=diff_day + " días";
        }
        if (diff_year==0 && diff_month==0 && diff_day==0){
            diff_tot="hoy";
        }
        return diff_tot;
    }

}