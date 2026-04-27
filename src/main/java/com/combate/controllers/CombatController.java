package com.combate.controllers;

import com.combate.model.Criatura;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.*;
import java.util.Random;

public class CombatController {

    @FXML private ProgressBar barraJugador, barraRival;
    @FXML private Label lblVida, lblVidaRival, lblNombreJugador, lblNombreRival; 
    @FXML private TextArea txtHistorial; 
    @FXML private ImageView imgJugador, imgRival; 
    @FXML private Button btnAtaque1, btnAtaque2, btnAtaque3, btnAtaque4;

    private Criatura jugador, rival;
    private Random random = new Random();

    public void setDatos(Criatura jugador, Criatura rival) {
        this.jugador = jugador;
        this.rival = rival;
        
        // Cargar imágenes de combate
        imgJugador.setImage(new Image(getClass().getResourceAsStream(jugador.imagenCombate)));
        imgRival.setImage(new Image(getClass().getResourceAsStream(rival.imagenCombate)));
        
        // Poner nombres en los Labels que tienes en el FXML
        if(lblNombreJugador != null) lblNombreJugador.setText(jugador.nombre);
        if(lblNombreRival != null) lblNombreRival.setText(rival.nombre);
        
        actualizarInterfaz();
        txtHistorial.setText("¡Comienza el duelo entre " + jugador.nombre + " y " + rival.nombre + "!");
    }

    private void actualizarInterfaz() {
        // Actualizar barras de progreso
        barraJugador.setProgress((double) jugador.vidaActual / jugador.vidaMax);
        barraRival.setProgress((double) rival.vidaActual / rival.vidaMax);
        
        // Actualizar textos de vida
        lblVida.setText(jugador.vidaActual + " / " + jugador.vidaMax);
        lblVidaRival.setText(rival.vidaActual + " / " + rival.vidaMax);
    }

    @FXML void usarGolpeBasico(ActionEvent event) { atacar(20, " lanza un ataque básico."); }

    @FXML void usarRoboVida(ActionEvent event) {
        rival.recibirDanio(15);
        jugador.curar(10);
        txtHistorial.appendText("\n" + jugador.nombre + " drena vida del rival.");
        finalizarTurno();
    }

    @FXML void usarSanacion(ActionEvent event) {
        jugador.curar(25);
        txtHistorial.appendText("\n" + jugador.nombre + " se siente mejor.");
        finalizarTurno();
    }

    @FXML void usarFuria(ActionEvent event) {
        rival.recibirDanio(35);
        txtHistorial.appendText("\n" + jugador.nombre + " ¡ESTÁ FURIOSO!");
        finalizarTurno();
    }

    private void atacar(int danio, String msg) {
        rival.recibirDanio(danio);
        txtHistorial.appendText("\n" + jugador.nombre + msg);
        finalizarTurno();
    }

    private void finalizarTurno() {
        actualizarInterfaz();
        if (rival.vidaActual <= 0) {
            txtHistorial.appendText("\n¡VICTORIA! El rival ha sido derrotado.");
            desactivarBotones();
        } else {
            turnoRival();
        }
    }

    private void turnoRival() {
        int accion = random.nextInt(3);
        if (accion == 0) {
            jugador.recibirDanio(15);
            txtHistorial.appendText("\nEl rival te golpea.");
        } else if (accion == 1) {
            rival.curar(10);
            txtHistorial.appendText("\nEl rival se recupera un poco.");
        } else {
            jugador.recibirDanio(25);
            txtHistorial.appendText("\n¡El rival lanza un ataque fuerte!");
        }
        actualizarInterfaz();
        if (jugador.vidaActual <= 0) {
            txtHistorial.appendText("\nHas perdido el combate...");
            desactivarBotones();
        }
    }

    private void desactivarBotones() {
        btnAtaque1.setDisable(true);
        btnAtaque2.setDisable(true);
        btnAtaque3.setDisable(true);
        btnAtaque4.setDisable(true);
    }
}