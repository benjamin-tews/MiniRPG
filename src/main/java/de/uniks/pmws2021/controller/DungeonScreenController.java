package de.uniks.pmws2021.controller;

import de.uniks.pmws2021.RPGEditor;
import de.uniks.pmws2021.StageManager;
import de.uniks.pmws2021.controller.subcontroller.EnemyViewSubController;
import de.uniks.pmws2021.controller.subcontroller.HeroStatViewSubController;
import de.uniks.pmws2021.controller.subcontroller.HeroViewSubController;
import de.uniks.pmws2021.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;

public class DungeonScreenController {

    private Parent view;
    private RPGEditor editor;
    private Button exitButton;
    private Button resetButton;
    private VBox enemySubView;
    private VBox heroSubView;
    private ArrayList<HeroViewSubController> heroSubViewControllerList;
    private ArrayList<EnemyViewSubController> enemySubViewControllerList;
    private Label dungeonNameLabel;
    private VBox heroStatSubView;
    private Label heroCoinsLabel;

    public DungeonScreenController(Parent view, RPGEditor editor) {
        this.view = view;
        this.editor = editor;
        this.heroSubViewControllerList = new ArrayList<>();
        this.enemySubViewControllerList = new ArrayList<>();
    }

    public void init() {
        // Load all view references
        exitButton = (Button) view.lookup("#ExitButton");
        resetButton = (Button) view.lookup("#ResetButton");
        heroSubView = (VBox) this.view.lookup("#HeroSubView");
        enemySubView = (VBox) this.view.lookup("#EnemySubView");
        dungeonNameLabel = (Label) view.lookup("#DungeonNameLabel");
        heroStatSubView = (VBox) view.lookup("#HeroStatSubView");
        heroCoinsLabel = (Label) view.lookup("#HeroCoinsLabel");

        // Add action listeners
        exitButton.setOnAction(this::exitButtonOnClick);
        resetButton.setOnAction(this::resetButtonOnClick);

        //init Views
        dungeonNameLabel.setText(this.editor.getDungeonName());
        heroCoinsLabel.setText(String.valueOf(this.editor.getDungeon().getHero().getCoins()));

        // init subViews
        initHeroViewSubController();
        initEnemyViewSubController();
        initHeroStatViewSubController();
    }


    public void stop() {
        // Clear references
        // Clear action listeners
        exitButton.setOnAction(null);
        resetButton.setOnAction(null);

        for (HeroViewSubController heroController : heroSubViewControllerList
        ) {
            heroController.stop();
        }

        for (EnemyViewSubController enemyController : enemySubViewControllerList
        ) {
            enemyController.stop();
        }
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
    private void initEnemyViewSubController() {

        try {
            Parent enemyView = FXMLLoader.load(StageManager.class.getResource("subview/EnemyView.fxml"));
            this.enemySubView.getChildren().clear();
            this.enemySubView.getChildren().add(enemyView);

            EnemyViewSubController enemyViewSubController = new EnemyViewSubController(this.editor.getDungeon().getEnemy().get(0), enemyView, this.editor);
            enemyViewSubController.init();

            // add subcontroller to list of controllers for removal
            enemySubViewControllerList.add(enemyViewSubController);

        } catch (IOException e) {
            System.err.println("Failed to load Enemy SubView :: initEnemyViewSubController");
            e.printStackTrace();
        }
    }

    // lookup heroSubView
    // create and init HeroViewSubController
    private void initHeroViewSubController() {
        this.heroSubView.getChildren().clear();
        try {
            Parent heroView = FXMLLoader.load(StageManager.class.getResource("subview/HeroView.fxml"));
            this.heroSubView.getChildren().add(heroView);

            HeroViewSubController heroViewSubController = new HeroViewSubController(this.editor.getDungeon().getHero(), heroView, this.editor);
            heroViewSubController.init();

            // add subcontroller to list of controllers for removal
            heroSubViewControllerList.add(heroViewSubController);

        } catch (IOException e) {
            System.err.println("Failed to load Hero SubView :: initHeroViewSubController");
            e.printStackTrace();
        }
    }

    // iterate hero stats
    // load fxml for every stat
    // create and init HeroStatViewSubController for each
    // add to heroStatContainer in dungeonView
    private void initHeroStatViewSubController() {
        this.heroStatSubView.getChildren().clear();
        for ( HeroStat heroStat : this.editor.getDungeon().getHero().getStats()
             ){
            try {
                Parent heroStatView = FXMLLoader.load(StageManager.class.getResource("subview/HeroStatView.fxml"));
                this.heroStatSubView.getChildren().add(heroStatView);

                HeroStatViewSubController heroStatViewSubController = new HeroStatViewSubController(heroStat, heroStatView, this.editor);
                heroStatViewSubController.init();

                // add subcontroller to list of controllers for removal

            } catch (IOException e) {
                System.err.println("Failed to load Hero SubView :: initHeroViewSubController");
                e.printStackTrace();
            }
        }

    }

}