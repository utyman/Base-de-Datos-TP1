package ubadb.components.bufferManager.bufferPool.replacementStrategies;

import java.util.Collection;

import ubadb.common.Page;
import ubadb.components.bufferManager.bufferPool.BufferFrame;
import ubadb.exceptions.PageReplacementStrategyException;

public interface PageReplacementStrategy
{
	/** If no victim is found, throw an exception */
	BufferFrame findVictim(Collection<BufferFrame> bufferFrames) throws PageReplacementStrategyException;
	
	BufferFrame createNewFrame(Page page);
}
