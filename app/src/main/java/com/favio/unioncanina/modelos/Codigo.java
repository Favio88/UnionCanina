package com.favio.unioncanina.modelos;

public class Codigo {

    private Integer id;
    private String codigo;

    public Codigo(){

    }

    public Codigo(Integer id, String codigo) {
        this.id = id;
        this.codigo = codigo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
