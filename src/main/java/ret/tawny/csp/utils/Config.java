package ret.tawny.csp.utils;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class Config {

    private static FileConfiguration config;
    private static File configFile;

    /**
     * Loads the config file. If it doesn't exist, it will be created.
     * @param plugin The main plugin instance
     */
    public static void loadConfig(Plugin plugin) {
        configFile = new File(plugin.getDataFolder(), "config.yml");

        if (!configFile.exists()) {
            plugin.saveResource("config.yml", false);  // Create default config if it doesn't exist
        }

        config = YamlConfiguration.loadConfiguration(configFile);
    }

    /**
     * Save the configuration back to file
     */
    public static void saveConfig() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reload the configuration from file
     */
    public static void reloadConfig() {
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    /**
     * Get a configuration string with color codes translated.
     * @param path The path to the string in the config.
     * @return The formatted string
     */
    public static String getMessage(String path) {
        return ChatColor.translateAlternateColorCodes('&', config.getString(path, "&cMessage not found in config."));
    }

    /**
     * Get a boolean from the config file.
     * @param path The path to the boolean in the config.
     * @return The boolean value
     */
    public static boolean getBoolean(String path) {
        return config.getBoolean(path, false);
    }

    /**
     * Get an integer from the config file.
     * @param path The path to the integer in the config.
     * @return The integer value
     */
    public static int getInt(String path) {
        return config.getInt(path, 0);
    }

    /**
     * Get a double from the config file.
     * @param path The path to the double in the config.
     * @return The double value
     */
    public static double getDouble(String path) {
        return config.getDouble(path, 0.0);
    }

    /**
     * Set a value in the config file and save it.
     * @param path The path to the value.
     * @param value The value to set.
     */
    public static void setValue(String path, Object value) {
        config.set(path, value);
        saveConfig();
    }
}
