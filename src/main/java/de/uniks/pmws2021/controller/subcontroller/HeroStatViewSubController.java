package de.uniks.pmws2021.controller.subcontroller;

import de.uniks.pmws2021.StageManager;
import de.uniks.pmws2021.controller.DungeonScreenController;
import de.uniks.pmws2021.model.HeroStat;
import de.uniks.pmws2021.RPGEditor;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


public class HeroStatViewSubController {

    private RPGEditor editor;
    private Parent view;
    private HeroStat model;
    private Label levelLabel;
    private Label valueLabel;
    private Label costLabel;
    private Button statUpgradeButton;

    // ===========================================================================================
    // Controller
    // ===========================================================================================

    public HeroStatViewSubController(HeroStat model, Parent view, RPGEditor editor) {
        this.model = model;
        this.view = view;
        this.editor = editor;
    }

    public void init() {
        // Load all view references
        levelLabel = (Label) view.lookup("#LevelLabel");
        valueLabel = (Label) view.lookup("#ValueLabel");
        costLabel = (Label) view.lookup("#CostLabel");
        statUpgradeButton = (Button) view.lookup("#StatUpgradeButton");

        // Add mouse actions
        statUpgradeButton.setOnAction(this::statUpgradeButtonAction);

        // Init view with model
        levelLabel.setText(String.valueOf(this.model.getLevel()));
        valueLabel.setText(String.valueOf(this.model.getValue()));
        costLabel.setText(String.valueOf(this.model.getCost()));

    }

    public void stop() {
        statUpgradeButton.setOnAction(null);
    }


    // ===========================================================================================
    // Button Action Methods
    // ===========================================================================================

    private void statUpgradeButtonAction(ActionEvent actionEvent) {
        if (this.model.getCost() > this.editor.getDungeon().getHero().getCoins()) {
            Alert fail = new Alert(Alert.AlertType.WARNING);
            fail.setHeaderText("Empty Wallet");
            fail.setContentText("Please earn more money!");
            fail.showAndWait();
        } else {
            this.editor.heroStatUpdate(this.model);
            StageManager.showDungeonScreen();
            this.init();
        }
    }

}
