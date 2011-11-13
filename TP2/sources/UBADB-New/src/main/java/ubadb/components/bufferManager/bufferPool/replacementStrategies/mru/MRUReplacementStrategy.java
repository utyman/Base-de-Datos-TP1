package ubadb.components.bufferManager.bufferPool.replacementStrategies.mru;

import java.util.Collection;
import java.util.Date;

import ubadb.common.Page;
import ubadb.components.bufferManager.bufferPool.BufferFrame;
import ubadb.components.bufferManager.bufferPool.replacementStrategies.PageReplacementStrategy;
import ubadb.components.bufferManager.bufferPool.util.DatedBufferFrame;
import ubadb.exceptions.PageReplacementStrategyException;

/**
 * PageReplacementStrategy based on a Most Recently Used (MRU) cache strategy.
 * We only take into account the 'pin' calls on BufferFrames.
 * 
 * @author Grupo9
 */
public final class MRUReplacementStrategy implements PageReplacementStrategy
{
	/* (non-Javadoc)
	 * @see ubadb.components.bufferManager.bufferPool.replacementStrategies.PageReplacementStrategy#findVictim(java.util.Collection)
	 */
	public BufferFrame findVictim(Collection<BufferFrame> bufferFrames) 
			throws PageReplacementStrategyException {
		DatedBufferFrame victim = null;
		Date victimDate = null;		
		for (BufferFrame bufferFrame : bufferFrames) {
			// safe cast as we know all frames are of this type
			DatedBufferFrame DatedBufferFrame = (DatedBufferFrame) bufferFrame;
			// If buffer cannot be replaced, ignore.
			if (!DatedBufferFrame.canBeReplaced()) {
				continue;
			}
			// Compare with least recently used so far...
			if (victimDate == null || DatedBufferFrame.getLastUsed().after(victimDate)) {
				victim = DatedBufferFrame;
				victimDate = DatedBufferFrame.getLastUsed();
			}
		}		
		if (victim == null) {
			throw new PageReplacementStrategyException("No page can be removed from pool");
		}
		return victim;
	}

	/* (non-Javadoc)
	 * @see ubadb.components.bufferManager.bufferPool.replacementStrategies.PageReplacementStrategy#createNewFrame(ubadb.common.Page)
	 */
	public BufferFrame createNewFrame(Page page) {
		return new DatedBufferFrame(page, false);
	}
}
