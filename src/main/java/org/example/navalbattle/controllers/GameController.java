package org.example.navalbattle.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.example.navalbattle.model.TableroAliado;
import org.example.navalbattle.model.Player;
import org.example.navalbattle.model.Figures;
import org.example.navalbattle.views.InstructionsView;
import java.io.IOException;

public class GameController {
    private Player player;

    @FXML
    private Label nicknameLabel;

    @FXML
    private Pane contenedorBarco1, contenedorSubmarino1, contenedorAircraft1,contenedorDestroyer1;



    @FXML
    private GridPane boardGridPane;

    @FXML
    private ToggleButton verticalHorizontal;

    private boolean isVertical = false;

    private TableroAliado tableroAliado;



    public void initialize() {
        // Inicializar una fragata con las dimensiones correspondientes
        Figures barco = new Figures(50, 50, "fragata", isVertical);  // Usar tamaño basado en casillas
        contenedorBarco1.getChildren().add(barco);

        // Inicializar un submarino con las dimensiones correspondientes
        Figures submarino = new Figures(50 * 3, 50, "submarino", isVertical); // 3 casillas en horizontal
        contenedorSubmarino1.getChildren().add(submarino);


        // Inicializar un avión con las dimensiones correspondientes
        Figures avion = new Figures(50 * 4, 50, "aircraft", isVertical); // Usar tamaño basado en casillas
        contenedorAircraft1.getChildren().add(avion);

        // Inicializar un destructor con las dimensiones correspondientes
        Figures destructor = new Figures(50 * 2, 50, "destroyer", isVertical); // Usar tamaño basado en casillas
        contenedorDestroyer1.getChildren().add(destructor);

        // Inicializa el tablero con los contenedores correspondientes
        tableroAliado = new TableroAliado(contenedorBarco1, contenedorSubmarino1, contenedorAircraft1, contenedorDestroyer1, boardGridPane);

        // Mandar los objetos al tablero del aliado
        // Event handler para el ToggleButton
        updateToggleButtonText();
    }


    @FXML
    private void toggleOrientation() {
        isVertical = verticalHorizontal.isSelected();
        updateToggleButtonText();
    }
    private void updateToggleButtonText() {
        verticalHorizontal.setText(isVertical ? "Horizontal" : "Vertical");
    }


    @FXML
    public void handleDragDetected(MouseEvent event) { //Eventos para detectar arrastre
        tableroAliado.handleDragDetected(event);
    }

    @FXML
    public void handleDragOver(DragEvent event) { //Evento para detectar donde se pone el objeto
        tableroAliado.handleDragOver(event, isVertical);
    }

    @FXML
    public void handleDragDropped(DragEvent event) { //evento para arrastrar y solta
        tableroAliado.handleDragDropped(event, isVertical);
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
