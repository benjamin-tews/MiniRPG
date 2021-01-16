package de.uniks.pmws2021.controller.subcontroller;

import de.uniks.pmws2021.model.HeroStat;
import de.uniks.pmws2021.RPGEditor;
import javafx.scene.Parent;

public class HeroStatViewSubController {

    private RPGEditor editor;
    private Parent view;
    private HeroStat model;

    public HeroStatViewSubController(HeroStat model, Parent view, RPGEditor editor) {
        this.editor = editor;
        this.view = view;
        this.model = model;
    }

    // ===========================================================================================
    // Controller
    // ===========================================================================================

    public void init() {
        // Load all view references
        // Add mouse actions
        // Init view with model
    }

    public void stop() {
    }

   // ===========================================================================================
   // Button Action Methods
   // ===========================================================================================
}
