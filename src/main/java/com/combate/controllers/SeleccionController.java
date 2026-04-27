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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Random;

public class SeleccionController {

    @FXML private ImageView img0, img1, img2, img3;
    @FXML private Label info0, info1, info2, info3;
    @FXML private VBox card0, card1, card2, card3; 

    private List<Criatura> listaPokemon;

    @FXML
    public void initialize() {
        try {
            Gson gson = new Gson();
            var is = getClass().getResourceAsStream("/data/criaturas.json");
            listaPokemon = gson.fromJson(new InputStreamReader(is), new TypeToken<List<Criatura>>(){}.getType());

            configurarFicha(0, img0, info0, card0);
            configurarFicha(1, img1, info1, card1);
            configurarFicha(2, img2, info2, card2);
            configurarFicha(3, img3, info3, card3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void configurarFicha(int index, ImageView img, Label lbl, VBox card) {
        if (index < listaPokemon.size()) {
            Criatura c = listaPokemon.get(index);
            img.setImage(new Image(getClass().getResourceAsStream(c.imagen)));
            img.setPreserveRatio(true);
            card.setUserData(index); 
            lbl.setText(c.nombre.toUpperCase() + "\n❤ " + c.vidaMax + " | ⚔ " + c.ataque + " | 🛡 " + c.defensa);
        }
    }

    @FXML
    void seleccionarPokemon(MouseEvent event) {
        try {
            VBox fuente = (VBox) event.getSource();
            Integer index = (Integer) fuente.getUserData();
            
            if (index != null) {
                Criatura elegido = listaPokemon.get(index);
                Criatura rival = listaPokemon.get(new Random().nextInt(listaPokemon.size()));

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/vistaCombate.fxml"));
                Parent root = loader.load();

                CombatController controller = loader.getController();
                controller.setDatos(elegido, rival);

                Stage stage = (Stage) fuente.getScene().getWindow();
                stage.setScene(new Scene(root));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}