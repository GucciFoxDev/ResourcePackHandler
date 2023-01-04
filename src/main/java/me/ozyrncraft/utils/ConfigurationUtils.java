package me.ozyrncraft.utils;

import me.ozyrncraft.PackHandler;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

import static me.ozyrncraft.PackHandler.configFile;
import static me.ozyrncraft.PackHandler.worldDataFile;

public class ConfigurationUtils {

    /**
     * Creates the config file with the contents of the one in the resource folder
     */
    public static void createConfig() {
        configFile = new File(PackHandler.getPlugin(PackHandler.class).getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            PackHandler.getPlugin(PackHandler.class).saveResource("config.yml", false);
        }

        PackHandler.configuration = new YamlConfiguration();
        try {
            PackHandler.configuration.load(configFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a world data file to store resource pack ID data
     */
    public static void createWorldDataConfig() {
        worldDataFile = new File(PackHandler.getPlugin(PackHandler.class).getDataFolder(), "world-data.yml");
        if (!worldDataFile.exists()) {
            worldDataFile.getParentFile().mkdirs();
            PackHandler.getPlugin(PackHandler.class).saveResource("world-data.yml", false);
        }

        PackHandler.worldData = new YamlConfiguration();
        try {
            PackHandler.worldData.load(worldDataFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}
