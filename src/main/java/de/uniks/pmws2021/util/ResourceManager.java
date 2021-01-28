package de.uniks.pmws2021.util;

import de.uniks.pmws2021.model.Hero;
import org.fulib.yaml.YamlIdMap;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ResourceManager {
    // Choose your own SAVED_HEROES_FOLDER_NAME and SAVED_HEROES_FILE_NAME
    private static final String SAVED_HEROES_FOLDER_NAME = "database";
    private static final String SAVED_HEROES_FILE_NAME = "myHeroes.yaml";
    // Add the saved-hero-folder-name to your .gitignore
    private static final Path HERO_FOLDER = Path.of(SAVED_HEROES_FOLDER_NAME);
    private static final Path HERO_FILE = HERO_FOLDER.resolve(SAVED_HEROES_FILE_NAME);

    // static constructor magic to create the file if absent
    static {
        try {
            if (!Files.isDirectory(HERO_FOLDER)) {
                Files.createDirectory(HERO_FOLDER);
            }
            if (!Files.exists(HERO_FILE)) {
                Files.createFile(HERO_FILE);
            }
        } catch (Exception e) {
            System.err.println("Error while loading " + HERO_FILE);
            e.printStackTrace();
        }
    }

    public static List<Hero> loadAllHeroes() {
        List<Hero> heroList = new ArrayList<>();

        try {
            // try to read heroList from File
            String heroesString = Files.readString(HERO_FILE);

            // parse yaml-string to YamlIdMap
            YamlIdMap yamlIdMap = new YamlIdMap(Hero.class.getPackageName());

            // decode map
            yamlIdMap.decode(heroesString);

            // map the decoded yaml data to real java objects and return list of heros
            for (Object object : yamlIdMap.getObjIdMap().values()
            ) {
                if (object instanceof  Hero) {
                    heroList.add((Hero) object);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return heroList;

    }

    public static void saveHero(Hero hero) {


        // save as .yaml
        try {
            // load all existing heroes
            List<Hero> oldHeroes = loadAllHeroes();

            oldHeroes.removeIf(Hero -> oldHeroes.contains(hero));

            // add copy of victor to list ToDo: save HeroStats
            oldHeroes.add(hero);

            // serialize as yaml
            YamlIdMap yamlIdMap = new YamlIdMap(Hero.class.getPackageName());
            yamlIdMap.discoverObjects(oldHeroes);
            String yamlData = yamlIdMap.encode();
            Files.writeString(HERO_FILE, yamlData);
        } catch (Exception e) {
            System.out.println("Error saving hero");
            e.printStackTrace();
        }

    }
}
