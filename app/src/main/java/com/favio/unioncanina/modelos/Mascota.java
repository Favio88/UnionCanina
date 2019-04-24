package com.favio.unioncanina.modelos;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;

import com.favio.unioncanina.modelos.Ciudad;
import com.favio.unioncanina.modelos.Codigo;
import com.favio.unioncanina.modelos.Extravio;
import com.favio.unioncanina.modelos.Fotografia;
import com.favio.unioncanina.modelos.Raza;
import com.favio.unioncanina.modelos.Usuario;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Mascota {

    private Integer id;
    private String nombre;
    private String sexo;
    private String color;
    private String f_nac;
    private String estatus;
    private String esterilizado;
    private String enfermedad;
    private String foto;
    private String rasgos;
    private List<Extravio> extravios;
    private Usuario usuario;
    private List<Fotografia> fotografias;
    private Ciudad ciudad;
    private Raza raza;
    private Codigo codigo;
    private String habilitada;

    public Mascota(){

    }

    public Mascota(Integer id, String nombre, String sexo, String color, String f_nac, String estatus, String esterilizado, String enfermedad, String foto, String rasgos, List<Extravio> extravios, Usuario usuario, List<Fotografia> fotografias, Ciudad ciudad, Raza raza, Codigo codigo, String habilitada) {
        this.id = id;
        this.nombre = nombre;
        this.sexo = sexo;
        this.color = color;
        this.f_nac = f_nac;
        this.estatus = estatus;
        this.esterilizado = esterilizado;
        this.enfermedad = enfermedad;
        this.foto = foto;
        this.rasgos = rasgos;
        this.extravios = extravios;
        this.usuario = usuario;
        this.fotografias = fotografias;
        this.ciudad = ciudad;
        this.raza = raza;
        this.codigo = codigo;
        this.habilitada=habilitada;
    }

    public List<Fotografia> getFotografias() {
        return fotografias;
    }

    public void setFotografias(ArrayList<Fotografia> fotografias) {
        this.fotografias = fotografias;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getF_nac() {
        return f_nac;
    }

    public void setF_nac(String f_nac) {
        this.f_nac = f_nac;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getEsterilizado() {
        return esterilizado;
    }

    public void setEsterilizado(String esterilizado) {
        this.esterilizado = esterilizado;
    }

    public String getEnfermedad() {
        return enfermedad;
    }

    public void setEnfermedad(String enfermedad) {
        this.enfermedad = enfermedad;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getRasgos() {
        return rasgos;
    }

    public void setRasgos(String rasgos) {
        this.rasgos = rasgos;
    }

    public List<Extravio> getExtravio() {
        return extravios;
    }

    public void setExtravio(List<Extravio> extravio) {
        this.extravios = extravio;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

    public Raza getRaza() {
        return raza;
    }

    public void setRaza(Raza raza) {
        this.raza = raza;
    }

    public Codigo getCodigo() {
        return codigo;
    }

    public void setCodigo(Codigo codigo) {
        this.codigo = codigo;
    }

    public String getHabilitada() {
        return habilitada;
    }

    public void setHabiltada(String habiltada) {
        this.habilitada = habiltada;
    }

    public List<Extravio> getExtravios() {
        return extravios;
    }

    public void setExtravios(List<Extravio> extravios) {
        this.extravios = extravios;
    }

    public void setFotografias(List<Fotografia> fotografias) {
        this.fotografias = fotografias;
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }

}