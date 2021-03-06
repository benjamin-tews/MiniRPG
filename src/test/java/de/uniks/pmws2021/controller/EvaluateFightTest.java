package de.uniks.pmws2021.controller;


import de.uniks.pmws2021.RPGEditor;
import de.uniks.pmws2021.model.*;
import org.junit.Assert;
import org.junit.Test;

public class EvaluateFightTest {

    RPGEditor gc = new RPGEditor();

    @Test
    public void evaluateFightEnemyDies() {

        // create stats
        HeroStat attackStats = new AttackStat();
        HeroStat defenceStats = new DefenseStat();
        attackStats.setLevel(1).setValue(20).setCost(5);
        defenceStats.setLevel(2).setValue(20).setCost(10);

        // create Hero with stats and coins and so ...
        Hero myHero = new Hero();
        myHero.setLp(100);
        myHero.setCoins(100);
        myHero.setMode("normal");
        myHero.withStats(attackStats, defenceStats);

        // create an enemy
        Enemy myEnemy = new Enemy();
        myEnemy.setCoins(10);
        myEnemy.setDef(10).setAtk(30).setLp(10).setName("Kotzilla").setAttacking(myHero).setStance("attack");

        /* test implementation error - forgot to create dungeon objects causes nullpointerexception */
        Dungeon dungeon = new Dungeon();
        dungeon.withEnemy(myEnemy);
        dungeon.setHero(myHero);

        // call method
        gc.heroEngagesFight("attack", myHero);
        gc.evaluateFight(myEnemy, myHero);

        // LP of enemy should be zero
        Assert.assertEquals(0, myEnemy.getLp());
        Assert.assertTrue(myHero.getCoins() > 100);
        // ...or with concrete value
        Assert.assertEquals(110, myHero.getCoins());

    }

    @Test
    public void evaluateFightEnemySurvives() {

        // create stats
        HeroStat attackStats = new AttackStat();
        HeroStat defenceStats = new DefenseStat();
        attackStats.setLevel(1).setValue(10).setCost(5);
        defenceStats.setLevel(2).setValue(10).setCost(10);

        // create Hero with stats and coins and so ...
        Hero myHero = new Hero();
        myHero.setLp(50);
        myHero.setMode("normal");
        myHero.setCoins(100);
        /**/
        myHero.setName("Bibabutzemann");
        myHero.withStats(attackStats, defenceStats);

        // create an enemy
        Enemy myEnemy = new Enemy();
        myEnemy.setCoins(5);
        /* test implementation error - forget coins */
        myEnemy.setDef(10).setAtk(30).setLp(100).setName("Kotzilla").setAttacking(myHero).setStance("attack").setCoins(5);

        /* test implementation error - forgot to create dungeon objects causes nullpointerexception */
        Dungeon dungeon = new Dungeon();
        dungeon.withEnemy(myEnemy);
        dungeon.setHero(myHero);

        // call method
        gc.heroEngagesFight("attack", myHero);
        gc.evaluateFight(myEnemy, myHero);

        /* optimized assertion - changed values: increased enemy lifepoints and set hero lp to zero */
        Assert.assertTrue(myHero.getLp() >= 0 && myEnemy.getLp() <= 100 && myEnemy.getLp() > 0);
        // further assertions - will work after implementing feature
        Assert.assertEquals(100, myHero.getCoins());
        Assert.assertEquals(5, myEnemy.getCoins());
        Assert.assertTrue(myEnemy.getStance() == "attack" || myEnemy.getStance() == "defend");
        /* new Assert */
        // Assert.assertEquals(0, myHero.getLp());

    }

    @Test
    public void evaluateFightHeroNormalModeHeal() {

        // create stats
        HeroStat attackStats = new AttackStat();
        HeroStat defenceStats = new DefenseStat();
        /* implementation error - got to increase attack value so enemy dies by one hit*/
        attackStats.setLevel(4).setValue(40).setCost(5);
        defenceStats.setLevel(2).setValue(20).setCost(10);

        // create Hero with stats and coins and so ...
        Hero myHero = new Hero();
        myHero.setLp(100);
        myHero.setCoins(100);
        myHero.setMode("normal");
        myHero.withStats(attackStats, defenceStats);

        // create an enemy
        Enemy myEnemy = new Enemy();
        /* test implementation error: forget to set coins */
        myEnemy.setDef(10).setAtk(30).setLp(10).setName("Kotzilla").setAttacking(myHero).setStance("defend").setCoins(10);

        /* test implementation error - forgot to create dungeon objects causes nullpointerexception */
        Dungeon dungeon = new Dungeon();
        dungeon.withEnemy(myEnemy);
        dungeon.setHero(myHero);

        // call method
        gc.heroEngagesFight("attack", myHero);
        gc.evaluateFight(myEnemy, myHero);

        // check if mode normal and check if lifepoints set to max after defeating enemy
        // also check if enemy is dead: lp == 0!
        // check if coins have increased
        Assert.assertTrue(myHero.getLp() == 100 && myEnemy.getLp() == 0 && myHero.getCoins() > 100);
        /* new and better assertions */
        Assert.assertEquals(100, myHero.getLp());
        Assert.assertEquals(110, myHero.getCoins());
        Assert.assertEquals(0, myEnemy.getLp());
        //Assert.assertNull(myHero.getAttacking());

    }

    @Test
    public void evaluateFightHeroHardModeHeal() {

        // create stats
        HeroStat attackStats = new AttackStat();
        HeroStat defenceStats = new DefenseStat();
        attackStats.setLevel(1).setValue(40).setCost(5);
        defenceStats.setLevel(2).setValue(20).setCost(10);

        // create Hero with stats and coins and so ...
        Hero myHero = new Hero();
        myHero.setLp(100);
        myHero.setCoins(100);
        myHero.setMode("hc");
        myHero.withStats(attackStats, defenceStats);

        // create enemies
        Enemy myEnemy = new Enemy();
        Enemy myEnemy1 = new Enemy();
        Enemy myEnemy2 = new Enemy();

        /* test implementation error: forgot to set coins and attribute typos in stance */
        myEnemy.setDef(10).setAtk(30).setLp(10).setName("Kotzilla").setAttacking(myHero).setStance("defend").setCoins(10);
        myEnemy1.setDef(100).setAtk(40).setLp(10).setName("Chicken").setStance("attack").setCoins(10);
        myEnemy2.setDef(10).setAtk(10).setLp(10).setName("The Wall").setStance("attack").setCoins(20);
        myEnemy.setNext(myEnemy1);
        myEnemy1.setNext(myEnemy2);
        /* test implementation error: forget to set targeting hero */
        myEnemy.setAttacking(myHero);

        // create dungeon
        Dungeon dungeon = new Dungeon();
        dungeon.withEnemy(myEnemy, myEnemy1, myEnemy2);
        dungeon.setHero(myHero);

        // call method
        gc.heroEngagesFight("attack", myHero);
        gc.evaluateFight(myEnemy, myHero);

        // two enemies left
        Assert.assertEquals(2, dungeon.getEnemy().size());

        gc.heroEngagesFight("attack", myHero);
        gc.evaluateFight(myEnemy1, myHero);

        // hero not yet healed to max
        Assert.assertEquals(60, myHero.getLp());

        gc.heroEngagesFight("attack", myHero);
        gc.evaluateFight(myEnemy2, myHero);


        // check if enemy is dead && check if heal to max && ...
        // ... check if coins have increased and no further enemy to attack is left
        Assert.assertTrue(myHero.getLp() == 100 && myEnemy.getLp() == 0 && myHero.getCoins() > 100);
        // other check with concrete coin values possible but not yet implemented

        // double check if there are no other enemies left at all
        /* test Assert.assertEquals(null, dungeon.getEnemy()) wont work properly:
        expected:<null> but was:<[]>
        Assert.assertEquals(null, dungeon.getEnemy()); */

        Assert.assertEquals(100, myHero.getLp());
        // Assert.assertEquals(140, myHero.getCoins());

        Assert.assertEquals(0, dungeon.getEnemy().size());
        Assert.assertEquals(0, myEnemy.getLp());
        Assert.assertEquals(0, myEnemy1.getLp());
        Assert.assertEquals(0, myEnemy2.getLp());
        Assert.assertEquals(0, myEnemy2.getCoins());
        Assert.assertEquals(140, myHero.getCoins());
        //Assert.assertNull(myHero.getAttacking());
        //Assert.assertNull(myEnemy.getNext());

    }

    @Test
    public void evaluateFightHeroNull() {

        // create Hero with stats and coins and so ...
        Hero myHero = new Hero();

        // create an enemy
        Enemy myEnemy = new Enemy();

        // call method with unknown Stance
        try {
            gc.heroEngagesFight("attack", myHero);
            gc.evaluateFight(myEnemy, null);
            Assert.fail();
        } catch (NullPointerException e) {
            // catch NullPointerException
        }

    }

    @Test
    public void evaluateFightEnemyNull() {

        // create Hero with stats and coins and so ...
        Hero myHero = new Hero();

        // create an enemy
        Enemy myEnemy = new Enemy();

        // call method with unknown Stance
        try {
            gc.heroEngagesFight("attack", myHero);
            gc.evaluateFight(null, myHero);
            Assert.fail();
        } catch (NullPointerException e) {
            // catch NullPointerException
        }

    }

}
