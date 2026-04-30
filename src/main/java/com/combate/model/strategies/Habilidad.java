package com.combate.model.strategies;

import com.combate.model.Criatura;

/**
 * Interfaz base que define el contrato para todas las habilidades de combate.
 * Utiliza el patrón de diseño <b>Strategy</b> para permitir que cada ataque o 
 * técnica tenga un comportamiento lógico diferente sin modificar la clase de combate principal.
 * 
 */
public interface Habilidad {

    /**
     * Ejecuta la lógica específica de la habilidad entre dos criaturas.
     * Cada implementación debe definir cómo se ven afectados los puntos de vida, 
     * estadísticas o estados de los participantes.
     * 
     * @param usuario  La {@link Criatura} que activa y realiza la habilidad.
     * @param objetivo La {@link Criatura} sobre la cual recae el efecto de la habilidad.
     * @return Una cadena de texto (String) que narra lo sucedido durante la ejecución 
     *         para ser mostrada en el historial de la interfaz gráfica.
     */
    String ejecutar(Criatura usuario, Criatura objetivo);
}