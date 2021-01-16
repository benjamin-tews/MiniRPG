package de.uniks.pmws2021.controller;

import de.uniks.pmws2021.StageManager;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Button;

public class DungeonScreenController {

    private Parent view;
    private Button exitButton;
    private Button resetButton;

    public DungeonScreenController(Parent view) {
        this.view = view;
    }

    public void init() {
        // Load all view references
        exitButton = (Button) view.lookup("#ExitButton");
        resetButton = (Button) view.lookup("#ResetButton");

        // Add action listeners
        exitButton.setOnAction(this::exitButtonOnClick);
        resetButton.setOnAction(this::resetButtonOnClick);
    }


    public void stop() {
        // Clear references
        // Clear action listeners
        exitButton.setOnAction(null);
        resetButton.setOnAction(null);
    }

    // Additional methods
    private void exitButtonOnClick(ActionEvent actionEvent) {
        StageManager.showHeroScreen();
    }

    private void resetButtonOnClick(ActionEvent actionEvent) {
        // set all values to beginning and show DungeonScreen
        StageManager.showDungeonScreen();
    }

    // lookup enemySubView
    // create and init EnemyViewSubController

    // lookup heroSubView
    // create and init HeroViewSubController

    // iterate hero stats
    // load fxml for every stat
    // create and init HeroStatViewSubController for each
    // add to heroStatContainer in dungeonView

}