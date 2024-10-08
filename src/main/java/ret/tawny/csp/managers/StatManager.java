package ret.tawny.csp.managers;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class StatManager {

    public static void init() {

    }

    public static void modifyHealth(Player player, double health) {
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health);
        player.setHealth(Math.min(player.getHealth(), health)); // Prevent health overflow
    }

    public static void modifySpeed(Player player, double speed) {
        player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(speed);
    }

    public static void modifyResistance(Player player, int level) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, level - 1, false, false));
    }

    public static void modifyAttackDamage(Player player, double damage) {
        player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(damage);
    }

    public static void modifyJumpHeight(Player player, int level) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, level - 1, false, false));
    }
}
