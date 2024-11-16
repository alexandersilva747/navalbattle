package org.example.navalbattle;

/**
 * Clse principal que permite la ejecucion del juego
 * @author Estudiantes: Lady Vanessa Matabanchoy Lasso 2370571
 * Sebastian Orejuela Albornoz 2242232
 * Olman Alexander Silva Zuñiga 2343025
 * @version 1.0
 */

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.navalbattle.views.WelcomeView;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        WelcomeView.getInstance();
    }
}