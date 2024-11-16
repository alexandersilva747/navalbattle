package org.example.navalbattle.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.example.navalbattle.model.TableroAliado.GRID_COLUMNS;
import static org.example.navalbattle.model.TableroAliado.GRID_ROWS;

/**
 * Controlador principal del juego de Batalla Naval. Maneja las interacciones del jugador con la interfaz gráfica,
 * la colocación de los barcos de la máquina, la validación de la flota de la máquina, y la actualización del tablero.
 */
public class GameController {

    public Button startGameButton;
    public Button fireButton;
    /**
     * Jugador actual del juego.
     */
    private Player player;

    /**
     * Etiqueta para mostrar el apodo del jugador.
     */
    @FXML
    private Label nicknameLabel;

    /**
     * Contenedores de los barcos del jugador.
     */
    @FXML
    private Pane contenedorBarco1, contenedorSubmarino1, contenedorAircraft1, contenedorDestroyer1;

    /**
     * Tableros del jugador y de la máquina.
     */
    @FXML
    private GridPane boardGridPane;
    @FXML
    private GridPane machineBoardGridPane;

    /**
     * Botón de verificación de la flota de la máquina.
     */
    @FXML
    private Button machineGameVerification;

    /**
     * Indica si la flota de la máquina ha sido revelada.
     */
    private boolean machineRevealed = false;

    /**
     * Botón para alternar entre orientación vertical y horizontal de los barcos.
     */
    @FXML
    private ToggleButton verticalHorizontal;


    /**
     * Indica si los barcos se colocan en posición vertical.
     */
    private boolean isVertical = false;

    /**
     * Tablero aliado del jugador.
     */
    private TableroAliado tableroAliado;

    /**
     * Flota de la máquina.
     */
    private List<Figures> machineFleet = new ArrayList<>();


    /**
     * Inicializa el controlador. Se configuran los barcos, el tablero y la flota de la máquina.
     */
    public void initialize() {
        // Inicializacion de una fragata con las dimensiones correspondientes
        Figures barco = new Figures(50, 50, "fragata", isVertical);  // Usar tamaño basado en casillas
        contenedorBarco1.getChildren().add(barco);

        // Inicializacion de un submarino con las dimensiones correspondientes
        Figures submarino = new Figures(50 * 3, 50, "submarino", isVertical); // 3 casillas en horizontal
        contenedorSubmarino1.getChildren().add(submarino);

        // Inicializacion de un avión con las dimensiones correspondientes
        Figures avion = new Figures(50 * 4, 50, "aircraft", isVertical); // Usar tamaño basado en casillas
        contenedorAircraft1.getChildren().add(avion);

        // Inicializacion de un destructor con las dimensiones correspondientes
        Figures destructor = new Figures(50 * 2, 50, "destroyer", isVertical); // Usar tamaño basado en casillas
        contenedorDestroyer1.getChildren().add(destructor);

        // Inicializacion del tablero con los contenedores correspondientes
        tableroAliado = new TableroAliado(contenedorBarco1, contenedorSubmarino1, contenedorAircraft1, contenedorDestroyer1, boardGridPane);


        // Event handler para el ToggleButton
        updateToggleButtonText();

//          Comprobación que va en en start button
//        if (tableroAliado.todosLosBarcosColocados()) {
//            System.out.println("La colocación de barcos está completa.");
//            tableroAliado.imprimirPosicionesBarcos();
//        } else {
//            System.out.println("Aún quedan barcos por colocar.");
//        }

        // Configuración de la flota de la máquina
        machineFleet.add(new Figures(50, 50, "fragata", isVertical));
        machineFleet.add(new Figures(50 * 3, 50, "submarino", isVertical));
        machineFleet.add(new Figures(50 * 4, 50, "aircraft", isVertical));
        machineFleet.add(new Figures(50 * 2, 50, "destroyer", isVertical));

        //colocacion de los barcos de la máquina aleatoriamente
        placeMachineFleet();

        // Configuraracion el botón de verificación de la flota de la máquina
        machineGameVerification.setOnAction(e -> handleMachineGameVerification());
    }


    /**
     * Coloca los barcos de la máquina de forma aleatoria en el tablero.
     */
    private void placeMachineFleet() {
        Random random = new Random();

        // Coloca cada barco de la máquina de forma aleatoria
        for (Figures ship : machineFleet) {
            boolean placed = false;
            while (!placed) {
                boolean isVertical = random.nextBoolean();
                int col = random.nextInt(GRID_COLUMNS);
                int row = random.nextInt(GRID_ROWS);

                if (isValidPositionForMachine(col, row, ship.getLength(), isVertical)) {
                    addShipToMachineBoard(ship, col, row, ship.getLength(), isVertical);
                    placed = true;
                }
            }
        }
    }

    /**
     * Verifica si es válida la posición para colocar un barco de la máquina.
     *
     * @param col Columna de inicio
     * @param row Fila de inicio
     * @param shipLength Longitud del barco
     * @param isVertical Si el barco está en posición vertical
     * @return true si la posición es válida, false en caso contrario
     */
    private boolean isValidPositionForMachine(int col, int row, int shipLength, boolean isVertical) {
        int shipWidth = isVertical ? 1 : shipLength;
        int shipHeight = isVertical ? shipLength : 1;

        // Comprobacion si la posición está fuera de los límites del tablero
        if (col < 0 || row < 0 || col + shipWidth > GRID_COLUMNS || row + shipHeight > GRID_ROWS) {
            return false;
        }
        // Verificacion de que las celdas estén vacías
        for (int i = col; i < col + shipWidth; i++) {
            for (int j = row; j < row + shipHeight; j++) {
                if (!isCellEmptyOnMachineBoard(i, j)) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Verifica si una celda está vacía en el tablero de la máquina.
     *
     * @param col Columna de la celda
     * @param row Fila de la celda
     * @return true si la celda está vacía, false si está ocupada
     */
    private boolean isCellEmptyOnMachineBoard(int col, int row) {
        for (Node node : machineBoardGridPane.getChildren()) {
            Integer nodeCol = GridPane.getColumnIndex(node);
            Integer nodeRow = GridPane.getRowIndex(node);
            if (nodeCol != null && nodeRow != null && nodeCol == col && nodeRow == row) {
                Button cell = (Button) node;
                // Verificacion si la celda está ocupada
                if (cell.getUserData() != null) {
                    return false; // La celda está ocupada
                }
            }
        }
        return true; // La celda está vacía
    }


    /**
     * Coloca un barco en el tablero de la máquina en las coordenadas especificadas.
     *
     * @param ship El barco a colocar
     * @param col Columna de inicio
     * @param row Fila de inicio
     * @param shipLength Longitud del barco
     * @param isVertical Si el barco está en posición vertical
     */
    private void addShipToMachineBoard(Figures ship, int col, int row, int shipLength, boolean isVertical) {
        for (int i = 0; i < shipLength; i++) {
            int targetCol = isVertical ? col : col + i;
            int targetRow = isVertical ? row + i : row;

            Button cell = getButtonAtMachineBoard(targetCol, targetRow);
            if (cell != null) {
                // Asignacion del barco a la celda
                cell.setStyle("-fx-background-color: gray;");
                cell.setUserData(ship.getName()); // Almacenar el nombre del barco
            }
        }
    }

    /**
     * Obtiene el botón en las coordenadas especificadas del tablero de la máquina.
     *
     * @param col Columna del botón
     * @param row Fila del botón
     * @return El botón en las coordenadas especificadas, o null si no existe
     */
    private Button getButtonAtMachineBoard(int col, int row) {
        for (Node node : machineBoardGridPane.getChildren()) {
            Integer nodeCol = GridPane.getColumnIndex(node);
            Integer nodeRow = GridPane.getRowIndex(node);
            if (nodeCol != null && nodeRow != null && nodeCol == col && nodeRow == row) {
                return (Button) node;
            }
        }
        return null;
    }

    /**
     * Cambia la orientación de los barcos (horizontal o vertical).
     */
    @FXML
    private void toggleOrientation() {
        isVertical = verticalHorizontal.isSelected();
        updateToggleButtonText();
    }

    /**
     * Actualiza el texto del botón para reflejar la orientación actual.
     */
    private void updateToggleButtonText() {
        verticalHorizontal.setText(isVertical ? "Horizontal" : "Vertical");
    }

    /**
     * Maneja el evento de arrastre de los barcos en el tablero del jugador.
     *
     * @param event El evento de arrastre detectado
     */
    @FXML
    public void handleDragDetected(MouseEvent event) { //Eventos para detectar arrastre
        tableroAliado.handleDragDetected(event);
    }

    /**
     * Maneja el evento de arrastre sobre una celda del tablero del jugador.
     *
     * @param event El evento de arrastre detectado
     */
    @FXML
    public void handleDragOver(DragEvent event) { //Evento para detectar donde se pone el objeto
        tableroAliado.handleDragOver(event, isVertical);
    }
    /**
     * Maneja el evento de soltar un barco en una celda del tablero del jugador.
     *
     * @param event El evento de soltar detectado
     */
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

    /**
     * Maneja la verificación de la flota de la máquina.
     */
    @FXML
    private void handleMachineGameVerification() {
        if (!machineRevealed) {
            mostrarTableroMaquina();
            machineRevealed = true;
        } else {
            machineGameVerification.setDisable(true);
        }
    }

    /**
     * Muestra visualmente los barcos de la máquina en su tablero.
     */
    private void mostrarTableroMaquina() {
        for (Node node : machineBoardGridPane.getChildren()) {
            if (node instanceof Button) {  // Verifica que el nodo sea un botón
                Button cell = (Button) node;
                String shipType = (String) cell.getUserData();  // Obtener el tipo de barco desde los datos del botón
                if (shipType != null) {
                    // Cambiar el estilo para destacar los barcos en el tablero
                    cell.setStyle("-fx-background-color: lightblue; -fx-border-color: black; -fx-border-width: 2;");
                }
            }
        }
    }


    public void onActionStarGameButton(ActionEvent actionEvent) {
        //
    }

    public void onActionFireButton(ActionEvent actionEvent) {
        //
    }
}
