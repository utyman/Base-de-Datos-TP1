package ubadb.components.bufferManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import ubadb.common.Page;
import ubadb.common.PageId;
import ubadb.common.TableId;
import ubadb.exceptions.BufferManagerException;
import ubadb.mocks.BufferPoolMock;
import ubadb.mocks.DiskManagerMock;



public class BufferManagerImplTest
{
	private static final Page PAGE_0 = new Page(new PageId(0, new TableId("a.txt")),"aaa".getBytes());
	private static final Page PAGE_1 = new Page(new PageId(1, new TableId("b.txt")),"bbb".getBytes());
	private static final Page PAGE_2 = new Page(new PageId(2, new TableId("c.txt")),"ccc".getBytes());
	private BufferManagerImpl bufferManager;
	private DiskManagerMock diskManagerMock;
	private BufferPoolMock bufferPoolMock;
	private static final int BUFFER_POOL_MAX_SIZE = 2;
	
	@Before
	public void setUp()
	{
		diskManagerMock = new DiskManagerMock();
		bufferPoolMock = new BufferPoolMock(BUFFER_POOL_MAX_SIZE);
		bufferManager = new BufferManagerImpl(diskManagerMock, bufferPoolMock);
	}
	
	@Test
	public void testReadPageThatIsInPool() throws Exception
	{
		bufferPoolMock.addNewPage(PAGE_0);
		
		Page actualPage = bufferManager.readPage(PAGE_0.getPageId());
		
		assertEquals(PAGE_0, actualPage);
		assertEquals(1, bufferPoolMock.getBufferFrame(PAGE_0.getPageId()).getPinCount());
	}
	
	@Test
	public void testReadPageThatIsInDisk() throws Exception
	{
		diskManagerMock.setSinglePage(PAGE_1);
		Page actualPage = bufferManager.readPage(PAGE_1.getPageId());
		
		assertEquals(PAGE_1, actualPage);
		assertEquals(1, bufferPoolMock.getBufferFrame(PAGE_1.getPageId()).getPinCount());
	}

	@Test
	public void testAddNewPageToBufferPoolWithSpace() throws Exception
	{
		bufferManager.addNewPageToBufferPool(PAGE_0);
		assertEquals(1,bufferManager.getBufferPool().countPagesInPool());
	}
	
	@Test
	public void testAddNewPageWithoutSpace() throws Exception
	{
		bufferPoolMock.addNewPage(PAGE_0);
		bufferPoolMock.addNewPage(PAGE_1);
		bufferPoolMock.setVictim(PAGE_0.getPageId());
		
		bufferManager.addNewPageToBufferPool(PAGE_2);
		
		assertEquals(2, bufferManager.getBufferPool().countPagesInPool());
		assertTrue(bufferManager.getBufferPool().isPageInPool(PAGE_1.getPageId()));
		assertTrue(bufferManager.getBufferPool().isPageInPool(PAGE_2.getPageId()));
	}
	
	@Test
	public void testFlushNonDirtyPage() throws Exception
	{
		bufferPoolMock.addNewPage(PAGE_0);
		
		bufferManager.flushPage(PAGE_0.getPageId());
		
		assertTrue(diskManagerMock.getWrittenPages().isEmpty());
	}
	
	@Test
	public void testFlushDirtyPage() throws Exception
	{
		bufferPoolMock.addNewPage(PAGE_0);
		bufferPoolMock.getBufferFrame(PAGE_0.getPageId()).setDirty(true);
		
		bufferManager.flushPage(PAGE_0.getPageId());
		
		assertTrue(diskManagerMock.getWrittenPages().contains(PAGE_0));
		assertEquals(1, diskManagerMock.getWrittenPages().size());
	}
	
	@Test
	public void testReleasePageFoundInPool() throws Exception
	{
		bufferPoolMock.addNewPage(PAGE_0);
		bufferPoolMock.getBufferFrame(PAGE_0.getPageId()).pin();
		
		bufferManager.releasePage(PAGE_0.getPageId());

		assertEquals(0,bufferPoolMock.getBufferFrame(PAGE_0.getPageId()).getPinCount());
		assertTrue(bufferManager.getBufferPool().isPageInPool(PAGE_0.getPageId())); //Check that "releasePage" doesn't remove the page from pool (it just unpins it)
	}
	
	@Test(expected=BufferManagerException.class)
	public void testReleasePageNotFoundInPool() throws Exception
	{
		bufferManager.releasePage(PAGE_2.getPageId());
	}
}
