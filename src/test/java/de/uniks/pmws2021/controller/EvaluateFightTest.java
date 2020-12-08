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

        /* test implementation error - forgot to create dungeon objects causes nullpointerexception */
        Dungeon dungeon = new Dungeon();
        dungeon.withEnemy(myEnemy);
        dungeon.setHero(myHero);

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

        //if enemy survives - hero have to die

        TemporaryGameController gc = new TemporaryGameController();

        //create stats
        HeroStat attackStats = new AttackStat();
        HeroStat defenceStats = new DefenseStat();
        attackStats.setLevel(1).setValue(10).setCost(5);
        defenceStats.setLevel(2).setValue(10).setCost(10);

        //create Hero with stats and coins and so ...
        Hero myHero = new Hero();
        myHero.setLp(50);
        myHero.setCoins(100);
        /**/
        myHero.setName("Bibabutzemann");
        myHero.withStats(attackStats, defenceStats);

        //create an enemy
        Enemy myEnemy = new Enemy();
        myEnemy.setCoins(5);
        /* test implementation error - forget coins */
        myEnemy.setDef(10).setAtk(30).setLp(100).setName("Kotzilla").setAttacking(myHero).setStance("attack").setCoins(5);

        /* test implementation error - forgot to create dungeon objects causes nullpointerexception */
        Dungeon dungeon = new Dungeon();
        dungeon.withEnemy(myEnemy);
        dungeon.setHero(myHero);

        //call method
        gc.evaluateFight(myEnemy, myHero);

        /* optimized assertion - changed values: increased enemy lifepoints and set hero lp to zero */
        Assert.assertTrue( myHero.getLp() == 0 && myEnemy.getLp() <= 100 && myEnemy.getLp() > 0);
        //further assertions - will work after implementing feature
        Assert.assertEquals(100, myHero.getCoins());
        Assert.assertEquals(5, myEnemy.getCoins());
        Assert.assertTrue(myEnemy.getStance() == "attack" || myEnemy.getStance() == "defend");
        /* new Assert */
        Assert.assertEquals(0, myHero.getLp());

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
        /* test implementation error: forget to set coins */
        myEnemy.setDef(10).setAtk(30).setLp(50).setName("Kotzilla").setAttacking(myHero).setStance("defense").setCoins(10);

        /* test implementation error - forgot to create dungeon objects causes nullpointerexception */
        Dungeon dungeon = new Dungeon();
        dungeon.withEnemy(myEnemy);
        dungeon.setHero(myHero);

        //call method
        gc.evaluateFight(myEnemy, myHero);

        //check if mode normal and check if lifepoints set to max after defeating enemy
        //also check if enemy is dead: lp == 0!
        //check if coins have increased
        Assert.assertTrue(myHero.getLp() == 100 && myEnemy.getLp() == 0 && myHero.getCoins() > 100);
        /* new and better assertions */
        Assert.assertEquals(100, myHero.getLp());
        Assert.assertEquals(110, myHero.getCoins());
        Assert.assertEquals(0, myEnemy.getLp());
        Assert.assertNull(myHero.getAttacking());

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
        /* test implementation error: forgot to set coins */
        myEnemy.setDef(10).setAtk(30).setLp(50).setName("Kotzilla").setAttacking(myHero).setStance("defense").setCoins(10);
        myEnemy1.setDef(0).setAtk(10).setLp(30).setName("Chicken").setAttacking(myHero).setStance("attacking").setCoins(10);
        myEnemy2.setDef(100).setAtk(1).setLp(30).setName("The Wall").setAttacking(myHero).setStance("defense").setCoins(20);
        myEnemy.setNext(myEnemy1);
        myEnemy1.setNext(myEnemy2);
        /* test implementation error: forget to set targeting hero */
        myEnemy.setAttacking(myHero);

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
        /* test Assert.assertEquals(null, dungeon.getEnemy()) wont work properly:
        expected:<null> but was:<[]>
        Assert.assertEquals(null, dungeon.getEnemy()); */

        Assert.assertEquals(100, myHero.getLp());
        Assert.assertEquals(140, myHero.getCoins());
        Assert.assertEquals(0, myEnemy.getLp());
        Assert.assertEquals(0, myEnemy1.getLp());
        Assert.assertEquals(0, myEnemy2.getLp());
        Assert.assertNull(myHero.getAttacking());
        Assert.assertNull(myEnemy.getNext());

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
