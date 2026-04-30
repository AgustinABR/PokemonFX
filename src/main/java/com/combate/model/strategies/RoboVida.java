package com.combate.model.strategies;

import com.combate.model.Criatura;

/**
 * Implementación de la habilidad "Robo de Vida".
 * Esta estrategia permite al usuario infligir daño al oponente y, simultáneamente,
 * recuperar la misma cantidad de puntos de salud, representando una transferencia de energía.
 * 
 */
public class RoboVida implements Habilidad {

    /**
     * Ejecuta la acción de drenaje de vida sobre un objetivo.
     * La habilidad resta una cantidad fija de puntos de salud al objetivo 
     * y suma esa misma cantidad a la salud actual del usuario, respetando 
     * siempre los límites de vida máxima definidos en {@link Criatura}.
     * 
     * @param usuario  La {@link Criatura} que realiza el robo de vida y se beneficia de la curación.
     * @param objetivo La {@link Criatura} que recibe el daño.
     * @return Un mensaje narrativo que detalla quién ha usado la habilidad y cuántos puntos se han transferido.
     */
    @Override
    public String ejecutar(Criatura usuario, Criatura objetivo) {
        /** Cantidad fija de puntos de salud a transferir entre las criaturas. */
        int puntosRobados = 15;
        
        // Aplica el daño al oponente
        objetivo.recibirDanio(puntosRobados);
        
        // Restaura la salud del atacante en la misma proporción
        usuario.curar(puntosRobados);
        
        return usuario.nombre + " usó Robo de Vida y le quitó " + puntosRobados + " a " + objetivo.nombre;
    }
}