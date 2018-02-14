package braayy.spawners;

/*
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.craftbukkit.v1_8_R1.inventory.CraftItemStack;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.minecraft.server.v1_8_R1.Blocks;
import net.minecraft.server.v1_8_R1.Item;
import net.minecraft.server.v1_8_R1.Items;
import net.minecraft.server.v1_8_R1.NBTTagCompound;
import net.minecraft.server.v1_8_R1.NBTTagList;

import static braayy.spawns.NMSUtils.*;
*/

public class SpawnsAPIBackup {
	/*
	private SpawnsAPIBackup() {}
	
	private static SpawnsAPIBackup instance = new SpawnsAPIBackup();
	
	public static SpawnsAPIBackup getInstance() {
		return instance;
	}
	
	private Config config = Config.getInstance();
	
	/**
	 * Retorna um objeto do tipo ItemStack com as info do parametro type.
	 * @param type a entidade a ser colocada no mobspawn.
	 * @return um objeto do tipo ItemStack com as info do parametro type, ou null caso algo de errado.
	 
	public ItemStack getMobSpawn(final EntityType type) {
		try {
			if (!type.isAlive()) return null;
			
			//CraftItemStack item = CraftItemStack.asNewCraftStack(Item.getItemOf(Blocks.MOB_SPAWNER));
			Object mobSpawner = getField(getNMSClass("Blocks"), "MOB_SPAWNER");
			Object itemArg = invokeMethod(getNMSClass("Item"), "getItemOf", mobSpawner);
			Object item = invokeMethod(getOBCClass("CraftItemStack"), "asNewCraftStack", itemArg);
			net.minecraft.server.v1_8_R1.ItemStack nmsItem = getHandle(item);
			NBTTagCompound nbt = nmsItem.getTag() == null ? new NBTTagCompound() : nmsItem.getTag();
			
			nbt.setString("EntityType", type.name());
			
			nmsItem.setTag(nbt);
			
			ItemMeta meta = item.getItemMeta();
			final ConfigurationSection sec = config.getMobSpawnSection(type);
			meta.setDisplayName(sec.getString("Nome").replace('&', '§'));
			List<String> lore = new ArrayList<>(sec.getStringList("Lore"));
			lore.replaceAll(new UnaryOperator<String>() {
				@Override
				public String apply(String str) {
					return str.replace('&', '§');
				}
			});
			lore.addAll(config.getPrecoLore(sec.getDouble("Preco")));
			meta.setLore(lore);
			item.setItemMeta(meta);
			
			return item;
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * Verifica se um item é um MobSpawn feito pelo meu plugin.
	 * @param item o item a ser verificado.
	 * @return se é um mobspawn feito pelo meu plugin.
	 
	public boolean isMobSpawn(ItemStack item) {
		try {
			CraftItemStack craftItem = CraftItemStack.asCraftCopy(item);
			net.minecraft.server.v1_8_R1.ItemStack nmsItem = getHandle(craftItem);
			if (nmsItem.getTag() == null || !nmsItem.getTag().hasKey("EntityType")) return false;
			
			return !nmsItem.getTag().getString("EntityType").isEmpty();
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * Pega o EntityType de um MobSpawn feito pelo meu plugin, aqui eu ja assumo que foi meu plugin que gerou esse mobspawn.
	 * @param item o item do MobSpawn.
	 * @return o EntityType caso tudo tenha dado certo.
	
	public EntityType getMobSpawnEntityType(ItemStack item) {
		try {
			CraftItemStack craftItem = CraftItemStack.asCraftCopy(item);
			net.minecraft.server.v1_8_R1.ItemStack nmsItem = getHandle(craftItem);
			NBTTagCompound nbt = nmsItem.getTag();
			return EntityType.valueOf(nbt.getString("EntityType"));
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * Retorna um brilhante sem encatamento
	 * @param item o item a ser brilhado
	 * @return o item brilhado
	 
	public ItemStack getGlowingItem(ItemStack item) {
		net.minecraft.server.v1_8_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
		NBTTagCompound nbt = nmsItem.getTag() == null ? new NBTTagCompound() : nmsItem.getTag();
		
		nbt.set("ench", new NBTTagList());
		nmsItem.setTag(nbt);
		return CraftItemStack.asCraftMirror(nmsItem);
	}
	
	/**
	 * Retorna a picareta para poder quebrar os spawners.
	 * @return a picareta para poder quebrar os spawners.
	 
	public ItemStack getPicareta() {
		CraftItemStack item = CraftItemStack.asNewCraftStack(Items.DIAMOND_PICKAXE);
		net.minecraft.server.v1_8_R1.ItemStack nmsItem = getHandle(item);
		NBTTagCompound nbt = nmsItem.getTag() == null ? new NBTTagCompound() : nmsItem.getTag();
		nbt.setBoolean("bSpawners", true);
		nmsItem.setTag(nbt);
		
		ItemMeta meta = item.getItemMeta();
		ConfigurationSection sec = config.getPicaretaSection();
		meta.setDisplayName(sec.getString("Nome").replace('&', '§'));
		List<String> lore = new ArrayList<>(sec.getStringList("Lore"));
		lore.replaceAll(new UnaryOperator<String>() {
			@Override
			public String apply(String str) {
				return str.replace('&', '§');
			}
		});
		lore.addAll(config.getPrecoLore(sec.getDouble("Preco")));
		meta.setLore(lore);
		item.setItemMeta(meta);
		
		return getGlowingItem(item);
	}
	
	/**
	 * Verifica se o item é a picareta que pode quebrar os spawners do meu plugin.
	 * @param item o item a ser verificado.
	 * @return se é a picareta para poder quebrar os spawners do meu plugin.
	 
	public boolean isPicareta(ItemStack item) {
		try {
			CraftItemStack craftItem = CraftItemStack.asCraftCopy(item);
			net.minecraft.server.v1_8_R1.ItemStack nmsItem = getHandle(craftItem);
			if (nmsItem.getTag() == null || !nmsItem.getTag().hasKey("bSpawners")) return false;
			
			return nmsItem.getTag().getBoolean("bSpawners");
		} catch (Exception e) {
			return false;
		}
	}
*/	
}