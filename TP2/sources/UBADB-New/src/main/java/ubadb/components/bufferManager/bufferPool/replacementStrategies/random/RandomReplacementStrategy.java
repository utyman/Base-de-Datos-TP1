package ubadb.components.bufferManager.bufferPool.replacementStrategies.random;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import ubadb.common.Page;
import ubadb.components.bufferManager.bufferPool.BufferFrame;
import ubadb.components.bufferManager.bufferPool.replacementStrategies.PageReplacementStrategy;
import ubadb.exceptions.PageReplacementStrategyException;

/**
 * PageReplacementStrategy which randomly choose the victim out of all
 * possible candidates.
 * 
 * @author Grupo9
 */
public final class RandomReplacementStrategy implements PageReplacementStrategy {
	
	/**
	 * Random selector.
	 */
	private final Random rand = new Random();

	/* (non-Javadoc)
	 * @see ubadb.components.bufferManager.bufferPool.replacementStrategies.PageReplacementStrategy#findVictim(java.util.Collection)
	 */
	public BufferFrame findVictim(Collection<BufferFrame> bufferFrames)
			throws PageReplacementStrategyException {
		List<BufferFrame> candidates = new ArrayList<BufferFrame>();
		// Filter out unreplaceable frames.
		for (BufferFrame frame : bufferFrames) {
			if (frame.canBeReplaced()) {
				candidates.add(frame);
			}
		}
		// Check if there are any candidates to remove.
		if (candidates.isEmpty())
			throw new PageReplacementStrategyException(
					"No frames to select from");

		// Randomly choose the victim.
		int victimIndex = rand.nextInt(candidates.size());
		return candidates.get(victimIndex);
	}

	/* (non-Javadoc)
	 * @see ubadb.components.bufferManager.bufferPool.replacementStrategies.PageReplacementStrategy#createNewFrame(ubadb.common.Page)
	 */
	public BufferFrame createNewFrame(Page page) {
		return new BufferFrame(page);
	}
}
