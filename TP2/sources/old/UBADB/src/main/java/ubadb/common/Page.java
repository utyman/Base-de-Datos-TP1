package ubadb.common;

import java.util.Arrays;

public class Page
{
	private PageId pageId;
	private byte[] pageContents;
	
	public Page(PageId pageId, byte[] pageContents)
	{
		this.pageId = pageId;
		this.pageContents = pageContents;
	}

	public PageId getPageId()
	{
		return pageId;
	}

	public byte[] getPageContents()
	{
		return pageContents;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(pageContents);
		result = prime * result + ((pageId == null) ? 0 : pageId.hashCode());
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
		Page other = (Page) obj;
		if (!Arrays.equals(pageContents, other.pageContents))
			return false;
		if (pageId == null)
		{
			if (other.pageId != null)
				return false;
		}
		else if (!pageId.equals(other.pageId))
			return false;
		return true;
	}
}
