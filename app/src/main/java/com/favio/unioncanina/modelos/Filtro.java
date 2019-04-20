package com.favio.unioncanina.modelos;

import java.util.List;

public class Filtro {

    private List<Ciudad> Ciudades;
    private List<Estado> Estados;
    private List<Raza> Razas;

    public Filtro(){

    }

    public Filtro(List<Ciudad> ciudades, List<Estado> estados, List<Raza> razas) {
        this.Ciudades = ciudades;
        this.Estados = estados;
        this.Razas = razas;
    }

    public List<Ciudad> getCiudades() {
        return Ciudades;
    }

    public void setCiudades(List<Ciudad> ciudades) {
        this.Ciudades = ciudades;
    }

    public List<Estado> getEstados() {
        return Estados;
    }

    public void setEstados(List<Estado> estados) {
        this.Estados = estados;
    }

    public List<Raza> getRazas() {
        return Razas;
    }

    public void setRazas(List<Raza> razas) {
        this.Razas = razas;
    }
}
