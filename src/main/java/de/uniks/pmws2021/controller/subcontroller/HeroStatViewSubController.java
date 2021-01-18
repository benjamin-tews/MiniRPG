package de.uniks.pmws2021.controller.subcontroller;

import de.uniks.pmws2021.model.HeroStat;
import de.uniks.pmws2021.RPGEditor;
import javafx.scene.Parent;
import javafx.scene.control.Label;

import java.util.List;


public class HeroStatViewSubController {

    private RPGEditor editor;
    private Parent view;
    private List<HeroStat> model;
    private Label attackLevelLabel;

    public HeroStatViewSubController(List<HeroStat> model, Parent view, RPGEditor editor) {
        this.model = model;
        this.view = view;
        this.editor = editor;
    }

    // ===========================================================================================
    // Controller
    // ===========================================================================================

    public void init() {
        // Load all view references
        attackLevelLabel = (Label) view.lookup("#AttackLevelLabel");

        // Add mouse actions


        // Init view with model
        //attackLevelLabel.setText(this.model.toString());

    }

    public void stop() {
    }

   // ===========================================================================================
   // Button Action Methods
   // ===========================================================================================


}
