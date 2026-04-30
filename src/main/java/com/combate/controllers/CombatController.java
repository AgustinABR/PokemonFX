package com.combate.controllers;

import com.combate.model.Criatura;
import com.combate.model.LogicaCombate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.stage.Stage;

/**
 * Controlador de la interfaz de combate en JavaFX.
 * Gestiona la interacción del usuario durante la batalla, actualiza los componentes 
 * visuales (barras de vida, imágenes, historial) y coordina los turnos 
 * a través de la lógica de negocio.
 * 
 * @author Lourdes Molina y Agustín
 * @version 1.0
 */
public class CombatController {

    // --- Componentes vinculados al archivo FXML ---
    
    /** Barras de progreso que representan la salud actual del jugador y el rival. */
    @FXML private ProgressBar barraJugador, barraRival;
    
    /** Etiquetas para mostrar la salud numérica y los nombres de los contendientes. */
    @FXML private Label lblVida, lblVidaRival, lblNombreJugador, lblNombreRival; 
    
    /** Área de texto donde se narran los sucesos del combate. */
    @FXML private TextArea txtHistorial; 
    
    /** Contenedores para los sprites o imágenes de los Pokémon en combate. */
    @FXML private ImageView imgJugador, imgRival; 
    
    /** Botones de acción para ataques y navegación. */
    @FXML private Button btnAtaque1, btnAtaque2, btnAtaque3, btnAtaque4, btnVolver;

    /** Instancia del Pokémon controlado por el usuario. */
    private Criatura jugador;
    
    /** Instancia del Pokémon controlado por la IA. */
    private Criatura rival;
    
    /** Motor de reglas que procesa los cálculos de daño y estados. */
    private LogicaCombate logica;

    /**
     * Inicializa la escena de combate con los datos transferidos desde la selección.
     * Configura las imágenes, nombres, botones y el motor de lógica.
     * 
     * @param jugador Objeto Criatura seleccionado por el usuario.
     * @param rival Objeto Criatura seleccionado como oponente.
     */
    public void setDatos(Criatura jugador, Criatura rival) {
        this.jugador = jugador;
        this.rival = rival;
        this.logica = new LogicaCombate(jugador, rival); 
        
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

        // Ocultamos el botón de volver al iniciar un nuevo combate
        btnVolver.setVisible(false);
    }

    /**
     * Personaliza la interfaz de botones de ataque según la criatura elegida.
     * Cambia el texto de los botones y deshabilita aquellos ataques que la 
     * criatura no posee según su diseño.
     */
    private void configurarBotonesPorPokemon() {
        // Habilitamos todos por defecto antes de filtrar
        btnAtaque1.setDisable(false); btnAtaque2.setDisable(false);
        btnAtaque3.setDisable(false); btnAtaque4.setDisable(false);

        String nombre = jugador.nombre.toLowerCase();

        if (nombre.contains("pikachu")) {
            btnAtaque1.setText("Impactrueno");
            btnAtaque2.setText("Onda Trueno");
            btnAtaque3.setDisable(true); btnAtaque3.setText("---"); 
            btnAtaque4.setText("Rayo");
        } 
        else if (nombre.contains("charizard")) {
            btnAtaque1.setText("Lanzallamas");
            btnAtaque2.setDisable(true); btnAtaque2.setText("---"); 
            btnAtaque3.setText("Respiro");
            btnAtaque4.setText("Llamarada");
        } 
        else if (nombre.contains("blastoise")) {
            btnAtaque1.setText("Hidrobomba");
            btnAtaque2.setText("Pistola Agua");
            btnAtaque3.setText("Refugio");
            btnAtaque4.setDisable(true); btnAtaque4.setText("---"); 
        }
        else if (nombre.contains("snorlax")) {
            btnAtaque1.setText("Golpe Cuerpo");
            btnAtaque2.setText("Lengüetazo");
            btnAtaque3.setText("Descanso");
            btnAtaque4.setDisable(true); btnAtaque4.setText("---"); 
        }
    }

    /**
     * Sincroniza los datos del modelo con los componentes visuales.
     * Actualiza las ProgressBar de salud y las etiquetas de texto de vida actual/máxima.
     */
    private void actualizarInterfaz() {
        barraJugador.setProgress((double) jugador.vidaActual / jugador.vidaMax);
        barraRival.setProgress((double) rival.vidaActual / rival.vidaMax);
        lblVida.setText(jugador.vidaActual + " / " + jugador.vidaMax);
        lblVidaRival.setText(rival.vidaActual + " / " + rival.vidaMax);
    }

    /**
     * Ejecuta el ataque de tipo "Golpe Básico" del jugador.
     * @param event Evento de acción del botón.
     */
    @FXML void usarGolpeBasico(ActionEvent event) { procesarTurno(1, btnAtaque1.getText()); }

    /**
     * Ejecuta el ataque de tipo "Robo Vida" del jugador.
     * @param event Evento de acción del botón.
     */
    @FXML void usarRoboVida(ActionEvent event) { procesarTurno(2, btnAtaque2.getText()); }

    /**
     * Ejecuta la habilidad de tipo "Sanación" del jugador.
     * @param event Evento de acción del botón.
     */
    @FXML void usarSanacion(ActionEvent event) { procesarTurno(3, btnAtaque3.getText()); }

    /**
     * Ejecuta el ataque de tipo "Furia" del jugador.
     * @param event Evento de acción del botón.
     */
    @FXML void usarFuria(ActionEvent event) { procesarTurno(4, btnAtaque4.getText()); }

    /**
     * Controla el flujo del combate por turnos. 
     * Primero procesa la acción del jugador, verifica si el combate ha terminado, 
     * y si no, procesa la respuesta automática del rival.
     * 
     * @param tipoAtaque Identificador entero del tipo de ataque a realizar.
     * @param nombreAtaque Nombre legible del ataque para el historial.
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
     * Finaliza la sesión de combate. 
     * Inhabilita los controles de ataque, muestra el mensaje de victoria/derrota 
     * y habilita el botón para regresar al menú principal.
     */
    private void finalizarCombate() {
        txtHistorial.appendText("\n" + logica.obtenerResultadoFinal());
        
        // Bloqueamos los botones de ataque al terminar
        btnAtaque1.setDisable(true); 
        btnAtaque2.setDisable(true);
        btnAtaque3.setDisable(true); 
        btnAtaque4.setDisable(true);
        
        // Hacemos visible el botón para regresar a la selección
        btnVolver.setVisible(true);
    }

    /**
     * Cambia la escena actual para regresar a la pantalla de selección de personajes.
     * Carga el archivo FXML correspondiente y lo establece en el Stage actual.
     */
    @FXML
    void volverSeleccion() {
        try {
            // Cargar el archivo de la pantalla de selección
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/seleccion.fxml")); 
            Parent root = loader.load();

            // Obtener la ventana actual y cambiar la escena
            Stage stage = (Stage) btnVolver.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}