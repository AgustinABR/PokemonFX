package com.combate;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Clase principal de la aplicación que inicia el ciclo de vida de JavaFX.
 * Se encarga de cargar la primera escena del juego y configurar la ventana principal (Stage).
 * 
 */
public class App extends Application {

    /**
     * Punto de entrada principal para la interfaz gráfica de JavaFX.
     * Carga el archivo FXML inicial de selección de Pokémon y establece las dimensiones
     * y el título de la ventana.
     * 
     * @param stage El escenario principal proporcionado por la plataforma JavaFX.
     * @throws Exception Si ocurre un error al cargar el archivo FXML o los recursos de la vista.
     */
    @Override
    public void start(Stage stage) throws Exception {
        // Carga la jerarquía de nodos desde el archivo FXML de selección
        Parent root = FXMLLoader.load(getClass().getResource("/view/seleccion.fxml"));
        
        // Configura la escena y la asigna al escenario
        stage.setScene(new Scene(root));
        
        // Define el título que aparecerá en la barra superior de la ventana
        stage.setTitle("Selecciona tu Pokémon");
        
        // Hace visible la ventana al usuario
        stage.show();
    }

    /**
     * Método estándar de Java para lanzar la aplicación.
     * Llama internamente al método {@link #launch(String...)} de la clase {@link Application}.
     * 
     * @param args Argumentos de la línea de comandos (opcionales).
     */
    public static void main(String[] args) { 
        launch(); 
    }
}