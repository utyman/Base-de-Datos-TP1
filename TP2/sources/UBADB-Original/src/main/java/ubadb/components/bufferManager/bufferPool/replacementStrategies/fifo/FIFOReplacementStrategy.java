package ubadb.components.bufferManager.bufferPool.replacementStrategies.fifo;

import java.util.Collection;
import java.util.Date;

import ubadb.common.Page;
import ubadb.components.bufferManager.bufferPool.BufferFrame;
import ubadb.components.bufferManager.bufferPool.replacementStrategies.PageReplacementStrategy;
import ubadb.exceptions.PageReplacementStrategyException;

public class FIFOReplacementStrategy implements PageReplacementStrategy
{
	public BufferFrame findVictim(Collection<BufferFrame> bufferFrames) throws PageReplacementStrategyException
	{
		FIFOBufferFrame victim = null;
		Date oldestReplaceablePageDate = null;
		
		for(BufferFrame bufferFrame : bufferFrames)
		{
			FIFOBufferFrame fifoBufferFrame = (FIFOBufferFrame) bufferFrame; //safe cast as we know all frames are of this type
			if(fifoBufferFrame.canBeReplaced() && (oldestReplaceablePageDate==null || fifoBufferFrame.getCreationDate().before(oldestReplaceablePageDate)))
			{
				victim = fifoBufferFrame;
				oldestReplaceablePageDate = fifoBufferFrame.getCreationDate();
			}
		}
		
		if(victim == null)
			throw new PageReplacementStrategyException("No page can be removed from pool");
		else
			return victim;
	}

	public BufferFrame createNewFrame(Page page)
	{
		return new FIFOBufferFrame(page);
	}
}
