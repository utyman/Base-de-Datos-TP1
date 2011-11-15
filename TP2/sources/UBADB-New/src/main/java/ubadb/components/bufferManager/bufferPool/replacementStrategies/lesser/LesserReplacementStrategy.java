package ubadb.components.bufferManager.bufferPool.replacementStrategies.lesser;

import java.util.Collection;

import ubadb.common.Page;
import ubadb.components.bufferManager.bufferPool.BufferFrame;
import ubadb.components.bufferManager.bufferPool.replacementStrategies.PageReplacementStrategy;
import ubadb.exceptions.PageReplacementStrategyException;

/**
 * PageReplacementStrategy which selects victims based on the minumum page id 
 * number. This is theorized to be good on linear page requests (filescans). 
 * 
 * @author Grupo9
 */
public class LesserReplacementStrategy implements PageReplacementStrategy {

	/* (non-Javadoc)
	 * @see ubadb.components.bufferManager.bufferPool.replacementStrategies.PageReplacementStrategy#findVictim(java.util.Collection)
	 */
	@Override
	public BufferFrame findVictim(Collection<BufferFrame> bufferFrames)
			throws PageReplacementStrategyException {
		BufferFrame min = null;
		int minNumber = Integer.MAX_VALUE;
		for(BufferFrame candidate : bufferFrames) {
			// If buffer cannot be replaced, ignore.
			if (!candidate.canBeReplaced()) {
				continue;
			}
			// Compare with minimum page number found so far...
			int candidateNumber = candidate.getPage().getPageId().getNumber();
			if (candidateNumber < minNumber) {
				minNumber = candidateNumber;
				min = candidate;
			}
		}
		// no victim found, exception!
		if (min == null) {
			throw new PageReplacementStrategyException("No victim found");
		}
		return min;
	}

	/* (non-Javadoc)
	 * @see ubadb.components.bufferManager.bufferPool.replacementStrategies.PageReplacementStrategy#createNewFrame(ubadb.common.Page)
	 */
	@Override
	public BufferFrame createNewFrame(Page page) {
		return new BufferFrame(page);
	}

}
