package ubadb.common;

public class PageId
{
	private int number;
	private TableId tableId;
	
	public PageId(int number, TableId tableId)
	{
		this.number = number;
		this.tableId = tableId;
	}
	
	public int getNumber()
	{
		return number;
	}
	public TableId getTableId()
	{
		return tableId;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + number;
		result = prime * result + ((tableId == null) ? 0 : tableId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PageId other = (PageId) obj;
		if (number != other.number)
			return false;
		if (tableId == null)
		{
			if (other.tableId != null)
				return false;
		}
		else if (!tableId.equals(other.tableId))
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "[" + tableId.toString() + ", " + number + "]";
	}
}
