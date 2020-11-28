package de.uniks.pmws2021.controller;

import de.uniks.pmws2021.model.*;
import org.fulib.FulibTools;
import org.junit.Test;

public class TemporaryGameControllerTest {

    @Test
    public void enterDungeonTest() {

        TemporaryGameController gc = new TemporaryGameController();
        Hero hero = new Hero();
        HeroStat attackStats = new AttackStat();
        HeroStat defenseStats = new DefenseStat();

        hero.setName("Sir Slayalot").setLp(100).setCoins(30).setMode("normal mode");
        hero.withStats(attackStats.setLevel(2).setValue(15).setCost(5),defenseStats.setCost(5).setLevel(3).setValue(20).setCost(5));

        //1st call method ...
        gc.enterDungeon(hero);
        //... then draw diagram
        FulibTools.objectDiagrams().dumpSVG("diagrams/end.svg", hero.getDungeon() );

        /* all in one but against task
        FulibTools.objectDiagrams().dumpSVG("diagrams/end.svg", gc.enterDungeon(hero) );
         */

    }

}
