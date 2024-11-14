package org.example.navalbattle.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.example.navalbattle.controllers.GameController;

import java.io.IOException;




public class MachineVerification extends Stage {
    private GameController gameController;

public MachineVerification() throws IOException {
    FXMLLoader loader = new FXMLLoader(
        getClass().getResource("/org/example/navalbattle/machine-view.fxml"));
    Parent root = loader.load();
        this.gameController = loader.getController();
        this.setTitle("NAVAL BATTLE");
    Scene scene = new Scene(root);
        this.setScene(scene);
        this.getIcons().add(new Image(
            getClass().getResource("/org/example/navalbattle/images/icon.png").toString()
        ));
        this.show();
}

public static MachineVerification getInstance() throws IOException {
    if (  MachineVerification.MachineViewHolder.INSTANCE == null) {
        return MachineVerification.MachineViewHolder.INSTANCE = new MachineVerification();
    } else {
        return MachineVerification.MachineViewHolder.INSTANCE;
    }
}

public GameController getGameController() {
    return this.gameController;
}




private static class MachineViewHolder {
    private static MachineVerification INSTANCE;
    }
}
