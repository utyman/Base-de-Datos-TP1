package ubadb.components.bufferManager.bufferPool.replacementStrategies.beststrategy;

import java.util.Collection;
import java.util.List;

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
	private PageReferenceTrace trace;
	private int positionInTrace = -1;

	public BestReplacementStrategy(PageReferenceTrace trace) {
		this.trace = trace;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ubadb.components.bufferManager.bufferPool.replacementStrategies.
	 * PageReplacementStrategy#findVictim(java.util.Collection)
	 */
	public BufferFrame findVictim(Collection<BufferFrame> bufferFrames)
			throws PageReplacementStrategyException {

		BufferFrame victim = null;
		int victimTime = Integer.MIN_VALUE;

		for (BufferFrame bufferFrame : bufferFrames) {
			if (bufferFrame.canBeReplaced()) {
				int bufferTime = getFutureRequestTime(bufferFrame);
				if (bufferTime > victimTime) {
					victim = bufferFrame;
					victimTime = bufferTime;
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

	public int getFutureRequestTime(BufferFrame bufferFrame) {
		PageId pageId = bufferFrame.getPage().getPageId();
		List<PageReference> pageReferences = trace.getPageReferences();
		for (int position = positionInTrace + 1; position < pageReferences
				.size(); position++) {
			PageReference reference = pageReferences.get(position);
			if (pageId.equals(reference.getPageId())) {
				return position - positionInTrace;
			}
		}
		return Integer.MAX_VALUE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ubadb.components.bufferManager.bufferPool.replacementStrategies.
	 * PageReplacementStrategy#createNewFrame(ubadb.common.Page)
	 */
	public BufferFrame createNewFrame(Page page) {
		return new BufferFrame(page);
	}

	public void nextPositionInTrace() {
		positionInTrace++;
	}

}
