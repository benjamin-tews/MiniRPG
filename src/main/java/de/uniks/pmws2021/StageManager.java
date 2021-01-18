package de.uniks.pmws2021;

import de.uniks.pmws2021.controller.DungeonScreenController;
import de.uniks.pmws2021.controller.HeroScreenController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class StageManager extends Application {

    private static Stage stage;
    private static DungeonScreenController dungeonCtrl;
    private static HeroScreenController heroCtrl;
    private static RPGEditor rpgEditor;

    public static void showHeroScreen() {
        cleanup();
        // show hero screen
        // load view
        try {
            Parent root = FXMLLoader.load(StageManager.class.getResource("HeroScreen.fxml"));
            Scene scene = new Scene(root);

            // editor
            rpgEditor = new RPGEditor();

            // init controller
            heroCtrl = new HeroScreenController(root, rpgEditor);
            heroCtrl.init();

            // display
            stage.setTitle("MiniRPG - Main Menu");
            stage.setScene(scene);
            stage.centerOnScreen();

        } catch (Exception e) {
            System.err.println("Failed to load Hero Screen :: showHeroScreen");
            e.printStackTrace();
        }
    }

    public static void showDungeonScreen() {
        cleanup();


        // show dungeon screen
        // load view
        try {
            Parent root = FXMLLoader.load(StageManager.class.getResource("DungeonScreen.fxml"));
            Scene scene = new Scene(root);

            // init controller
            stage.setTitle("MiniRPG - Ingame");
            dungeonCtrl = new DungeonScreenController(root, rpgEditor);
            dungeonCtrl.init();

            // display
            stage.setScene(scene);
            stage.centerOnScreen();

        } catch (Exception e) {
            System.err.println("Failed to load Hero Screen :: showHeroScreen");
            e.printStackTrace();
        }
    }

    private static void cleanup() {
        // call cascading stop
        if (dungeonCtrl != null) {
            dungeonCtrl.stop();
            dungeonCtrl = null;
        }
        if (heroCtrl != null) {
            heroCtrl.stop();
            heroCtrl = null;
        }
    }

    @Override
    public void start(Stage primaryStage) {
        // start application
        stage = primaryStage;
        showHeroScreen();
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        cleanup();
    }

    public RPGEditor getModel() {
        return rpgEditor;
    }
}
