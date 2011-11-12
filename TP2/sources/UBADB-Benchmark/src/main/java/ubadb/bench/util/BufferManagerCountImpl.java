package ubadb.bench.util;

import ubadb.common.Page;
import ubadb.common.PageId;
import ubadb.components.bufferManager.BufferManagerImpl;
import ubadb.components.bufferManager.bufferPool.BufferPool;
import ubadb.components.diskManager.DiskManager;
import ubadb.exceptions.BufferManagerException;

public class BufferManagerCountImpl extends BufferManagerImpl {

	private int readsCount = 0;

	public BufferManagerCountImpl(DiskManager diskManager, BufferPool bufferPool) {
		super(diskManager, bufferPool);
	}

	@Override
	public synchronized Page readPage(PageId id) throws BufferManagerException {
		readsCount++;
		return super.readPage(id);
	}

	public int getReadsCount() {
		return readsCount;
	}

}
