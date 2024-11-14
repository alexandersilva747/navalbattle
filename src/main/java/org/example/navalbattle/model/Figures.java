package org.example.navalbattle.model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Figures extends Canvas {
    public Figures(double width, double height) {
        super(width, height);
        drawShip();
    }

    private void drawShip() {
        GraphicsContext gc = this.getGraphicsContext2D();

        // Fondo
        gc.setFill(Color.LIGHTBLUE);
        gc.fillRect(0, 0, getWidth(), getHeight());

        // Base del barco (gris oscuro)
        gc.setFill(Color.DARKGRAY);
        gc.fillRect(30, 60, 140, 20);

        // Parte superior del barco (gris claro)
        gc.setFill(Color.GRAY);
        gc.fillRect(75, 40, 50, 20);

        // Detalles de ventanas o "ventanas" en el barco
        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(80, 45, 5, 5);
        gc.fillRect(90, 45, 5, 5);
        gc.fillRect(100, 45, 5, 5);
        gc.fillRect(110, 45, 5, 5);

        // Torres o partes superiores
        gc.setFill(Color.DARKGRAY);
        gc.fillRect(85, 20, 30, 20);

        // Antenas (o banderas, en el estilo de pixel art)
        gc.setFill(Color.GOLD);
        gc.fillRect(95, 5, 10, 10);

        //Por ahora no incluido mientras se hacen pruebas porque se daña fxml :c
    }

    public void drawWaterAttack() {}
}
