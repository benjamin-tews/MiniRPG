package de.uniks.pmws2021.controller.subcontroller;

import de.uniks.pmws2021.model.Enemy;
import de.uniks.pmws2021.RPGEditor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;


public class EnemyViewSubController {

    private RPGEditor editor;
    private Enemy model;
    private Parent view;
    private Label enemyNameLabel;
    private Label enemyLpLabel;

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
        enemyNameLabel = (Label) view.lookup("#EnemyNameLabel");
        enemyLpLabel = (Label) view.lookup("#EnemyLpLabel");

        // Add mouse actions

        // Init view with model
        enemyNameLabel.setText(model.getName());
        enemyLpLabel.setText(String.valueOf(model.getLp()) +"/" +String.valueOf(model.getLp()) );

        // ToDo: if enemy name eq .... set image to ....

    }

    public void stop() {
    }
}
