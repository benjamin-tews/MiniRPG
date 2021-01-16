package de.uniks.pmws2021.controller.subcontroller;

import de.uniks.pmws2021.model.Hero;
import de.uniks.pmws2021.RPGEditor;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;


public class HeroViewSubController {

    private RPGEditor editor;
    private Parent view;
    private Hero model;
    private Label heroNameLabel;
    private Label heroLpLabel;

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
        heroNameLabel = (Label) view.lookup("#HeroNameLabel");
        heroLpLabel = (Label) view.lookup("#HeroLpLabel");

        // Add mouse actions
        heroNameLabel.setOnMouseClicked(this::heroNameLabelClicked);

        // Init view with model

    }

    private void heroNameLabelClicked(MouseEvent mouseEvent) {
        heroNameLabel.setText(model.getName());
    }

    public void stop() {
    }

   // ===========================================================================================
   // Button Action Methods
   // ===========================================================================================
}
