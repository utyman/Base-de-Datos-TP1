package ubadb.components.catalogManager;

public class FieldDescriptor
{
	private String name;
	private FieldType type;
	
	public FieldDescriptor(String name, FieldType type)
	{
		this.name = name;
		this.type = type;
	}

	public String getName()
	{
		return name;
	}

	public FieldType getType()
	{
		return type;
	}
}
