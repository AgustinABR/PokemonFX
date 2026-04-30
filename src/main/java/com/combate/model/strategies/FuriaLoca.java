package com.combate.model.strategies;

import com.combate.model.Criatura;

/**
 * Implementación de la habilidad "Furia Loca".
 * Esta estrategia aplica un daño variable basado en el ataque del usuario
 * multiplicado por un factor aleatorio, representando un estado de frenesí.
 * 
 */
public class FuriaLoca implements Habilidad {

    /**
     * Ejecuta el ataque de furia contra un objetivo.
     * El daño se calcula multiplicando el ataque base del usuario por un 
     * factor aleatorio que oscila entre 0.5 y 1.5.
     * 
     * @param usuario  La {@link Criatura} que realiza la acción.
     * @param objetivo La {@link Criatura} que recibe el impacto del ataque.
     * @return Un mensaje narrativo indicando el resultado del ataque y el daño infligido.
     */
    @Override
    public String ejecutar(Criatura usuario, Criatura objetivo) {
        // Genera un multiplicador entre 0.5 (50%) y 1.5 (150%)
        double azar = 0.5 + Math.random(); 
        
        // Cálculo del daño final basado en el ataque del usuario
        int danioFinal = (int) (usuario.ataque * azar);
        
        // Aplicación del daño al objetivo
        objetivo.recibirDanio(danioFinal);
        
        return usuario.nombre + " se volvió loco e hizo " + danioFinal + " de danio.";
    }
}