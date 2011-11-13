package ubadb.components.bufferManager.bufferPool.replacementStrategies.count;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import ubadb.common.Page;
import ubadb.common.PageId;
import ubadb.components.bufferManager.bufferPool.BufferFrame;
import ubadb.components.bufferManager.bufferPool.replacementStrategies.PageReplacementStrategy;
import ubadb.exceptions.PageReplacementStrategyException;

public class CountReplacementStrategy implements PageReplacementStrategy {

	private Map<PageId, Integer> count = new HashMap<PageId, Integer>();

	public BufferFrame findVictim(Collection<BufferFrame> bufferFrames)
			throws PageReplacementStrategyException {
		BufferFrame victim = lessCounted(bufferFrames);
		if (victim == null)
			throw new PageReplacementStrategyException("No victim found");
		return victim;
	}

	private void pinBuffer(PageId id) {
		Integer c = count.get(id);
		if (c == null) {
			c = 0;
		}
		c++;
		count.put(id, c);
	}

	private BufferFrame lessCounted(Collection<BufferFrame> candidates) {
		BufferFrame result = null;
		int resultCount = Integer.MAX_VALUE;
		for (BufferFrame frame : candidates) {
			if (frame.canBeReplaced()) {
				PageId id = frame.getPage().getPageId();
				Integer c = count.get(id);
				if (c == null || c >= resultCount) {
					continue;
				}
				// if (c.intValue() == resultCount) {
				// PageId resultId = result.getPage().getPageId();
				// if (id.hashCode() > resultId.hashCode()) {
				// continue;
				// }
				// }
				resultCount = c;
				result = frame;
			}
		}
		return result;
	}

	public BufferFrame createNewFrame(Page page) {
		return new BufferFrame(page) {

			@Override
			public void pin() {
				super.pin();
				pinBuffer(getPage().getPageId());
			}

		};
	}

}
