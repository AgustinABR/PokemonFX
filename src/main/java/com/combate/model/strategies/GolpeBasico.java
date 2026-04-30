package com.combate.model.strategies;

import com.combate.model.Criatura;

/**
 * Implementación de la habilidad "Golpe Básico".
 * Esta estrategia representa un ataque físico estándar donde el daño final
 * se ve mitigado por la capacidad defensiva del oponente.
 * 
 */
public class GolpeBasico implements Habilidad {

    /**
     * Ejecuta un ataque físico directo sobre el objetivo.
     * El daño se calcula restando la mitad de la defensa del objetivo al 
     * ataque total del usuario. Se garantiza un daño mínimo de 5 puntos 
     * para evitar turnos nulos.
     * 
     * @param usuario  La {@link Criatura} que lanza el ataque.
     * @param objetivo La {@link Criatura} que recibe el impacto.
     * @return Un mensaje descriptivo con el resultado de la acción y la vida restada.
     */
    @Override
    public String ejecutar(Criatura usuario, Criatura objetivo) {
        // Fórmula: Daño = Ataque - (Defensa Rival / 2)
        int danio = usuario.ataque - (objetivo.defensa / 2);
        
        // Control de daño mínimo para asegurar progreso en el combate
        if (danio < 5) danio = 5; 
        
        // Aplicación del resultado al modelo del oponente
        objetivo.recibirDanio(danio);
        
        return usuario.nombre + " atacó con todo y quitó " + danio + " de vida.";
    }
}