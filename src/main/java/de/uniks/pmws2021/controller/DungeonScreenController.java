package de.uniks.pmws2021.controller;

import de.uniks.pmws2021.RPGEditor;
import de.uniks.pmws2021.StageManager;
import de.uniks.pmws2021.controller.subcontroller.EnemyViewSubController;
import de.uniks.pmws2021.controller.subcontroller.HeroStatViewSubController;
import de.uniks.pmws2021.controller.subcontroller.HeroViewSubController;
import de.uniks.pmws2021.model.Enemy;
import de.uniks.pmws2021.model.Hero;
import de.uniks.pmws2021.model.HeroStat;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;

public class DungeonScreenController {

    private PropertyChangeListener onLpChanged = this::onLpChanged;
    private PropertyChangeListener onNextEnemyChanged = this::onNextEnemyChanged;;
    private PropertyChangeListener onCoinsChanged = this::onCoinsChanged;

    private Parent view;
    private RPGEditor editor;
    private Button exitButton;
    private Button resetButton;
    private VBox enemySubView;
    private VBox heroSubView;
    private ArrayList<HeroViewSubController> heroSubViewControllerList;
    private ArrayList<EnemyViewSubController> enemySubViewControllerList;
    private ArrayList<HeroStatViewSubController> heroStatSubViewControllerList;
    private Label dungeonNameLabel;
    private VBox heroStatSubView;
    private Label heroCoinsLabel;
    private Button attackButton;
    private Button defenseButton;

    public DungeonScreenController(Parent view, RPGEditor editor) {
        this.view = view;
        this.editor = editor;
        this.heroSubViewControllerList = new ArrayList<>();
        this.enemySubViewControllerList = new ArrayList<>();
        this.heroStatSubViewControllerList = new ArrayList<>();
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
        attackButton = (Button) view.lookup("#AttackButton");
        defenseButton = (Button) view.lookup("#DefenseButton");

        // Add action listeners
        exitButton.setOnAction(this::exitButtonOnClick);
        resetButton.setOnAction(this::resetButtonOnClick);
        attackButton.setOnAction(this::attackButtonClick);
        defenseButton.setOnAction(this::defenseButtonClick);

        // init Views
        dungeonNameLabel.setText(this.editor.getDungeonName());
        heroCoinsLabel.setText(String.valueOf(this.editor.getDungeon().getHero().getCoins()));

        // init subViews
        initHeroViewSubController();
        initEnemyViewSubController();
        initHeroStatViewSubController();

        // PCL
        this.editor.getDungeon().getHero().addPropertyChangeListener(Hero.PROPERTY_COINS, onCoinsChanged);
        this.editor.getDungeon().getHero().getAttacking().addPropertyChangeListener(Enemy.PROPERTY_LP, onLpChanged);
        this.editor.getDungeon().getHero().getAttacking().addPropertyChangeListener(Enemy.PROPERTY_NEXT, onNextEnemyChanged);

    }

    public void onNextEnemyChanged(PropertyChangeEvent event) {

    }

    public void onCoinsChanged(PropertyChangeEvent event) {
        heroCoinsLabel.setText(String.valueOf(this.editor.getDungeon().getHero().getCoins()));
    }


    public void onLpChanged(PropertyChangeEvent event) {
        //ToDo: extend if condition so it works properly
        if (event.getNewValue().equals(0) && this.editor.getDungeon().getEnemy().isEmpty()) {
            Alert info = new Alert(Alert.AlertType.INFORMATION);
            info.setHeaderText("Dungeon clear!");
            info.setContentText("Return to main Menu");
            info.showAndWait();
            StageManager.showHeroScreen();
        }
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

        for (HeroStatViewSubController heroStatViewController : heroStatSubViewControllerList
        ) {
            heroStatViewController.stop();
        }

        // remove PCL
        this.editor.getDungeon().getHero().removePropertyChangeListener(Hero.PROPERTY_COINS, onCoinsChanged);
        this.editor.getDungeon().getHero().removePropertyChangeListener(Hero.PROPERTY_LP, onLpChanged);

    }


    // ===========================================================================================
    // Button Action Methods
    // ===========================================================================================

    private void attackButtonClick(ActionEvent actionEvent) {
        this.editor.heroEngagesFight("attack", this.editor.getDungeon().getHero());
        this.editor.evaluateFight(this.editor.getDungeon().getHero().getAttacking(), this.editor.getDungeon().getHero());
    }

    private void defenseButtonClick(ActionEvent actionEvent) {
        //ToDo fix EvaluateFight Method
        this.editor.heroEngagesFight("defend", this.editor.getDungeon().getHero());
        this.editor.evaluateFight(this.editor.getDungeon().getHero().getAttacking(), this.editor.getDungeon().getHero());
    }

    private void exitButtonOnClick(ActionEvent actionEvent) {
        StageManager.showHeroScreen();
    }

    private void resetButtonOnClick(ActionEvent actionEvent) {
        // set all values to beginning and show DungeonScreen
        StageManager.showDungeonScreen();
    }

    // ===========================================================================================
    // additional Methods

    // ===========================================================================================

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
        for (HeroStat heroStat : this.editor.getDungeon().getHero().getStats()
        ) {
            try {
                Parent heroStatView = FXMLLoader.load(StageManager.class.getResource("subview/HeroStatView.fxml"));
                this.heroStatSubView.getChildren().add(heroStatView);

                HeroStatViewSubController heroStatViewSubController = new HeroStatViewSubController(heroStat, heroStatView, this.editor);
                heroStatViewSubController.init();

                // add subcontroller to list of controllers for removal
                heroStatSubViewControllerList.add(heroStatViewSubController);

            } catch (IOException e) {
                System.err.println("Failed to load Hero SubView :: initHeroViewSubController");
                e.printStackTrace();
            }
        }

    }

}