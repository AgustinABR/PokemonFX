package com.combate.model.strategies;

import com.combate.model.Criatura;

public class RoboVida implements Habilidad {
    @Override
    public String ejecutar(Criatura usuario, Criatura objetivo) {
        int puntosRobados = 15;
        
        objetivo.recibirDanio(puntosRobados);
        
        usuario.curar(puntosRobados);
        
        return usuario.nombre + " usó Robo de Vida y le quitó " + puntosRobados + " a " + objetivo.nombre;
    }
}