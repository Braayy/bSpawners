package braayy.spawners.listeners;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;

import braayy.spawners.Config;
import braayy.spawners.Spawners;
import braayy.spawners.SpawnersAPI;

public class Evento implements Listener {
	
	private SpawnersAPI api = SpawnersAPI.getInstance();
	private Spawners spawns = Spawners.getInstance();
	private Config config = Config.getInstance();
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void aoQuebrarBloco(BlockBreakEvent e) {
		if (!(e.getBlock().getState() instanceof CreatureSpawner)) return;
		if (!e.getBlock().hasMetadata("bSpawns")) return;
		if (!api.isPicareta(e.getPlayer().getItemInHand())) return;
		if (!e.getPlayer().hasPermission(config.getString("Permissao.Quebrar"))) {
			e.setCancelled(true);
			e.getPlayer().sendMessage(config.getMsg("Sem-Permissao"));
			return;
		}
		CreatureSpawner cs = (CreatureSpawner) e.getBlock().getState();
		
		if (e.getPlayer().getInventory().addItem(getToUse(api.getMobSpawn(cs.getSpawnedType()))).size() == 0) {
			e.getBlock().setType(Material.AIR);
			e.getPlayer().sendMessage(config.getMsg("Quebrou").replace("{tipo}", cs.getSpawnedType().name().replace('_', ' ')));
		} else {
			e.setCancelled(true);
			e.getPlayer().sendMessage("§cSeu inventario esta cheio!");
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void aoColocarBlock(BlockPlaceEvent e) {
		if (!(e.getBlock().getState() instanceof CreatureSpawner)) return;
		if (!api.isMobSpawn(e.getItemInHand())) return;
		if (!e.getPlayer().hasPermission(config.getString("Permissao.Colocar"))) {
			e.setCancelled(true);
			e.getPlayer().sendMessage(config.getMsg("Sem-Permissao"));
			return;
		}
		CreatureSpawner cs = (CreatureSpawner) e.getBlock().getState();
		EntityType entityType = api.getMobSpawnEntityType(e.getItemInHand());
		
		cs.setSpawnedType(entityType);
		cs.update();
		
		e.getBlock().setMetadata("bSpawns", new FixedMetadataValue(Spawners.getInstance(), true));
		
		e.getPlayer().sendMessage(config.getMsg("Colocou").replace("{tipo}", cs.getSpawnedType().name().replace('_', ' ')));
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void aoClicarInventario(InventoryClickEvent e) {
		if (e.getCurrentItem() == null) return;
		if (!(e.getWhoClicked() instanceof Player)) return;
		if (!e.getClickedInventory().getTitle().equals(config.getColored("Inventario.Nome"))) return;
		Player p = (Player) e.getWhoClicked();
		e.setCancelled(true);
		
		if (e.getCurrentItem().getType() == Material.DIAMOND_PICKAXE) {
			ConfigurationSection picareta = config.getPicaretaSection();
			double preco = picareta.getDouble("Preco");
			
			if (spawns.econ.getBalance(p) >= preco) {
				if (p.getInventory().addItem(getToUse(e.getCurrentItem())).size() == 0) {
					spawns.econ.withdrawPlayer(p, preco);
					p.sendMessage(config.getMsg("Comprou-Picareta").replace("{nome}", ChatColor.translateAlternateColorCodes('&', picareta.getString("Nome"))));
				} else {
					p.sendMessage(config.getMsg("Inventario-Cheio"));
				}
			} else {
				p.sendMessage(config.getMsg("Sem-Dinheiro"));
			}
			p.closeInventory();
		} else if (e.getCurrentItem().getType() == Material.MOB_SPAWNER) {
			ConfigurationSection mobspawn = config.getMobSpawnSection(api.getMobSpawnEntityType(e.getCurrentItem()));
			double preco = mobspawn.getDouble("Preco");
			
			if (spawns.econ.getBalance(p) >= preco) {
				if (p.getInventory().addItem(getToUse(e.getCurrentItem())).size() == 0) {
					spawns.econ.withdrawPlayer(p, preco);
					p.sendMessage(config.getMsg("Comprou-MobSpawner").replace("{nome}", ChatColor.translateAlternateColorCodes('&', mobspawn.getString("Nome"))));
				} else {
					p.sendMessage(config.getMsg("Inventario-Cheio"));
				}
			} else {
				p.sendMessage(config.getMsg("Sem-Dinheiro"));
			}
			p.closeInventory();
		}
	}
	
	private ItemStack getToUse(ItemStack item) {
		ItemStack clone = item.clone();
		ItemMeta meta = clone.getItemMeta();
		List<String> lore = meta.getLore();
		lore = lore.subList(0, lore.size() - config.getPrecoLore(-1).size());
		meta.setLore(lore);
		clone.setItemMeta(meta);
		return clone;
	}
	
}