package de.uniks.pmws2021.controller;


import de.uniks.pmws2021.model.*;
import org.junit.Assert;
import org.junit.Test;

public class EvaluateFightTest {

    @Test
    public void evaluateFightEnemyDies() {

        TemporaryGameController gc = new TemporaryGameController();

        //create stats
        HeroStat attackStats = new AttackStat();
        HeroStat defenceStats = new DefenseStat();
        attackStats.setLevel(1).setValue(20).setCost(5);
        defenceStats.setLevel(2).setValue(20).setCost(10);

        //create Hero with stats and coins and so ...
        Hero myHero = new Hero();
        myHero.setLp(100);
        myHero.setCoins(100);
        myHero.withStats(attackStats, defenceStats);

        //create an enemy
        Enemy myEnemy = new Enemy();
        myEnemy.setCoins(10);
        myEnemy.setDef(10).setAtk(30).setLp(50).setName("Kotzilla").setAttacking(myHero).setStance("attack");

        //call method
        gc.evaluateFight(myEnemy, myHero);

        //LP of enemy should be zero
        Assert.assertEquals(0, myEnemy.getLp());
        Assert.assertTrue(myHero.getCoins() > 100);
        //...or with concrete value
        Assert.assertEquals(110, myHero.getCoins());

    }

    @Test
    public void evaluateFightEnemySurvives() {

        TemporaryGameController gc = new TemporaryGameController();

        //create stats
        HeroStat attackStats = new AttackStat();
        HeroStat defenceStats = new DefenseStat();
        attackStats.setLevel(1).setValue(20).setCost(5);
        defenceStats.setLevel(2).setValue(20).setCost(10);

        //create Hero with stats and coins and so ...
        Hero myHero = new Hero();
        myHero.setLp(100);
        myHero.setCoins(100);
        myHero.withStats(attackStats, defenceStats);

        //create an enemy
        Enemy myEnemy = new Enemy();
        myEnemy.setCoins(5);
        myEnemy.setDef(10).setAtk(30).setLp(50).setName("Kotzilla").setAttacking(myHero).setStance("defense");

        //call method
        gc.evaluateFight(myEnemy, myHero);

        //Asserts
        //this one will fail without implementing method
        Assert.assertTrue((myHero.getLp() == 0 || myHero.getLp() >= 0) && myEnemy.getLp() < 50 && myEnemy.getLp() > 0);
        //further assertions - will work after implementing feature
        Assert.assertEquals(100, myHero.getCoins());
        Assert.assertEquals(5, myEnemy.getCoins());
        Assert.assertTrue(myEnemy.getStance() == "attack" || myEnemy.getStance() == "defend");

    }

    @Test
    public void evaluateFightHeroNormalModeHeal() {

        TemporaryGameController gc = new TemporaryGameController();

        //create stats
        HeroStat attackStats = new AttackStat();
        HeroStat defenceStats = new DefenseStat();
        attackStats.setLevel(1).setValue(20).setCost(5);
        defenceStats.setLevel(2).setValue(20).setCost(10);

        //create Hero with stats and coins and so ...
        Hero myHero = new Hero();
        myHero.setLp(100);
        myHero.setCoins(100);
        myHero.withStats(attackStats, defenceStats);

        //create an enemy
        Enemy myEnemy = new Enemy();
        myEnemy.setDef(10).setAtk(30).setLp(50).setName("Kotzilla").setAttacking(myHero).setStance("defense");

        //call method
        gc.evaluateFight(myEnemy, myHero);

        //check if mode normal and check if lifepoints set to max after defeating enemy
        //also check if enemy is dead: lp == 0!
        //check if coins have increased
        Assert.assertTrue(myHero.getLp() == 100 && myEnemy.getLp() == 0 && myHero.getCoins() > 100);

    }

    @Test
    public void evaluateFightHeroHardModeHeal() {

        TemporaryGameController gc = new TemporaryGameController();

        //create stats
        HeroStat attackStats = new AttackStat();
        HeroStat defenceStats = new DefenseStat();
        attackStats.setLevel(1).setValue(20).setCost(5);
        defenceStats.setLevel(2).setValue(20).setCost(10);

        //create Hero with stats and coins and so ...
        Hero myHero = new Hero();
        myHero.setLp(100);
        myHero.setCoins(100);
        myHero.withStats(attackStats, defenceStats);

        //create enemies
        Enemy myEnemy = new Enemy();
        Enemy myEnemy1 = new Enemy();
        Enemy myEnemy2 = new Enemy();
        myEnemy.setDef(10).setAtk(30).setLp(50).setName("Kotzilla").setAttacking(myHero).setStance("defense");
        myEnemy1.setDef(0).setAtk(10).setLp(30).setName("Chicken").setAttacking(myHero).setStance("attacking");
        myEnemy2.setDef(100).setAtk(1).setLp(30).setName("The Wall").setAttacking(myHero).setStance("defense");
        myEnemy.setNext(myEnemy1);
        myEnemy1.setNext(myEnemy2);

        //create dungeon
        Dungeon dungeon = new Dungeon();
        dungeon.withEnemy(myEnemy, myEnemy1, myEnemy2);
        dungeon.setHero(myHero);

        //call method
        gc.evaluateFight(myEnemy, myHero);

        //check if enemy is dead && check if heal to max && ...
        //... check if coins have increased and no further enemy to attack is left
        Assert.assertTrue(myHero.getLp() == 100 && myEnemy.getLp() == 0 && myHero.getAttacking() == null && myHero.getCoins() > 100);
        //other check with concrete coin values possible but not yet implemented

        //double check if there are no other enemies left at all
        Assert.assertEquals(null, dungeon.getEnemy());

    }

    @Test
    public void evaluateFightHeroNull() {

        TemporaryGameController gc = new TemporaryGameController();

        //create Hero with stats and coins and so ...
        Hero myHero = new Hero();

        //create an enemy
        Enemy myEnemy = new Enemy();

        //call method with unknown Stance
        try {
            gc.evaluateFight(myEnemy, null);
            Assert.fail();
        } catch (NullPointerException e) {
            //catch NullPointerException
        }

    }

    @Test
    public void evaluateFightEnemyNull() {

        TemporaryGameController gc = new TemporaryGameController();

        //create Hero with stats and coins and so ...
        Hero myHero = new Hero();

        //create an enemy
        Enemy myEnemy = new Enemy();

        //call method with unknown Stance
        try {
            gc.evaluateFight(null, myHero);
            Assert.fail();
        } catch (NullPointerException e) {
            //catch NullPointerException
        }

    }

}
