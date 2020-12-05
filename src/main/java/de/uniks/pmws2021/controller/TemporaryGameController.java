package de.uniks.pmws2021.controller;

import de.uniks.pmws2021.model.*;

public class TemporaryGameController {

    public Dungeon enterDungeon(Hero hero) {

        Dungeon dungeon = new Dungeon();
        Enemy enemy1 = new Enemy();
        Enemy enemy2 = new Enemy();
        Enemy enemy3 = new Enemy();

        dungeon.withEnemy(enemy1, enemy2, enemy3);
        dungeon.setHero(hero);
        enemy1.setAttacking(hero);

        enemy1.setNext(enemy2);
        enemy3.setPrev(enemy2);

        enemy1.setName("Goblin").setLp(30).setAtk(5).setDef(7).setCoins(5);
        enemy2.setName("Ogre").setLp(60).setAtk(10).setDef(10).setCoins(7);
        enemy3.setName("Hydra").setLp(50).setAtk(20).setDef(8).setCoins(10);

        return dungeon;

    }

    //methods for exercise 5 task 2
    public void heroStatUpdate(HeroStat stat) {

    }

    public void heroEngagesFight(String heroStance, Hero hero) {

    }

    public void evaluateFight(Enemy enemy, Hero hero) {

    }

}
