package com.favio.unioncanina.modelos;

public class Mensaje {

    private Integer id;
    private String mensaje;
    private String hora;

    public Mensaje() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getHora(){
        return hora;
    }

    public void setHora(String hora){
        this.hora=hora;
    }
}
