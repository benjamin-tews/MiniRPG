package de.uniks.pmws2021.controller.test;

import de.uniks.pmws2021.controller.TemporaryGameController;
import de.uniks.pmws2021.model.Dungeon;
import de.uniks.pmws2021.model.Hero;
import org.fulib.FulibTools;
import org.junit.Test;

public class TemporaryGameControllerTest {

    @Test
    public void enterDungeonTest() {

        TemporaryGameController gc = new TemporaryGameController();
        Hero hero = new Hero();
        hero.setName("Sir Slayalot").setLp(100).setCoins(30);
        //hero.setAttacking("15").setAttacking("20").setMode("normal");

        FulibTools.objectDiagrams().dumpSVG("diagrams/end.svg", gc.enterDungeon(hero) );

    }

}
