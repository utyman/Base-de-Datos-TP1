package ubadb.components.bufferManager.bufferPool.replacementStrategies.beststrategy;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import ubadb.apps.bufferManagement.PageReference;
import ubadb.apps.bufferManagement.PageReferenceTrace;
import ubadb.common.Page;
import ubadb.common.PageId;
import ubadb.components.bufferManager.bufferPool.BufferFrame;
import ubadb.components.bufferManager.bufferPool.replacementStrategies.PageReplacementStrategy;
import ubadb.exceptions.PageReplacementStrategyException;

/**
 * 
 * @author Grupo9
 */
public class BestReplacementStrategy implements PageReplacementStrategy {
	private Map<PageId, Integer> count = new HashMap<PageId, Integer>();

	public BestReplacementStrategy(PageReferenceTrace trace) {
		for (PageReference page : trace.getPageReferences()) {
			PageId id = page.getPageId();
			Integer c = count.get(id);
			if (c == null) {
				c = 0;
			}
			c++;
			count.put(id, c);
		}
	}

	/* (non-Javadoc)
	 * @see ubadb.components.bufferManager.bufferPool.replacementStrategies.PageReplacementStrategy#findVictim(java.util.Collection)
	 */
	public BufferFrame findVictim(Collection<BufferFrame> bufferFrames)
			throws PageReplacementStrategyException {

		BufferFrame victim = null;
		int victimCount = Integer.MAX_VALUE;
		for (BufferFrame frame : bufferFrames) {
			if (frame.canBeReplaced()) {
				int bufferCount = count.get(frame.getPage().getPageId());
				if (bufferCount < victimCount) {
					victim = frame;
					victimCount = bufferCount;
				}
			}
		}
		if (victim == null) {
			throw new PageReplacementStrategyException(
					"No page can be removed from pool");
		} else {
			return victim;
		}
	}

	/* (non-Javadoc)
	 * @see ubadb.components.bufferManager.bufferPool.replacementStrategies.PageReplacementStrategy#createNewFrame(ubadb.common.Page)
	 */
	public BufferFrame createNewFrame(Page page) {
		return new BufferFrame(page);
	}

}