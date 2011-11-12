package ubadb.bench;

import java.util.Properties;

import org.apache.log4j.Logger;

import ubadb.util.properties.PropertiesConstants;
import ubadb.util.properties.PropertiesUtil;

public class PropertiesUtilImpl implements PropertiesUtil {
	private Properties properties;

	private static final Logger logger = Logger
			.getLogger(PropertiesUtilImpl.class);

	public PropertiesUtilImpl() {
		this(PropertiesConstants.CONFIG_PROPERTIES_FILE_NAME);
	}

	public PropertiesUtilImpl(String filename) {
		try {
			properties = new Properties();
			properties.load(getClass().getClassLoader().getResourceAsStream(
					filename));
		} catch (Exception e) {
			logger.fatal("Properties file not found", e);
			throw new RuntimeException("Cannot load properties", e);
		}
	}

	public String get(String key) {
		return ((String) properties.get(key)).trim();
	}

	// public List<String> getMultiple(String key)
	// {
	// List<String> ret = new ArrayList<String>();
	//
	// for(String value : get(key).split(","))
	// {
	// ret.add(value.trim());
	// }
	//
	// return ret;
	// }
}
