package ubadb.components.catalogManager;


/**
 * Serializable class that represents the catalog
 * 
 */
public class Catalog
{
	private TableCatalog tableCatalog;
	
	public String getTablePathByName(String name)
	{
		return tableCatalog.getPathByName(name);
	}
}
