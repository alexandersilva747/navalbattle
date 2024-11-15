package org.example.navalbattle.model;

import javafx.geometry.Bounds;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.Node;
public class TableroAliado {
    private Pane contenedorBarco1, contenedorSubmarino1, contenedorAircraft, contenedorDestroyer;
    private GridPane boardGridPane;

    private int fragataCount = 0;
    private int submarinoCount = 0;
    private int aircraftCount = 0;
    private int destroyerCount = 0;

    public static final int CELL_WIDTH = 35;
    public static final int CELL_HEIGHT = 40;
    private static final int GRID_COLUMNS = 10;
    private static final int GRID_ROWS = 10;

    public TableroAliado(Pane contenedorBarco1, Pane contenedorSubmarino1, Pane contenedorAircraft, Pane contenedorDestroyer, GridPane boardGridPane) {
        this.contenedorBarco1 = contenedorBarco1;
        this.contenedorSubmarino1 = contenedorSubmarino1;
        this.contenedorAircraft = contenedorAircraft;
        this.contenedorDestroyer = contenedorDestroyer;
        this.boardGridPane = boardGridPane;
    }

    public void handleDragDetected(MouseEvent event) {
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

    public void handleDragOver(DragEvent event, boolean isVertical) {
        if (event.getGestureSource() != boardGridPane && event.getDragboard().hasString()) {
            Bounds gridBounds = boardGridPane.getBoundsInParent();
            double localX = event.getX() - gridBounds.getMinX();
            double localY = event.getY() - gridBounds.getMinY();

            int col = (int) Math.floor(localX / CELL_WIDTH);
            int row = (int) Math.floor(localY / CELL_HEIGHT);

            String barcoTipo = event.getDragboard().getString();
            int shipLength = getShipWidth(barcoTipo);

            int shipWidth = isVertical ? 1 : shipLength;
            int shipHeight = isVertical ? shipLength : 1;

            if (isValidPosition(col, row, shipWidth, isVertical)) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
        }
        event.consume();
    }
    public void handleDragDropped(DragEvent event, boolean isVertical) {
        Dragboard db = event.getDragboard();
        boolean success = false;

        if (db.hasString()) {
            Bounds gridBounds = boardGridPane.getBoundsInParent();
            double localX = event.getX() - gridBounds.getMinX();
            double localY = event.getY() - gridBounds.getMinY();

            int col = (int) Math.floor(localX / CELL_WIDTH);
            int row = (int) Math.floor(localY / CELL_HEIGHT);

            String barcoTipo = db.getString();
            int shipLength = getShipWidth(barcoTipo);

            // Ajustar el tamaño real en función de las celdas y la longitud del barco
            int shipWidth = isVertical ? 1 : shipLength;
            int shipHeight = isVertical ? shipLength : 1;

            if (isValidPosition(col, row, shipLength, isVertical)) {
                switch (barcoTipo) {
                    case "Barco":
                        if (fragataCount < 4) {
                            Figures fragata = new Figures(CELL_WIDTH, CELL_HEIGHT, "fragata", isVertical);
                            boardGridPane.add(fragata, col, row, shipWidth, shipHeight); // Aquí usas shipWidth y shipHeight
                            fragataCount++;
                            success = true;
                        }
                        break;
                    case "Submarino":
                        if (submarinoCount < 2) {
                            Figures submarino = new Figures(CELL_WIDTH, CELL_HEIGHT * 3, "submarino", isVertical);
                            boardGridPane.add(submarino, col, row, shipWidth, shipHeight); // Aquí usas shipWidth y shipHeight
                            submarinoCount++;
                            success = true;
                        }
                        break;
                    case "Aircraft":
                        if (aircraftCount < 1) {
                            Figures aircraft = new Figures(CELL_WIDTH, CELL_HEIGHT * 4, "aircraft", isVertical);
                            boardGridPane.add(aircraft, col, row, shipWidth, shipHeight); // Aquí usas shipWidth y shipHeight
                            aircraftCount++;
                            success = true;
                        }
                        break;
                    case "Destroyer":
                        if (destroyerCount < 3) {
                            Figures destroyer = new Figures(CELL_WIDTH, CELL_HEIGHT * 2, "destroyer", isVertical);
                            boardGridPane.add(destroyer, col, row, shipWidth, shipHeight); // Aquí usas shipWidth y shipHeight
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



    private int getShipWidth(String barcoTipo) {
        return switch (barcoTipo) {
            case "Barco" -> 1;
            case "Submarino" -> 3;
            case "Aircraft" -> 4;
            case "Destroyer" -> 2;
            default -> 1;
        };
    }

    private boolean isValidPosition(int col, int row, int shipLength, boolean isVertical) {
        int shipWidth = isVertical ? 1 : shipLength;
        int shipHeight = isVertical ? shipLength : 1;

        if (col < 0 || row < 0 || col + shipWidth > GRID_COLUMNS || row + shipHeight > GRID_ROWS) {
            return false;
        }

        for (int i = col; i < col + shipWidth; i++) {
            for (int j = row; j < row + shipHeight; j++) {
                if (!isCellEmpty(i, j)) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean isCellEmpty(int col, int row) {
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
