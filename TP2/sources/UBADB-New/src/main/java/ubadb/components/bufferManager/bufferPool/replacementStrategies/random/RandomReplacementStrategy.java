package ubadb.components.bufferManager.bufferPool.replacementStrategies.random;


import java.util.Collection;
import java.util.Date;
import java.util.Random;

import ubadb.common.Page;
import ubadb.components.bufferManager.bufferPool.BufferFrame;
import ubadb.components.bufferManager.bufferPool.replacementStrategies.PageReplacementStrategy;
import ubadb.exceptions.PageReplacementStrategyException;

public class RandomReplacementStrategy implements PageReplacementStrategy
{
	private Random rand = new Random();
	
	public BufferFrame findVictim(Collection<BufferFrame> bufferFrames) throws PageReplacementStrategyException
	{
		BufferFrame victim = null;
		if (bufferFrames.isEmpty())
			throw new PageReplacementStrategyException("No frames to select from");
		
		int victimIndex = rand.nextInt(bufferFrames.size());
		
		for (BufferFrame frame : bufferFrames) {
			if (victimIndex == 1) {
				victim = frame;
				break;
			}
			victimIndex--;
		}
		
		return victim;
	}

	public BufferFrame createNewFrame(Page page)
	{
		return new BufferFrame(page);
	}

}
 