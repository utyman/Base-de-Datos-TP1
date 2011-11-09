package ubadb.components.bufferManager.bufferPool;

import java.util.HashMap;
import java.util.Map;

import ubadb.common.Page;
import ubadb.common.PageId;
import ubadb.components.bufferManager.bufferPool.replacementStrategies.PageReplacementStrategy;
import ubadb.exceptions.BufferPoolException;

/**
 * A single buffer pool shared by all tables 
 *
 */
public class SingleBufferPool implements BufferPool
{
	private Map<PageId, BufferFrame> framesMap;
	private PageReplacementStrategy pageReplacementStrategy;
	private final int maxBufferPoolSize;
	
	public SingleBufferPool(int maxBufferPoolSize, PageReplacementStrategy pageReplacementStrategy)
	{
		this.maxBufferPoolSize = maxBufferPoolSize;
		this.pageReplacementStrategy = pageReplacementStrategy;
		this.framesMap = new HashMap<PageId, BufferFrame>(maxBufferPoolSize);
	}

	public boolean isPageInPool(PageId pageId)
	{
		return framesMap.containsKey(pageId);
	}
	
	public BufferFrame getBufferFrame(PageId pageId) throws BufferPoolException
	{
		if(isPageInPool(pageId))
			return framesMap.get(pageId);
		else
			throw new BufferPoolException("The requested page is not in the pool");
	}
	
	public boolean hasSpace()
	{
		return countPagesInPool() < maxBufferPoolSize;
	}

	public BufferFrame addNewPage(Page page) throws BufferPoolException
	{
		if(!hasSpace())
			throw new BufferPoolException("No space in pool for new page");
		else if(isPageInPool(page.getPageId()))
			throw new BufferPoolException("Page already exists in the pool");
		else
		{
			//Add it to pool
			BufferFrame bufferFrame = pageReplacementStrategy.createNewFrame(page);
			framesMap.put(page.getPageId(),bufferFrame);
			
			return bufferFrame;
		}
	}

	public void removePage(PageId pageId) throws BufferPoolException
	{
		if(isPageInPool(pageId))
		{
			framesMap.remove(pageId);
		}
		else
			throw new BufferPoolException("Cannot remove an unexisting page");
	}

	public BufferFrame findVictim(PageId pageIdToBeAdded) throws BufferPoolException
	{
		try
		{
			return pageReplacementStrategy.findVictim(framesMap.values());
		}
		catch(Exception e)
		{
			throw new BufferPoolException("Cannot find a victim page for removal",e);
		}
	}

	public int countPagesInPool()
	{
		return framesMap.size();
	}
}
