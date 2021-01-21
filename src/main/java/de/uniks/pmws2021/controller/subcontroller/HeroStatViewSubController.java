package de.uniks.pmws2021.controller.subcontroller;

import de.uniks.pmws2021.model.HeroStat;
import de.uniks.pmws2021.RPGEditor;
import javafx.scene.Parent;
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


        // Init view with model
        //attackLevelLabel.setText(String.valueOf(this.editor.getDungeon().getHero().getStats().get(0).getLevel()));
        levelLabel.setText(String.valueOf(this.model.getLevel()));
        valueLabel.setText(String.valueOf(this.model.getValue()));
        costLabel.setText(String.valueOf(this.model.getCost()));

    }

    public void stop() {
    }

    // ===========================================================================================
    // Button Action Methods
    // ===========================================================================================


}
