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

public class SeleccionController {

    @FXML private FlowPane panelCriaturas; 

    private List<Criatura> listaPokemon;

    @FXML
    public void initialize() {
        try {
            Gson gson = new Gson();
            var is = getClass().getResourceAsStream("/data/criaturas.json");
            listaPokemon = gson.fromJson(new InputStreamReader(is), new TypeToken<List<Criatura>>(){}.getType());

            generarTarjetas();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void generarTarjetas() {
        panelCriaturas.getChildren().clear();

        for (Criatura pokemon : listaPokemon) {
            VBox card = new VBox(10);
            card.setStyle("-fx-alignment: center; -fx-padding: 15; -fx-border-color: #ccc; -fx-border-radius: 10; -fx-background-color: white; -fx-cursor: hand;");
            card.setPrefWidth(180);

            ImageView img = new ImageView();
            try {
                img.setImage(new Image(getClass().getResourceAsStream(pokemon.imagen)));
            } catch (Exception e) {
                System.out.println("Error cargando imagen de: " + pokemon.nombre);
            }
            img.setFitHeight(120);
            img.setPreserveRatio(true);

            Label lblInfo = new Label(pokemon.nombre.toUpperCase() + "\n❤ " + pokemon.vidaMax + " | ⚔ " + pokemon.ataque);
            lblInfo.setStyle("-fx-text-alignment: center; -fx-font-weight: bold;");

            card.setOnMouseClicked(event -> irACombate(pokemon));

            card.getChildren().addAll(img, lblInfo);
            panelCriaturas.getChildren().add(card);
        }
    }

    private void irACombate(Criatura elegido) {
        try {
            Criatura rival = listaPokemon.get(new Random().nextInt(listaPokemon.size()));

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/vistaCombate.fxml"));
            Parent root = loader.load();

            CombatController controller = loader.getController();
            controller.setDatos(elegido, rival);

            Stage stage = (Stage) panelCriaturas.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}