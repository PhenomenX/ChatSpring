package com.epam.chatspring.dao;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Class for using properties
 */
public class ResourceManager {
	private static Locale locale = Locale.ENGLISH;
	private static ResourceBundle rb = ResourceBundle.getBundle("properties.SQL_Queries", locale);

	public static String getRegExp(String key) {
		return rb.getString(key);
	}
}
