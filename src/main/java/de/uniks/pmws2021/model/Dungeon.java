package de.uniks.pmws2021.model;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Collections;
import java.util.Collection;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;

public class Dungeon
{
   public static final String PROPERTY_NAME = "name";
   public static final String PROPERTY_ENEMY = "enemy";
   public static final String PROPERTY_HERO = "hero";
   private String name;
   private List<Enemy> enemy;
   private Hero hero;
   protected PropertyChangeSupport listeners;

   public String getName()
   {
      return this.name;
   }

   public Dungeon setName(String value)
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

   public List<Enemy> getEnemy()
   {
      return this.enemy != null ? Collections.unmodifiableList(this.enemy) : Collections.emptyList();
   }

   public Dungeon withEnemy(Enemy value)
   {
      if (this.enemy == null)
      {
         this.enemy = new ArrayList<>();
      }
      if (!this.enemy.contains(value))
      {
         this.enemy.add(value);
         value.setDungeon(this);
         this.firePropertyChange(PROPERTY_ENEMY, null, value);
      }
      return this;
   }

   public Dungeon withEnemy(Enemy... value)
   {
      for (final Enemy item : value)
      {
         this.withEnemy(item);
      }
      return this;
   }

   public Dungeon withEnemy(Collection<? extends Enemy> value)
   {
      for (final Enemy item : value)
      {
         this.withEnemy(item);
      }
      return this;
   }

   public Dungeon withoutEnemy(Enemy value)
   {
      if (this.enemy != null && this.enemy.remove(value))
      {
         value.setDungeon(null);
         this.firePropertyChange(PROPERTY_ENEMY, value, null);
      }
      return this;
   }

   public Dungeon withoutEnemy(Enemy... value)
   {
      for (final Enemy item : value)
      {
         this.withoutEnemy(item);
      }
      return this;
   }

   public Dungeon withoutEnemy(Collection<? extends Enemy> value)
   {
      for (final Enemy item : value)
      {
         this.withoutEnemy(item);
      }
      return this;
   }

   public Hero getHero()
   {
      return this.hero;
   }

   public Dungeon setHero(Hero value)
   {
      if (this.hero == value)
      {
         return this;
      }

      final Hero oldValue = this.hero;
      if (this.hero != null)
      {
         this.hero = null;
         oldValue.setDungeon(null);
      }
      this.hero = value;
      if (value != null)
      {
         value.setDungeon(this);
      }
      this.firePropertyChange(PROPERTY_HERO, oldValue, value);
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
      return result.substring(1);
   }

   public void removeYou()
   {
      this.withoutEnemy(new ArrayList<>(this.getEnemy()));
      this.setHero(null);
   }
}
