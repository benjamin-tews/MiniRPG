package de.uniks.pmws2021.controller;

import de.uniks.pmws2021.StageManager;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Button;

public class DungeonScreenController {

    private Parent view;
    private Button exitButton;

    public DungeonScreenController(Parent view) {
        this.view = view;
    }

    public void init() {
        // Load all view references
        exitButton = (Button) view.lookup("#ExitButton");

        // Add action listeners
        exitButton.setOnAction(this::exitButtonOnClick);
    }

    public void stop() {
        // Clear references
        // Clear action listeners
        exitButton.setOnAction(null);
    }

    // Additional methods
    private void exitButtonOnClick(ActionEvent actionEvent) {
        StageManager.showHeroScreen();
    }

}