package ret.tawny.csp.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ret.tawny.csp.utils.Config;

public class AdminToolCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("abcstats.admin")) {
            player.sendMessage(Config.getMessage("messages.no_permission"));
            return true;
        }


        ItemStack adminTool = new ItemStack(Material.COMPASS, 1);
        player.getInventory().addItem(adminTool);

        player.sendMessage(Config.getMessage("messages.admin_tool_given"));
        return true;
    }
}
