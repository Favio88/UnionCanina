package com.favio.unioncanina.modelos;

public class Conversacion {

    private int Id;
    private String Fecha_Inicio;
    private String Fecha_Actividad;


    public Conversacion(int id, String fecha_Inicio, String fecha_Actividad) {
        Id = id;
        Fecha_Inicio = fecha_Inicio;
        Fecha_Actividad = fecha_Actividad;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getFecha_Inicio() {
        return Fecha_Inicio;
    }

    public void setFecha_Inicio(String fecha_Inicio) {
        Fecha_Inicio = fecha_Inicio;
    }

    public String getFecha_Actividad() {
        return Fecha_Actividad;
    }

    public void setFecha_Actividad(String fecha_Actividad) {
        Fecha_Actividad = fecha_Actividad;
    }
}
