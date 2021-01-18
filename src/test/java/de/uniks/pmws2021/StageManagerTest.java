package de.uniks.pmws2021;

import de.uniks.pmws2021.model.Hero;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.Assert;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;


public class StageManagerTest extends ApplicationTest {

    private Stage stage;
    private StageManager app;

    @Override
    public void start(Stage stage) {
        // start application
        this.stage = stage;
        app = new StageManager();
        app.start(stage);
        this.stage.centerOnScreen();
    }

    @Test
    public void changeViewTest() {

        // assert the initial view
        Button createStartButton = lookup("#CreateStartButton").query();
        Assert.assertEquals("Create & Enter Dungeon", createStartButton.getText());
        Assert.assertEquals("MiniRPG - Main Menu", stage.getTitle());

        // write Hero into Field
        TextField createHeroField = lookup("#CreateHeroField").query();
        Assert.assertEquals("Enter Hero Name", createHeroField.getPromptText());
        clickOn("#CreateHeroField").write("Batman");
        Assert.assertEquals("Batman", createHeroField.getText());
        CheckBox hardModeCheckbox = lookup("#HardModeCheckBox").query();
        clickOn(hardModeCheckbox);
        Assert.assertTrue(hardModeCheckbox.isSelected());

        // click button and change view to Ingame DungeonScreen
        clickOn("#CreateStartButton");
        Label dungeonNameLabel = lookup("#DungeonNameLabel").query();
        Assert.assertEquals("The Fire Pits", dungeonNameLabel.getText());
        Assert.assertEquals("MiniRPG - Ingame", stage.getTitle());

        // in HeroScreen: check hero name
        Label heroNameLabel = lookup("#HeroNameLabel").query();
        Assert.assertEquals("Batman [hc]", heroNameLabel.getText());
        // check ExitButton
        Button exitButton = lookup("#ExitButton").query();
        Assert.assertEquals("EXIT", exitButton.getText());

        // check if hero is in hero List
        Hero listHero = app.getModel().getFromHeroes("Batman");
        Assert.assertNotNull(listHero);

        // click ExitButton and change view to Menu HeroScreen
        clickOn("#ExitButton");
        Assert.assertEquals("MiniRPG - Main Menu", stage.getTitle());
        // check if CreateHeroField is empty
        TextField newCreateHeroField = lookup("#CreateHeroField").query();
        Assert.assertEquals("", newCreateHeroField.getText());
        Assert.assertEquals("Enter Hero Name", createHeroField.getPromptText());

    }

}