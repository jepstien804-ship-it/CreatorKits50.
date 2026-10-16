package com.example.creatorkits;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.Locale;

public final class CreatorKitsPlugin extends JavaPlugin {
    @Override public void onEnable() {
        saveDefaultConfig();
        ConfigurationSection kits = getConfig().getConfigurationSection("kits");
        getLogger().info("CreatorKits50 enabled with " + (kits == null ? 0 : kits.getKeys(false).size()) + " kits.");
    }

    @Override public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        ConfigurationSection kits = getConfig().getConfigurationSection("kits");
        if (kits == null) return true;
        if (args.length == 0 || args[0].equalsIgnoreCase("list")) {
            sender.sendMessage(color("&6Creator Kits &7(" + kits.getKeys(false).size() + ")"));
            sender.sendMessage(color("&7Use &f/kit <kitname>&7. IDs: " + String.join(", ", kits.getKeys(false))));
            return true;
        }
        if (!(sender instanceof Player player)) { sender.sendMessage("Only players can claim kits."); return true; }
        String id = args[0].toLowerCase(Locale.ROOT);
        ConfigurationSection kit = getConfig().getConfigurationSection("kits." + id);
        if (kit == null) { player.sendMessage(color("&cUnknown kit. Use /kit list.")); return true; }
        if (getConfig().getBoolean("settings.kit-permission", false) && !player.hasPermission("creatorkits.kit." + id)) {
            player.sendMessage(color("&cYou don't have permission to use this kit.")); return true;
        }
        int cooldown = getConfig().getInt("settings.cooldown-seconds", 30);
        String key = "cooldowns." + player.getUniqueId() + "." + id;
        long now = System.currentTimeMillis(), remaining = cooldown * 1000L - (now - getConfig().getLong(key, 0));
        if (remaining > 0) { player.sendMessage(color("&cWait " + ((remaining + 999) / 1000) + " seconds.")); return true; }
        for (String entry : kit.getStringList("items")) give(player, entry);
        for (String entry : kit.getStringList("armor")) give(player, entry);
        PotionEffectType type = PotionEffectType.getByName(kit.getString("effect", "SPEED").toUpperCase(Locale.ROOT));
        if (type != null) player.addPotionEffect(new PotionEffect(type, kit.getInt("effect-duration-seconds", 20) * 20,
                kit.getInt("effect-amplifier", 0)));
        getConfig().set(key, now); saveConfig();
        player.sendMessage(color("&aClaimed &6" + kit.getString("display-name", id) + "&a! &7" + kit.getString("description", "")));
        return true;
    }
    private void give(Player player, String entry) {
        String[] p = entry.split(":"); if (p.length != 2) return;
        Material material = Material.matchMaterial(p[0]); if (material == null || !material.isItem()) return;
        int amount; try { amount = Integer.parseInt(p[1]); } catch (NumberFormatException e) { amount = 1; }
        ItemStack item = new ItemStack(material, Math.max(1, Math.min(amount, material.getMaxStackSize())));
        player.getInventory().addItem(item).values().forEach(left -> player.getWorld().dropItemNaturally(player.getLocation(), left));
    }
    private String color(String s) { return ChatColor.translateAlternateColorCodes('&', s); }
}
