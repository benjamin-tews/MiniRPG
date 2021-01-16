package de.uniks.pmws2021.controller;

import de.uniks.pmws2021.RPGEditor;
import de.uniks.pmws2021.StageManager;
import de.uniks.pmws2021.controller.subcontroller.EnemyViewSubController;
import de.uniks.pmws2021.controller.subcontroller.HeroViewSubController;
import de.uniks.pmws2021.model.Enemy;
import de.uniks.pmws2021.model.Hero;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;

public class DungeonScreenController {

    private Parent view;
    private RPGEditor editor;
    private Button exitButton;
    private Button resetButton;
    private VBox enemySubView;
    private ArrayList<HeroViewSubController> heroSubViewControllerList;
    private VBox heroSubView;
    private ArrayList<EnemyViewSubController> enemySubViewControllerList;

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

        // Add action listeners
        exitButton.setOnAction(this::exitButtonOnClick);
        resetButton.setOnAction(this::resetButtonOnClick);

        // init subViews
        initHeroViewSubController();
        initEnemyViewSubController();
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

        //temp helper
        Enemy enemy = new Enemy();

        try {
            Parent enemyView = FXMLLoader.load(StageManager.class.getResource("subview/EnemyView.fxml"));
            //ToDo: better/other implementation?
            this.enemySubView.getChildren().clear();
            this.enemySubView.getChildren().add(enemyView);

            EnemyViewSubController enemyViewSubController = new EnemyViewSubController(enemy, enemyView, this.editor);
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

        Hero hero = new Hero();
        hero.setName("Kackapoo");

        try {
            Parent heroView = FXMLLoader.load(StageManager.class.getResource("subview/HeroView.fxml"));
            //ToDo: better/other implementation?
            this.heroSubView.getChildren().clear();
            this.heroSubView.getChildren().add(heroView);

            HeroViewSubController heroViewSubController = new HeroViewSubController(hero, heroView, this.editor);
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

}