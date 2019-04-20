package com.favio.unioncanina.modelos;

public class Extravio {

    private Integer id;
    private String colonia;
    private String f_extrav;
    private String info_extra;
    private String estado;
    private String f_encontrado;

    public Extravio(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getF_extrav() {
        return f_extrav;
    }

    public void setF_extrav(String f_extrav) {
        this.f_extrav = f_extrav;
    }

    public String getInfo_extra() {
        return info_extra;
    }

    public void setInfo_extra(String info_extra) {
        this.info_extra = info_extra;
    }

    public String getF_encontrado() {
        return f_encontrado;
    }

    public void setF_encontrado(String f_encontrado) {
        this.f_encontrado = f_encontrado;
    }

    public String getEstado() {

        if(estado!=null){
            return estado;
        }
        else{
            return null;
        }

    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
