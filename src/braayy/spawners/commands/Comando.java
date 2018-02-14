package braayy.spawners.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import braayy.spawners.Config;
import braayy.spawners.SpawnersAPI;

public class Comando implements CommandExecutor {
	
	private Inventory inv;
	private Config config = Config.getInstance();
	private SpawnersAPI api = SpawnersAPI.getInstance();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (!(sender instanceof Player)) return true;
		Player p = (Player) sender;
		p.openInventory(inv);
		return false;
	}
	
	public Comando setup() {
		inv = Bukkit.createInventory(null, 9 * 6, config.getColored("Inventario.Nome"));
		
		inv.setItem(4, api.getPicareta());
		
		int i = 9;
		for (EntityType type : EntityType.values()) {
			if (type.isAlive() && type != EntityType.PLAYER && type != EntityType.ARMOR_STAND) {
				if (config.hasMobSpawnSection(type)) {
					inv.setItem(i++, api.getMobSpawn(type));
				}
			}
		}
		return this;
	}
	
}