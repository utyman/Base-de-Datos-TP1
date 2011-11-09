package ubadb.components.bufferManager.bufferPool.replacementStrategies.fifo;

import java.util.Date;

import ubadb.common.Page;
import ubadb.components.bufferManager.bufferPool.BufferFrame;

public class FIFOBufferFrame extends BufferFrame
{
	private Date creationDate;
	
	public FIFOBufferFrame(Page page)
	{
		super(page);
		creationDate = new Date();
	}
	
	public Date getCreationDate()
	{
		return creationDate;
	}
}
