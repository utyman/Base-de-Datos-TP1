package ubadb.components.catalogManager;

import java.util.List;

public class TableCatalog
{
	private List<TableDescriptor> tableDescriptors;
	
	public TableCatalog(List<TableDescriptor> tableDescriptors)
	{
		this.tableDescriptors = tableDescriptors;
	}

	public String getPathByName(String name)
	{
		String ret = null;
		
		for(TableDescriptor tableDescriptor : tableDescriptors)
		{
			if(name.equals(tableDescriptor.getTableName()))
			{
//				ret = tableDescriptor.getRelativeFilePath();
				break;
			}
		}
		
		return ret;
	}

}
