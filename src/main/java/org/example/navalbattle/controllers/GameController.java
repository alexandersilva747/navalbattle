package org.example.navalbattle.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;
import org.example.navalbattle.model.Player;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController {
    //control entre logica y vista
    private Player player;

    @FXML
    private Label nicknameLabel;

    @FXML
    private Rectangle aircraft, destroyer, destroyer2, destroyer3, frigate, frigate2, frigate3, frigate4,submarine, submarine2;

    Draggable draggable=new Draggable();


    public void initialize(URL url, ResourceBundle rb)
    {
        draggable.makeDraggable(aircraft);
        draggable.makeDraggable(destroyer);
        draggable.makeDraggable(destroyer2);
        draggable.makeDraggable(destroyer3);
        draggable.makeDraggable(frigate);
        draggable.makeDraggable(frigate2);
        draggable.makeDraggable(frigate3);
        draggable.makeDraggable(frigate4);
        draggable.makeDraggable(submarine);
        draggable.makeDraggable(submarine2);
    }





    public void setPlayer(Player player) {
        this.player = player;
        this.setNicknameToLabel(this.player.getNickname());
    }

    public void setNicknameToLabel(String nickname) {
         this.nicknameLabel.setText("Welcome " + nickname+ " !");
    }
}



