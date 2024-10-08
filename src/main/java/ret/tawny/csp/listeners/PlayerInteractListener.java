package ret.tawny.csp.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.Material;
import ret.tawny.csp.utils.Config;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerInteractListener implements Listener {


    private final Map<UUID, String> pendingStatChanges = new HashMap<>();


    public void openStatEditGUI(Player player) {
        Inventory gui = Bukkit.createInventory(null, 9, "Edit Player Stats");

        ItemStack compass = new ItemStack(Material.COMPASS);
        ItemMeta compassMeta = compass.getItemMeta();
        compassMeta.setDisplayName("Edit Health");
        compass.setItemMeta(compassMeta);

        ItemStack netherStar = new ItemStack(Material.NETHER_STAR);
        ItemMeta netherStarMeta = netherStar.getItemMeta();
        netherStarMeta.setDisplayName("Edit Speed");
        netherStar.setItemMeta(netherStarMeta);

        gui.setItem(0, compass);
        gui.setItem(1, netherStar);

        player.openInventory(gui);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

        if (event.getView().getTitle().equals("Edit Player Stats")) {
            event.setCancelled(true);

            if (clickedItem.getType() == Material.COMPASS) {
                player.closeInventory();
                promptStatChange(player, "health");
            } else if (clickedItem.getType() == Material.NETHER_STAR) {
                player.closeInventory();
                promptStatChange(player, "speed");
            }
        }
    }

    private void promptStatChange(Player player, String stat) {
        player.sendMessage(Config.getMessage("messages.enter_stat_value").replace("{stat}", stat));
        pendingStatChanges.put(player.getUniqueId(), stat);
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();

        if (pendingStatChanges.containsKey(playerUUID)) {
            event.setCancelled(true);

            String stat = pendingStatChanges.get(playerUUID);
            String input = event.getMessage();
            try {
                int value = Integer.parseInt(input);
                if (value < 0 || value > 255) {
                    player.sendMessage(Config.getMessage("messages.invalid_number"));
                } else {
                    applyStatChange(player, stat, value);
                }
            } catch (NumberFormatException e) {
                player.sendMessage(Config.getMessage("messages.invalid_input"));
            }

            pendingStatChanges.remove(playerUUID);
        }
    }


    private void applyStatChange(Player player, String stat, int value) {
        switch (stat) {
            case "health":
                player.setHealth(Math.min(value, 20));  // Health is capped at 20 (10 hearts)
                player.sendMessage(Config.getMessage("messages.stat_changed").replace("{stat}", "health").replace("{value}", String.valueOf(value)));
                break;
            case "speed":
                player.setWalkSpeed(Math.min(value / 100f, 1f));  // Walk speed max is 1.0
                player.sendMessage(Config.getMessage("messages.stat_changed").replace("{stat}", "speed").replace("{value}", String.valueOf(value)));
                break;
            default:
                player.sendMessage(Config.getMessage("messages.invalid_stat"));
                break;
        }
    }
}
