package ret.tawny.csp.managers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GUIManager {

    public static void openStatGUI(Player admin, Player target) {
        Inventory gui = Bukkit.createInventory(null, 9, ChatColor.DARK_GREEN + "Edit Stats: " + target.getName());

        ItemStack health = new ItemStack(Material.APPLE);
        ItemMeta healthMeta = health.getItemMeta();
        healthMeta.setDisplayName(ChatColor.RED + "Modify Health");
        health.setItemMeta(healthMeta);

        ItemStack speed = new ItemStack(Material.SUGAR);
        ItemMeta speedMeta = speed.getItemMeta();
        speedMeta.setDisplayName(ChatColor.YELLOW + "Modify Speed");
        speed.setItemMeta(speedMeta);

        ItemStack resistance = new ItemStack(Material.SHIELD);
        ItemMeta resistanceMeta = resistance.getItemMeta();
        resistanceMeta.setDisplayName(ChatColor.BLUE + "Modify Resistance");
        resistance.setItemMeta(resistanceMeta);

        ItemStack attackDamage = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta attackMeta = attackDamage.getItemMeta();
        attackMeta.setDisplayName(ChatColor.AQUA + "Modify Attack Damage");
        attackDamage.setItemMeta(attackMeta);

        ItemStack jump = new ItemStack(Material.FEATHER);
        ItemMeta jumpMeta = jump.getItemMeta();
        jumpMeta.setDisplayName(ChatColor.GOLD + "Modify Jump Height");
        jump.setItemMeta(jumpMeta);

        gui.setItem(0, health);
        gui.setItem(1, speed);
        gui.setItem(2, resistance);
        gui.setItem(3, attackDamage);
        gui.setItem(4, jump);

        admin.openInventory(gui);
    }

    public static void handleGUIClick(InventoryClickEvent event) {
        Player admin = (Player) event.getWhoClicked();
        if (event.getView().getTitle().startsWith(ChatColor.DARK_GREEN + "Edit Stats: ")) {
            event.setCancelled(true);
            if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) return;

            Player target = Bukkit.getPlayer(event.getView().getTitle().split(": ")[1]);
            if (target == null) {
                admin.sendMessage(ChatColor.RED + "Player not found!");
                return;
            }

            switch (event.getCurrentItem().getType()) {
                case APPLE:
                    StatManager.modifyHealth(target, 20); // Set health to 10 hearts (20 half-hearts)
                    admin.sendMessage(ChatColor.GREEN + "Modified health for " + target.getName());
                    break;
                case SUGAR:
                    StatManager.modifySpeed(target, 0.2); // Set speed
                    admin.sendMessage(ChatColor.GREEN + "Modified speed for " + target.getName());
                    break;
                case SHIELD:
                    StatManager.modifyResistance(target, 2); // Set resistance
                    admin.sendMessage(ChatColor.GREEN + "Modified resistance for " + target.getName());
                    break;
                case DIAMOND_SWORD:
                    StatManager.modifyAttackDamage(target, 10); // Modify attack damage
                    admin.sendMessage(ChatColor.GREEN + "Modified attack damage for " + target.getName());
                    break;
                case FEATHER:
                    StatManager.modifyJumpHeight(target, 2); // Modify jump height
                    admin.sendMessage(ChatColor.GREEN + "Modified jump height for " + target.getName());
                    break;
            }
        }
    }
}
