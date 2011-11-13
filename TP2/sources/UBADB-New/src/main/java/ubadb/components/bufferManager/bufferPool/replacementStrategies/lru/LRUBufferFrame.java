package ubadb.components.bufferManager.bufferPool.replacementStrategies.lru;

import java.util.Date;

import ubadb.common.Page;
import ubadb.components.bufferManager.bufferPool.BufferFrame;

/**
 * LRU based BufferFrame, keeps track of the last time the BufferFrame was pinned.
 * 
 * @author Grupo9
 */
public final class LRUBufferFrame extends BufferFrame {
	
	/**
	 * Last time the BufferFrame was pinned or created.
	 */
	private Date lastUsed;
	
	/**
	 * Creates a new LRUBufferFrame. 
	 * 
	 * @param page The page to hold.
	 */
	public LRUBufferFrame(Page page) {
		super(page);
		this.lastUsed = new Date();
	}
	
	/**
	 * Returns the last time the BufferFrame was pinned or created.
	 * 
	 * @return the last time the BufferFrame was pinned or created.
	 */
	public Date getLastUsed() {
		return lastUsed;
	}
	
	/* (non-Javadoc)
	 * @see ubadb.components.bufferManager.bufferPool.BufferFrame#pin()
	 */
	public void pin() {
		super.pin();
		// Updates the used date.
		lastUsed = new Date();
	}	
}
