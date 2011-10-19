package ubadb.components.catalogManager;

import org.apache.log4j.Logger;

import ubadb.components.diskManager.DiskManager;
import ubadb.util.properties.PropertiesConstants;
import ubadb.util.properties.PropertiesUtil;
import ubadb.util.xml.XmlUtil;

public class CatalogManager
{
	private Catalog catalog;
	private PropertiesUtil propertiesUtil;
	private XmlUtil xmlUtil;
	private DiskManager diskManager;
	
	private final static Logger logger = Logger.getLogger(CatalogManager.class);
	
	public CatalogManager(PropertiesUtil propertiesUtil, XmlUtil xmlUtil, DiskManager diskManager)
	{
		this.propertiesUtil = propertiesUtil;
		this.xmlUtil = xmlUtil;
		this.diskManager = diskManager;
		
		initializeCatalog();
	}

	/**
	 * No exception is thrown because it's called inside the constructor
	 */
	private void initializeCatalog()
	{
		try
		{
			catalog = (Catalog) xmlUtil.fromXml(propertiesUtil.get(PropertiesConstants.CATALOG_FILE_NAME));
		}
		catch(Exception e)
		{
			logger.fatal("Error while reading catalog from disk",e);
		}
	}

//	public Table getTableByName(String name)
//	{
//		String tablePath = catalog.getTablePathByName(name);
//		return null;
//	}
}
