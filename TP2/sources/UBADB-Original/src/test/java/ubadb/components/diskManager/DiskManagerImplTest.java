package ubadb.components.diskManager;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.io.RandomAccessFile;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import ubadb.common.Page;
import ubadb.common.PageId;
import ubadb.common.TableId;
import ubadb.exceptions.DiskManagerException;

public class DiskManagerImplTest
{
	private static final String		PREFIX	 	= "diskManagerTests/";
	private static final Integer	PAGE_SIZE	= 10;
	private DiskManagerImpl diskManager;
	
	@Before
	public void setUp()
	{
		diskManager = new DiskManagerImpl(PAGE_SIZE, PREFIX);
	}
	
	@Test
	public void testCalculateNewPageNumberZero()
	{
		assertEquals(0,diskManager.calculateNewPageNumber(0));
	}
	
	@Test
	public void testCalculateNewPageNumberNonZero()
	{
		assertEquals(5,diskManager.calculateNewPageNumber(50));
	}
	
	@Test
	public void testCalculatePageOffsetZero()
	{
		assertEquals(0, diskManager.calculatePageOffset(new PageId(0, new TableId(""))));
	}
	
	@Test
	public void testCalculatePageOffsetNonZero()
	{
		assertEquals(70, diskManager.calculatePageOffset(new PageId(7, new TableId(""))));
	}
	
	@Test
	public void testCheckPageIsExistingTrue() throws Exception
	{
		diskManager.checkPageIsExisting(0, 100);
	}
	
	@Test(expected=DiskManagerException.class)
	public void testCheckPageIsExistingFalse() throws Exception
	{
		diskManager.checkPageIsExisting(110, 100);
	}
	
	@Test
	public void testCheckPageSizeTrue() throws Exception
	{
		diskManager.checkPageSize("0123456789".getBytes());
	}
	
	@Test(expected=DiskManagerException.class)
	public void testCheckPageSizeFalse() throws Exception
	{
		diskManager.checkPageSize("0123".getBytes());
	}
	
	@Test
	public void testReadFirstPage() throws Exception
	{
		readPageAndAssert(0, "fileToRead.txt", "0123456789");
	}

	@Test
	public void testReadPageInTheMiddle() throws Exception
	{
		readPageAndAssert(1, "fileToRead.txt", "abcdefghij");
	}

	@Test
	public void testReadLastPage() throws Exception
	{
		readPageAndAssert(3, "fileToRead.txt", "klmnopqrst");
	}

	@Test(expected=DiskManagerException.class)
	public void testReadUnexistingPage() throws Exception
	{
		readPageAndAssert(99999, "fileToRead.txt", "0123456789");
	}

	@Test(expected=DiskManagerException.class)
	public void testReadUnexistingFile() throws Exception
	{
		readPageAndAssert(0, "UNEXISTING_FILE.txt", "0123456789");
	}
	
	@Test
	public void testWriteFirstPage() throws Exception
	{
		String relativePath = "fileToWrite.txt";
		String fileContents = "0123456789012345678901234567890123456789";
		String pageNewContents = "9999999999";
		int pageIdNumber = 0;
		
		writePageAndAssert(relativePath, fileContents, pageNewContents, pageIdNumber);
	}

	@Test
	public void testWritePageInTheMiddle() throws Exception
	{
		String relativePath = "fileToWrite.txt";
		String fileContents = "0123456789012345678901234567890123456789";
		String pageNewContents = "8888888888";
		int pageIdNumber = 2;
		
		writePageAndAssert(relativePath, fileContents, pageNewContents, pageIdNumber);
	}
	
	@Test
	public void testWriteLastPage() throws Exception
	{
		String relativePath = "fileToWrite.txt";
		String fileContents = "0123456789012345678901234567890123456789";
		String pageNewContents = "7777777777";
		int pageIdNumber = 3;
		
		writePageAndAssert(relativePath, fileContents, pageNewContents, pageIdNumber);
	}

	@Test(expected=DiskManagerException.class)
	public void testWriteUnexistingPage() throws Exception
	{
		String relativePath = "fileToWrite.txt";
		String fileContents = "0123456789012345678901234567890123456789";
		String pageNewContents = "6666666666";
		int pageIdNumber = 9999;
		
		writePageAndAssert(relativePath, fileContents, pageNewContents, pageIdNumber);
	}

	@Test(expected=DiskManagerException.class)
	public void testWriteUnexistingFile() throws Exception
	{
		diskManager.writeExistingPage(new Page(new PageId(0, new TableId("UNEXISTING_FILE.txt")),"0123456789".getBytes()));
	}
	
	@Test(expected=DiskManagerException.class)
	public void testWriteSmallerPage() throws Exception
	{
		String relativePath = "fileToWrite.txt";
		String fileContents = "0123456789012345678901234567890123456789";
		String pageNewContents = "111";
		int pageIdNumber = 1;
		
		writePageAndAssert(relativePath, fileContents, pageNewContents, pageIdNumber);
	}
	
	@Test(expected=DiskManagerException.class)
	public void testWriteBiggerPage() throws Exception
	{
		String relativePath = "fileToWrite.txt";
		String fileContents = "0123456789012345678901234567890123456789";
		String pageNewContents = "111111111111111111111111111";
		int pageIdNumber = 1;
		
		writePageAndAssert(relativePath, fileContents, pageNewContents, pageIdNumber);
	}
	
	@Test
	public void testCreateNewPage() throws Exception
	{
		String relativePath = "fileToWrite.txt";
		String fileContents = "0123456789012345678901234567890123456789";
		String pageContents = "6666666666";
		
		createPageAndAssert(relativePath,fileContents,pageContents);
	}
	
	@Test(expected=DiskManagerException.class)
	public void testCreateNewPageInUnexistingFile() throws Exception
	{
		diskManager.createNewPage(new TableId("UNEXISTING_FILE.txt"),"0123456789".getBytes());
	}
	
	@Test(expected=DiskManagerException.class)
	public void testCreateNewSmallerPage() throws Exception
	{
		String relativePath = "fileToWrite.txt";
		String fileContents = "0123456789012345678901234567890123456789";
		String pageContents = "666";
		
		createPageAndAssert(relativePath,fileContents,pageContents);
	}
	
	@Test(expected=DiskManagerException.class)
	public void testCreateNewBiggerPage() throws Exception
	{
		String relativePath = "fileToWrite.txt";
		String fileContents = "0123456789012345678901234567890123456789";
		String pageContents = "6666666666666666666666666666";
		
		createPageAndAssert(relativePath,fileContents,pageContents);
	}
	
	private void createPageAndAssert(String relativePath, String fileContents, String pageContents) throws Exception
	{
		clearFileContentsAndWrite(PREFIX + relativePath, fileContents);
		
		Page actualPage = diskManager.createNewPage(new TableId(relativePath), pageContents.getBytes());
		
		int newPageId = fileContents.length()/PAGE_SIZE;
		Page expectedPage = diskManager.readPage(new PageId(newPageId, new TableId(relativePath)));
		
		assertEquals(expectedPage, actualPage);
	}

	private void writePageAndAssert(String relativePath, String fileContents, String pageNewContents, int pageIdNumber) throws Exception
	{
		clearFileContentsAndWrite(PREFIX + relativePath, fileContents);
		
		byte[] expectedBytes = pageNewContents.getBytes();
		
		PageId pageId = new PageId(pageIdNumber,new TableId(relativePath));
		diskManager.writeExistingPage(new Page(pageId,expectedBytes));
		
		byte[] actualBytes =  readFromFile(PREFIX + relativePath,diskManager.calculatePageOffset(pageId));
		
		assertArrayEquals(expectedBytes, actualBytes);
	}

	private byte[] readFromFile(String fileName, long offset) throws Exception
	{
		byte[] bytes = new byte[PAGE_SIZE];
		RandomAccessFile fileToRead = new RandomAccessFile(new ClassPathResource(fileName).getFile(), "r");
		fileToRead.seek(offset);
		fileToRead.read(bytes);
		
		return bytes;
	}

	private void clearFileContentsAndWrite(String fileName, String contents) throws Exception
	{
		RandomAccessFile fileToWrite = new RandomAccessFile(new ClassPathResource(fileName).getFile(), "rw");
		fileToWrite.setLength(0); //clear file content
		fileToWrite.writeBytes(contents);
		fileToWrite.close();
	}
	
	private void readPageAndAssert(int id, String relativeFilePath, String pageContents) throws Exception
	{
		PageId pageId = new PageId(id, new TableId(relativeFilePath));
		Page expectedPage = new Page(pageId, pageContents.getBytes());
		
		Page actualPage = diskManager.readPage(pageId);
		
		assertEquals(expectedPage, actualPage);
	}
}
