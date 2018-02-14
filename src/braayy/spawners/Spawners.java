package braayy.spawners;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import braayy.spawners.commands.Comando;
import braayy.spawners.listeners.Evento;
import net.milkbowl.vault.economy.Economy;

public class Spawners extends JavaPlugin {
	
	private static Spawners instance;
	
	public Economy econ;
	
	@Override
	public void onEnable() {
		instance = this;
		
		if (!hookEconomy()) {
			Bukkit.getConsoleSender().sendMessage("§c=============================================");
			Bukkit.getConsoleSender().sendMessage("§c                 bSpawns v" + getDescription().getVersion() + "                ");
			Bukkit.getConsoleSender().sendMessage("§cNao foi possivel acessar a Economia do Vault!");
			Bukkit.getConsoleSender().sendMessage("§c              Desativando Plugin!            ");
			Bukkit.getConsoleSender().sendMessage("§c=============================================");
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		saveDefaultConfig();
		criarLeiaMe();
		
		getServer().getPluginManager().registerEvents(new Evento(), this);
		getCommand("bspawns").setExecutor(new Comando().setup());
	}
	
	@Override
	public void onDisable() {
		HandlerList.unregisterAll(this);
	}
	
	private boolean hookEconomy() {
		if (!getServer().getPluginManager().isPluginEnabled("Vault")) return false;
		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null) return false;
		econ = rsp.getProvider();
		
		return econ != null;
	}
	
	private void criarLeiaMe() {
		try {
			File file = new File(getDataFolder(), "LeiaMe.txt");
			if (file.exists()) return;
			else file.createNewFile();
			
			PrintWriter w = new PrintWriter(new FileWriter(file));
			w.println("Antes de começar a editar a config.yml, leia o texto abaixo");
			w.println();
			w.println("Para adicionar mais Spawners no plugin, siga os passos abaixo:");
			w.println("Itens:");
			w.println("  MobSpawn-TIPO:");
			w.println("    Preco: 1000.0");
			w.println("    Nome: '&aSpawner de NOME'");
			w.println("    Lore:");
			w.println("      - '&7Esse MobSpawner é de NOME'");
			w.println();
			w.println("Talvez voce esteja com duvida, o que eu coloco no TIPO. Bom voce deve escolher um dos Mobs abaixo e colocar la");
			for (EntityType type : EntityType.values()) {
				if (type.isAlive() && type != EntityType.PLAYER && type != EntityType.ARMOR_STAND) {
					w.println(type.name());
				}
			}
			w.println();
			w.println("Era so isso mesmo, caso haja mais duvida entre em contato comigo: Braayy#8929(Discord)");
			w.flush();
			w.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Spawners getInstance() {
		return instance;
	}
	
}