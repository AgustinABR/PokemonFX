package com.combate.controllers;

import com.combate.model.Criatura;
import com.combate.model.LogicaCombate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.*;

public class CombatController {

    // Componentes vinculados al archivo FXML
    @FXML private ProgressBar barraJugador, barraRival;
    @FXML private Label lblVida, lblVidaRival, lblNombreJugador, lblNombreRival; 
    @FXML private TextArea txtHistorial; 
    @FXML private ImageView imgJugador, imgRival; 
    @FXML private Button btnAtaque1, btnAtaque2, btnAtaque3, btnAtaque4;

    private Criatura jugador, rival;
    private LogicaCombate logica; // Referencia a la clase de lógica

    /**
     * Recibe los datos de la pantalla de selección e inicializa el combate.
     */
    public void setDatos(Criatura jugador, Criatura rival) {
        this.jugador = jugador;
        this.rival = rival;
        this.logica = new LogicaCombate(jugador, rival); // Iniciamos el cerebro del combate
        
        // Carga de imágenes de combate 
        imgJugador.setImage(new Image(getClass().getResourceAsStream(jugador.imagenCombate)));
        imgRival.setImage(new Image(getClass().getResourceAsStream(rival.imagenCombate)));
        
        // Asignación de nombres a los Labels
        if(lblNombreJugador != null) lblNombreJugador.setText(jugador.nombre);
        if(lblNombreRival != null) lblNombreRival.setText(rival.nombre);
        
        // Personaliza los botones según el Pokémon elegido
        configurarBotonesPorPokemon();
        
        actualizarInterfaz();
        txtHistorial.setText("¡El combate comienza!");
    }

    /**
     * Configura textos y bloquea botones específicos para cada criatura.
     */
    private void configurarBotonesPorPokemon() {
        // Habilitamos todos por defecto antes de filtrar
        btnAtaque1.setDisable(false); btnAtaque2.setDisable(false);
        btnAtaque3.setDisable(false); btnAtaque4.setDisable(false);

        String nombre = jugador.nombre.toLowerCase();

        if (nombre.contains("pikachu")) {
            btnAtaque1.setText("Impactrueno");
            btnAtaque2.setText("Onda Trueno");
            btnAtaque3.setDisable(true); btnAtaque3.setText("---"); // Pikachu no se cura
            btnAtaque4.setText("Rayo");
        } 
        else if (nombre.contains("charizard")) {
            btnAtaque1.setText("Lanzallamas");
            btnAtaque2.setDisable(true); btnAtaque2.setText("---"); // Charizard no roba vida
            btnAtaque3.setText("Respiro");
            btnAtaque4.setText("Llamarada");
        } 
        else if (nombre.contains("blastoise")) {
            btnAtaque1.setText("Hidrobomba");
            btnAtaque2.setText("Pistola Agua");
            btnAtaque3.setText("Refugio");
            btnAtaque4.setDisable(true); btnAtaque4.setText("---"); // Blastoise no usa furia
        }
        else if (nombre.contains("snorlax")) {
            btnAtaque1.setText("Golpe Cuerpo");
            btnAtaque2.setText("Lengüetazo");
            btnAtaque3.setText("Descanso");
            btnAtaque4.setDisable(true); btnAtaque4.setText("---"); // Snorlax es vago para la furia
        }
    }

    /**
     * Refresca las barras de vida y los textos numéricos.
     */
    private void actualizarInterfaz() {
        barraJugador.setProgress((double) jugador.vidaActual / jugador.vidaMax);
        barraRival.setProgress((double) rival.vidaActual / rival.vidaMax);
        lblVida.setText(jugador.vidaActual + " / " + jugador.vidaMax);
        lblVidaRival.setText(rival.vidaActual + " / " + rival.vidaMax);
    }

    // Eventos de los botones: llaman a procesarTurno pasando el ID del ataque
    @FXML void usarGolpeBasico(ActionEvent event) { procesarTurno(1, btnAtaque1.getText()); }
    @FXML void usarRoboVida(ActionEvent event) { procesarTurno(2, btnAtaque2.getText()); }
    @FXML void usarSanacion(ActionEvent event) { procesarTurno(3, btnAtaque3.getText()); }
    @FXML void usarFuria(ActionEvent event) { procesarTurno(4, btnAtaque4.getText()); }

    /**
     * Gestiona la secuencia: Ataque Jugador -> ¿Muerte? -> Ataque Rival -> ¿Muerte?
     */
    private void procesarTurno(int tipoAtaque, String nombreAtaque) {
        // 1. Acción del Jugador
        logica.realizarAtaqueJugador(tipoAtaque);
        txtHistorial.appendText("\n" + jugador.nombre + " ha usado " + nombreAtaque);
        actualizarInterfaz();

        if (logica.combateTerminado()) {
            finalizarCombate();
            return;
        }

        // 2. Acción del Rival 
        String msjRival = logica.realizarTurnoRival();
        txtHistorial.appendText("\n" + msjRival);
        actualizarInterfaz();

        if (logica.combateTerminado()) {
            finalizarCombate();
        }
    }

    /**
     * Muestra el resultado final y bloquea la entrada del usuario.
     */
    private void finalizarCombate() {
        txtHistorial.appendText("\n" + logica.obtenerResultadoFinal());
        btnAtaque1.setDisable(true); btnAtaque2.setDisable(true);
        btnAtaque3.setDisable(true); btnAtaque4.setDisable(true);
    }
}