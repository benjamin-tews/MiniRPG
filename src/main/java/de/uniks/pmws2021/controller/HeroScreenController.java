package de.uniks.pmws2021.controller;

import de.uniks.pmws2021.RPGEditor;
import de.uniks.pmws2021.StageManager;
import de.uniks.pmws2021.controller.subcontroller.HeroSubListController;
import de.uniks.pmws2021.model.Hero;
import de.uniks.pmws2021.util.ResourceManager;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class HeroScreenController {
    private Parent view;
    private RPGEditor editor;
    private Button createStartButton;
    private Button exitButton;
    private ListView<Hero> listView;
    private ObservableList<Hero> heroObservableList;
    private TextField createHeroField;
    private CheckBox hardModeCheckBox;
    private VBox heroListVbox;
    private ArrayList<HeroSubListController> heroSubListControllerList;
    private Label startAttackLevelLabel;
    private Label startAttackValueLabel;
    private Label startAttackCostLabel;
    private Label startCoinsLabel;
    private Label startDefenseLevelLabel;
    private Label startDefenseValueLabel;
    private Label startDefenseCostLabel;

    public HeroScreenController(Parent view, RPGEditor editor) {
        this.view = view;
        this.editor = editor;
        this.heroSubListControllerList = new ArrayList<>();
    }

    public void init() {
        // Load all view references
        createStartButton = (Button) view.lookup("#CreateStartButton");
        exitButton = (Button) view.lookup("#ExitButton");
        createHeroField = (TextField) view.lookup("#CreateHeroField");
        hardModeCheckBox = (CheckBox) view.lookup("#HardModeCheckBox");
        heroListVbox = (VBox) view.lookup("#HeroListVbox");

        startAttackLevelLabel = (Label) view.lookup("#StartAttackLevelLabel");
        startAttackValueLabel = (Label) view.lookup("#StartAttackValueLabel");
        startAttackCostLabel = (Label) view.lookup("#StartAttackCostLabel");
        startDefenseLevelLabel = (Label) view.lookup("#StartDefenseLevelLabel");
        startDefenseValueLabel = (Label) view.lookup("#StartDefenseValueLabel");
        startDefenseCostLabel = (Label) view.lookup("#StartDefenseCostLabel");

        startCoinsLabel = (Label) view.lookup("#StartCoinsLabel");

        // on  mouse action
        createStartButton.setOnAction(this::createStartButtonOnClick);
        exitButton.setOnAction(this::exitButtonOnClick);

        // init views
        startAttackLevelLabel.setText(String.valueOf(editor.getStartAttackStats().getLevel()));
        startAttackValueLabel.setText(String.valueOf(editor.getStartAttackStats().getValue()));
        startAttackCostLabel.setText(String.valueOf(editor.getStartAttackStats().getCost()));
        startDefenseLevelLabel.setText(String.valueOf(editor.getStartDefenseStats().getLevel()));
        startDefenseValueLabel.setText(String.valueOf(editor.getStartDefenseStats().getValue()));
        startDefenseCostLabel.setText(String.valueOf(editor.getStartDefenseStats().getCost()));

        startCoinsLabel.setText(String.valueOf(editor.getStartCoins()));

        List<Hero> heroList = ResourceManager.loadAllHeroes();
        this.editor.setLoadedHeroes(heroList);

        initHeroListSubController();

        /*  OLD LIST VIEW - Maybe implement later

        //ListView
        listView = (ListView) view.lookup("#listView");
        heroObservableList = FXCollections.observableArrayList();

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
        */

    }

    public void stop() {
        // Clear references
        // Clear action listeners
        createStartButton.setOnAction(null);
        exitButton.setOnAction(null);

        for (HeroSubListController heroSubListController : heroSubListControllerList
        ) {
            heroSubListController.stop();
        }
    }

    // Additional methods

    private void exitButtonOnClick(ActionEvent actionEvent) {
        Platform.exit();
    }

    private void createStartButtonOnClick(ActionEvent actionEvent) {
        // if no hero than message box
        if (createHeroField.getText() == null || createHeroField.getText().trim().isEmpty()) {
            Alert fail = new Alert(Alert.AlertType.WARNING);
            fail.setHeaderText("Empty Hero Input Field");
            fail.setContentText("Please do enter Hero Name");
            fail.showAndWait();
        } else {

            if (hardModeCheckBox.isSelected()) {
                this.editor.enterDungeon(this.editor.haveHero(createHeroField.getText(), "hc"));
            } else {
                this.editor.enterDungeon(this.editor.haveHero(createHeroField.getText(), "normal"));
            }

            StageManager.showDungeonScreen();

            // it should become the current hero?!
        }
    }

    private void initHeroListSubController() {
        this.heroListVbox.getChildren().clear();
        for (Hero hero : editor.getHeroes()
        ) {
            //Parent heroSubListView = null;
            // ToDo schow actual coins in hero list
            try {
                Parent heroSubListView = FXMLLoader.load(StageManager.class.getResource("subview/HeroSubListView.fxml"));

                // adds another Hero to View
                this.heroListVbox.getChildren().add(heroSubListView);

                HeroSubListController heroSubListController = new HeroSubListController(hero, heroSubListView, editor);
                heroSubListController.init();

                // add subcontroller to list of controllers for removal
                heroSubListControllerList.add(heroSubListController);

            } catch (IOException e) {
                System.err.println("Failed to load Hero SubList :: initHeroListSubController");
                e.printStackTrace();
            }
        }
    }

}