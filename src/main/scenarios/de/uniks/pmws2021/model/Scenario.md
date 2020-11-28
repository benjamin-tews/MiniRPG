#mymodel
//create classes via concret objects
There is a Dungeon with name dungeon.

There is a Enemy with name ogre and with lp 100 and with coins 30 and with atk 25 and with def 40.
//another option to add attributes
Every Enemy has stance of type String.

There is a Enemy with name monster.

There is a Hero with name KrasserDude and with lp 100 and with coins 30 and with mode hard.

Dungeon has Enemy and is dungeon of ogre and monster.

Dungeon has Hero and is Dungeon of KrasserDude.

Monster has attacking and is attacking of KrasserDude.

Monster has next and is prev of Ogre.