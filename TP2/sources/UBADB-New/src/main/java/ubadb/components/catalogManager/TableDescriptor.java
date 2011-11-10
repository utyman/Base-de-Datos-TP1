package ubadb.components.catalogManager;

import java.util.List;

import ubadb.common.TableId;

public class TableDescriptor
{
	private TableId tableId;
	private String tableName;
	private List<FieldDescriptor> fieldDescriptors;

	public TableDescriptor(TableId tableId, String tableName, List<FieldDescriptor> fieldDescriptors)
	{
		this.tableId = tableId;
		this.tableName = tableName;
		this.fieldDescriptors = fieldDescriptors;
	}

	public TableId getTableId()
	{
		return tableId;
	}

	public String getTableName()
	{
		return tableName;
	}

	public List<FieldDescriptor> getFieldDescriptors()
	{
		return fieldDescriptors;
	}
}
