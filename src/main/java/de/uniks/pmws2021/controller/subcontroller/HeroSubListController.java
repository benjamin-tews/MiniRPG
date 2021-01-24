package de.uniks.pmws2021.controller.subcontroller;

import de.uniks.pmws2021.RPGEditor;
import de.uniks.pmws2021.model.Hero;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class HeroSubListController {

    private Hero model;
    private Parent view;
    private RPGEditor editor;
    private Label heroSubListNameLabel;
    private Label lookup;

    public HeroSubListController(Hero model, Parent view, RPGEditor editor) {
        this.model = model;
        this.view = view;
        this.editor = editor;
    }

    public void init() {
        // Load all view references
        heroSubListNameLabel = (Label) view.lookup("#HeroSubListNameLabel");

        // Add mouse actions

        // Init view with model
        heroSubListNameLabel.setText(this.model.getName());
    }

    public void stop() {
    }
}
