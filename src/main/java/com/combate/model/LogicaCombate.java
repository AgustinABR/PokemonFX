package com.combate.model;

import java.util.Random;

public class LogicaCombate {
    private Criatura jugador;
    private Criatura rival;
    private Random random = new Random();

    public LogicaCombate(Criatura jugador, Criatura rival) {
        this.jugador = jugador;
        this.rival = rival;
    }

    //Los ataques
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

    public boolean combateTerminado() {
        return jugador.vidaActual <= 0 || rival.vidaActual <= 0;
    }

    public String obtenerResultadoFinal() {
        if (rival.vidaActual <= 0) return "¡VICTORIA! El rival ha sido derrotado.";
        if (jugador.vidaActual <= 0) return "Has perdido el combate...";
        return "";
    }
}
