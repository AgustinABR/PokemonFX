package com.combate.model;

import java.util.Random;

/**
 * Motor de lógica que gestiona las reglas de combate entre dos criaturas.
 * Controla el cálculo de daño, la ejecución de habilidades especiales,
 * la inteligencia artificial básica del rival y la determinación del estado del juego.
 * 
 */
public class LogicaCombate {
    
    /** Referencia a la criatura controlada por el usuario. */
    private Criatura jugador;
    
    /** Referencia a la criatura controlada por el sistema. */
    private Criatura rival;
    
    /** Generador de números aleatorios para las decisiones del rival. */
    private Random random = new Random();

    /**
     * Construye una nueva instancia de lógica vinculando a los dos contendientes.
     * 
     * @param jugador Objeto {@link Criatura} que representa al usuario.
     * @param rival Objeto {@link Criatura} que representa al oponente.
     */
    public LogicaCombate(Criatura jugador, Criatura rival) {
        this.jugador = jugador;
        this.rival = rival;
    }

    /**
     * Procesa la acción seleccionada por el jugador basándose en un identificador numérico.
     * 
     * @param tipo Identificador del ataque:
     *             <ul>
     *             <li>1: Ataque Básico (Daño: 20)</li>
     *             <li>2: Robo Vida (Daño: 15, Curación: 10)</li>
     *             <li>3: Sanación (Curación: 25)</li>
     *             <li>4: Furia (Daño: 35)</li>
     *             </ul>
     * @return Un mensaje descriptivo de la acción realizada para mostrar en el historial.
     */
    public String realizarAtaqueJugador(int tipo) {
        switch (tipo) {
            case 1: // Básico
                rival.recibirDanio(20);
                return jugador.nombre + " lanza un ataque básico.";
            case 2: // Robo Vida
                rival.recibirDanio(15);
                jugador.curar(10);
                return jugador.nombre + " drena vida del rival.";
            case 3: // Sanación
                jugador.curar(25);
                return jugador.nombre + " se siente mejor.";
            case 4: // Furia
                rival.recibirDanio(35);
                return jugador.nombre + " ¡ESTÁ FURIOSO!";
            default:
                return "";
        }
    }

    /**
     * Ejecuta el turno automático de la IA (rival).
     * Selecciona aleatoriamente entre tres acciones posibles: ataque ligero,
     * recuperación de salud o ataque fuerte.
     * 
     * @return Un mensaje narrativo con la acción que ha decidido realizar el rival.
     */
    public String realizarTurnoRival() {
        int accion = random.nextInt(3);
        if (accion == 0) {
            jugador.recibirDanio(15);
            return "El rival te golpea.";
        } else if (accion == 1) {
            rival.curar(10);
            return "El rival se recupera un poco.";
        } else {
            jugador.recibirDanio(25);
            return "¡El rival lanza un ataque fuerte!";
        }
    }

    /**
     * Verifica si el combate ha llegado a su fin.
     * El combate termina si cualquiera de los dos contendientes llega a 0 puntos de vida.
     * 
     * @return {@code true} si el combate ha terminado; {@code false} en caso contrario.
     */
    public boolean combateTerminado() {
        return jugador.vidaActual <= 0 || rival.vidaActual <= 0;
    }

    /**
     * Genera el mensaje de conclusión del encuentro.
     * Evalúa quién posee 0 puntos de vida para determinar si el usuario ha ganado o perdido.
     * 
     * @return Un {@link String} con el resultado final: Victoria, Derrota o vacío si sigue en curso.
     */
    public String obtenerResultadoFinal() {
        if (rival.vidaActual <= 0) return "¡VICTORIA! El rival ha sido derrotado.";
        if (jugador.vidaActual <= 0) return "Has perdido el combate...";
        return "";
    }
}