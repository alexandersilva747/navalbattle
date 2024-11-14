package org.example.navalbattle.model;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.event.ActionEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

public class MainBoard {
    private final int size = 10;  // Defines the size of the board (10x10)
    private final Button[][] buttons = new Button[size][size];  // 2D array to store board buttons
    private final GridPane boardGrid;  // GridPane where buttons are displayed
    private final EnemyFleet enemyFleet;  // EnemyFleet instance to handle ships and hits

    /**
     * Constructor to initialize the board and place the enemy fleet.
     *
     * @param boardGrid The GridPane where the board is displayed
     * @param enemyFleet The enemy fleet with ships randomly positioned
     */
    public MainBoard(GridPane boardGrid, EnemyFleet enemyFleet) {
        this.boardGrid = boardGrid;
        this.enemyFleet = enemyFleet;
        createBoard();  // Initializes the board with buttons
    }

    /**
     * Initializes the 10x10 board by creating buttons for each cell.
     * Each button has a click event to handle shots.
     */
    private void createBoard() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                Button button = new Button();
                button.setMinSize(30, 30);  // Sets the button size
                int finalRow = row;
                int finalCol = col;

                // Sets the action for each button when clicked
                button.setOnAction(event -> handleButtonPress(finalRow, finalCol));

                // Adds the button to the GridPane and stores it in the buttons array
                buttons[row][col] = button;
                boardGrid.add(button, col, row);
            }
        }
    }

    /**
     * Handles button press events to check for hits, misses, or sunk ships.
     *
     * @param row The row of the pressed button
     * @param col The column of the pressed button
     */
    private void handleButtonPress(int row, int col) {
        Button button = buttons[row][col];

        if (enemyFleet.isShipHit(row, col)) {  // Checks if a ship is hit
            if (enemyFleet.isShipSunkAt(row, col)) {  // Checks if the ship is fully sunk
                button.setGraphic(createTriangle());  // Displays an orange triangle for sunk ship
            } else {
                Circle hitCircle = new Circle(5, Color.RED);  // Red circle for individual hit
                button.setGraphic(hitCircle);
            }
        } else {  // If miss, show a blue "X" on the button
            Line line1 = new Line(0, 0, 30, 30);
            Line line2 = new Line(30, 0, 0, 30);
            line1.setStroke(Color.BLUE);
            line2.setStroke(Color.BLUE);
            button.setGraphic(new javafx.scene.Group(line1, line2));
        }
    }

    /**
     * Creates an orange triangle shape to indicate a sunk ship.
     *
     * @return Polygon representing an orange triangle
     */
    private Polygon createTriangle() {
        Polygon triangle = new Polygon();
        triangle.getPoints().addAll(
                15.0, 0.0,
                0.0, 30.0,
                30.0, 30.0
        );
        triangle.setFill(Color.ORANGE);
        return triangle;
    }
}
