package ubadb.mocks;

import java.util.HashSet;
import java.util.Set;

import ubadb.common.Page;
import ubadb.common.PageId;
import ubadb.common.TableId;
import ubadb.components.diskManager.DiskManager;
import ubadb.exceptions.DiskManagerException;

public class DiskManagerMock implements DiskManager
{
	private Page singlePage;
	private Set<Page> writtenPages;

	public DiskManagerMock()
	{
		writtenPages = new HashSet<Page>();
	}
	
	public void setSinglePage(Page page)
	{
		singlePage = page;
	}
	
	public Page readPage(PageId pageId) throws DiskManagerException
	{
		if(singlePage!=null && pageId.equals(singlePage.getPageId()))
			return singlePage;
		else
			throw new DiskManagerException("Error");
	}

	public void writeExistingPage(Page page) throws DiskManagerException
	{
		writtenPages.add(page);
	}

	public Page createNewPage(TableId tableId, byte[] pageContents) throws DiskManagerException
	{
		throw new DiskManagerException("Error");
	}
	
	public Set<Page> getWrittenPages()
	{
		return writtenPages;
	}
}
