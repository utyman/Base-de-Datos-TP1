package ubadb.components.bufferManager;

import ubadb.common.Page;
import ubadb.common.PageId;
import ubadb.common.TableId;
import ubadb.exceptions.BufferManagerException;

public interface BufferManager
{
	Page readPage(PageId pageId) throws BufferManagerException; 
	Page createNewPage(TableId tableId, byte[] pageContents) throws BufferManagerException;
	void releasePage(PageId pageId) throws BufferManagerException;
}