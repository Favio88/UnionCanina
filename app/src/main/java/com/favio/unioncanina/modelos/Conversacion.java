package com.favio.unioncanina.modelos;

import java.io.Serializable;

public class Conversacion implements Serializable {

    private int id;
    private String fecha_inicio;
    private String fecha_actividad;
    private Usuario participante;
    private Mensaje ultimo_mensaje;


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

    public Usuario getParticipante() {
        return participante;
    }

    public void setParticipante(Usuario participante) {
        this.participante = participante;
    }

    public Mensaje getUltimo_mensaje() {
        return ultimo_mensaje;
    }

    public void setUltimo_mensaje(Mensaje ultimo_mensaje) {
        this.ultimo_mensaje = ultimo_mensaje;
    }
}
