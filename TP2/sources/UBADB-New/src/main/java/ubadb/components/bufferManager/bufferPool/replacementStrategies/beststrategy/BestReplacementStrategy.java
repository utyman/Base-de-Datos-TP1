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
 * Optimum page replacement strategy. Used for benchmark comparison since it
 * needs to know the future page requests.
 * 
 * @see
 * "BELADY, L.A. A study of replacement algorithms for virtual storage computers. IBM Syst. J. 5,
2 (1966), 78-101."
 * 
 * @author Grupo9
 */
public final class BestReplacementStrategy implements PageReplacementStrategy {

	/**
	 * The trace of all (including future) page references.
	 */
	private final PageReferenceTrace trace;
	
	/**
	 * The next position in trace.
	 */
	private int nextPositionInTrace = 0;

	/**
	 * Creates a new BestReplacementStrategy, with the trace given.
	 * 
	 * @param trace the page reference trace, including future references.
	 */
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
			// Ignore unreplaceable pages.
			if (!bufferFrame.canBeReplaced()) {
				continue;
			}
			// Compare to get the page with the biggest time distance
			// till its next use.
			int bufferTime = getFutureRequestTime(bufferFrame.getPage());
			if (bufferTime > victimTime) {
				victim = bufferFrame;
				victimTime = bufferTime;
			}
		}
		// If no page to evict found, exception
		if (victim == null) {
			throw new PageReplacementStrategyException(
					"No page can be removed from pool");
		}
		return victim;
	}

	/**
	 * Returns the closest time when the specified page will be referenced.
	 * 
	 * @param page the page.
	 * @return the closest time when the specified page will be referenced.
	 */
	public int getFutureRequestTime(Page page) {
		PageId pageId = page.getPageId();
		List<PageReference> pageReferences = trace.getPageReferences();
		for (int position = nextPositionInTrace; position < pageReferences
				.size(); position++) {
			PageReference reference = pageReferences.get(position);
			if (pageId.equals(reference.getPageId())) {
				return position;
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

	/**
	 * Marks as one unit of time (page reference) passed.
	 */
	public void moveNextPositionInTrace() {
		nextPositionInTrace++;
	}

}
