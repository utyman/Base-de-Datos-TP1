package ubadb.components.bufferManager.bufferPool.replacementStrategies.mru;

import java.util.Collection;
import java.util.Date;

import ubadb.common.Page;
import ubadb.components.bufferManager.bufferPool.BufferFrame;
import ubadb.components.bufferManager.bufferPool.replacementStrategies.PageReplacementStrategy;
import ubadb.components.bufferManager.bufferPool.replacementStrategies.mru.MRUBufferFrame;
import ubadb.exceptions.PageReplacementStrategyException;

public class MRUReplacementStrategy implements PageReplacementStrategy
{
	public BufferFrame findVictim(Collection<BufferFrame> bufferFrames) throws PageReplacementStrategyException
	{
		MRUBufferFrame victim = null;
		Date leastRecentlyUsedReplaceablePageDate = null;
		
		for(BufferFrame bufferFrame : bufferFrames)
		{
			MRUBufferFrame mruBufferFrame = (MRUBufferFrame) bufferFrame; //safe cast as we know all frames are of this type
			if(mruBufferFrame.canBeReplaced() && (leastRecentlyUsedReplaceablePageDate==null || mruBufferFrame.getLastUsed().after(leastRecentlyUsedReplaceablePageDate)))
			{
				victim = mruBufferFrame;
				leastRecentlyUsedReplaceablePageDate = mruBufferFrame.getLastUsed();
			}
		}
		
		if(victim == null)
			throw new PageReplacementStrategyException("No page can be removed from pool");
		else
			return victim;
	}

	public BufferFrame createNewFrame(Page page)
	{
		return new MRUBufferFrame(page);
	}

}
