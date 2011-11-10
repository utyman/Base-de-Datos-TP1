package ubadb.apps.bufferManagement;

import ubadb.common.PageId;
import ubadb.common.TableId;
import ubadb.components.bufferManager.BufferManager;
import ubadb.components.bufferManager.BufferManagerImpl;
import ubadb.components.bufferManager.bufferPool.BufferPool;
import ubadb.components.bufferManager.bufferPool.SingleBufferPool;
import ubadb.components.bufferManager.bufferPool.replacementStrategies.PageReplacementStrategy;
import ubadb.components.bufferManager.bufferPool.replacementStrategies.beststrategy.BestReplacementStrategy;
import ubadb.components.bufferManager.bufferPool.replacementStrategies.fifo.FIFOReplacementStrategy;
import ubadb.components.diskManager.DiskManager;

public class BufferManagementEvaluator
{
	private static final int MAX_BUFFER_POOL_SIZE = 4;
	private static final int PAUSE_BETWEEN_REFERENCES	= 50;
	
	public static void main(String[] args) throws Exception
	{
		DiskManagerFaultCounterMock diskManagerFaultCounterMock = new DiskManagerFaultCounterMock();
		BufferManager bufferManager = createBufferManager(diskManagerFaultCounterMock);
		PageReferenceTrace trace = generateTrace();
		
		int requestsCount = 0;
		
		for(PageReference pageReference : trace.getPageReferences())
		{
			//Pause references to have different dates in LRU and MRU
			Thread.sleep(PAUSE_BETWEEN_REFERENCES);
			
			switch(pageReference.getType())
			{
				case REQUEST:
				{
					bufferManager.readPage(pageReference.getPageId());
					requestsCount++;
					break;
				}
				case RELEASE:
				{
					bufferManager.releasePage(pageReference.getPageId());
					break;
				}
			}
		}
		
		int faultsCount = diskManagerFaultCounterMock.getFaultsCount();
		System.out.println("Hit rate: " + calculateHitRate(faultsCount, requestsCount));
	}

	private static double calculateHitRate(int faults, int requests)
	{
		return (double)(requests - faults)/(double)requests;
	}

	private static PageReferenceTrace generateTrace()
	{
		PageReferenceTrace trace = new PageReferenceTrace();
		
		trace.addPageReference(new PageReference(new PageId(0, new TableId("a")), PageReferenceType.REQUEST));
		trace.addPageReference(new PageReference(new PageId(0, new TableId("a")), PageReferenceType.RELEASE));
		trace.addPageReference(new PageReference(new PageId(1, new TableId("a")), PageReferenceType.REQUEST));
		trace.addPageReference(new PageReference(new PageId(1, new TableId("a")), PageReferenceType.RELEASE));
		trace.addPageReference(new PageReference(new PageId(1, new TableId("a")), PageReferenceType.REQUEST));
		trace.addPageReference(new PageReference(new PageId(1, new TableId("a")), PageReferenceType.RELEASE));
		
		return trace;
	}

	private static BufferManager createBufferManager(DiskManager diskManager)
	{
		PageReplacementStrategy pageReplacementStrategy = new FIFOReplacementStrategy();
		BufferPool basicBufferPool = new SingleBufferPool(MAX_BUFFER_POOL_SIZE, pageReplacementStrategy);
		
		BufferManager bufferManager = new BufferManagerImpl(diskManager, basicBufferPool);
		return bufferManager;
	}

	private static BufferManager createBufferManagerForBestStrategy(DiskManager diskManager, BestReplacementStrategy pageReplacementStrategy)
	{
		BufferPool basicBufferPool = new SingleBufferPool(MAX_BUFFER_POOL_SIZE, pageReplacementStrategy);
		BufferManager bufferManager = new BufferManagerImpl(diskManager, basicBufferPool);
		return bufferManager;
	}

}
