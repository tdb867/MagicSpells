package com.nisovin.magicspells.shop;

import java.io.File;

import com.nisovin.magicspells.util.compat.EventUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import com.nisovin.magicspells.MagicSpells;
import com.nisovin.magicspells.Spell;
import com.nisovin.magicspells.Spellbook;
import com.nisovin.magicspells.events.MagicSpellsLoadedEvent;
import com.nisovin.magicspells.spells.command.ScrollSpell;

public class MagicSpellsShop extends JavaPlugin implements Listener {

	private boolean ignoreOtherPlugins;
	private boolean requireKnownSpell;
	private boolean requireTeachPerm;
	
	private String firstLine;
	private String strAlreadyKnown;
	private String strCantAfford;
	private String strCantLearn;
	private String strPurchased;
	
	private String firstLineScroll;
	private String scrollSpellName;
	private String strCantAffordScroll;
	private String strPurchasedScroll;
	
	private CurrencyHandler currency;
	
	@Override
	public void onEnable() {
		load();
		
		// Register events
		EventUtil.register(this, this);
	}
	
	public void load() {
		if (!new File(getDataFolder(), "config.yml").exists()) saveDefaultConfig();
		
		Configuration config = getConfig();
		
		ignoreOtherPlugins = config.getBoolean("ignore-other-plugins", false);
		requireKnownSpell = config.getBoolean("require-known-spell", true);
		requireTeachPerm = config.getBoolean("require-teach-perm", true);
		
		firstLine = config.getString("first-line", "[SPELL SHOP]");
		strAlreadyKnown = config.getString("str-already-known", "You already know that spell.");
		strCantAfford = config.getString("str-cant-afford", "You cannot afford that spell.");
		strCantLearn = config.getString("str-cant-learn", "You are not able to buy that spell.");
		strPurchased = config.getString("str-purchased", "You have purchased the %s spell.");
		
		firstLineScroll = config.getString("first-line-scroll", "[SCROLL SHOP]");
		scrollSpellName = config.getString("scroll-spell-name", "scroll");
		strCantAffordScroll = config.getString("str-cant-afford-scroll", "You cannot afford that scroll.");
		strPurchasedScroll = config.getString("str-purchased-scroll", "You have purchased a scroll for the %s spell.");
		
		currency = new CurrencyHandler(config);
		
		getLogger().info("MagicSpellsShop config loaded.");
	}
	
	@EventHandler
	public void onMagicSpellsLoad(MagicSpellsLoadedEvent event) {
		load();
	}
	
	@EventHandler(priority=EventPriority.MONITOR)
	public void onInteract(PlayerInteractEvent event) {
		if (!ignoreOtherPlugins && event.isCancelled()) return;
		
		// Check for right-click on sign
		if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
		Block block = event.getClickedBlock();
		if (block.getType() != Material.WALL_SIGN && block.getType() != Material.SIGN_POST) return;
		
		// Get shop sign
		Sign sign = (Sign)block.getState();
		String[] lines = sign.getLines();		
		if (lines[0].equals(firstLine)) {
			processSpellShopSign(event.getPlayer(), lines);
		} else if (lines[0].equals(firstLineScroll)) {
			processScrollShopSign(event.getPlayer(), lines);
		}		
	}
	
	private void processSpellShopSign(Player player, String[] lines) {
		// Get spell
		String spellName = lines[1];
		Spell spell = MagicSpells.getSpellByInGameName(spellName);
		if (spell == null) return;
		
		// Check if already known
		Spellbook spellbook = MagicSpells.getSpellbook(player);
		if (spellbook.hasSpell(spell)) {
			MagicSpells.sendMessage(MagicSpells.formatMessage(strAlreadyKnown, "%s", spellName), player, null);
			return;
		}
		
		// Get cost
		Cost cost = getCost(lines[2]);
		
		// Check for currency
		if (!currency.has(player, cost.amount, cost.currency)) {
			MagicSpells.sendMessage(MagicSpells.formatMessage(strCantAfford, "%s", spellName, "%c", cost + ""), player, null);
			return;
		}
		
		// Attempt to teach
		boolean taught = MagicSpells.teachSpell(player, spellName);
		if (!taught) {
			MagicSpells.sendMessage(MagicSpells.formatMessage(strCantLearn, "%s", spellName), player, null);
			return;
		}
		
		// Remove currency
		currency.remove(player, cost.amount, cost.currency);
		
		// Success!
		MagicSpells.sendMessage(MagicSpells.formatMessage(strPurchased, "%s", spellName, "%c", cost + ""), player, null);
	}
	
	private void processScrollShopSign(Player player, String[] lines) {
		// Get spell
		String spellName = lines[1];
		Spell spell = MagicSpells.getSpellByInGameName(spellName);
		if (spell == null) return;
		
		// Get uses
		if (!lines[2].matches("^[0-9]+( .+)?$")) return;
		int uses = Integer.parseInt(lines[2].split(" ")[0]);
		
		// Get cost
		Cost cost = getCost(lines[3]);
		
		// Check if can afford
		if (!currency.has(player, cost.amount, cost.currency)) {
			MagicSpells.sendMessage(MagicSpells.formatMessage(strCantAffordScroll, "%s", spellName, "%c", cost + "", "%u", uses + ""), player, null);
			return;
		}
		
		// Create scroll
		ScrollSpell scrollSpell = (ScrollSpell)MagicSpells.getSpellByInternalName(scrollSpellName);
		ItemStack scroll = scrollSpell.createScroll(spell, uses, null);
		
		// Remove currency
		currency.remove(player, cost.amount, cost.currency);
		
		// Give to player
		int slot = player.getInventory().firstEmpty();
		if (player.getItemInHand() == null) {
			player.setItemInHand(scroll);
			player.updateInventory();
		} else if (slot >= 0) {
			player.getInventory().setItem(slot, scroll);
			player.updateInventory();
		} else {
			player.getWorld().dropItem(player.getLocation(), scroll);
		}
		
		// Done!
		MagicSpells.sendMessage(MagicSpells.formatMessage(strPurchasedScroll, "%s", spellName, "%c", cost + "", "%u", uses + ""), player, null);
	}
	
	private Cost getCost(String line) {
		Cost cost = new Cost();
		if (!line.isEmpty()) {
			if (!line.contains(" ") && line.matches("^[0-9]+(\\.[0-9]+)?$")) {
				cost.amount = Double.parseDouble(line);
			} else if (line.contains(" ")) {
				String[] s = line.split(" ");
				if (s[0].matches("^[0-9]+(\\.[0-9]+)?$")) {
					cost.amount = Double.parseDouble(s[0]);
					cost.currency = s[1];
				}
			}
		}
		return cost;
	}
	
	@EventHandler
	public void onSignCreate(SignChangeEvent event) {
		if (event.isCancelled()) return;
		
		boolean isSpellShop;
		
		String lines[] = event.getLines();
		if (lines[0].equals(firstLine)) {
			isSpellShop = true;
		} else if (lines[0].equals(firstLineScroll)) {
			isSpellShop = false;
		} else {
			return;
		}
		
		// Check permission
		Player player = event.getPlayer();
		if (!player.hasPermission("magicspells.createsignshop")) {
			player.sendMessage(ChatColor.RED + "You do not have permission to do that.");
			event.setCancelled(true);
			return;
		}
		
		// Check for valid spell
		String spellName = lines[1];
		Spell spell = MagicSpells.getSpellByInGameName(spellName);
		if (spell == null) {
			player.sendMessage(ChatColor.RED + "A spell by that name does not exist.");
			event.setCancelled(true);
			return;
		}
		
		// Check permissions
		Spellbook spellbook = MagicSpells.getSpellbook(player);
		if (requireKnownSpell && !spellbook.hasSpell(spell)) {
			player.sendMessage(ChatColor.RED + "You do not have permission to do that.");
			event.setCancelled(true);
			return;
		}
		if (requireTeachPerm && !spellbook.canTeach(spell)) {
			player.sendMessage(ChatColor.RED + "You do not have permission to do that.");
			event.setCancelled(true);
			return;
		}
		
		// Get cost
		Cost cost = getCost(lines[isSpellShop ? 2 : 3]);
		
		// FIXME this should probably just use string.format
		String shopType = isSpellShop ? "Spell" : "Scroll";
		player.sendMessage(shopType + " shop created: " + spellName + (isSpellShop ? "" : '(' + lines[2] + ')') +
				" for " + cost.amount + ' ' + (currency.isValidCurrency(cost.currency) ? cost.currency : "currency") + '.');
		
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if (event.isCancelled()) return;
		
		Material mat = event.getBlock().getType();
		if (mat != Material.WALL_SIGN && mat != Material.SIGN_POST) return;
		
		Sign sign = (Sign)event.getBlock().getState();
		String line = sign.getLine(0);
		if (isShopSignFirstLine(line) && !event.getPlayer().hasPermission("magicspells.createsignshop")) {
			event.setCancelled(true);
		}
	}

	@Override
	public void onDisable() {
		
	}
	
	private class Cost {
		
		double amount = 0;
		String currency = null;
		
	}
	
	private boolean isShopSignFirstLine(String firstLine) {
		return firstLine.equals(this.firstLine) || firstLine.equals(this.firstLineScroll);
	}

}
