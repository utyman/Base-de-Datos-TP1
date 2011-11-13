package ubadb.components.bufferManager.bufferPool.replacementStrategies.lru;

import java.util.Collection;
import java.util.Date;

import ubadb.common.Page;
import ubadb.components.bufferManager.bufferPool.BufferFrame;
import ubadb.components.bufferManager.bufferPool.replacementStrategies.PageReplacementStrategy;
import ubadb.components.bufferManager.bufferPool.replacementStrategies.lru.LRUBufferFrame;
import ubadb.exceptions.PageReplacementStrategyException;

public class LRUReplacementStrategy implements PageReplacementStrategy
{
	public BufferFrame findVictim(Collection<BufferFrame> bufferFrames) throws PageReplacementStrategyException
	{
		LRUBufferFrame victim = null;
		Date leastRecentlyUsedReplaceablePageDate = null;
		
		for(BufferFrame bufferFrame : bufferFrames)
		{
			LRUBufferFrame lruBufferFrame = (LRUBufferFrame) bufferFrame; //safe cast as we know all frames are of this type
			if(lruBufferFrame.canBeReplaced() && (leastRecentlyUsedReplaceablePageDate==null || lruBufferFrame.getLastUsed().before(leastRecentlyUsedReplaceablePageDate)))
			{
				victim = lruBufferFrame;
				leastRecentlyUsedReplaceablePageDate = lruBufferFrame.getLastUsed();
			}
		}
		
		if(victim == null)
			throw new PageReplacementStrategyException("No page can be removed from pool");
		else
			return victim;
	}

	public BufferFrame createNewFrame(Page page)
	{
		return new LRUBufferFrame(page);
	}

}
