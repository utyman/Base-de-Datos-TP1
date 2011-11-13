package ubadb.common;

public class TableId
{
	private String relativeFilePath;

	public TableId(String relativeFilePath)
	{
		this.relativeFilePath = relativeFilePath;
	}

	public String getRelativeFilePath()
	{
		return relativeFilePath;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((relativeFilePath == null) ? 0 : relativeFilePath.hashCode());
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
		TableId other = (TableId) obj;
		if (relativeFilePath == null)
		{
			if (other.relativeFilePath != null)
				return false;
		}
		else if (!relativeFilePath.equals(other.relativeFilePath))
			return false;
		return true;
	}
	
	@Override
	public String toString()
	{
		return relativeFilePath;
	}
}
