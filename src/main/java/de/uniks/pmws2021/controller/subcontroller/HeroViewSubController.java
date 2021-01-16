package de.uniks.pmws2021.controller.subcontroller;

import de.uniks.pmws2021.model.Hero;
import de.uniks.pmws2021.RPGEditor;
import javafx.scene.Parent;


public class HeroViewSubController {

    private RPGEditor editor;
    private Parent view;
    private Hero model;

    public HeroViewSubController(Hero model, Parent view, RPGEditor editor) {
        this.model = model;
        this.view = view;
        this.editor = editor;
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