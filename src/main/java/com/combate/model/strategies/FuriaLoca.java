package com.combate.model.strategies;
import com.combate.model.Criatura;

public class FuriaLoca implements Habilidad {
    @Override
    public String ejecutar(Criatura usuario, Criatura objetivo) {
        double azar = 0.5 + Math.random(); 
        int danioFinal = (int) (usuario.ataque * azar);
        objetivo.recibirDanio(danioFinal);
        return usuario.nombre + " se volvió loco e hizo " + danioFinal + " de danio.";
    }
}