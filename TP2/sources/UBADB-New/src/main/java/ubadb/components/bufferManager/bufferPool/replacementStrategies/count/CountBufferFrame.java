package ubadb.components.bufferManager.bufferPool.replacementStrategies.count;

import ubadb.common.Page;
import ubadb.components.bufferManager.bufferPool.BufferFrame;

/**
 * Keeps track of the total amount of pins made to the buffer frame.
 * 
 * @author Grupo9
 */
public final class CountBufferFrame extends BufferFrame {
	
	/**
	 * Count strategy reference, used to count page pins.
	 */
	private CountReplacementStrategy strategy;
	
	/**
	 * Creates a new CountBufferFrame. 
	 * 
	 * @param strategy The CountReplacementStrategy reference.
	 * @param page The page to hold.
	 */
	public CountBufferFrame(CountReplacementStrategy strategy, Page page) {
		super(page);
		this.strategy = strategy;
	}
	
	/* (non-Javadoc)
	 * @see ubadb.components.bufferManager.bufferPool.BufferFrame#pin()
	 */
	@Override
	public void pin() {
		super.pin();
		strategy.countPin(getPage().getPageId());
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "[CountBufferFrame: " + this.getPage().getPageId() + "]";
	}
	
}
