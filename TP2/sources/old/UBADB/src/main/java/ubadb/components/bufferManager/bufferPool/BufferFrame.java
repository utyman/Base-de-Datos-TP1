package ubadb.components.bufferManager.bufferPool;

import ubadb.common.Page;
import ubadb.exceptions.BufferFrameException;

public class BufferFrame
{
	private boolean dirty;
	private int pinCount;
	private Page page;

	public BufferFrame(Page page)
	{
		this.dirty = false;
		this.pinCount = 0;
		this.page = page;
	}

	public boolean isDirty()
	{
		return dirty;
	}

	public void setDirty(boolean dirty)
	{
		this.dirty = dirty;
	}
	
	public void pin()
	{
		pinCount++;
	}
	
	public void unpin() throws BufferFrameException
	{
		if(pinCount > 0)
			pinCount--;
		else
			throw new BufferFrameException("Cannot unpin a frame that has never been pinned");
	}
	
	public Page getPage()
	{
		return page;
	}
	
	public int getPinCount()
	{
		return pinCount;
	}

	public boolean canBeReplaced()
	{
		return pinCount == 0;
	}
}
