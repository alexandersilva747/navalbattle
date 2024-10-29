package org.example.navalbattle.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class GameView extends Stage {
    public GameView() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/org/example/navalbattle/game-view.fxml")
        );
        Parent root = loader.load();
        this.setTitle("NAVAL BATTLE");
        Scene scene = new Scene(root);
        this.setScene(scene);
        this.getIcons().add(new Image(
                getClass().getResource("/org/example/navalbattle/images/icon.png").toString()
        ));
        this.show();
    }

    public static GameView getInstance() throws IOException {
        if (GameView.GameViewHolder.INSTANCE == null) {
            return GameView.GameViewHolder.INSTANCE = new GameView();
        } else {
            return GameView.GameViewHolder.INSTANCE;
        }
    }

    private static class GameViewHolder {
        private static GameView INSTANCE;
    }
}
