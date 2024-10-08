package ret.tawny.csp;

import org.bukkit.plugin.java.JavaPlugin;
import ret.tawny.csp.commands.AdminToolCommand;
import ret.tawny.csp.listeners.PlayerInteractListener;
import ret.tawny.csp.utils.Config;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        // Load the configuration
        Config.loadConfig(this);

        // Register the commands and events
        this.getCommand("admin").setExecutor(new AdminToolCommand());
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
