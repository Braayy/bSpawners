package braayy.spawners;

import static braayy.spawners.NMSUtils.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SpawnersAPI {
	
	private SpawnersAPI() {}
	
	private static SpawnersAPI instance = new SpawnersAPI();
	
	public static SpawnersAPI getInstance() {
		return instance;
	}
	
	private Config config = Config.getInstance();
	
	// Créditos: WinX64#0123(Discord)
	private static final Class<?> CRAFT_ITEM_STACK = getOBCClass("inventory.CraftItemStack");
	private static final Class<?> ITEM_STACK = getNMSClass("ItemStack");
	private static final Class<?> NBT_TAG_COMPOUND = getNMSClass("NBTTagCompound");
	private static final Class<?> NBT_BASE = getNMSClass("NBTBase");
	private static final Class<?> NBT_TAG_LIST = getNMSClass("NBTTagList");

	private static final Method AS_NMS_COPY = getMethod(CRAFT_ITEM_STACK, "asNMSCopy", ItemStack.class);
	private static final Method GET_TAG = getMethod(ITEM_STACK, "getTag");
	private static final Method SET = getMethod(NBT_TAG_COMPOUND, "set", String.class, NBT_BASE);
	private static final Method SET_TAG = getMethod(ITEM_STACK, "setTag", NBT_TAG_COMPOUND);
	private static final Method AS_CRAFT_MIRROR = getMethod(CRAFT_ITEM_STACK, "asCraftMirror", ITEM_STACK);

	private static final Constructor<?> NBT_TAG_COMPOUND_CTOR = getConstructor(NBT_TAG_COMPOUND);
	private static final Constructor<?> NBT_TAG_LIST_CTOR = getConstructor(NBT_TAG_LIST);
	
	/**
	 * Retorna um brilhante sem encatamento
	 * @param item o item a ser brilhado
	 * @return o item brilhado
	 */
	public static ItemStack setGlowing(ItemStack itemStack) {
		Object nmsItemStack = invokeMethodWin(AS_NMS_COPY, null, itemStack);
		Object nbtTag = invokeMethodWin(GET_TAG, nmsItemStack);
		if (nbtTag == null) {
			nbtTag = createInstance(NBT_TAG_COMPOUND_CTOR);
		}
		invokeMethodWin(SET, nbtTag, "ench", createInstance(NBT_TAG_LIST_CTOR));
		invokeMethodWin(SET_TAG, nmsItemStack, nbtTag);
		return (ItemStack) invokeMethodWin(AS_CRAFT_MIRROR, null, nmsItemStack);
	}
	// Créditos: WinX64#0123(Discord)
	
	/**
	 * Retorna um objeto do tipo ItemStack com as info do parametro type.
	 * @param type a entidade a ser colocada no mobspawn.
	 * @return um objeto do tipo ItemStack com as info do parametro type, ou null caso algo de errado.
	 */
	public ItemStack getMobSpawn(final EntityType type) {
		try {
			if (!type.isAlive()) return null;
			
			Object mobSpawner = getField(getNMSClass("Blocks"), "MOB_SPAWNER");
			Object itemArg = invokeMethod(getNMSClass("Item"), types(getNMSClass("Block")), "getItemOf", mobSpawner);
			Object craftItem = invokeMethod(getOBCClass("inventory.CraftItemStack"), types(getNMSClass("Item")), "asNewCraftStack", itemArg);
			Object nmsItem = getHandle(craftItem);
			if (nmsItem == null) return null;
			
			Object nbt = newInstance(getNMSClass("NBTTagCompound"));
			invokeMethod(nbt, types(String.class, String.class), "setString", "EntityType", type.name());
			
			invokeMethod(nmsItem, types(getNMSClass("NBTTagCompound")), "setTag", nbt);
			
			ItemMeta meta = ((ItemStack) craftItem).getItemMeta();
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
			
			invokeMethod(craftItem, types(ItemMeta.class), "setItemMeta", meta);
			
			return (ItemStack) craftItem;
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * Verifica se um item é um MobSpawn feito pelo meu plugin.
	 * @param item o item a ser verificado.
	 * @return se é um mobspawn feito pelo meu plugin.
	 */
	public boolean isMobSpawn(ItemStack item) {
		try {
			Object craftItem = invokeMethod(getOBCClass("inventory.CraftItemStack"), types(ItemStack.class), "asCraftCopy", item);
			Object nmsItem = getHandle(craftItem);
			if (nmsItem == null) return false;
			Object nbt = invokeMethod(nmsItem, types(), "getTag");
			if (nbt == null || !((boolean) invokeMethod(nbt, types(String.class), "hasKey", "EntityType"))) return false;
			
			Object type = invokeMethod(nbt, types(String.class), "getString", "EntityType");
			return !((String) type).isEmpty();
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * Pega o EntityType de um MobSpawn feito pelo meu plugin, aqui eu ja assumo que foi meu plugin que gerou esse mobspawn.
	 * @param item o item do MobSpawn.
	 * @return o EntityType caso tudo tenha dado certo.
	 */
	public EntityType getMobSpawnEntityType(ItemStack item) {
		try {
			Object craftItem = invokeMethod(getOBCClass("inventory.CraftItemStack"), types(ItemStack.class),  "asCraftCopy", item);
			Object nmsItem = getHandle(craftItem);
			if (nmsItem == null) return null;
			Object nbt = invokeMethod(nmsItem, types(), "getTag");
			
			return EntityType.valueOf((String) invokeMethod(nbt, types(String.class), "getString", "EntityType"));
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * Retorna a picareta para poder quebrar os spawners.
	 * @return a picareta para poder quebrar os spawners.
	 */
	public ItemStack getPicareta() {
		Object itemArg = getField(getNMSClass("Items"), "DIAMOND_PICKAXE");
		Object craftItem = invokeMethod(getOBCClass("inventory.CraftItemStack"), types(getNMSClass("Item")), "asNewCraftStack", itemArg);
		Object nmsItem = getHandle(craftItem);
		if (nmsItem == null) return null;
		
		Object nbt = newInstance(getNMSClass("NBTTagCompound"));
		
		invokeMethod(nbt, types(String.class, boolean.class), "setBoolean", "bSpawners", true);
		
		invokeMethod(nmsItem, types(getNMSClass("NBTTagCompound")), "setTag", nbt);
		
		ItemMeta meta = ((ItemStack) craftItem).getItemMeta();
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
		
		invokeMethod(craftItem, types(ItemMeta.class), "setItemMeta", meta);
		
		return setGlowing((ItemStack) craftItem);
	}
	
	/**
	 * Verifica se o item é a picareta que pode quebrar os spawners do meu plugin.
	 * @param item o item a ser verificado.
	 * @return se é a picareta para poder quebrar os spawners do meu plugin.
	 */
	public boolean isPicareta(ItemStack item) {
		try {
			Object craftItem = invokeMethod(getOBCClass("inventory.CraftItemStack"), types(ItemStack.class), "asCraftCopy", item);
			Object nmsItem = getHandle(craftItem);
			if (nmsItem == null) return false;
			Object nbt = invokeMethod(nmsItem, types(), "getTag");
			if (nbt == null || !((boolean) invokeMethod(nbt, types(String.class), "hasKey", "bSpawners"))) return false;
			
			return (boolean) invokeMethod(nbt, types(String.class), "getBoolean", "bSpawners");
		} catch (Exception e) {
			return false;
		}
	}
	
}