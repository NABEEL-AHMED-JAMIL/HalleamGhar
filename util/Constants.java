package hg.util;

import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;

public class Constants {
	
	private static ResourceBundle rb = ResourceBundle
			.getBundle("com.hg.setup");
	
	static {
		System.setProperties(convertResourceBundleToProperties(rb));
	}

	public static Properties convertResourceBundleToProperties(ResourceBundle resource) {
	    Properties properties = new Properties();

	    Enumeration<String> keys = resource.getKeys();
	    while (keys.hasMoreElements()) {
	      String key = keys.nextElement();
	      properties.put(key, resource.getString(key));
	    }

	    return properties;
	  }
}
