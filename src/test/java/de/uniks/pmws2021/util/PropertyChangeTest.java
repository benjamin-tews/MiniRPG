package de.uniks.pmws2021.util;

import de.uniks.pmws2021.StageManager;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

public class PropertyChangeTest extends ApplicationTest {
    private static Stage stage;
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
        // Add test here
    }
}
