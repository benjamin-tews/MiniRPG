package de.uniks.pmws2021.controller;

import de.uniks.pmws2021.model.*;
import org.junit.Assert;
import org.junit.Test;


public class HeroEngagesFightTest {

    @Test
    public void testHeroEngagesFightBothDefend() {

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
        myEnemy.setDef(10).setAtk(30).setLp(100).setName("Kotzilla").setAttacking(myHero).setStance("defend");

        //call method
        gc.heroEngagesFight("defend", myHero);

        //Assertion - no damage incomming and so no loss of lifepoints ... nothing changes at all.
        Assert.assertEquals(100, myEnemy.getLp());
        Assert.assertEquals(100, myHero.getLp());

    }

    @Test
    public void testHeroEngagesFightBothAttack() {

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
        myEnemy.setDef(10).setAtk(30).setLp(50).setName("Kotzilla").setAttacking(myHero).setStance("attack");

        //call method
        gc.heroEngagesFight("attack", myHero);

        //Assertion
        Assert.assertEquals(30, myEnemy.getLp());
        Assert.assertEquals(70, myHero.getLp());

    }

    @Test
    public void testHeroEngagesFightEnemyDefendHeroAttack() {

        TemporaryGameController gc = new TemporaryGameController();

        //create stats
        HeroStat attackStats = new AttackStat();
        HeroStat defenceStats = new DefenseStat();
        attackStats.setLevel(1).setValue(20).setCost(5);
        defenceStats.setLevel(2).setValue(30).setCost(10);

        //create Hero with stats and coins and so ...
        Hero myHero = new Hero();
        myHero.setLp(100);
        myHero.setCoins(100);
        myHero.withStats(attackStats, defenceStats);

        //create an enemy
        Enemy myEnemy = new Enemy();
        myEnemy.setDef(10).setAtk(10).setLp(50).setName("Kotzilla").setAttacking(myHero).setStance("defend");

        //call method
        gc.heroEngagesFight("attack", myHero);

        //Assertion lifepoints := lifepoints - (hero attacking value - enemy defense value)
        Assert.assertEquals(40, myEnemy.getLp());

    }


    @Test
    public void testHeroEngagesFightEnemyAttackHeroDefend() {

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
        myEnemy.setDef(10).setAtk(30).setLp(50).setName("Kotzilla").setAttacking(myHero).setStance("attack");

        //call method
        gc.heroEngagesFight("defend", myHero);

        //Assertion
        Assert.assertEquals(90, myHero.getLp());

    }

    @Test
    public void testHeroEngagesFightNullHero() {

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
        myEnemy.setDef(10).setAtk(30).setLp(50).setName("Kotzilla").setAttacking(myHero).setStance("attack");

        //call method
        try {
            gc.heroEngagesFight("defend", null);
            Assert.fail();
        } catch (NullPointerException e) {
            //catch NullPointerException
        }

    }

    @Test
    public void testHeroEngagesFightUnknownStance() {

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
        myEnemy.setDef(10).setAtk(30).setLp(50).setName("Kotzilla").setAttacking(myHero).setStance("defend");

        //call method with unknown Stance
        try {
            gc.heroEngagesFight("posing", myHero);
            Assert.fail();
        } catch (RuntimeException e) {
            //catch RuntimeException
        }

    }

}
