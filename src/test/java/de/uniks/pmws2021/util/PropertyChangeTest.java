package de.uniks.pmws2021.util;

import de.uniks.pmws2021.RPGEditor;
import de.uniks.pmws2021.StageManager;
import de.uniks.pmws2021.model.Hero;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.Assert;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

public class PropertyChangeTest extends ApplicationTest {
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
    public void propertyChangeTest() {
        //  DungeonScreen öffnen
        Button createStartButton = lookup("#CreateStartButton").query();
        TextField createHeroField = lookup("#CreateHeroField").query();
        Assert.assertEquals("Enter Hero Name", createHeroField.getPromptText());
        clickOn("#CreateHeroField").write("Batman");
        Assert.assertEquals("Batman", createHeroField.getText());
        CheckBox hardModeCheckbox = lookup("#HardModeCheckBox").query();
        clickOn(hardModeCheckbox);
        Assert.assertTrue(hardModeCheckbox.isSelected());
        clickOn(createStartButton);
        Label dungeonNameLabel = lookup("#DungeonNameLabel").query();
        Assert.assertEquals("The Fire Pits", dungeonNameLabel.getText());
        Assert.assertEquals("MiniRPG - Ingame", stage.getTitle());
        Label enemyNameLabel = lookup("#EnemyNameLabel").query();
        Assert.assertEquals("Shinigami", enemyNameLabel.getText());

        Assert.assertEquals("attack", app.getModel().getDungeon().getEnemy().get(0).getStance());
        Assert.assertEquals(3, app.getModel().getDungeon().getEnemy().stream().count());
        Label enemyLpLabel = lookup("#EnemyLpLabel").query();
        Assert.assertEquals("30/30", enemyLpLabel.getText());
        Label heroCoinsLabel = lookup("#HeroCoinsLabel").query();
        Label heroValueLabel = lookup("#ValueLabel").query();
        Assert.assertEquals("50", heroCoinsLabel.getText());
        Button attackButton = lookup("#AttackButton").query();

        Platform.runLater( () -> app.getModel().getDungeon().getHero().setCoins(100) );
        WaitForAsyncUtils.waitForFxEvents();
        Assert.assertEquals("100", heroCoinsLabel.getText());

        Platform.runLater( () -> app.getModel().getDungeon().getHero().getStats().get(0).setValue(50) );
        WaitForAsyncUtils.waitForFxEvents();
        Platform.runLater( () -> app.getModel().getDungeon().getHero().getAttacking().setLp(100) );
        WaitForAsyncUtils.waitForFxEvents();
        clickOn(attackButton);
        WaitForAsyncUtils.waitForFxEvents();

        Assert.assertEquals("50/30", enemyLpLabel.getText());
        Assert.assertEquals("50", heroValueLabel.getText());

    }
}
