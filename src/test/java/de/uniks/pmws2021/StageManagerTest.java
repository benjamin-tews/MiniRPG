package de.uniks.pmws2021;

import de.uniks.pmws2021.model.Hero;
import javafx.scene.Node;
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

        // HERO SCREEN
        TextField createHeroField = lookup("#CreateHeroField").query();
        Assert.assertEquals("Enter Hero Name", createHeroField.getPromptText());
        clickOn("#CreateHeroField").write("Batman");
        Assert.assertEquals("Batman", createHeroField.getText());
        CheckBox hardModeCheckbox = lookup("#HardModeCheckBox").query();
        clickOn(hardModeCheckbox);
        Assert.assertTrue(hardModeCheckbox.isSelected());

        // DUNGEON SCREEN
        clickOn("#CreateStartButton");
        Label dungeonNameLabel = lookup("#DungeonNameLabel").query();
        Assert.assertEquals("The Fire Pits", dungeonNameLabel.getText());
        Assert.assertEquals("MiniRPG - Ingame", stage.getTitle());
        Label enemyNameLabel = lookup("#EnemyNameLabel").query();
        Assert.assertEquals("Shinigami", enemyNameLabel.getText());
        // ToDo: make this one more dynamic
        Assert.assertEquals("attack", app.getModel().getDungeon().getEnemy().get(0).getStance());
        Assert.assertEquals(1, app.getModel().getDungeon().getEnemy().stream().count());
        Label enemyLpLabel = lookup("#EnemyLpLabel").query();
        Assert.assertEquals("30/30", enemyLpLabel.getText());
        Label heroCoinsLabel = lookup("#HeroCoinsLabel").query();
        Assert.assertEquals("50", heroCoinsLabel.getText());
        Button attackButton = lookup("#AttackButton").query();
        clickOn(attackButton);
        clickOn(attackButton);

        // HERO SCREEN
        Label heroNameLabel = lookup("#HeroNameLabel").query();
        Assert.assertEquals("Batman", heroNameLabel.getText());
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
        // check if hero is in hero List
        listHero = app.getModel().getFromHeroes("Batman");
        Assert.assertNotNull(listHero);

        TextField newCreateHeroField = lookup("#CreateHeroField").query();
        Assert.assertEquals("", newCreateHeroField.getText());
        Assert.assertEquals("Enter Hero Name", createHeroField.getPromptText());

    }

}