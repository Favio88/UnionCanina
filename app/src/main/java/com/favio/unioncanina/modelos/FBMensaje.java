package com.favio.unioncanina.modelos;

public class FBMensaje {

    private int conversacion;
    private int destinatario;
    private int leido;
    private String mensaje;
    private int remitente;

    public FBMensaje(){}

    public FBMensaje(int conversacion, int destinatario, int leido, String mensaje, int remitente) {
        this.conversacion = conversacion;
        this.destinatario = destinatario;
        this.leido = leido;
        this.mensaje = mensaje;
        this.remitente = remitente;
    }


    public int getConversacion() {
        return conversacion;
    }

    public void setConversacion(int conversacion) {
        this.conversacion = conversacion;
    }

    public int getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(int destinatario) {
        this.destinatario = destinatario;
    }

    public int getLeido() {
        return leido;
    }

    public void setLeido(int leido) {
        this.leido = leido;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public int getRemitente() {
        return remitente;
    }

    public void setRemitente(int remitente) {
        this.remitente = remitente;
    }
}
