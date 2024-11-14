package org.example.navalbattle.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.example.navalbattle.model.EnemyFleet;
import org.example.navalbattle.model.Figures;
import org.example.navalbattle.model.MainBoard;
import org.example.navalbattle.model.Player;
import javafx.scene.control.Button;
import org.example.navalbattle.views.GameView;
import org.example.navalbattle.views.MachineVerification;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    //control entre logica y vista
    private Player player;

    @FXML
    private Label nicknameLabel;

   

    @FXML
    private GridPane boardGrid;

    private MainBoard gameBoard;
    private EnemyFleet enemyFleet;






//    private void initializeBoard() {
//        for (int row = 0; row < 10; row++) {
//            for (int col = 0; col < 10; col++) {
//                Button button = new Button();
//                button.setPrefSize(40, 40);  // Ajusta el tamaño del botón si es necesario
//                button.setId("button" + (row * 10 + col + 1));  // Asignar un id único
//                button.setOnAction(this::handleButtonAction);  // Asignar el mismo evento a todos
//                boardGrid.add(button, col, row);
//            }
//        }
//    }

//    @FXML
//    private void handleButtonAction(ActionEvent event) {
//        Button button = (Button) event.getSource();
//        String buttonId = button.getId();
//
//        // Aquí puedes decidir si el disparo es agua, un impacto o un hundimiento
//        boolean hit = checkHit(buttonId);  // Lógica para verificar si se impactó un barco
//        if (hit) {
//            button.setText("Hit");  // Cambia el texto o el color para mostrar impacto
//        } else {
//            button.setText("X");  // Marca el botón como agua
//        }
//    }

//    private boolean checkHit(String buttonId) {
//        // Implementa la lógica para verificar si el botón presionado corresponde a un barco
//        // Puedes usar un mapa o lista para almacenar la ubicación de los barcos
//        return false;  // Solo como ejemplo
//    }





    Figures ship= new Figures(200, 200);


    @Override
    public void initialize(URL location, ResourceBundle resources) {

//        gameBoard = new MainBoard(boardGrid);
    }






    public void setPlayer(Player player) {
        this.player = player;
        this.setNicknameToLabel(this.player.getNickname());
    }

    public void setNicknameToLabel(String nickname) {
         this.nicknameLabel.setText("Welcome " + nickname +"!");
    }

    @FXML
    public void machineGameVerificationButton(ActionEvent event) throws IOException {
        // Carga la vista MachineVerification
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/path/to/MachineVerification.fxml"));
        Parent root = loader.load();

        // Crea una nueva ventana o escena para mostrar la vista
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Machine Verification");
        stage.show();


    }


    @FXML
    void startGameButton(ActionEvent event) {
        enemyFleet = new EnemyFleet();
        gameBoard = new MainBoard(boardGrid, enemyFleet);
    }
}



