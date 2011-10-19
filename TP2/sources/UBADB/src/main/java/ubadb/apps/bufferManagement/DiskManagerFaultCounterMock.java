package ubadb.apps.bufferManagement;

import java.util.ArrayList;
import java.util.List;

import ubadb.common.Page;
import ubadb.common.PageId;
import ubadb.common.TableId;
import ubadb.components.diskManager.DiskManager;
import ubadb.exceptions.DiskManagerException;

public class DiskManagerFaultCounterMock implements DiskManager 
{
	private List<PageId> pageIdsWithFault;
	
	public DiskManagerFaultCounterMock()
	{
		pageIdsWithFault = new ArrayList<PageId>();
	}
	
	public Page readPage(PageId pageId) throws DiskManagerException
	{
		pageIdsWithFault.add(pageId);
		
		return new Page(pageId, "aaa".getBytes());
	}

	public void writeExistingPage(Page page) throws DiskManagerException
	{
		throw new DiskManagerException("This method should not be called");
	}

	public Page createNewPage(TableId tableId, byte[] pageContents) throws DiskManagerException
	{
		throw new DiskManagerException("This method should not be called");
	}

	public int getFaultsCount()
	{
		return pageIdsWithFault.size();
	}
}
