package ubadb.apps.bufferManagement;

import ubadb.common.PageId;

public class PageReference
{
	private PageId pageId;
	private PageReferenceType type;
	
	public PageReference(PageId pageId, PageReferenceType type)
	{
		this.pageId = pageId;
		this.type = type;
	}

	public PageId getPageId()
	{
		return pageId;
	}

	public PageReferenceType getType()
	{
		return type;
	}
	
	@Override
	public String toString()
	{
		return type.toString() + "(" + pageId.toString() + ")";
	}
}
