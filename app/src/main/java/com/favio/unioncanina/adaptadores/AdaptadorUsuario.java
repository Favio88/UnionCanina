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
import com.favio.unioncanina.modelos.Usuario;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdaptadorUsuario extends RecyclerView.Adapter<AdaptadorUsuario.ViewHoler> {

    List<Usuario> listaUsuarios;
    Context context;
    Integer item_layout;

    public AdaptadorUsuario(List<Usuario> listaUsuarios, Context context, Integer item_layout) {
        this.listaUsuarios = listaUsuarios;
        this.context = context;
        this.item_layout = item_layout;
    }

    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view=LayoutInflater.from(parent.getContext()).inflate(item_layout, parent,false);

        ViewHoler vh=new ViewHoler(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler holder, int position) {

        Picasso.with(context).load("http://unioncanina.mipantano.com/api/profilePicture/" +
                listaUsuarios.get(position).getFoto()).transform(new CircleTransform()).fit()
                .centerCrop().into(holder.iv_fotoPersonaMensaje);
        holder.tv_ultimoMensaje.setText(listaUsuarios.get(position).getMensaje().getMensaje());
        holder.tv_horaMensaje.setText(listaUsuarios.get(position).getMensaje().getHora());
    }

    @Override
    public int getItemCount() {
        return listaUsuarios.size();
    }

    public class ViewHoler extends RecyclerView.ViewHolder {

        ImageView iv_fotoPersonaMensaje;
        TextView tv_ultimoMensaje, tv_horaMensaje;

        public ViewHoler(View itemView) {
            super(itemView);

            iv_fotoPersonaMensaje=itemView.findViewById(R.id.iv_fotoPersonaMensaje);
            tv_ultimoMensaje=itemView.findViewById(R.id.tv_ultimoMensaje);
            tv_horaMensaje=itemView.findViewById(R.id.tv_horaMensaje);
        }
    }
}
