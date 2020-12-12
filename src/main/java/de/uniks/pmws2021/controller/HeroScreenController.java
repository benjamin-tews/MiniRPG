package de.uniks.pmws2021.controller;

import de.uniks.pmws2021.StageManager;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;

public class HeroScreenController {

    private Parent view;
    private Button createStartButton;

    public HeroScreenController(Parent view) {
        this.view = view;
    }

    public void init() {
        // Load all view references
        createStartButton = (Button) view.lookup("#CreateStartButton");

        // Add action listeners
        createStartButton.setOnAction(this::createStartButtonOnClick);
    }

    public void stop() {
        // Clear references
        // Clear action listeners
        createStartButton.setOnAction(null);
    }

    // Additional methods
    private void createStartButtonOnClick(ActionEvent actionEvent) {
        StageManager.showDungeonScreen();
    }

}