package de.uniks.pmws2021.controller;

import de.uniks.pmws2021.model.*;
import org.fulib.FulibTools;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.fail;

public class HeroStatUpdateTest {

    @Test
    public void testHeroStatUpdateNormalBehaviour() {

        TemporaryGameController gc = new TemporaryGameController();

        //create stats
        HeroStat attackStats = new AttackStat();
        HeroStat defenceStats = new DefenseStat();
        attackStats.setLevel(1).setValue(10).setCost(5);
        defenceStats.setLevel(2).setValue(20).setCost(10);

        //create Hero with stats and coins and so ...
        Hero myHero = new Hero();
        myHero.setCoins(250);
        myHero.withStats(attackStats, defenceStats);

        //stat update costs 50 coins and increases level by one
        gc.heroStatUpdate(attackStats);

        //level number increased (and so stats, cost), number of coins decreased
        Assert.assertEquals(2, attackStats.getLevel());
        Assert.assertEquals(200, myHero.getCoins());
        Assert.assertTrue(attackStats.getCost() > 5 && attackStats.getValue() > 10);
    }

    @Test
    public void testHeroStatUpdateNotEnoughCoins() {

        TemporaryGameController gc = new TemporaryGameController();

        //create stats
        HeroStat attackStats = new AttackStat();
        HeroStat defenceStats = new DefenseStat();
        attackStats.setLevel(1).setValue(10).setCost(5);
        defenceStats.setLevel(2).setValue(20).setCost(10);

        //create Hero with stats and coins and so ...
        Hero myHero = new Hero();
        myHero.setCoins(25);
        myHero.withStats(attackStats, defenceStats);

        //call update method
        gc.heroStatUpdate(defenceStats);

        //values should be unchanged: no level upgrade, same amount of coins in it's wallet
        Assert.assertEquals(2, defenceStats.getLevel());
        Assert.assertEquals(25, myHero.getCoins());

    }

    @Test
    public void testHeroStatUpdateNullStat() {

        TemporaryGameController gc = new TemporaryGameController();

        //create stats
        HeroStat attackStats = new AttackStat();
        HeroStat defenceStats = new DefenseStat();
        attackStats.setLevel(1).setValue(10).setCost(5);
        defenceStats.setLevel(2).setValue(20).setCost(10);

        //create Hero with stats and coins and so ...
        Hero myHero = new Hero();
        myHero.setCoins(100);
        myHero.withStats(attackStats, defenceStats);

        //call try-catch update method with null value or hero is dead
        try {
            gc.heroStatUpdate(null);
            Assert.fail();
        } catch (NullPointerException e) {
            //catch Nullpointer
        }

    }

    @Test
    public void testHeroStatUpdateNullHero() {

        TemporaryGameController gc = new TemporaryGameController();

        //create stats
        HeroStat attackStats = new AttackStat();
        HeroStat defenceStats = new DefenseStat();
        attackStats.setLevel(1).setValue(10).setCost(5);
        defenceStats.setLevel(2).setValue(20).setCost(10);

        //create Hero with stats and coins and so ...
        Hero myHero = new Hero();
        myHero.setCoins(100);
        myHero.withStats(defenceStats);

        //call try-catch update method with stats aren't linked to any hero or hero is dead
        try {
            gc.heroStatUpdate(attackStats);
            Assert.fail();
        } catch (NullPointerException e) {
            //catch Nullpointer Exception
        }

    }

    @Test
    public void testHeroStatUpdateNegativePrice() {

        TemporaryGameController gc = new TemporaryGameController();

        //create stats
        HeroStat attackStats = new AttackStat();
        HeroStat defenceStats = new DefenseStat();
        attackStats.setLevel(1).setValue(10).setCost(5);
        defenceStats.setLevel(2).setValue(20).setCost(10);

        //create Hero with stats and coins and so ...
        Hero myHero = new Hero();
        myHero.setCoins(100);
        myHero.withStats(attackStats, defenceStats);

        //call try-catch update method with exception of negative amount of coins at the end
        try {
            gc.heroStatUpdate(attackStats);
            Assert.fail();
        } catch (RuntimeException e) {
            //catch Runtime Exception
        }

    }

}
