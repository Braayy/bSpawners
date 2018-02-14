package braayy.spawners;

import java.util.List;
import java.util.function.UnaryOperator;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;

public class Config {
	
	private Config() {}
	
	private static Config instance = new Config();
	
	public static Config getInstance() {
		return instance;
	}
	
	private Spawners spawns = Spawners.getInstance();
	
	public ConfigurationSection getPicaretaSection() {
		return spawns.getConfig().getConfigurationSection("Itens.Picareta");
	}
	
	public List<String> getPrecoLore(final double preco) {
		List<String> list = spawns.getConfig().getStringList("Itens.Preco-Lore");
		if (preco != -1) {
			list.replaceAll(new UnaryOperator<String>() {
				@Override
				public String apply(String str) {
					return str.replace('&', '§').replace("{preco}", String.valueOf(preco));
				}
			});
		}
		return list;
	}
	
	public ConfigurationSection getMobSpawnSection(EntityType type) {
		return spawns.getConfig().getConfigurationSection("Itens.MobSpawn-" + type.name());
	}
	
	public boolean hasMobSpawnSection(EntityType type) {
		return spawns.getConfig().contains("Itens.MobSpawn-" + type.name());
	}
	
	public String getMsg(String name) {
		return getColored("Mensagens." + name);
	}
	
	public String getColored(String path) {
		return ChatColor.translateAlternateColorCodes('&', getString(path));
	}
	
	public String getString(String path) {
		return spawns.getConfig().getString(path);
	}
	
}