package org.example.navalbattle.controllers;

/**
 * Clase controladora para la vista de bienvenida en el juego.
 * Esta clase maneja la interacción con la vista inicial, donde el jugador introduce su apodo
 * y da inicio al juego. La clase se encarga de recoger el apodo del jugador, crear un objeto
 * de la clase {@link Player} y pasar ese objeto al controlador del juego.
 * @author Estudiantes: Lady Vanessa Matabanchoy Lasso 2370571
 * Sebastian Orejuela Albornoz 2242232
 * Olman Alexander Silva Zuñiga 2343025
 * @version 1.0
 */

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.example.navalbattle.model.Player;
import org.example.navalbattle.views.GameView;


import java.io.IOException;
import javafx.event.ActionEvent;
/**
 La clase se encarga de recoger el apodo del jugador, crear un objeto
 * de la clase {@link Player} y pasar ese objeto al controlador del juego.
 */
public class WelcomeController {
    /**
     * Campo de texto donde el jugador ingresa su apodo.
     */
    @FXML
    private TextField nicknameTextField;

    /**
     * Acción que se ejecuta cuando el jugador hace clic en el botón "Let's Play".
     * Este metodo toma el apodo ingresado por el jugador, crea un objeto de tipo {@link Player},
     * establece el apodo en dicho objeto y luego pasa el objeto {@link Player} al controlador del juego.
     * Después, redirige al juego para iniciar la sesión del jugador.
     *
     * @param event El evento de acción generado por el botón "Let's Play".
     * @throws IOException Si ocurre un error al intentar cargar la vista del juego.
     */
    @FXML
    public void onActionLetsPlayButton (ActionEvent event)throws IOException {
        String nickname = nicknameTextField.getText();
        Player player = new Player();
        player.setNickname(nickname);
        System.out.println(player.getNickname());
        GameView gameView = GameView.getInstance();
        gameView.getGameController().setPlayer(player);
    }
}
