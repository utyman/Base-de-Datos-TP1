package ubadb.mocks;

import java.util.Collection;

import ubadb.common.Page;
import ubadb.components.bufferManager.bufferPool.BufferFrame;
import ubadb.components.bufferManager.bufferPool.replacementStrategies.PageReplacementStrategy;
import ubadb.exceptions.PageReplacementStrategyException;

public class PageReplacementStrategyMock implements PageReplacementStrategy
{
	private BufferFrame victimFrame;
	
	public void setVictimFrame(BufferFrame victimFrame)
	{
		this.victimFrame = victimFrame;
	}

	public BufferFrame findVictim(Collection<BufferFrame> bufferFrames) throws PageReplacementStrategyException
	{
		if(victimFrame == null)
			throw new IllegalStateException("Method should not be called");
		else
			return victimFrame;
	}

	public BufferFrame createNewFrame(Page page)
	{
		return new BufferFrame(page);
	}
}
