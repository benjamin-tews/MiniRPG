package de.uniks.pmws2021.controller;

import de.uniks.pmws2021.StageManager;
import de.uniks.pmws2021.model.Hero;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;


public class HeroScreenController {
    //ToDo: Fix Pathname
    File myImage = new File ("C:\\Users\\Shrabbit\\UNI\\Programmierung und Modellierung 2021\\pmws2021-minirpg-benjamin-tews\\src\\main\\resources\\de\\uniks\\pmws2021\\img\\skull.png");
    private Image skull = new Image(myImage.toURI().toString());
    private Parent view;
    private Button createStartButton;
    private Button exitButton;
    private ListView<Hero> listView;
    private ObservableList<Hero> heroObservableList;

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

        //ListView
        listView = (ListView) view.lookup("#listView");
        heroObservableList = FXCollections.observableArrayList();

        // create some dummy heros
        Hero hero1 = new Hero();
        Hero hero2 = new Hero();
        hero1.setName("Sir DanceALot").setMode("normal");
        hero2.setName("Sir SlayALot").setMode("hardcore");

        heroObservableList.addAll(
                hero1,
                hero2
        );

        //  unsafe operation - fix this
        listView.setItems(heroObservableList);

        // update listView with images
        listView.setCellFactory(param -> new ListCell<Hero>() {
            private ImageView imageView = new ImageView();
            @Override
            public void updateItem(Hero hero, boolean empty) {
                super.updateItem(hero, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    if(hero.getMode() == "hardcore") {
                        imageView.setImage(skull);
                    }
                    setText(hero.getName());
                    setGraphic(imageView);
                }
            }
        });

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