package com.favio.unioncanina.modelos;

import java.io.Serializable;
import java.util.ArrayList;

public class Usuario implements Serializable {

    private int id;
    private String nombre;
    private String apat;
    private String amat;
    private String correo;
    private String pwd;
    private String habilitado;
    private String admin;
    private String foto;
    private String fecha_registro;
    private ArrayList<Mascota> mascotas;
    private ArrayList<Conversacion> conversaciones;


    public Usuario(int id, String nombre, String apellido_Paterno, String apellido_Materno,
                   String correo, String password, String habilitado, String admin, String foto,
                   String fecha_Registro, ArrayList<Mascota> mascotas, ArrayList<Conversacion> conversaciones) {

        this.id = id;
        this.nombre = nombre;
        this.apat = apellido_Paterno;
        this.amat = apellido_Materno;
        this.correo = correo;
        this.pwd = password;
        this.habilitado = habilitado;
        this.admin = admin;
        this.foto = foto;
        this.fecha_registro = fecha_Registro;
        this.mascotas = mascotas;
        this.conversaciones = conversaciones;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApat() {
        return apat;
    }

    public void setApat(String apat) {
        this.apat = apat;
    }

    public String getAmat() {
        return amat;
    }

    public void setAmat(String amat) {
        this.amat = amat;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(String habilitado) {
        this.habilitado = habilitado;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(String fecha_registro) {
        this.fecha_registro = fecha_registro;
    }


    public ArrayList<Mascota> getMascotas() {
        return mascotas;
    }

    public void setMascotas(ArrayList<Mascota> mascotas) {
        this.mascotas = mascotas;
    }

    public ArrayList<Conversacion> getConversaciones() {
        return conversaciones;
    }

    public void setConversaciones(ArrayList<Conversacion> conversaciones) {
        this.conversaciones = conversaciones;
    }
}
