package de.uniks.pmws2021.controller;

import de.uniks.pmws2021.StageManager;
import de.uniks.pmws2021.model.Hero;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;


public class HeroScreenController {

    private Parent view;
    private Button createStartButton;
    private Button exitButton;
    private ListView listView;

    public HeroScreenController(Parent view) {
        this.view = view;
    }

    public void init() {
        // Load all view references
        createStartButton = (Button) view.lookup("#CreateStartButton");
        exitButton = (Button) view.lookup("#ExitButton");

        // Add action listeners
        createStartButton.setOnAction(this::createStartButtonOnClick);
        exitButton.setOnAction(this::exitButtonOnClick);

        // ListView
        listView = new ListView();
        listView = (ListView) view.lookup("#listView");
        listView.setEditable(false);
        //  unsafe operation - fix this
        listView.getItems().addAll("hero1","hero2","hero3");

    }


    public void stop() {
        // Clear references
        // Clear action listeners
        createStartButton.setOnAction(null);
        exitButton.setOnAction(null);
    }

    // Additional methods
    private void createStartButtonOnClick(ActionEvent actionEvent) {
        StageManager.showDungeonScreen();
    }

    private void exitButtonOnClick(ActionEvent actionEvent) {
        Platform.exit();
    }

}