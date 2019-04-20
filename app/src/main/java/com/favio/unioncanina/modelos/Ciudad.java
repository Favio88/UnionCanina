package com.favio.unioncanina.modelos;

public class Ciudad {

    private Integer id;
    private String nombre;
    //private Integer id_estado;
    private Estado estado;

    public Ciudad(){

    }

    public Ciudad(Integer id, String nombre){
        this.id=id;
        this.nombre=nombre;
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

    public String toString() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
}
