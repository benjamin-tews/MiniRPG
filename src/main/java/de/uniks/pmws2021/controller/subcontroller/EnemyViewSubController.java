package de.uniks.pmws2021.controller.subcontroller;

import de.uniks.pmws2021.model.Enemy;
import de.uniks.pmws2021.RPGEditor;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.File;


public class EnemyViewSubController {

    private RPGEditor editor;
    private Enemy model;
    private Parent view;
    private Label enemyNameLabel;
    private Label enemyLpLabel;
    private Pane enemyStancePane;

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
        File defendImage = new File("src\\main\\resources\\de\\uniks\\pmws2021\\img\\defend1.png");
        File attackImage = new File("src\\main\\resources\\de\\uniks\\pmws2021\\img\\attack1.png");
        Image defend = new Image(defendImage.toURI().toString());
        Image attack = new Image(attackImage.toURI().toString());
        ImageView attackImageView = new ImageView(attack);
        ImageView defendImageView = new ImageView(defend);
        enemyNameLabel = (Label) view.lookup("#EnemyNameLabel");
        enemyLpLabel = (Label) view.lookup("#EnemyLpLabel");
        enemyStancePane = (Pane) view.lookup("#EnemyStancePane");

        // Add mouse actions

        // Init view with model
        enemyNameLabel.setText(model.getName());
        enemyLpLabel.setText(String.valueOf(model.getLp()) +"/" +String.valueOf(model.getLp()) );
        if (model.getStance().equals("attack")) {
            enemyStancePane.getChildren().add(attackImageView);
        } else if (model.getStance().equals("defend")) {
            enemyStancePane.getChildren().add(defendImageView);
        }

        // ToDo: if enemy name eq .... set image to ....

    }

    public void stop() {
    }
}
