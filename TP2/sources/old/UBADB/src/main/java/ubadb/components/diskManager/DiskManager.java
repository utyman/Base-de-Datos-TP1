package ubadb.components.diskManager;

import ubadb.common.Page;
import ubadb.common.PageId;
import ubadb.common.TableId;
import ubadb.exceptions.DiskManagerException;

public interface DiskManager
{
	Page readPage(PageId pageId) throws DiskManagerException;
	void writeExistingPage(Page page) throws DiskManagerException;
	Page createNewPage(TableId tableId, byte[] pageContents) throws DiskManagerException;
}