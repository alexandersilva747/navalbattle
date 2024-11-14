package org.example.navalbattle.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.example.navalbattle.model.TableroAliado;
import org.example.navalbattle.model.Player;
import org.example.navalbattle.model.figures;
import org.example.navalbattle.views.InstructionsView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameController {
    private Player player;

    @FXML
    private Label nicknameLabel;

    @FXML
    private Pane contenedorBarco1, contenedorSubmarino1, contenedorAircraft1,contenedorDestroyer1;



    @FXML
    private GridPane boardGridPane;

    private TableroAliado tableroAliado;



    public void initialize() {
        // Inicializar una fragata
        figures barco = new figures(150, 40, "fragata"); //Creación de una fragata en Pane, visualización en juego
        contenedorBarco1.getChildren().add(barco);

        // Inicializar un submarino
        figures submarino = new figures(150, 40, "submarino"); //Creación submarino, visualizacion juego
        contenedorSubmarino1.getChildren().add(submarino);

        // Inicializar un avión
        figures avion = new figures(150, 40, "aircraft"); //Creación portaaviones, visual
        contenedorAircraft1.getChildren().add(avion);

        // Inicializar un destructor
        figures destructor = new figures(150, 40, "destroyer"); //Creación destructor, visual
        contenedorDestroyer1.getChildren().add(destructor);

        tableroAliado = new TableroAliado(contenedorBarco1, contenedorSubmarino1, contenedorAircraft1, contenedorDestroyer1, boardGridPane);

        //se mandan los objetos al tablero del aliado
    }

    @FXML
    public void handleDragDetected(MouseEvent event) { //Eventos para detectar arrastre
        tableroAliado.handleDragDetected(event);
    }

    @FXML
    public void handleDragOver(DragEvent event) { //Evento para detectar donde se pone el objeto
        tableroAliado.handleDragOver(event);
    }

    @FXML
    public void handleDragDropped(DragEvent event) { //evento para arrastrar y solta
        tableroAliado.handleDragDropped(event);
    }

    public void setPlayer(Player player) {
        this.player = player;
        this.setNicknameToLabel(this.player.getNickname());
    }

    public void setNicknameToLabel(String nickname) {
        this.nicknameLabel.setText("Welcome " + nickname + "!");
    }
    public void onActionGameInstructions (ActionEvent actionEvent) throws IOException{
        InstructionsView.getInstance();
    }
}
