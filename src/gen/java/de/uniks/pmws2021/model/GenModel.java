package de.uniks.pmws2021.model;

import org.fulib.builder.ClassModelDecorator;
import org.fulib.builder.ClassModelManager;
import org.fulib.builder.Type;
import org.fulib.classmodel.Clazz;

public class GenModel implements ClassModelDecorator
{
	@Override
	public void decorate(ClassModelManager mm)
	{

		Clazz heroStat = mm.haveClass("HeroStat");
		Clazz attackStat = mm.haveClass("AttackStat");
		Clazz defenseStat = mm.haveClass("DefenseStat");
		//clas already exists but needed for further associations hero <--> heroStat
		Clazz hero = mm.haveClass("Hero");

		mm.haveSuper(attackStat, heroStat);
		mm.haveSuper(defenseStat, heroStat);

		mm.haveAttribute(heroStat,"level", Type.INT);
		mm.haveAttribute(heroStat,"value", Type.INT);
		mm.haveAttribute(heroStat,"cost", Type.INT);

		//associations
		mm.associate(heroStat,"hero",1,hero,"stats",2);

	}
}
