package braayy.spawners;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;

public class NMSUtils {
	
	public static String getNMSVersion() {
		return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
	}
	
	public static Class<?> getNMSClass(String name) {
		try {
			return Class.forName("net.minecraft.server." + getNMSVersion() + "." + name);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Class<?> getOBCClass(String name) {
		try {
			return Class.forName("org.bukkit.craftbukkit." + getNMSVersion() + "." + name);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Object invokeMethod(Object obj, Class<?>[] types, String name, Object... args) {
		try {
			if (obj instanceof Class) {
				Class<?> clazz = (Class<?>) obj;
				return clazz.getMethod(name, types).invoke(null, args);
			} else {
				Class<?> clazz = obj.getClass();
				return clazz.getMethod(name, types).invoke(obj, args);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Method getMethod(Class<?> clazz, String name, Class<?>... types) {
		try {
			return clazz.getMethod(name, types);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Constructor<?> getConstructor(Class<?> clazz) {
		try {
			return clazz.getConstructor();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Object invokeMethodWin(Method method, Object obj, Object... args) {
		try {
			return method.invoke(obj, args);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Object createInstance(Constructor<?> constructor) {
		try {
			return constructor.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Class<?>[] types(Class<?>... clazzes) { return clazzes; }
	
	public static Object getField(Object obj, String name) {
		try {
			Field field = obj.getClass().getDeclaredField(name);
			if (!field.isAccessible()) field.setAccessible(true);
			return field.get(obj);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Object getField(Class<?> clazz, String name) {
		try {
			Field field = clazz.getDeclaredField(name);
			if (!field.isAccessible()) field.setAccessible(true);
			return field.get(null);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Object newInstance(Class<?> clazz, Object... args) {
		try {
			Class<?>[] types = new Class<?>[args.length];
			for (int i = 0; i < types.length; i++) {
				types[i] = args[i].getClass();
			}
			return clazz.getConstructor(types).newInstance(args);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Object getHandle(Object item) {
		try {
			Field handleField = item.getClass().getDeclaredField("handle");
			handleField.setAccessible(true);
			return handleField.get(item);
		} catch (Exception e) {
			return null;
		}
	}
	
}