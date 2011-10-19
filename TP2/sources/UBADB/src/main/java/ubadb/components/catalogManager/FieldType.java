package ubadb.components.catalogManager;

public enum FieldType
{
	//Only integers and char of length 100 are allowed
	INT(8),
	CHAR_100(100);
	
	private int size;
	
	private FieldType(int size)
	{
		this.size = size;
	}

	public int getSize()
	{
		return size;
	}
}
