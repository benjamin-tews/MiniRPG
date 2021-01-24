package de.uniks.pmws2021.controller.subcontroller;

import de.uniks.pmws2021.model.Enemy;
import de.uniks.pmws2021.RPGEditor;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;


public class EnemyViewSubController {

    private PropertyChangeListener onLpChanged= this::onLpChanged;
    private PropertyChangeListener onStanceChanged= this::onStanceChanged;

    private RPGEditor editor;
    private Enemy model;
    private Parent view;
    private Label enemyNameLabel;
    private Label enemyLpLabel;
    private Pane enemyStancePane;
    private int maxLife = 0;

    File defendImage = new File("src\\main\\resources\\de\\uniks\\pmws2021\\img\\defend1.png");
    File attackImage = new File("src\\main\\resources\\de\\uniks\\pmws2021\\img\\attack1.png");
    Image defend = new Image(defendImage.toURI().toString());
    Image attack = new Image(attackImage.toURI().toString());
    ImageView attackImageView = new ImageView(attack);
    ImageView defendImageView = new ImageView(defend);


    // ===========================================================================================
    // Controller
    // ===========================================================================================

    public EnemyViewSubController(Enemy model, Parent view, RPGEditor editor) {
        this.model = model;
        this.view = view;
        this.editor = editor;
        this.maxLife = model.getLp();
    }

    public void init() {
        // Load all view references

        enemyNameLabel = (Label) view.lookup("#EnemyNameLabel");
        enemyLpLabel = (Label) view.lookup("#EnemyLpLabel");
        enemyStancePane = (Pane) view.lookup("#EnemyStancePane");

        // Add mouse actions

        // Init view with model
        enemyNameLabel.setText(model.getName());

        enemyLpLabel.setText(String.valueOf(model.getLp()) + "/" + String.valueOf(maxLife));
        if (model.getStance().equals("attack")) {
            enemyStancePane.getChildren().add(attackImageView);
        } else if (model.getStance().equals("defend")) {
            enemyStancePane.getChildren().add(defendImageView);
        }

        // PCL

        this.model.addPropertyChangeListener(Enemy.PROPERTY_LP, onLpChanged);
        this.model.addPropertyChangeListener(Enemy.PROPERTY_STANCE, onStanceChanged);

        // ToDo: if enemy name eq .... set image to ....

    }

    private void onLpChanged(PropertyChangeEvent event) {
        enemyLpLabel.setText(String.valueOf(model.getLp()) + "/" + String.valueOf(maxLife));
    }

    private void onStanceChanged(PropertyChangeEvent event) {
        if (model.getStance().equals("attack")) {
            enemyStancePane.getChildren().set(0,attackImageView);
        } else if (model.getStance().equals("defend")) {
            enemyStancePane.getChildren().set(0,defendImageView);
        }
    }

    public void stop() {
        // remove PCL
        this.model.removePropertyChangeListener(Enemy.PROPERTY_LP, onLpChanged);
        this.model.removePropertyChangeListener(Enemy.PROPERTY_STANCE, onStanceChanged);
    }

}
