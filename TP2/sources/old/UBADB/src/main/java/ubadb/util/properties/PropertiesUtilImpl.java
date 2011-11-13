package ubadb.util.properties;

import java.util.Properties;

import org.apache.log4j.Logger;

public class PropertiesUtilImpl implements PropertiesUtil
{
	private Properties properties;
	
	private static final Logger logger = Logger.getLogger(PropertiesUtilImpl.class);
	
	private PropertiesUtilImpl()
	{
		try
		{
			properties = new Properties();
			properties.load(ClassLoader.getSystemResourceAsStream(PropertiesConstants.CONFIG_PROPERTIES_FILE_NAME));
		}
		catch(Exception e)
		{
			logger.fatal("Properties file not found",e);
		}
	}
	
	public String get(String key)
	{
		return ((String)properties.get(key)).trim();
	}
	
//	public List<String> getMultiple(String key)
//	{
//		List<String> ret = new ArrayList<String>();
//		
//		for(String value : get(key).split(","))
//		{
//			ret.add(value.trim());
//		}
//		
//		return ret;
//	}
}
