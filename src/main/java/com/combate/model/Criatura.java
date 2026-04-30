package com.combate.model;

/**
 * Representa la entidad básica de un Pokémon en el sistema.
 * Esta clase almacena las estadísticas de combate, los estados de salud 
 * y las rutas de los recursos visuales necesarios para la interfaz.
 * 
 */
public class Criatura {
    
    /** Nombre identificativo de la criatura. */
    public String nombre; 
    
    /** Puntos de salud máximos que puede alcanzar la criatura. */
    public int vidaMax;
    
    /** Puntos de salud que posee la criatura en el momento actual del combate. */
    public int vidaActual;
    
    /** Valor numérico que determina la potencia ofensiva. */
    public int ataque;    
    
    /** Valor numérico que determina la resistencia ante ataques enemigos. */
    public int defensa; 
    
    /** Ruta del recurso de imagen para la pantalla de selección (miniatura). */
    public String imagen;        
    
    /** Ruta del recurso de imagen para la pantalla de combate (sprite principal). */
    public String imagenCombate; 

    /**
     * Reduce los puntos de vida actuales de la criatura según una cantidad recibida.
     * Si la vida resultante es menor a cero, se ajusta automáticamente a 0.
     * 
     * @param cantidad Valor de daño que se restará a la vida actual.
     */
    public void recibirDanio(int cantidad) {
        this.vidaActual -= cantidad;
        if (this.vidaActual < 0) this.vidaActual = 0;
    }

    /**
     * Aumenta los puntos de vida actuales de la criatura.
     * La curación no permite que la vida actual supere el valor de {@code vidaMax}.
     * 
     * @param cantidad Valor de salud que se sumará a la vida actual.
     */
    public void curar(int cantidad) {
        this.vidaActual += cantidad;
        if (this.vidaActual > vidaMax) this.vidaActual = vidaMax;
    }

    /**
     * Obtiene el nombre de la criatura.
     * @return El nombre almacenado en el atributo {@code nombre}.
     */
    public String getNombre() { return nombre; }

    /**
     * Obtiene la salud actual de la criatura.
     * @return El valor entero de {@code vidaActual}.
     */
    public int getVidaActual() { return vidaActual; }

    /**
     * Obtiene el límite máximo de salud de la criatura.
     * @return El valor entero de {@code vidaMax}.
     */
    public int getVidaMax() { return vidaMax; }
}