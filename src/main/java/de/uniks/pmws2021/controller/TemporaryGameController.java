package de.uniks.pmws2021.controller;

import de.uniks.pmws2021.model.*;

import java.util.Optional;
import java.util.Random;

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

        final int UPGRADE_COST = 50;
        final double UPGRADE_FACTOR = 1.1;

        if (stat.getHero() == null) {
            throw new NullPointerException("empty hero - doh!");
        }

        if (stat == null) {
            throw new NullPointerException("empty stats - doh!");
        }

        if (stat.getHero().getCoins() - UPGRADE_COST < 0) {
            throw new RuntimeException("Too less coins in wallet - no Upgrade possible!");
        } else {
            stat.getHero().setCoins(stat.getHero().getCoins() - UPGRADE_COST);
            stat.setLevel(stat.getLevel() + 1);
            stat.setValue((int) ((stat.getValue()) * UPGRADE_FACTOR)).setCost((int) ((stat.getCost()) * UPGRADE_FACTOR));
        }

    }

    public void heroEngagesFight(String heroStance, Hero hero) {

        String attack = Stances.stances[0];
        String defend = Stances.stances[1];

        //both attack
        if (heroStance == attack && hero.getAttacking().getStance() == attack) {
            //set heros life
            if ( (hero.getLp() - hero.getAttacking().getAtk() ) < 0) {
                hero.setLp(0);
            } else {
                hero.setLp(hero.getLp() - hero.getAttacking().getAtk());
            }
            //take first object of AttackStat and create local object
            Optional<HeroStat> firstHeroStat = hero.getStats().stream().filter(heroStat -> heroStat instanceof AttackStat).findFirst();
            HeroStat attackingStat = firstHeroStat.get();
            //sets the enemy's lifepoints
            if ((hero.getAttacking().getLp() - attackingStat.getValue()) < 0) {
                hero.getAttacking().setLp(0);
            } else {
                hero.getAttacking().setLp(hero.getAttacking().getLp() - attackingStat.getValue());
            }
        }
        //hero attacks and enemy defends
        else if (heroStance == attack && hero.getAttacking().getStance() == defend) {

            //take first object of AttackStat and create local object
            Optional<HeroStat> firstHeroStat = hero.getStats().stream().filter(heroStat -> heroStat instanceof AttackStat).findFirst();
            HeroStat attackingStat = firstHeroStat.get();
            //sets the enemy's lifepoints
            if ((hero.getAttacking().getLp() - (attackingStat.getValue() - hero.getAttacking().getDef())) < 0) {
                hero.getAttacking().setLp(0);
            } else {
                hero.getAttacking().setLp(hero.getAttacking().getLp() - (attackingStat.getValue() - hero.getAttacking().getDef()));
            }
        }
        //hero defends and enemy attacks
        else if (heroStance == defend && hero.getAttacking().getStance() == attack) {
            //take first object of DefenseStats and create local object
            Optional<HeroStat> firstHeroStat = hero.getStats().stream().filter(heroStat -> heroStat instanceof DefenseStat).findFirst();
            HeroStat defenceStat = firstHeroStat.get();

            if ((hero.getAttacking().getAtk() - defenceStat.getValue()) < 0) {
                hero.setLp(0);
            } else {
                hero.setLp(hero.getLp() - (hero.getAttacking().getAtk() - defenceStat.getValue()));
            }
        }
        //both defend
        else if (heroStance == defend && hero.getAttacking().getStance() == defend) {
            //take first object of DefenseStats and create local object
            hero.setLp(hero.getLp());
            hero.getAttacking().setLp(hero.getAttacking().getLp());
        } else {
            throw new RuntimeException("Invalid Stance combination");
        }

    }

    public void evaluateFight(Enemy enemy, Hero hero) {

        String attack = Stances.stances[0];
        String defend = Stances.stances[1];

        //create random Stance of stances array ... 0 or 1
        Random random = new Random();
        String rndStance = Stances.stances[random.nextInt(Stances.stances.length)];

        while (enemy.getLp() > 0 && hero.getLp() > 0) {
            //both are alive
            //random stance - make stance static
            enemy.setStance(rndStance);
            heroEngagesFight(attack, hero);
        }
        if (enemy.getLp() == 0) {
            //enemy died
            hero.setCoins(hero.getCoins() + enemy.getCoins());
            //Static later
            hero.setLp(100);
            //set next enemy if exists
            if (hero.getAttacking().getNext() != null) {
                hero.setAttacking(enemy.getNext());
            }
        } else {
            //hero died
            //return to menu
        }
    }

}
