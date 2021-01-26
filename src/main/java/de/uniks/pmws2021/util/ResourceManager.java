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
    private static final Path SAVED_HEROES_FOLDER_NAME = Path.of("database");
    private static final Path SAVED_HEROES_FILE_NAME = Path.of("myHeroes.yaml");
    // Add the saved-hero-folder-name to your .gitignore
    private static final Path HERO_FOLDER = Path.of("database");
    private static final Path HERO_FILE = HERO_FOLDER.resolve("myHeroes.yaml");

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

        // try to read heroList from File 
        // parse yaml-string to YamlIdMap
        // decode map
        // map the decoded yaml data to real java objects and return list of heros

        return heroList;
    }

    public static void saveHero(Hero victor) {


        // save as .yaml
        try {
            // load all existing heroes
            List<Hero> oldHeroes = loadAllHeroes();

            // delete existing hero with the same name as the victor
            //oldHeroes.removeIf(oldHeroes -> oldHeroes.getName().equals(victor));

            // add copy of victor to list
            oldHeroes.add(victor);

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
