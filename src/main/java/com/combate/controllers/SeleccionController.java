package com.combate.controllers;

import com.combate.model.Criatura;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Random;

/**
 * Controlador de la pantalla de selección de Pokémon.
 * Se encarga de cargar los datos de las criaturas desde un archivo JSON,
 * generar dinámicamente las tarjetas de selección en la interfaz y gestionar
 * la transición hacia la pantalla de combate.
 * 
 */
public class SeleccionController {

    /** Contenedor de disposición fluida donde se mostrarán las tarjetas de los Pokémon. */
    @FXML private FlowPane panelCriaturas; 

    /** Lista que almacena todas las criaturas cargadas desde el archivo de datos. */
    private List<Criatura> listaPokemon;

    /**
     * Método de inicialización automática de JavaFX.
     * Utiliza la librería {@link com.google.gson.Gson} para leer el archivo 
     * 'criaturas.json' y transformar los datos en una lista de objetos {@link Criatura}.
     */
    @FXML
    public void initialize() {
        try {
            Gson gson = new Gson();
            // Carga del archivo JSON desde los recursos
            var is = getClass().getResourceAsStream("/data/criaturas.json");
            
            // Deserialización del JSON a una lista de objetos Criatura
            listaPokemon = gson.fromJson(new InputStreamReader(is), new TypeToken<List<Criatura>>(){}.getType());

            // Una vez cargados los datos, se dibujan en la pantalla
            generarTarjetas();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Genera y renderiza visualmente una tarjeta (VBox) por cada Pokémon en la lista.
     * Cada tarjeta incluye la imagen, el nombre y las estadísticas base. 
     * Además, asigna un evento de clic para iniciar el combate.
     */
    private void generarTarjetas() {
        // Limpiar el panel antes de añadir nuevos elementos
        panelCriaturas.getChildren().clear();

        for (Criatura pokemon : listaPokemon) {
            // Configuración del contenedor de la tarjeta
            VBox card = new VBox(10);
            card.setStyle("-fx-alignment: center; -fx-padding: 15; -fx-border-color: #ccc; " +
                          "-fx-border-radius: 10; -fx-background-color: white; -fx-cursor: hand;");
            card.setPrefWidth(180);

            // Gestión de la imagen del Pokémon
            ImageView img = new ImageView();
            try {
                img.setImage(new Image(getClass().getResourceAsStream(pokemon.imagen)));
            } catch (Exception e) {
                System.out.println("Error cargando imagen de: " + pokemon.nombre);
            }
            img.setFitHeight(120);
            img.setPreserveRatio(true);

            // Etiqueta con información de nombre, vida y ataque
            Label lblInfo = new Label(pokemon.nombre.toUpperCase() + "\n❤ " + pokemon.vidaMax + " | ⚔ " + pokemon.ataque);
            lblInfo.setStyle("-fx-text-alignment: center; -fx-font-weight: bold;");

            // Al hacer clic, este Pokémon será el elegido por el jugador
            card.setOnMouseClicked(event -> irACombate(pokemon));

            // Construcción visual de la tarjeta
            card.getChildren().addAll(img, lblInfo);
            panelCriaturas.getChildren().add(card);
        }
    }

    /**
     * Prepara la transición a la escena de combate.
     * Selecciona un rival aleatorio de la lista de Pokémon disponibles y 
     * transfiere ambos objetos al {@link CombatController}.
     * 
     * @param elegido El Pokémon seleccionado por el usuario al hacer clic en su tarjeta.
     */
    private void irACombate(Criatura elegido) {
        try {
            // Selección aleatoria del rival
            Criatura rival = listaPokemon.get(new Random().nextInt(listaPokemon.size()));

            // Carga de la vista de combate
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/vistaCombate.fxml"));
            Parent root = loader.load();

            // Transferencia de datos al controlador de combate
            CombatController controller = loader.getController();
            controller.setDatos(elegido, rival);

            // Cambio de escena en la ventana actual
            Stage stage = (Stage) panelCriaturas.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}