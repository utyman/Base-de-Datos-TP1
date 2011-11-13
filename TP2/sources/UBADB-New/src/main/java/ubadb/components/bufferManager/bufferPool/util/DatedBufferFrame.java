package ubadb.components.bufferManager.bufferPool.util;

import java.util.Date;

import ubadb.common.Page;
import ubadb.components.bufferManager.bufferPool.BufferFrame;
import ubadb.exceptions.BufferFrameException;

/**
 * Dated BufferFrame, keeps track of the last time the BufferFrame was used.
 * 
 * @author Grupo9
 */
public class DatedBufferFrame extends BufferFrame {

	/**
	 * Update on unpin flag. 
	 */
	private final boolean updateOnUnpin;
	
	/**
	 * Last time the BufferFrame was used.
	 */
	private Date lastUsed;
	
	/**
	 * Creates a new DatedBufferFrame. 
	 * 
	 * @param page The page to hold.
	 */
	public DatedBufferFrame(Page page, boolean updateOnUnpin) {
		super(page);
		this.updateOnUnpin = updateOnUnpin;
		this.lastUsed = new Date();
	}
	
	/**
	 * Returns the last time the BufferFrame was used.
	 * 
	 * @return the last time the BufferFrame was used.
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

	/* (non-Javadoc)
	 * @see ubadb.components.bufferManager.bufferPool.BufferFrame#unpin()
	 */
	@Override
	public void unpin() throws BufferFrameException {
		super.unpin();
		// if flagged for update on unpin, update the used date.
		if (this.updateOnUnpin) {
			lastUsed = new Date();
		}
	}
}
