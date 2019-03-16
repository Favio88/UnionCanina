package com.favio.unioncanina.modelos;

import android.media.Image;

public class Perro {

    private Integer Id;
    //private Image Foto;
    private String Nombre;
    private String Dueno;
    private String Raza;
    private String Genero;
    private Integer Edad;
    private String Estado;
    private String Colonia;
    private String InfoExtra;
    private String Rasgos;
    private String Status;
    private String EstadoExtravio;
    private String ColoniaExtravio;
    private String FechaExtravio;

    public Perro(Integer id, Image foto, String nombre, String raza, String genero, Integer edad, String estado, String colonia, String infoExtra, String rasgos, String status, String estadoExtravio, String coloniaExtravio, String fechaExtravio) {
        Id = id;
        //Foto = foto;
        Nombre = nombre;
        Raza = raza;
        Genero = genero;
        Edad = edad;
        Estado = estado;
        Colonia = colonia;
        InfoExtra = infoExtra;
        Rasgos = rasgos;
        Status = status;
        EstadoExtravio = estadoExtravio;
        ColoniaExtravio = coloniaExtravio;
        FechaExtravio = fechaExtravio;
    }

    public Perro(){


    }

    public String getDueno() {
        return Dueno;
    }

    public void setDueno(String dueno) {
        Dueno = dueno;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getEstadoExtravio() {
        return EstadoExtravio;
    }

    public void setEstadoExtravio(String estadoExtravio) {
        EstadoExtravio = estadoExtravio;
    }

    public String getColoniaExtravio() {
        return ColoniaExtravio;
    }

    public void setColoniaExtravio(String coloniaExtravio) {
        ColoniaExtravio = coloniaExtravio;
    }

    public String getFechaExtravio() {
        return FechaExtravio;
    }

    public void setFechaExtravio(String fechaExtravio) {
        FechaExtravio = fechaExtravio;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getRaza() {
        return Raza;
    }

    public void setRaza(String raza) {
        Raza = raza;
    }

    public String getGenero() {
        return Genero;
    }

    public void setGenero(String genero) {
        Genero = genero;
    }

    public Integer getEdad() {
        return Edad;
    }

    public void setEdad(Integer edad) {
        Edad = edad;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }

    public String getColonia() {
        return Colonia;
    }

    public void setColonia(String colonia) {
        Colonia = colonia;
    }

    public String getInfoExtra() {
        return InfoExtra;
    }

    public void setInfoExtra(String infoExtra) {
        InfoExtra = infoExtra;
    }

    public String getRasgos() {
        return Rasgos;
    }

    public void setRasgos(String rasgos) {
        Rasgos = rasgos;
    }

}
