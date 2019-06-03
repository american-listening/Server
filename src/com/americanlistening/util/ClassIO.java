package com.americanlistening.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * Class for handling I/O of objects.
 * 
 * @author Ethan Vrhel
 * @since 1.0
 */
public class ClassIO {

	private ClassIO() {
		throw new UnsupportedOperationException("Cannot create a ClassPrinter");
	}

	/**
	 * Returns an object as a string.
	 * 
	 * @param obj The object to print.
	 * @return The string representation of the object.
	 */
	public static String asString(Object obj) {
		if (obj == null)
			return "null[]";
		Class<?> c = obj.getClass();
		StringBuilder builder = new StringBuilder();
		builder.append(c.getName()).append('[');
		Field[] fields = c.getFields();
		if (fields.length > 0) {
			builder.append(fields[0].getName()).append('=');
			try {
				builder.append(fields[0].get(obj));
			} catch (IllegalArgumentException | IllegalAccessException e) {
				builder.append("[error] ").append(e);
			}
			for (int i = 1; i < fields.length; i++) {
				builder.append(',').append(fields[i].getName()).append('=');
				try {
					builder.append(fields[i].get(obj));
				} catch (IllegalArgumentException | IllegalAccessException e) {
					builder.append("[error] ").append(e);
				}
			}
		}
		builder.append(']');
		return builder.toString();
	}

	/**
	 * Exports an object to a string. The object must implement the
	 * <code>java.io.Serializable</code> interface.
	 * 
	 * @param obj The object to export.
	 * @return The string representation of the object.
	 * @throws IOException When an I/O error occurs.
	 */
	public static String export(Object obj) throws IOException {
		ByteArrayOutputStream bos = null;
		ObjectOutputStream oos = null;
		String output = null;
		try {
			bos = new ByteArrayOutputStream();
			ObjectOutputStream os = new ObjectOutputStream(bos);
			os.writeObject(obj);
			output = new String(bos.toByteArray());
		} finally {
			if (bos != null)
				bos.close();
			if (oos != null)
				oos.close();
		}
		return output;
	}

	/**
	 * Loads an object from a string.
	 * 
	 * @param data The data to load from.
	 * @return The loaded object.
	 * @throws IOException            When an I/O error occurs.
	 * @throws ClassNotFoundException When the class contained in <code>data</code>
	 *                                does not exist.
	 */
	public static Object load(String data) throws IOException, ClassNotFoundException {
		ByteArrayInputStream sin = null;
		ObjectInputStream oin = null;
		Object ret = null;
		try {
			sin = new ByteArrayInputStream(data.getBytes());
			oin = new ObjectInputStream(sin);
			ret = oin.readObject();
		} finally {
			if (sin != null)
				sin.close();
			if (oin != null)
				oin.close();
		}
		return ret;
	}

	/**
	 * Loads an object from a string and casts it to the desired cast.
	 * 
	 * @param <T>  The type to cast to.
	 * @param data The data to load from.
	 * @param cast The class to cast to.
	 * @return The loaded object, or <code>null</code> if the object cannot be
	 *         casted.
	 * @throws IOException            When an I/O error occurs.
	 * @throws ClassNotFoundException When the class contained in <code>data</code>
	 *                                does not exist.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T load(String data, Class<T> cast) throws ClassNotFoundException, IOException {
		Object loaded = load(data);
		if (loaded.getClass() == cast)
			return (T) loaded;
		return null;
	}
}
