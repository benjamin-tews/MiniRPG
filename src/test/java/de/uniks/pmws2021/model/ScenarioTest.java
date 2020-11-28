package de.uniks.pmws2021.model;
import org.junit.Test;

public class ScenarioTest
{

   @Test
   public void mymodel()
   {
      // create classes via concret objects
      Dungeon dungeon = new Dungeon();
      dungeon.setName("dungeon");
      Enemy ogre = new Enemy();
      ogre.setName("ogre");
      ogre.setLp(100);
      ogre.setCoins(30);
      ogre.setAtk(25);
      ogre.setDef(40);
      // another option to add attributes
      Enemy monster = new Enemy();
      monster.setName("monster");
      Hero krasserDude = new Hero();
      krasserDude.setName("KrasserDude");
      krasserDude.setLp(100);
      krasserDude.setCoins(30);
      krasserDude.setMode("hard");
      dungeon.withEnemy(ogre, monster);
      dungeon.setHero(krasserDude);
      monster.setAttacking(krasserDude);
      monster.setNext(ogre);
   }
}
