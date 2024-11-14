package org.example.navalbattle.model;

import javafx.geometry.Bounds;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.Node;

public class TableroAliado {

    //Creación de todos los paneles

    private Pane contenedorBarco1,contenedorSubmarino1, contenedorAircraft, contenedorDestroyer;
    private GridPane boardGridPane;

    private int fragataCount = 0;
    private int submarinoCount = 0;
    private int aircraftCount = 0;
    private int destroyerCount = 0;

    private static final int CELL_WIDTH = 35;
    private static final int CELL_HEIGHT = 40;
    private static final int GRID_COLUMNS = 10;
    private static final int GRID_ROWS = 10;

    public TableroAliado(Pane contenedorBarco1, Pane contenedorSubmarino1, Pane contenedorAircraft, Pane contenedorDestroyer, GridPane boardGridPane) {
        this.contenedorBarco1 = contenedorBarco1;
        this.contenedorSubmarino1 = contenedorSubmarino1;
        this.contenedorAircraft = contenedorAircraft;
        this.contenedorDestroyer = contenedorDestroyer;
        this.boardGridPane = boardGridPane;
    }

    public void handleDragDetected(MouseEvent event) { //Evento dentro de tablero para diferentes contenedores
        Dragboard db;
        ClipboardContent content = new ClipboardContent();

        if (event.getSource() == contenedorBarco1) {
            db = contenedorBarco1.startDragAndDrop(TransferMode.MOVE);
            content.putString("Barco");
        } else if (event.getSource() == contenedorSubmarino1) {
            db = contenedorSubmarino1.startDragAndDrop(TransferMode.MOVE);
            content.putString("Submarino");
        } else if (event.getSource() == contenedorAircraft) {
            db = contenedorAircraft.startDragAndDrop(TransferMode.MOVE);
            content.putString("Aircraft");
        } else if (event.getSource() == contenedorDestroyer) {
            db = contenedorDestroyer.startDragAndDrop(TransferMode.MOVE);
            content.putString("Destroyer");
        } else {
            return;
        }

        db.setContent(content);
        event.consume();
    }

    public void handleDragOver(DragEvent event) { //Evento de tirar
        if (event.getGestureSource() != boardGridPane && event.getDragboard().hasString()) {
            Bounds gridBounds = boardGridPane.getBoundsInParent();
            double localX = event.getX() - gridBounds.getMinX();
            double localY = event.getY() - gridBounds.getMinY();

            int col = (int) (localX / CELL_WIDTH);
            int row = (int) (localY / CELL_HEIGHT);

            String barcoTipo = event.getDragboard().getString();
            int shipWidth = getShipWidth(barcoTipo);

            if (isValidPosition(col, row, shipWidth)) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
        }
        event.consume();
    }

    public void handleDragDropped(DragEvent event) {
        Dragboard db = event.getDragboard();
        boolean success = false;

        if (db.hasString()) {
            Bounds gridBounds = boardGridPane.getBoundsInParent();
            double localX = event.getX() - gridBounds.getMinX();
            double localY = event.getY() - gridBounds.getMinY();

            int col = (int) (localX / CELL_WIDTH);
            int row = (int) (localY / CELL_HEIGHT);

            String barcoTipo = db.getString();
            int shipWidth = getShipWidth(barcoTipo);

            if (isValidPosition(col, row, shipWidth)) {
                switch (barcoTipo) {
                    case "Barco":
                        if (fragataCount < 4) {
                            figures fragata = new figures(CELL_WIDTH, CELL_HEIGHT, "fragata");
                            boardGridPane.add(fragata, col, row, 1, 1);
                            fragataCount++;
                            success = true;
                        }
                        break;
                    case "Submarino":
                        if (submarinoCount < 2) {
                            figures submarino = new figures(CELL_WIDTH * 3, CELL_HEIGHT, "submarino");
                            boardGridPane.add(submarino, col, row, 3, 1);
                            submarinoCount++;
                            success = true;
                        }
                        break;
                    case "Aircraft":
                        if (aircraftCount < 1) {
                            figures aircraft = new figures(CELL_WIDTH * 4, CELL_HEIGHT, "aircraft");
                            boardGridPane.add(aircraft, col, row, 4, 1);
                            aircraftCount++;
                            success = true;
                        }
                        break;
                    case "Destroyer":
                        if (destroyerCount < 3) {
                            figures destroyer = new figures(CELL_WIDTH * 2, CELL_HEIGHT, "destroyer");
                            boardGridPane.add(destroyer, col, row, 2, 1);
                            destroyerCount++;
                            success = true;
                        }
                        break;
                }
            }
        }

        event.setDropCompleted(success);
        event.consume();
    }

    private int getShipWidth(String barcoTipo) { //Tipo de barco con su respectiva celda ocupada
        return switch (barcoTipo) {
            case "Barco" -> 1;
            case "Submarino" -> 3; // Submarino ocupa 3 celdas
            case "Aircraft" -> 4;  // Aircraft ocupa 4 celdas
            case "Destroyer" -> 2; // Destroyer ocupa 2 celdas
            default -> 1;
        };
    }

    private boolean isValidPosition(int col, int row, int shipWidth) { //posiciones validas para no ocupar celdas llenas
        if (col < 0 || row < 0 || col + shipWidth > GRID_COLUMNS || row >= GRID_ROWS) {
            return false;
        }

        for (int i = col; i < col + shipWidth; i++) {
            if (!isCellEmpty(i, row)) {
                return false;
            }
        }

        return true;
    }

    private boolean isCellEmpty(int col, int row) { //Celda vacia
        for (Node node : boardGridPane.getChildren()) {
            Integer nodeCol = GridPane.getColumnIndex(node);
            Integer nodeRow = GridPane.getRowIndex(node);

            if (nodeCol != null && nodeRow != null && nodeCol == col && nodeRow == row) {
                return false;
            }
        }
        return true;
    }
}
