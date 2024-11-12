package org.example.navalbattle.controllers;

import javafx.scene.Node;

public class Draggable {

    private double mouseAnchorX;
    private double mouseAnchorY;

    public void makeDraggable(Node node) {

        node.setOnMousePressed(event -> {
            mouseAnchorX = event.getSceneX() - node.getLayoutX();
            mouseAnchorY = event.getSceneY() - node.getLayoutY();
        });

        node.setOnMouseDragged(event -> {
            node.setLayoutX(event.getSceneX() - mouseAnchorX);
            node.setLayoutY(event.getSceneY() - mouseAnchorY);
        });
    }
}
