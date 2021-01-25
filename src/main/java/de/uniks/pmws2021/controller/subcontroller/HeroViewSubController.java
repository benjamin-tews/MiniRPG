package de.uniks.pmws2021.controller.subcontroller;

import de.uniks.pmws2021.RPGEditor;
import de.uniks.pmws2021.model.Enemy;
import de.uniks.pmws2021.model.Hero;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;


public class HeroViewSubController {

    private PropertyChangeListener onAttackingEnemyNameChanged = this::onAttackingEnemyNameChanged;
    private PropertyChangeListener onAttackingChanged = this::OnAttackingChanged;
    private PropertyChangeListener onLpChanged = this::OnLpChanged;

    private RPGEditor editor;
    private Parent view;
    private Hero model;
    private Label heroNameLabel;
    private Label heroLpLabel;
    private Label dungeonNameLabel;
    private Label enemyNameLabel;
    private Pane hardmodeImagePane;


    // ===========================================================================================
    // Controller
    // ===========================================================================================

    public HeroViewSubController(Hero model, Parent view, RPGEditor editor) {
        this.model = model;
        this.view = view;
        this.editor = editor;
    }

    public void init() {
        // Load all view references
        File myImage = new File("src\\main\\resources\\de\\uniks\\pmws2021\\img\\skull.png");
        Image skull = new Image(myImage.toURI().toString());
        ImageView imageView = new ImageView(skull);
        heroNameLabel = (Label) view.lookup("#HeroNameLabel");
        heroLpLabel = (Label) view.lookup("#HeroLpLabel");
        hardmodeImagePane = (Pane) view.lookup("#HardmodeImagePane");

        // Add mouse actions

        // Init view with model
        heroNameLabel.setText(this.model.getName());
        heroLpLabel.setText(String.valueOf(this.model.getLp()) + "/" + "100");
        if (model.getMode().equals("hc")) {
            hardmodeImagePane.getChildren().add(imageView);
        }

        // PCL
        this.model.addPropertyChangeListener(Hero.PROPERTY_LP, onLpChanged);
        this.model.addPropertyChangeListener(Hero.PROPERTY_ATTACKING, onAttackingChanged);
        this.model.getAttacking().addPropertyChangeListener(Enemy.PROPERTY_NAME, onAttackingEnemyNameChanged);

    }

    public void OnLpChanged(PropertyChangeEvent event) {
        //ToDo make "100" dynamic
        heroLpLabel.setText(String.valueOf(this.model.getLp()) + "/" + "100");
    }

    public void OnAttackingChanged(PropertyChangeEvent event) {

    }

    public void onAttackingEnemyNameChanged(PropertyChangeEvent event) {

    }

    public void stop() {
        this.model.removePropertyChangeListener(Hero.PROPERTY_LP, onLpChanged);
        this.model.removePropertyChangeListener(Hero.PROPERTY_ATTACKING, onAttackingChanged);
        this.model.getAttacking().removePropertyChangeListener(Enemy.PROPERTY_NAME, onAttackingEnemyNameChanged);
    }

    // ===========================================================================================
    // Button Action Methods
    // ===========================================================================================

}
