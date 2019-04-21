package com.favio.unioncanina;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.favio.unioncanina.adaptadores.AdaptadorMascota;
import com.favio.unioncanina.extras.CircleTransform;
import com.favio.unioncanina.modelos.Mascota;
import com.favio.unioncanina.modelos.Usuario;
import com.favio.unioncanina.singleton.VolleyS;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link InicioFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link InicioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InicioFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    List<Mascota> listaMascotasfiltro;
    AdaptadorMascota adaptadorMascota;
    RecyclerView rv_mascotasExtraviadas;
    ImageView ic_fotoPerfil, ic_buscarMascota, ic_filtrarMascota;

    public InicioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InicioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InicioFragment newInstance(String param1, String param2) {
        InicioFragment fragment = new InicioFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

            Bundle bundle=getActivity().getIntent().getExtras();
            if(bundle!=null){
                cargarFiltros(bundle);
            }



            JsonArrayRequest peticion01=new JsonArrayRequest(
                    Request.Method.GET,
                    "http://unioncanina.mipantano.com/api/extravios",
                    null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {

                            Gson gson=new Gson();

                            Type listType=new TypeToken<List<Mascota>>(){}.getType();

                            Log.d("valor",response.toString());
                            final List<Mascota> listaMascotasExtraviadas=gson.fromJson(response.toString(), listType);

                            adaptadorMascota=new AdaptadorMascota(listaMascotasExtraviadas, getActivity().getApplicationContext(), R.layout.item_mascota_extraviada);
                            adaptadorMascota.setOnclickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent itt_detallesMascotaExtraviadaActivity=new Intent(getActivity().getApplicationContext(),DetallesMascotaExtraviadaActivity.class);

                                    Gson gson=new Gson();
                                    String jsonMascota=gson.toJson(listaMascotasExtraviadas.get(rv_mascotasExtraviadas.getChildAdapterPosition(view)));

                                    Bundle bundle=new Bundle();
                                    bundle.putString("Mascota", jsonMascota);
                                    itt_detallesMascotaExtraviadaActivity.putExtras(bundle);

                                    startActivity(itt_detallesMascotaExtraviadaActivity);
                                }
                            });

                            rv_mascotasExtraviadas.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                            rv_mascotasExtraviadas.setAdapter(adaptadorMascota);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toast.makeText(getActivity().getApplicationContext(), "Error en la petición", Toast.LENGTH_SHORT).show();
                        }
                    }
            );

            VolleyS.getInstance(getActivity().getApplicationContext()).getRequestQueue().add(peticion01);

        }
    }

    private void cargarFiltros(Bundle bundle) {
        String filtroExtravios=bundle.getString("extravios");
        Log.e("Resultado bundle",filtroExtravios);
        Gson gson=new Gson();
        Type listType=new TypeToken<List<Mascota>>(){}.getType();

        listaMascotasfiltro= gson.fromJson(filtroExtravios,listType);
        adaptadorMascota=new AdaptadorMascota(listaMascotasfiltro, getActivity().getApplicationContext(), R.layout.item_mascota_extraviada);
        adaptadorMascota.setOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent itt_detallesMascotaExtraviadaActivity=new Intent(getActivity().getApplicationContext(), DetallesMascotaExtraviadaActivity.class);

                Gson gson=new Gson();
                String jsonMascota=gson.toJson(listaMascotasfiltro.get(rv_mascotasExtraviadas.getChildAdapterPosition(view)));

                Bundle bundle=new Bundle();
                bundle.putString("Mascota", jsonMascota);
                itt_detallesMascotaExtraviadaActivity.putExtras(bundle);

                startActivity(itt_detallesMascotaExtraviadaActivity);
            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_inicio, container, false);
        rv_mascotasExtraviadas=view.findViewById(R.id.rv_mascotasExtraviadas);

        rv_mascotasExtraviadas.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        rv_mascotasExtraviadas.setAdapter(adaptadorMascota);

        ic_fotoPerfil=view.findViewById(R.id.ic_fotoPerfil);
        ic_fotoPerfil.setOnClickListener(this);
        ic_buscarMascota=view.findViewById(R.id.ic_buscarMascota);
        ic_buscarMascota.setOnClickListener(this);
        ic_filtrarMascota=view.findViewById(R.id.ic_filtrarMascota);
        ic_filtrarMascota.setOnClickListener(this);

        SharedPreferences preferences=getActivity().getSharedPreferences("Usuario", Context.MODE_PRIVATE);
        Gson gson=new Gson();
        Usuario usuario=gson.fromJson(preferences.getString("Usuario","No existe dato"),Usuario.class);

        String url="http://unioncanina.mipantano.com/api/profilePicture/";

        Picasso.with(getActivity().getApplicationContext()).load(url + usuario.getFoto()).transform(new CircleTransform())
                .fit().centerCrop().into(ic_fotoPerfil);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.ic_fotoPerfil:
                Intent itt_perfilActivity=new Intent(getActivity().getApplicationContext(), PerfilActivity.class);
                startActivity(itt_perfilActivity);
                break;
            case R.id.ic_buscarMascota:
                Intent itt_buscarMascotaIdActivity=new Intent(getActivity().getApplicationContext(), BuscarMascotaIdActivity.class);
                startActivity(itt_buscarMascotaIdActivity);
                break;
            case R.id.ic_filtrarMascota:
                Intent itt_filtrarMascotasActivity=new Intent(getActivity().getApplicationContext(), FiltrarMascotasActivity.class);
                startActivity(itt_filtrarMascotasActivity);
                break;
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
