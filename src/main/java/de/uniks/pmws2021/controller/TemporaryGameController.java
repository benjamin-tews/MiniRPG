package de.uniks.pmws2021.controller;

import de.uniks.pmws2021.model.*;

import java.util.Optional;
import java.util.Random;

public class TemporaryGameController {

    final static int MAX_LIFE = 100;
    final static int UPGRADE_COST = 50;
    final static double UPGRADE_FACTOR = 1.1;

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

        //hero is null
        if (hero == null) {
            throw new NullPointerException("empty hero - doh!");
        }

        //heroStance is neither "defend" nor "attack"
        /* wrong AND implementation - need to check for both statements */
        if (!(heroStance.equals(defend) | heroStance.equals(attack))) {
            throw new RuntimeException("Invalid Stance combination");
        }

        //both attack
        if (heroStance == attack && hero.getAttacking().getStance() == attack) {

            //set heros life
            if ((hero.getLp() - hero.getAttacking().getAtk()) <= 0) {
                hero.setLp(0);
            } else {
                hero.setLp(hero.getLp() - hero.getAttacking().getAtk());
            }

            //take first object of AttackStat and create local object
            Optional<HeroStat> firstHeroStat = hero.getStats().stream().filter(heroStat -> heroStat instanceof AttackStat).findFirst();
            HeroStat attackingStat = firstHeroStat.get();

            //sets the enemy's lifepoints
            if ((hero.getAttacking().getLp() - attackingStat.getValue()) <= 0) {
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
            /* implementation error - fixed this if statement */

            //calculate incomming damage
            int damage = ( ((attackingStat.getValue() - hero.getAttacking().getDef()) > 0) ? (attackingStat.getValue() - hero.getAttacking().getDef()) : 0 );

            if ((hero.getAttacking().getLp() - (damage)) <= 0) {
                hero.getAttacking().setLp(0);
            } else {
                hero.getAttacking().setLp(hero.getAttacking().getLp() - damage);
            }
        }

        //hero defends and enemy attacks
        else if (heroStance == defend && hero.getAttacking().getStance() == attack) {
            //take first object of DefenseStats and create local object
            Optional<HeroStat> firstHeroStat = hero.getStats().stream().filter(heroStat -> heroStat instanceof DefenseStat).findFirst();
            HeroStat defenceStat = firstHeroStat.get();

            //set heros lp
            /* implementation error: wrong lp calculation -> fixed it */

            int damage = ( ((hero.getAttacking().getAtk() - defenceStat.getValue()) > 0) ? (hero.getAttacking().getAtk() - defenceStat.getValue()) : 0 );

            if ((hero.getLp() - damage) <= 0) {
                hero.setLp(0);
                //remove hero from dungeon
                //hero.setDungeon(null);
            } else {
                hero.setLp(hero.getLp() - damage);
            }
        }

        //both defend
        else if (heroStance == defend && hero.getAttacking().getStance() == defend) {
            //take first object of DefenseStats and create local object
            //set hero lp
            hero.setLp(hero.getLp());
            //set enemy lp
            hero.getAttacking().setLp(hero.getAttacking().getLp());
        }

    }

    public void evaluateFight(Enemy enemy, Hero hero) {

        //create random Stance of stances array ... 0 or 1
        Random random = new Random();
        String rndStance = Stances.stances[random.nextInt(Stances.stances.length)];

        //Exceptions to throw
        if (hero == null) {
            throw new NullPointerException("empty hero - doh!");
        }

        if (enemy == null) {
            throw new NullPointerException("empty enemy - doh!");
        }

        /* whole new implementation was necessary cause of wrong task assumptions */
        //initial fight round
        heroEngagesFight("attack", hero);

        //if enemy died in fight and hero still lives ...
        if ((enemy.getLp() == 0) & (hero.getLp() > 0)) {
            //hero gets its coins
            hero.setCoins(hero.getCoins() + enemy.getCoins());
        } else {
            //enemy still alive: set enemy random stance
            enemy.setStance(rndStance);
        }

        //if died enemy got next enemy and hero s still alive
        while ((enemy.getLp() == 0) & (enemy.getNext() != null) & (hero.getLp() > 0)) {
            Enemy nextEnemy;
            nextEnemy = enemy.getNext();
            //remove dead enemy and its links
            enemy.setNext(null);
            hero.getDungeon().withoutEnemy(enemy);
            //set enemy to next enemy
            enemy = nextEnemy;
            hero.setAttacking(enemy);
            //fight against next enemy
            heroEngagesFight("attack", hero);
            if (enemy.getLp() == 0) {
                //hero gets its coins
                hero.setCoins(hero.getCoins() + enemy.getCoins());
            } else {
                //enemy still alive: set enemy random stance
                enemy.setStance(rndStance);
            }
        }

        //at the end of round
        hero.setAttacking(null);
        hero.getDungeon().withoutEnemy(enemy);
        hero.setLp(MAX_LIFE);


        /*      OLD IMPLEMENTATION - FIGHT UNTIL ONE IS DEAD

        //while both are alive ...
        /* to get rid of memory errors if both defend we could implement a ttl counter */
        /*while (enemy.getLp() > 0 && hero.getLp() > 0) {
            //set enemy random stance "attack" or "defend"
            enemy.setStance(rndStance);
            //... call method heroEngagesFight(heroStance, hero)
            heroEngagesFight("attack", hero);
        }

        //if enemy died
        if (enemy.getLp() == 0) {
            hero.setCoins(hero.getCoins() + enemy.getCoins());
            //Static later
            hero.setLp(MAX_LIFE);
            //set next enemy if exists
            if (enemy.getNext() != null) {
                //enemy focuses on hero
                hero.setAttacking(enemy.getNext());
                Enemy tmpEnemy = new Enemy();
                tmpEnemy = enemy.getNext();
                enemy.setNext(null);
                hero.getDungeon().withoutEnemy(enemy);
                //recursive call method
                //evaluateFight(tmpEnemy, hero);
            } else {
                hero.setAttacking(null);
                hero.getDungeon().withoutEnemy(enemy);
            }
        } else {
            //hero died
            //return to menu
        }
       */

    }

}
