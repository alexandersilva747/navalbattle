package org.example.navalbattle.model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class figures extends Canvas {
    private Image backgroundImage;
    private String tipo;
    private Image image;

    public figures(double width, double height, String tipo) {
        super(width, height);
        this.tipo = tipo;
        backgroundImage = new Image(getClass().getResource("/org/example/navalbattle/images/aaaa.png").toExternalForm());
        dibujarBarco();
    }

    private void dibujarBarco() {
        GraphicsContext gc = this.getGraphicsContext2D();
        gc.setFill(Color.LIGHTSKYBLUE);
        gc.fillRect(0, 0, getWidth(), getHeight());
        gc.drawImage(backgroundImage, 0, 0, getWidth(), getHeight());

        // Dibujar el barco según el tipo
        switch (tipo) {
            case "fragata":
                image = new Image(getClass().getResource("/org/example/navalbattle/images/fragata.png").toExternalForm());
                gc.drawImage(image, 0, 0, getWidth(), getHeight());
                break;
            case "submarino":
                image = new Image(getClass().getResource("/org/example/navalbattle/images/submarino.png").toExternalForm());
                gc.drawImage(image, 0, 0, getWidth(), getHeight());
                break;
            case "aircraft":
                image = new Image(getClass().getResource("/org/example/navalbattle/images/portaviones.png").toExternalForm());
                gc.drawImage(image, 0, 0, getWidth(), getHeight());
                break;
            case "destroyer":
                image = new Image(getClass().getResource("/org/example/navalbattle/images/destructor.png").toExternalForm());
                gc.drawImage(image, 0, 0, getWidth(), getHeight());
                break;
        }
    }
}