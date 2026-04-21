package com.combate.model.strategies;
import com.combate.model.Criatura;

public class SanacionRapida implements Habilidad {
    @Override
    public String ejecutar(Criatura usuario, Criatura objetivo) {
        int cura = 25;
        usuario.curar(cura);
        return usuario.nombre + " se ha curado " + cura + " puntos de vida.";
    }
}
