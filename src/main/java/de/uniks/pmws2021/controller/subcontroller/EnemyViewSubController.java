package de.uniks.pmws2021.controller.subcontroller;

import de.uniks.pmws2021.model.Enemy;
import de.uniks.pmws2021.RPGEditor;
import javafx.scene.Parent;


public class EnemyViewSubController {

    private RPGEditor editor;
    private Enemy model;
    private Parent view;

    public EnemyViewSubController(Enemy model, Parent view, RPGEditor editor) {
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
}
