package de.uniks.pmws2021.controller.subcontroller;

import de.uniks.pmws2021.model.HeroStat;
import de.uniks.pmws2021.RPGEditor;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


public class HeroStatViewSubController {

    private PropertyChangeListener valueListener = this::onValueChange;
    private PropertyChangeListener levelListener = this::onLevelChange;
    private PropertyChangeListener costListener = this::onLevelChange;

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

        // PCL
        this.model.addPropertyChangeListener(HeroStat.PROPERTY_VALUE, this.levelListener);
        this.model.addPropertyChangeListener(HeroStat.PROPERTY_VALUE, this.valueListener);
        this.model.addPropertyChangeListener(HeroStat.PROPERTY_VALUE, this.costListener);

    }

    private void onLevelChange(PropertyChangeEvent event) {
        levelLabel.setText(String.valueOf(this.model.getLevel()));
    }

    private void onValueChange(PropertyChangeEvent event) {
        valueLabel.setText(String.valueOf(this.model.getValue()));
    }

    private void onCostChange(PropertyChangeEvent event) {
        costLabel.setText(String.valueOf(this.model.getCost()));
    }

    public void stop() {
        // remove actions
        this.statUpgradeButton.setOnAction(null);
        // remove PCL
        this.model.removePropertyChangeListener(HeroStat.PROPERTY_VALUE, this.levelListener);
        this.model.removePropertyChangeListener(HeroStat.PROPERTY_VALUE, this.valueListener);
        this.model.removePropertyChangeListener(HeroStat.PROPERTY_VALUE, this.costListener);
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
            //StageManager.showDungeonScreen();
        }
    }

}
