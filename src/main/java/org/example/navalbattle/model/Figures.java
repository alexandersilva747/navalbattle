package org.example.navalbattle.model;

/**
 * Represents a ship figure in the Naval Battle game.
 * This class handles the visual representation of the ship, including its type,
 * orientation, and size in cells.
 *
 * Implements {@link Serializable} to allow saving and loading game state.
 *
 * Note: Serialization of this class does not include the image resources.
 * These resources must be reloaded when deserializing the object.
 *
 * @author Estudiantes: Lady Vanessa Matabanchoy Lasso 2370571
 *  * Sebastian Orejuela Albornoz 2242232
 *  * Olman Alexander Silva Zuñiga 2343025
 * @version 1.0
 */
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.Serializable;

import static org.example.navalbattle.model.TableroAliado.CELL_HEIGHT;
import static org.example.navalbattle.model.TableroAliado.CELL_WIDTH;


public class Figures extends Canvas implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * The background image of the canvas (not serialized).
     */
    private transient Image backgroundImage;

    /**
     * The type of the ship (e.g., fragata, submarino).
     */
    private String tipo;

    /**
     * The ship's image (not serialized).
     */
    private transient Image image;

    /**
     * Whether the ship is oriented vertically.
     */
    private boolean isVertical;

    /**
     * The number of cells the ship occupies.
     */
    private int casillas;

    /**
     * Constructs a new {@code Figures} object.
     *
     * @param width the width of the canvas
     * @param height the height of the canvas
     * @param tipo the type of the ship
     * @param isVertical whether the ship is oriented vertically
     */
    public Figures(double width, double height, String tipo, boolean isVertical) {
        super(width, height);
        this.tipo = tipo;
        this.isVertical = isVertical;
        this.casillas = getCasillasForType(tipo); // Assign the number of cells based on ship type
        backgroundImage = new Image(getClass().getResource("/org/example/navalbattle/images/aaaa.png").toExternalForm());
        setImageForShipType();
        ajustarCanvas();
        dibujarBarco();
    }

    /**
     * Sets the image for the ship based on its type.
     */
    private void setImageForShipType() {
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

    /**
     * Returns the number of cells the ship occupies based on its type.
     *
     * @param tipo the type of the ship
     * @return the number of cells the ship occupies
     */
    private int getCasillasForType(String tipo) {
        switch (tipo) {
            case "fragata": return 1;
            case "submarino": return 3;
            case "aircraft": return 4;
            case "destroyer": return 2;
            default: return 1;
        }
    }

    /**
     * Returns the length of the ship in cells.
     *
     * @return the length of the ship
     */
    public int getLength() {
        return casillas;
    }

    /**
     * Returns the name (type) of the ship.
     *
     * @return the name of the ship
     */
    public String getName() {
        return tipo;
    }

    /**
     * Adjusts the canvas size based on the ship's orientation and type.
     */
    private void ajustarCanvas() {
        if (isVertical) {
            setWidth(CELL_WIDTH);
            setHeight(CELL_HEIGHT * casillas);
        } else {
            setWidth(CELL_WIDTH * casillas);
            setHeight(CELL_HEIGHT);
        }
    }

    /**
     * Draws the ship on the canvas.
     */
    private void dibujarBarco() {
        ajustarCanvas(); // Adjust canvas size before drawing
        GraphicsContext gc = this.getGraphicsContext2D();
        gc.clearRect(0, 0, getWidth(), getHeight()); // Clear canvas before drawing
        gc.setFill(Color.LIGHTSKYBLUE);

        if (isVertical) {
            gc.save();
            gc.translate(getWidth() / 2, getHeight() / 2); // Center for rotation
            gc.rotate(90); // Rotate for vertical orientation
            gc.drawImage(image, -getHeight() / 2, -getWidth() / 2, getHeight(), getWidth());
            gc.restore();
        } else {
            gc.drawImage(image, 0, 0, getWidth(), getHeight());
        }
    }

    /**
     * Sets the orientation of the ship.
     *
     * @param isVertical whether the ship should be vertical
     */
    public void setVertical(boolean isVertical) {
        this.isVertical = isVertical;
        dibujarBarco(); // Redraw the ship with the new orientation
    }
}
