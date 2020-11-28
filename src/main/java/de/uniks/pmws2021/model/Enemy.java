package de.uniks.pmws2021.model;
import java.util.Objects;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;

public class Enemy
{
   public static final String PROPERTY_NAME = "name";
   public static final String PROPERTY_LP = "lp";
   public static final String PROPERTY_COINS = "coins";
   public static final String PROPERTY_ATK = "atk";
   public static final String PROPERTY_DEF = "def";
   public static final String PROPERTY_STANCE = "stance";
   public static final String PROPERTY_DUNGEON = "dungeon";
   public static final String PROPERTY_ATTACKING = "attacking";
   public static final String PROPERTY_NEXT = "next";
   public static final String PROPERTY_PREV = "prev";
   private String name;
   private int lp;
   private int coins;
   private int atk;
   private int def;
   private String stance;
   private Dungeon dungeon;
   private Hero attacking;
   private Enemy next;
   private Enemy prev;
   protected PropertyChangeSupport listeners;

   public String getName()
   {
      return this.name;
   }

   public Enemy setName(String value)
   {
      if (Objects.equals(value, this.name))
      {
         return this;
      }

      final String oldValue = this.name;
      this.name = value;
      this.firePropertyChange(PROPERTY_NAME, oldValue, value);
      return this;
   }

   public int getLp()
   {
      return this.lp;
   }

   public Enemy setLp(int value)
   {
      if (value == this.lp)
      {
         return this;
      }

      final int oldValue = this.lp;
      this.lp = value;
      this.firePropertyChange(PROPERTY_LP, oldValue, value);
      return this;
   }

   public int getCoins()
   {
      return this.coins;
   }

   public Enemy setCoins(int value)
   {
      if (value == this.coins)
      {
         return this;
      }

      final int oldValue = this.coins;
      this.coins = value;
      this.firePropertyChange(PROPERTY_COINS, oldValue, value);
      return this;
   }

   public int getAtk()
   {
      return this.atk;
   }

   public Enemy setAtk(int value)
   {
      if (value == this.atk)
      {
         return this;
      }

      final int oldValue = this.atk;
      this.atk = value;
      this.firePropertyChange(PROPERTY_ATK, oldValue, value);
      return this;
   }

   public int getDef()
   {
      return this.def;
   }

   public Enemy setDef(int value)
   {
      if (value == this.def)
      {
         return this;
      }

      final int oldValue = this.def;
      this.def = value;
      this.firePropertyChange(PROPERTY_DEF, oldValue, value);
      return this;
   }

   public String getStance()
   {
      return this.stance;
   }

   public Enemy setStance(String value)
   {
      if (Objects.equals(value, this.stance))
      {
         return this;
      }

      final String oldValue = this.stance;
      this.stance = value;
      this.firePropertyChange(PROPERTY_STANCE, oldValue, value);
      return this;
   }

   public Dungeon getDungeon()
   {
      return this.dungeon;
   }

   public Enemy setDungeon(Dungeon value)
   {
      if (this.dungeon == value)
      {
         return this;
      }

      final Dungeon oldValue = this.dungeon;
      if (this.dungeon != null)
      {
         this.dungeon = null;
         oldValue.withoutEnemy(this);
      }
      this.dungeon = value;
      if (value != null)
      {
         value.withEnemy(this);
      }
      this.firePropertyChange(PROPERTY_DUNGEON, oldValue, value);
      return this;
   }

   public Hero getAttacking()
   {
      return this.attacking;
   }

   public Enemy setAttacking(Hero value)
   {
      if (this.attacking == value)
      {
         return this;
      }

      final Hero oldValue = this.attacking;
      if (this.attacking != null)
      {
         this.attacking = null;
         oldValue.setAttacking(null);
      }
      this.attacking = value;
      if (value != null)
      {
         value.setAttacking(this);
      }
      this.firePropertyChange(PROPERTY_ATTACKING, oldValue, value);
      return this;
   }

   public Enemy getNext()
   {
      return this.next;
   }

   public Enemy setNext(Enemy value)
   {
      if (this.next == value)
      {
         return this;
      }

      final Enemy oldValue = this.next;
      if (this.next != null)
      {
         this.next = null;
         oldValue.setPrev(null);
      }
      this.next = value;
      if (value != null)
      {
         value.setPrev(this);
      }
      this.firePropertyChange(PROPERTY_NEXT, oldValue, value);
      return this;
   }

   public Enemy getPrev()
   {
      return this.prev;
   }

   public Enemy setPrev(Enemy value)
   {
      if (this.prev == value)
      {
         return this;
      }

      final Enemy oldValue = this.prev;
      if (this.prev != null)
      {
         this.prev = null;
         oldValue.setNext(null);
      }
      this.prev = value;
      if (value != null)
      {
         value.setNext(this);
      }
      this.firePropertyChange(PROPERTY_PREV, oldValue, value);
      return this;
   }

   public boolean firePropertyChange(String propertyName, Object oldValue, Object newValue)
   {
      if (this.listeners != null)
      {
         this.listeners.firePropertyChange(propertyName, oldValue, newValue);
         return true;
      }
      return false;
   }

   public boolean addPropertyChangeListener(PropertyChangeListener listener)
   {
      if (this.listeners == null)
      {
         this.listeners = new PropertyChangeSupport(this);
      }
      this.listeners.addPropertyChangeListener(listener);
      return true;
   }

   public boolean addPropertyChangeListener(String propertyName, PropertyChangeListener listener)
   {
      if (this.listeners == null)
      {
         this.listeners = new PropertyChangeSupport(this);
      }
      this.listeners.addPropertyChangeListener(propertyName, listener);
      return true;
   }

   public boolean removePropertyChangeListener(PropertyChangeListener listener)
   {
      if (this.listeners != null)
      {
         this.listeners.removePropertyChangeListener(listener);
      }
      return true;
   }

   public boolean removePropertyChangeListener(String propertyName, PropertyChangeListener listener)
   {
      if (this.listeners != null)
      {
         this.listeners.removePropertyChangeListener(propertyName, listener);
      }
      return true;
   }

   @Override
   public String toString()
   {
      final StringBuilder result = new StringBuilder();
      result.append(' ').append(this.getName());
      result.append(' ').append(this.getStance());
      return result.substring(1);
   }

   public void removeYou()
   {
      this.setDungeon(null);
      this.setAttacking(null);
      this.setNext(null);
      this.setPrev(null);
   }
}
