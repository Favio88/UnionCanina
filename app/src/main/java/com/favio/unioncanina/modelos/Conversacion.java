package com.favio.unioncanina.modelos;

public class Conversacion {

    private int id;
    private String fecha_inicio;
    private String fecha_actividad;


    public Conversacion(int id, String fecha_inicio, String fecha_actividad) {
        this.id = id;
        this.fecha_inicio = fecha_inicio;
        this.fecha_actividad = fecha_actividad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(String fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public String getFecha_actividad() {
        return fecha_actividad;
    }

    public void setFecha_actividad(String fecha_actividad) {
        this.fecha_actividad = fecha_actividad;
    }
}
