package com.combate.model.strategies;
import com.combate.model.Criatura;

public class GolpeBasico implements Habilidad { // Cambiado a Habilidad
    @Override
    public String ejecutar(Criatura usuario, Criatura objetivo) {
        int danio = usuario.ataque - (objetivo.defensa / 2);
        if (danio < 5) danio = 5; 
        objetivo.recibirDanio(danio);
        return usuario.nombre + " atacó con todo y quitó " + danio + " de vida.";
    }
}