package ubadb.components.bufferManager.bufferPool.replacementStrategies.lru;

import java.util.Date;

import ubadb.common.Page;
import ubadb.components.bufferManager.bufferPool.BufferFrame;
import ubadb.exceptions.BufferFrameException;

public class LRUBufferFrame extends BufferFrame
{
	private Date lastUsed;
	
	public LRUBufferFrame(Page page)
	{
		super(page);
		lastUsed = new Date();
	}
	
	public Date getLastUsed()
	{
		return lastUsed;
	}
	
	public void pin()
	{
		super.pin();
		lastUsed = new Date();
	}

	public void unpin() throws BufferFrameException
	{
		super.unpin();
		lastUsed = new Date();
	}
}
