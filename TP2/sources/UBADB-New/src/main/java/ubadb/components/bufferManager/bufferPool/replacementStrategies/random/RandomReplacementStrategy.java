package ubadb.components.bufferManager.bufferPool.replacementStrategies.random;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import ubadb.common.Page;
import ubadb.components.bufferManager.bufferPool.BufferFrame;
import ubadb.components.bufferManager.bufferPool.replacementStrategies.PageReplacementStrategy;
import ubadb.exceptions.PageReplacementStrategyException;

public class RandomReplacementStrategy implements PageReplacementStrategy {
	private Random rand = new Random();

	public BufferFrame findVictim(Collection<BufferFrame> bufferFrames)
			throws PageReplacementStrategyException {
		List<BufferFrame> candidates = new ArrayList<BufferFrame>();
		for (BufferFrame frame : bufferFrames) {
			if (frame.canBeReplaced()) {
				candidates.add(frame);
			}
		}
		if (candidates.isEmpty())
			throw new PageReplacementStrategyException(
					"No frames to select from");

		int victimIndex = rand.nextInt(candidates.size());
		return candidates.get(victimIndex);
	}

	public BufferFrame createNewFrame(Page page) {
		return new BufferFrame(page);
	}

}
