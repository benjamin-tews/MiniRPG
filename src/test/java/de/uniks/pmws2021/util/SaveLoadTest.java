package de.uniks.pmws2021.util;

import de.uniks.pmws2021.RPGEditor;
import de.uniks.pmws2021.model.AttackStat;
import de.uniks.pmws2021.model.DefenseStat;
import de.uniks.pmws2021.model.Hero;
import org.junit.Assert;
import org.junit.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class SaveLoadTest {

    private static final Path HERO_FOLDER = Path.of("database");
    private static final Path HERO_FILE = HERO_FOLDER.resolve("myHeroes.yaml");

    @Test
    public void saveLoadTest() {

        // delete file if exists
        try {
            Files.deleteIfExists(HERO_FILE);
        } catch (IOException e) {
            e.printStackTrace();
        }

        RPGEditor editor = new RPGEditor();

        // create two test heroes
        AttackStat attackStat = new AttackStat();
        DefenseStat defenseStat = new DefenseStat();
        attackStat.setLevel(11).setValue(80).setCost(20);
        defenseStat.setLevel(1).setValue(8).setCost(5);

        Hero hero = editor.haveHero("Batman", "hc");
        hero.withStats(attackStat,defenseStat);
        hero.setLp(100);
        hero.setName("Batman");

        Hero hero1 = editor.haveHero("Robin", "normal");
        hero1.withStats(defenseStat);
        hero1.setLp(50).setCoins(200);

        // save heroes
        ResourceManager.saveHero(hero);
        ResourceManager.saveHero(hero1);

        // load heroes
        List<Hero> heroList = ResourceManager.loadAllHeroes();
        Hero loadedHero = ResourceManager.loadAllHeroes().get(0);
        Hero loadedHero1 = ResourceManager.loadAllHeroes().get(1);

        // Asserts
        Assert.assertEquals(2, heroList.stream().count());
        Assert.assertEquals(100, loadedHero.getLp());
        Assert.assertEquals(50, loadedHero1.getLp());
        Assert.assertEquals(11, loadedHero.getStats().get(0).getLevel());
        Assert.assertEquals(8, loadedHero1.getStats().get(0).getValue());
        Assert.assertEquals(80, loadedHero.getStats().get(0).getValue());

    }
}
