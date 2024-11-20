package org.example.navalbattle.controllers;
/**
 * Controlador principal del juego de Batalla Naval. Maneja las interacciones del jugador con la interfaz gráfica,
 * la colocación de los barcos de la máquina, la validación de la flota de la máquina, y la actualización del tablero.
 * @author Estudiantes: Lady Vanessa Matabanchoy Lasso 2370571
 * Sebastian Orejuela Albornoz 2242232
 * Olman Alexander Silva Zuñiga 2343025
 * @version 1.0
 */
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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


public class GameController {

    /**
     * Boton para iniciar el juego una vez se posicionen las naves de la flota,
     * ya hay un metodo que initialize para cargar los tableros y las naves, el metodo de este
     * boton se debe configurar en el metodo onActionStarGameButton
     *
     */

    public Button startGameButton;
    /**
     * Boton de disparo, al dar click en fire en estado del juego se
     * mostrara un mensaje, seleccionar cuadrante enemigo, al seleccionar el
     * cuadrante enemigo, si el disparo es fallido se marcara la celda con una figura 2d
     * azul sobre el boton que ocupa la celda y continuara  jugando la maquina,
     * si el disparo es acertado se marcara con una figura 2d amarilla hit, indicando
     * que se golpeo un barco y continua jugando el mismo jugador,
     * cuando el disparo acierta todas las casillas que ocupa la nave
     * se marcan las celdas con figuras 2d rojas indicando que se hundio la nave, gana el jugador o
     * maquina que hunda toda la flota enemiga.
     * esta logica se debe implementar en el metodo onActionFireButton
     */
    public Button fireButton;
    /**
     * Variable booleana para verificar inicio del juego
     */
    private boolean gameStarted = false;

    /**
     * label para mostrar los estados del juego
     */

    @FXML
    private Label avisosLabel;

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

        // Configuración de la flota de la máquina
        // Añadir fragatas (4 unidades)
        for (int i = 0; i < 4; i++) {
            machineFleet.add(new Figures(50, 50, "fragata", isVertical));
        }
        // Añadir submarinos (2 unidades)
        for (int i = 0; i < 2; i++) {
            machineFleet.add(new Figures(50 * 3, 50, "submarino", isVertical));
        }

        machineFleet.add(new Figures(50 * 4, 50, "aircraft", isVertical));

        // Añadir destroyers (3 unidades)
        for (int i = 0; i < 3; i++) {
            machineFleet.add(new Figures(50 * 2, 50, "destroyer", isVertical));
        }

        //colocacion de los barcos de la máquina aleatoriamente
        placeMachineFleet();

        // Configuraracion el botón de verificación de la flota de la máquina
        machineGameVerification.setOnAction(e -> handleMachineGameVerification());
        //ciclo for para registrar los eventos de click en cada boton
        for (Node node : machineBoardGridPane.getChildren()) {
            if (node instanceof Button) {
                Button button = (Button) node;
                button.setOnMouseClicked(e -> handlePlayerFire(button));
            }
        }

    }


    /**
     * Coloca los barcos de la máquina de forma aleatoria en el tablero.
     */
    private void placeMachineFleet() {
        Random random = new Random();

        // Colocacion de cada barco de la máquina de forma aleatoria

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
                    cell.setStyle("-fx-background-color: lightblue; -fx-border-color: red; -fx-border-width: 2;");
                }
            }
        }
    }

    /**
     * Maneja el evento del botón "Start Game".
     */
    public void onActionStarGameButton(ActionEvent actionEvent) {
        if (tableroAliado.todosLosBarcosColocados()) { // Verificar que todas las naves estén posicionadas
            avisosLabel.setText("¡Juego iniciado! Es tu turno.");
            fireButton.setDisable(false); // Habilitar el botón de disparo
            startGameButton.setDisable(true); // Desactivar el botón de inicio
            gameStarted = true;
        } else {
            avisosLabel.setText("Por favor, posiciona todas tus naves antes de iniciar el juego.");
        }
    }

    /**
     * Maneja el evento del botón "Fire".
     */
    public void onActionFireButton(ActionEvent actionEvent) {
        if (!gameStarted) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Por favor, inicia el juego antes de disparar.");
            alert.showAndWait();
            return;
        }

        avisosLabel.setText("Selecciona un cuadrante enemigo para disparar.");

        // EventHandler para manejar el clic en el tablero de la máquina
        machineBoardGridPane.setOnMouseClicked(e -> {
            Node targetNode = e.getPickResult().getIntersectedNode();
            System.out.println("Nodo clickeado: " + targetNode);
            if (targetNode instanceof Button) {
                Button targetCell = (Button) targetNode;

                System.out.println("Botón seleccionado: " + targetCell.getId());
                handlePlayerFire(targetCell);
                // Remocion del EventHandler para evitar múltiples disparos en un solo clic
                machineBoardGridPane.setOnMouseClicked(null);
            }
        });
    }
    /**
     * Maneja el disparo del jugador en el tablero de la máquina.
     *
     * @param targetCell La celda objetivo en el tablero de la máquina.
     */
    private void handlePlayerFire(Button targetCell) {
        String shipType = (String) targetCell.getUserData();

        if (shipType == null) {
            // Fallo: Marcar la celda con una figura azul
            targetCell.setStyle("-fx-background-color: blue;");
            avisosLabel.setText("Fallo. Turno de la máquina.");
            fireButton.setDisable(false); //ya no se deshabilita el boton
            targetCell.setVisible(false);
            handleMachineTurn();

        } else {
            // Impacto: Marcar la celda con una figura amarilla
            targetCell.setStyle("-fx-background-color: yellow;");
            avisosLabel.setText("¡Impacto!");

            // Registrar el golpe en el barco de la máquina
            if (isShipSunk(shipType)) {
                markSunkShipOnMachineBoard(shipType);
                avisosLabel.setText("¡Hundiste una nave enemiga!");
                checkGameOver();
            }
            //targetCell.setVisible(false);
        }

        // Deshabilitar el botón de disparo hasta que se complete el turno
        if (!fireButton.isDisabled()) {
            fireButton.setDisable(false);
        }
    }


    /**
     * Marca todas las celdas de un barco en el tablero de la máquina como hundido (rojo).
     *
     * @param shipType Tipo de barco hundido.
     */
    private void markSunkShipOnMachineBoard(String shipType) {
        for (Node node : machineBoardGridPane.getChildren()) {
            if (node instanceof Button) {
                Button cell = (Button) node;
                if (shipType.equals(cell.getUserData())) {
                    cell.setStyle("-fx-background-color: red;");
                }
            }
        }
    }

    /**
     * Comprueba si un barco de la máquina está hundido.
     *
     * @param shipType Tipo de barco.
     * @return true si el barco está hundido, false en caso contrario.
     */
    private boolean isShipSunk(String shipType) {
        // Verificar si todas las celdas del barco han sido golpeadas
        for (Node node : machineBoardGridPane.getChildren()) {
            if (node instanceof Button) {
                Button cell = (Button) node;
                if (shipType.equals(cell.getUserData()) && !cell.getStyle().contains("yellow") && !cell.getStyle().contains("red")) {
                    return false; // Aún hay partes del barco sin golpear
                }
            }
        }
        return true;
    }



    /**
     * Maneja el turno de la máquina.
     */
    private void handleMachineTurn() {
        Random random = new Random();
        boolean fired = false;

        while (!fired) {
            int col = random.nextInt(TableroAliado.GRID_COLUMNS);
            int row = random.nextInt(TableroAliado.GRID_ROWS);

            // Verificar si la celda ya ha sido disparada
            if (!isAlreadyFiredAtPlayer(col, row)) {
                fired = true;
                String shipType = tableroAliado.getShipAtPosition(col, row);

                if (shipType == null) {
                    // Fallo: Marcar la celda con una figura azul
                    markPlayerBoardMiss(col, row);
                    avisosLabel.setText("La máquina falló. Es tu turno.");
                } else {
                    // Impacto: Marcar la celda con una figura amarilla
                    markPlayerBoardHit(col, row);
                    avisosLabel.setText("¡La máquina golpeó tu nave!");

                    // Registrar el golpe en el barco
                    tableroAliado.registerHit(shipType);

                    // Verificar si el barco está hundido
                    if (tableroAliado.isShipSunk(shipType)) {
                        markPlayerBoardSunk(shipType);
                        avisosLabel.setText("¡La máquina hundió una de tus naves!");
                        checkGameOver();
                    } else {
                        // La máquina puede disparar nuevamente si golpea
                        handleMachineTurn();
                    }
                }
            }
        }
    }

    /**
     * Verifica si la máquina ya ha disparado en una celda específica del tablero del jugador.
     *
     * @param col Columna de la celda.
     * @param row Fila de la celda.
     * @return true si ya se ha disparado en la celda, false en caso contrario.
     */
    private boolean isAlreadyFiredAtPlayer(int col, int row) {
        // Iterar sobre los nodos del GridPane del jugador para verificar el estado
        for (Node node : boardGridPane.getChildren()) {
            Integer nodeCol = GridPane.getColumnIndex(node);
            Integer nodeRow = GridPane.getRowIndex(node);
            if (nodeCol != null && nodeRow != null && nodeCol == col && nodeRow == row) {
                // Verificar si la celda ya ha sido disparada (colores azul, amarillo o rojo)
                String style = node.getStyle();
                return style.contains("blue") || style.contains("yellow") || style.contains("red");
            }
        }
        return false;
    }

    /**
     * Marca una celda del tablero del jugador como fallo (azul).
     *
     * @param col Columna de la celda.
     * @param row Fila de la celda.
     */
    private void markPlayerBoardMiss(int col, int row) {
        // Crear una figura azul y agregarla al GridPane del jugador
        Rectangle missMark = new Rectangle(TableroAliado.CELL_WIDTH, TableroAliado.CELL_HEIGHT);
        missMark.setFill(Color.BLUE);
        missMark.setOpacity(0.5); // Transparencia para que se vea el barco debajo si lo hay
        boardGridPane.add(missMark, col, row);
    }

    /**
     * Marca una celda del tablero del jugador como impacto (amarillo).
     *
     * @param col Columna de la celda.
     * @param row Fila de la celda.
     */
    private void markPlayerBoardHit(int col, int row) {
        // Crear una figura amarilla y agregarla al GridPane del jugador
        Rectangle hitMark = new Rectangle(TableroAliado.CELL_WIDTH, TableroAliado.CELL_HEIGHT);
        hitMark.setFill(Color.YELLOW);
        hitMark.setOpacity(0.5); // Transparencia para que se vea el barco debajo
        boardGridPane.add(hitMark, col, row);
    }

    /**
     * Marca todas las celdas de un barco como hundido (rojo).
     *
     * @param shipType Tipo de barco hundido.
     */
    private void markPlayerBoardSunk(String shipType) {
        for (TableroAliado.ShipPlacement ship : tableroAliado.getPlacedShips()) {
            if (ship.type.equals(shipType)) {
                for (int c = ship.col; c <= ship.endCol; c++) {
                    for (int r = ship.row; r <= ship.endRow; r++) {
                        Rectangle sunkMark = new Rectangle(TableroAliado.CELL_WIDTH, TableroAliado.CELL_HEIGHT);
                        sunkMark.setFill(Color.RED);
                        sunkMark.setOpacity(0.5);
                        boardGridPane.add(sunkMark, c, r);
                    }
                }
            }
        }
    }



    /**
     * Verifica si el juego ha terminado.
     */
    private void checkGameOver() {
        if (allShipsSunkOnMachineBoard()) {
            avisosLabel.setText("¡Ganaste! Hundiste toda la flota enemiga.");
            fireButton.setDisable(true);
        } else if (allShipsSunkOnPlayerBoard()) {
            avisosLabel.setText("Perdiste. La máquina hundió toda tu flota.");
            fireButton.setDisable(true);
        }
    }

    /**
     * Comprueba si todas las naves en el tablero de la máquina están hundidas.
     *
     * @return True si todas las naves están hundidas, false en caso contrario.
     */
    private boolean allShipsSunkOnMachineBoard() {
        for (Figures ship : machineFleet) {
            // Para cada barco de la máquina, verifica si todas sus celdas han sido golpeadas (amarillo o rojo)
            boolean sunk = true;
            int shipLength = ship.getLength();
            List<Point> shipPositions = getShipPositions(machineBoardGridPane, ship.getName());

            for (Point pos : shipPositions) {
                Button cell = getButtonAtMachineBoard(pos.x, pos.y);
                if (cell != null && !cell.getStyle().contains("yellow") && !cell.getStyle().contains("red")) {
                    sunk = false;
                    break;
                }
            }

            if (!sunk) {
                return false;
            }
        }
        return true;
    }

    /**
     * Comprueba si todas las naves en el tablero del jugador están hundidas.
     *
     * @return True si todas las naves están hundidas, false en caso contrario.
     */
    private boolean allShipsSunkOnPlayerBoard() {
        for (TableroAliado.ShipPlacement ship : tableroAliado.getPlacedShips()) {
            if (!tableroAliado.isShipSunk(ship.type)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Obtiene todas las posiciones ocupadas por un barco en el tablero de la máquina.
     *
     * @param boardGridPane GridPane de la máquina.
     * @param shipName      Nombre del barco.
     * @return Lista de puntos (columna, fila) ocupados por el barco.
     */
    private List<Point> getShipPositions(GridPane boardGridPane, String shipName) {
        List<Point> positions = new ArrayList<>();
        for (Node node : boardGridPane.getChildren()) {
            if (node instanceof Button) {
                Button cell = (Button) node;
                String cellShipName = (String) cell.getUserData();
                if (shipName.equals(cellShipName)) {
                    Integer col = GridPane.getColumnIndex(cell);
                    Integer row = GridPane.getRowIndex(cell);
                    if (col != null && row != null) {
                        positions.add(new Point(col, row));
                    }
                }
            }
        }
        return positions;
    }


    /**
     * Clase auxiliar para representar un punto en el tablero.
     */
    private class Point {
        int x;
        int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
