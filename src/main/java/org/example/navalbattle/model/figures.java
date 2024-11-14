package org.example.navalbattle.model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
public class figures extends Canvas {
    private Image backgroundImage;
    private String tipo;
    private Image image;
    private boolean isVertical;

    public figures(double width, double height, String tipo, boolean isVertical) {
        super(width, height);
        this.tipo = tipo;
        this.isVertical = isVertical;
        backgroundImage = new Image(getClass().getResource("/org/example/navalbattle/images/aaaa.png").toExternalForm());
        setImageForShipType();  // Establecer la imagen correcta según el tipo de barco
        dibujarBarco();
    }

    private void setImageForShipType() {
        // Establecer la imagen correspondiente según el tipo de barco
        switch (tipo) {
            case "fragata":
                image = new Image(getClass().getResource("/org/example/navalbattle/images/fragata.png").toExternalForm());
                break;
            case "submarino":
                image = new Image(getClass().getResource("/org/example/navalbattle/images/submarino.png").toExternalForm());
                break;
            case "aircraft":
                image = new Image(getClass().getResource("/org/example/navalbattle/images/portaviones.png").toExternalForm());
                break;
            case "destroyer":
                image = new Image(getClass().getResource("/org/example/navalbattle/images/destructor.png").toExternalForm());
                break;
        }
    }

    private void dibujarBarco() {
        GraphicsContext gc = this.getGraphicsContext2D();
        gc.setFill(Color.LIGHTSKYBLUE);

        // Dibujar el fondo de la imagen
        gc.drawImage(backgroundImage, 0, 0, getWidth(), getHeight());

        // Si la orientación es vertical
        if (isVertical) {
            // Rotar la imagen 90 grados para la orientación vertical
            gc.save();
            gc.translate(getWidth() / 2, getHeight() / 2);  // Desplazar el origen al centro para rotar
            gc.rotate(90);  // Rotar 90 grados

            // Ajustar el tamaño de la imagen para que mantenga las dimensiones correctas al rotar
            double rotatedWidth = getHeight();  // Al rotar, el ancho se convierte en altura
            double rotatedHeight = getWidth(); // La altura se convierte en ancho

            // Dibujar la imagen con las dimensiones rotadas
            gc.drawImage(image, -rotatedWidth / 2, -rotatedHeight / 2, rotatedWidth, rotatedHeight);
            gc.restore();
        } else {
            // Dibujar en orientación horizontal con las dimensiones originales
            gc.drawImage(image, 0, 0, getWidth(), getHeight());
        }
    }

    // Metodo para actualizar la orientación del barco
    public void setVertical(boolean isVertical) {
        this.isVertical = isVertical;
        dibujarBarco();  // Redibujar el barco con la nueva orientación
    }
}



