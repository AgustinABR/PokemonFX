package com.combate.model;

public class Criatura {
    public String nombre; 
    public int vidaMax;
    public int vidaActual;
    public int ataque;    
    public int defensa; 
    public String imagen;        
    public String imagenCombate; 

    public void recibirDanio(int cantidad) {
        this.vidaActual -= cantidad;
        if (this.vidaActual < 0) this.vidaActual = 0;
    }

    public void curar(int cantidad) {
        this.vidaActual += cantidad;
        if (this.vidaActual > vidaMax) this.vidaActual = vidaMax;
    }

    public String getNombre() { return nombre; }
    public int getVidaActual() { return vidaActual; }
    public int getVidaMax() { return vidaMax; }
}