package org.example.navalbattle.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.example.navalbattle.model.Player;

public class GameController {
    //control entre logica y vista
    private Player player;

    @FXML
    private Label nicknameLabel;


    public void setPlayer(Player player) {
        this.player = player;
        this.setNicknameToLabel(this.player.getNickname());
    }

    public void setNicknameToLabel(String nickname) {
         this.nicknameLabel.setText("Welcome " + nickname+ " !");
    }
}



