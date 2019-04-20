package com.favio.unioncanina.modelos;

public class Raza {

    private Integer id;
    private String nombre;

    public Raza(){

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

    public String toString(){return nombre;}

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
