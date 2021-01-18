package de.uniks.pmws2021;

import de.uniks.pmws2021.model.*;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

public class RPGEditor {

    // constants
    final static int MAX_LIFE = 100;
    final static int UPGRADE_COST = 50;

    // ===========================================================================================
    // Hero
    // ===========================================================================================
    final static double UPGRADE_FACTOR = 1.1;

    // ===========================================================================================
    // Enemy
    // ===========================================================================================
    // Editor lookup lists
    private ArrayList<Hero> heroes = new ArrayList<>();

    // ===========================================================================================
    // Dungeon
    // ===========================================================================================
    // Connection to model root object
    private Dungeon dungeon;

    // ===========================================================================================
    // Game Logic
    // ===========================================================================================

    public Hero haveHero(String heroName, String hardMode) {
        // lookup hero in heroes list by heroName (This is important for saving heroes later)

        for (Hero hero : heroes) {
            if (hero.getName().equals(heroName)) {
                return hero;
            }
        }
        // create hero and heroStat with initial and given values
        Hero hero = new Hero();
        hero.setName(heroName).setMode(hardMode);
        hero.setLp(100);
        // add hero to heroes list (This is important for saving heroes later)
        heroes.add(hero);
        return hero;
    }

    // returns ArrayList heroes
    public ArrayList<Hero> getHeroes() {
        // some test heroes
        return this.heroes;
    }

    // returns hero if included in ArrayList heroes
    public Hero getFromHeroes(String name) {
        for (Hero hero : heroes
             ) {
            if(hero.getName().equals(name)) {
                return hero;
            }
        }
        return null;
    }

    public String getHeroName() {
        if (this.dungeon == null) {
            return "Dungeon is null";
        } else {
            return this.dungeon.getHero().getName();
        }
    }

    public Dungeon getDungeon() {
        return this.dungeon;
    }

    // If you want you can change this method and its parameter.
    // For example: you can implement a random mechanisem that generates enemies by a defined set of enemies
    // Or you keep it as fixed as it is now
    public Enemy haveEnemy(String name, int lp, int coins, int atk, int def, String stance) {
        // create enemy with initial and/or given values
        // ToDo: implement more dynamic enemies later
        Enemy enemy = new Enemy();
        enemy.setName(name).setLp(lp).setCoins(coins).setAtk(atk).setDef(def).setStance(stance);
        return enemy;
    }

    public Dungeon haveDungeon(String dungeonName, Hero hero, Enemy... enemies) {
        // create enemy with given values
        this.dungeon = new Dungeon();
        this.dungeon.setName(dungeonName).setHero(hero).withEnemy(enemies);
        return this.dungeon;
    }

    public String getDungeonName() {
        if (this.dungeon == null) {
            return "Dungeon is null";
        } else {
            return this.dungeon.getName();
        }
    }

    public void enterDungeon(Hero hero) {
        // Put in your code
        // Use the have<Entity>() Method from above instead of new <Entity>()
        // ToDo: implement random stance later
        haveDungeon("The Fire Pits", hero, haveEnemy("Goblin", 30, 5, 5, 7, "attack"));
    }

    public void heroStatUpdate(HeroStat heroStat) {
        // Put in your code
        if (heroStat.getHero() == null) {
            throw new NullPointerException("empty hero - doh!");
        }

        if (heroStat == null) {
            throw new NullPointerException("empty stats - doh!");
        }

        if (heroStat.getHero().getCoins() - UPGRADE_COST < 0) {
            throw new RuntimeException("Too less coins in wallet - no Upgrade possible!");
        } else {
            heroStat.getHero().setCoins(heroStat.getHero().getCoins() - UPGRADE_COST);
            heroStat.setLevel(heroStat.getLevel() + 1);
            heroStat.setValue((int) ((heroStat.getValue()) * UPGRADE_FACTOR)).setCost((int) ((heroStat.getCost()) * UPGRADE_FACTOR));
        }
    }

    public void heroEngagesFight(String heroStance, Hero hero) {
        // Put in your code
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
            int damage = (((attackingStat.getValue() - hero.getAttacking().getDef()) > 0) ? (attackingStat.getValue() - hero.getAttacking().getDef()) : 0);

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

            //set heroes lp
            /* implementation error: wrong lp calculation -> fixed it */

            int damage = (((hero.getAttacking().getAtk() - defenceStat.getValue()) > 0) ? (hero.getAttacking().getAtk() - defenceStat.getValue()) : 0);

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
        // Put in your code

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
    }
}
