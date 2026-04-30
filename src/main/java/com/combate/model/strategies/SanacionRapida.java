package com.combate.model.strategies;

import com.combate.model.Criatura;

/**
 * Implementación de la habilidad "Sanación Rápida".
 * Esta estrategia representa una acción defensiva que permite al usuario 
 * recuperar una cantidad fija de puntos de salud en un solo turno.
 * 
 */
public class SanacionRapida implements Habilidad {

    /**
     * Ejecuta el proceso de curación sobre el usuario.
     * La habilidad aumenta los puntos de salud actuales del usuario en una cantidad 
     * predefinida, sin exceder nunca su salud máxima. El objetivo no recibe 
     * ningún efecto durante esta acción.
     * 
     * @param usuario  La {@link Criatura} que realiza la acción y recibe la curación.
     * @param objetivo La {@link Criatura} oponente (no se ve afectada por esta habilidad).
     * @return Un mensaje descriptivo que indica la cantidad de vida recuperada por el usuario.
     */
    @Override
    public String ejecutar(Criatura usuario, Criatura objetivo) {
        /** Cantidad de puntos de salud que se restaurarán al usuario. */
        int cura = 25;
        
        // Aplica la lógica de recuperación al modelo del usuario
        usuario.curar(cura);
        
        return usuario.nombre + " se ha curado " + cura + " puntos de vida.";
    }
}